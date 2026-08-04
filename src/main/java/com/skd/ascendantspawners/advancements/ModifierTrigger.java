package com.skd.ascendantspawners.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.ascendantspawners.block.SpawnerTile;
import com.skd.ascendantspawners.block.SpawnerTile.SpawnerLogicExt;
import com.skd.ascendantspawners.modifiers.SpawnerModifier;
import com.skd.ascendantspawners.stats.SpawnerStat;
import com.skd.ascendantspawners.stats.SpawnerStats;
import java.util.Optional;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.MinMaxBounds.Ints;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;

public class ModifierTrigger extends SimpleCriterionTrigger<ModifierTrigger.TriggerInstance> {
    public Codec<ModifierTrigger.TriggerInstance> codec() {
        return ModifierTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, SpawnerTile tile, SpawnerModifier modif) {
        this.trigger(player, inst -> inst.test(player, tile, modif));
    }

    public record TriggerInstance(
        Optional<ContextAwarePredicate> player,
        Ints minDelay,
        Ints maxDelay,
        Ints spawnCount,
        Ints nearbyEnts,
        Ints playerRange,
        Ints spawnRange,
        Optional<Boolean> ignorePlayers,
        Optional<Boolean> ignoreConditions,
        Optional<Boolean> redstone,
        Optional<Boolean> ignoreLight,
        Optional<Boolean> noAI,
        Optional<Boolean> silent,
        Optional<Boolean> youthful
    ) implements SimpleInstance {
        public static final Codec<ModifierTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(ModifierTrigger.TriggerInstance::player),
                Ints.CODEC.optionalFieldOf("min_delay", Ints.ANY).forGetter(ModifierTrigger.TriggerInstance::minDelay),
                Ints.CODEC.optionalFieldOf("max_delay", Ints.ANY).forGetter(ModifierTrigger.TriggerInstance::maxDelay),
                Ints.CODEC.optionalFieldOf("spawn_count", Ints.ANY).forGetter(ModifierTrigger.TriggerInstance::spawnCount),
                Ints.CODEC.optionalFieldOf("max_nearby_entities", Ints.ANY).forGetter(ModifierTrigger.TriggerInstance::nearbyEnts),
                Ints.CODEC.optionalFieldOf("req_player_range", Ints.ANY).forGetter(ModifierTrigger.TriggerInstance::playerRange),
                Ints.CODEC.optionalFieldOf("spawn_range", Ints.ANY).forGetter(ModifierTrigger.TriggerInstance::spawnRange),
                Codec.BOOL.optionalFieldOf("ignore_players").forGetter(ModifierTrigger.TriggerInstance::ignorePlayers),
                Codec.BOOL.optionalFieldOf("ignore_conditions").forGetter(ModifierTrigger.TriggerInstance::ignoreConditions),
                Codec.BOOL.optionalFieldOf("redstone_control").forGetter(ModifierTrigger.TriggerInstance::redstone),
                Codec.BOOL.optionalFieldOf("ignore_light").forGetter(ModifierTrigger.TriggerInstance::ignoreLight),
                Codec.BOOL.optionalFieldOf("no_ai").forGetter(ModifierTrigger.TriggerInstance::noAI),
                Codec.BOOL.optionalFieldOf("silent").forGetter(ModifierTrigger.TriggerInstance::silent),
                Codec.BOOL.optionalFieldOf("youthful").forGetter(ModifierTrigger.TriggerInstance::youthful)
            )
            .apply(inst, ModifierTrigger.TriggerInstance::new)
        );

        public boolean test(ServerPlayer player, SpawnerTile tile, SpawnerModifier modif) {
            SpawnerLogicExt logic = (SpawnerLogicExt) tile.spawner;
            if (!this.minDelay.matches(logic.minSpawnDelay)) {
                return false;
            }
            else if (!this.maxDelay.matches(logic.maxSpawnDelay)) {
                return false;
            }
            else if (!this.spawnCount.matches(logic.spawnCount)) {
                return false;
            }
            else if (!this.nearbyEnts.matches(logic.maxNearbyEntities)) {
                return false;
            }
            else if (!this.playerRange.matches(logic.requiredPlayerRange)) {
                return false;
            }
            else if (!this.spawnRange.matches(logic.spawnRange)) {
                return false;
            }
            else if (!this.check(tile, SpawnerStats.IGNORE_PLAYERS, this.ignorePlayers)) {
                return false;
            }
            else if (!this.check(tile, SpawnerStats.IGNORE_CONDITIONS, this.ignoreConditions)) {
                return false;
            }
            else if (!this.check(tile, SpawnerStats.REDSTONE_CONTROL, this.redstone)) {
                return false;
            }
            else if (!this.check(tile, SpawnerStats.IGNORE_LIGHT, this.ignoreLight)) {
                return false;
            }
            else if (!this.check(tile, SpawnerStats.NO_AI, this.noAI)) {
                return false;
            }
            else {
                return !this.check(tile, SpawnerStats.SILENT, this.silent) ? false : this.check(tile, SpawnerStats.YOUTHFUL, this.youthful);
            }
        }

        private <T> boolean check(SpawnerTile tile, SpawnerStat<T> stat, Optional<T> target) {
            return target.isEmpty() || target.get() == stat.getValue(tile);
        }
    }
}
