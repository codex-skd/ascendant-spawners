package com.skd.ascendantspawners.data;

import com.skd.commontoolkit.datagen.LegacyRecipeProvider;
import com.skd.ascendantspawners.AscendantSpawners;
import com.skd.ascendantspawners.modifiers.SpawnerModifier;
import com.skd.ascendantspawners.modifiers.StatModifier;
import com.skd.ascendantspawners.stats.SpawnerStat;
import com.skd.ascendantspawners.stats.SpawnerStats;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class ASRecipeProvider extends LegacyRecipeProvider {
    public ASRecipeProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, AscendantSpawners.MODID);
    }

    public String getName() {
        return "Ascendant Spawners Recipes";
    }

    protected void genRecipes(RecipeOutput recipeOutput, Provider registries) {
        this.addModifier("spawner_modifiers/min_delay", Items.SUGAR, intChange(SpawnerStats.MIN_DELAY, -10, 20, null));
        this.addModifier("spawner_modifiers/max_delay", Items.CLOCK, intChange(SpawnerStats.MAX_DELAY, -20, 20, null));
        this.addModifier("spawner_modifiers/spawn_count", Items.FERMENTED_SPIDER_EYE, intChange(SpawnerStats.SPAWN_COUNT, 2, null, 16));
        this.addModifier("spawner_modifiers/max_nearby", Items.GHAST_TEAR, intChange(SpawnerStats.MAX_NEARBY_ENTITIES, 2, null, 32));
        this.addModifier("spawner_modifiers/player_range", Items.PRISMARINE_CRYSTALS, intChange(SpawnerStats.REQ_PLAYER_RANGE, 4, null, 48));
        this.addModifier("spawner_modifiers/spawn_range", Items.PISTON, intChange(SpawnerStats.SPAWN_RANGE, 2, null, 32));
        this.addModifier("spawner_modifiers/initial_health", Items.POINTED_DRIPSTONE, floatChange(SpawnerStats.INITIAL_HEALTH, -0.05F, 0.2F, null));
        this.addModifier("spawner_modifiers/ignore_players", Items.NETHER_STAR, boolSet(SpawnerStats.IGNORE_PLAYERS, true));
        this.addModifier("spawner_modifiers/ignore_conditions", Items.CONDUIT, boolSet(SpawnerStats.IGNORE_CONDITIONS, true));
        this.addModifier("spawner_modifiers/redstone_control", Items.COMPARATOR, boolSet(SpawnerStats.REDSTONE_CONTROL, true));
        this.addModifier("spawner_modifiers/ignore_light", Items.SOUL_LANTERN, boolSet(SpawnerStats.IGNORE_LIGHT, true));
        this.addModifier("spawner_modifiers/no_ai", Items.CHORUS_FRUIT, boolSet(SpawnerStats.NO_AI, true));
        this.addModifier("spawner_modifiers/silent", ItemTags.WOOL, boolSet(SpawnerStats.SILENT, true));
        this.addModifier("spawner_modifiers/youthful", Items.GOLDEN_DANDELION, boolSet(SpawnerStats.YOUTHFUL, true));
        this.addModifier("spawner_modifiers/burning", Items.CAMPFIRE, boolSet(SpawnerStats.BURNING, true));
        this.addModifier("spawner_modifiers/echoing", Items.ECHO_SHARD, intChange(SpawnerStats.ECHOING, 1, null, 3));
        this.addInverse("spawner_modifiers/_inverse/min_delay", Items.SUGAR, intChange(SpawnerStats.MIN_DELAY, 10, null, 1600));
        this.addInverse("spawner_modifiers/_inverse/max_delay", Items.CLOCK, intChange(SpawnerStats.MAX_DELAY, 20, null, 1600));
        this.addInverse("spawner_modifiers/_inverse/spawn_count", Items.FERMENTED_SPIDER_EYE, intChange(SpawnerStats.SPAWN_COUNT, -2, 1, null));
        this.addInverse("spawner_modifiers/_inverse/max_nearby", Items.GHAST_TEAR, intChange(SpawnerStats.MAX_NEARBY_ENTITIES, -2, 1, null));
        this.addInverse("spawner_modifiers/_inverse/player_range", Items.PRISMARINE_CRYSTALS, intChange(SpawnerStats.REQ_PLAYER_RANGE, -4, 1, null));
        this.addInverse("spawner_modifiers/_inverse/spawn_range", Items.PISTON, intChange(SpawnerStats.SPAWN_RANGE, -2, 1, null));
        this.addInverse("spawner_modifiers/_inverse/initial_health", Items.POINTED_DRIPSTONE, floatChange(SpawnerStats.INITIAL_HEALTH, 0.05F, null, 1.0F));
        this.addInverse("spawner_modifiers/_inverse/ignore_players", Items.NETHER_STAR, boolSet(SpawnerStats.IGNORE_PLAYERS, false));
        this.addInverse("spawner_modifiers/_inverse/ignore_conditions", Items.CONDUIT, boolSet(SpawnerStats.IGNORE_CONDITIONS, false));
        this.addInverse("spawner_modifiers/_inverse/redstone_control", Items.COMPARATOR, boolSet(SpawnerStats.REDSTONE_CONTROL, false));
        this.addInverse("spawner_modifiers/_inverse/ignore_light", Items.SOUL_LANTERN, boolSet(SpawnerStats.IGNORE_LIGHT, false));
        this.addInverse("spawner_modifiers/_inverse/no_ai", Items.CHORUS_FRUIT, boolSet(SpawnerStats.NO_AI, false));
        this.addInverse("spawner_modifiers/_inverse/silent", ItemTags.WOOL, boolSet(SpawnerStats.SILENT, false));
        this.addInverse("spawner_modifiers/_inverse/youthful", Items.GOLDEN_DANDELION, boolSet(SpawnerStats.YOUTHFUL, false));
        this.addInverse("spawner_modifiers/_inverse/burning", Items.CAMPFIRE, boolSet(SpawnerStats.BURNING, false));
        this.addInverse("spawner_modifiers/_inverse/echoing", Items.ECHO_SHARD, intChange(SpawnerStats.ECHOING, -1, 0, null));
    }

    private void addModifier(String path, Object mainhand, StatModifier<?> change) {
        Ingredient main = this.createInput(false, new Object[] { mainhand }).get(0);
        SpawnerModifier recipe = new SpawnerModifier(main, Optional.empty(), false, List.of(change));
        this.emitRecipe(path, recipe);
    }

    private void addInverse(String path, Object mainhand, StatModifier<?> change) {
        Ingredient main = this.createInput(false, new Object[] { mainhand }).get(0);
        Ingredient offhand = this.createInput(false, new Object[] { Items.QUARTZ }).get(0);
        SpawnerModifier recipe = new SpawnerModifier(main, Optional.of(offhand), false, List.of(change));
        this.emitRecipe(path, recipe);
    }

    private void emitRecipe(String path, Recipe<?> recipe) {
        Identifier id = Identifier.fromNamespaceAndPath(AscendantSpawners.MODID, path);
        this.recipeOutput.accept(ResourceKey.create(Registries.RECIPE, id), recipe, null);
    }

    private static StatModifier<Integer> intChange(SpawnerStat<Integer> stat, int value, Integer min, Integer max) {
        return new StatModifier<>(stat, value, Optional.ofNullable(min), Optional.ofNullable(max), StatModifier.Mode.ADD);
    }

    private static StatModifier<Float> floatChange(SpawnerStat<Float> stat, float value, Float min, Float max) {
        return new StatModifier<>(stat, value, Optional.ofNullable(min), Optional.ofNullable(max), StatModifier.Mode.ADD);
    }

    private static StatModifier<Boolean> boolSet(SpawnerStat<Boolean> stat, boolean value) {
        return new StatModifier<>(stat, value, Optional.empty(), Optional.empty(), StatModifier.Mode.SET);
    }
}
