package com.skd.ascendantspawners.data;

import com.skd.ascendantspawners.AscSpObjects;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ASLootProvider extends LootTableProvider {
    private ASLootProvider(
        PackOutput output, Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders, CompletableFuture<Provider> registries
    ) {
        super(output, requiredTables, subProviders, registries);
    }

    public static ASLootProvider create(PackOutput output, CompletableFuture<Provider> registries) {
        return new ASLootProvider(
            output,
            Set.of(AscSpObjects.UNSTABLE_SPAWNER_LOOT),
            List.of(new SubProviderEntry(ASLootProvider.GameplayLoot::new, LootContextParamSets.CHEST)),
            registries
        );
    }

    public record GameplayLoot(Provider registries) implements LootTableSubProvider {
        public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
            output.accept(
                AscSpObjects.UNSTABLE_SPAWNER_LOOT,
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(48.0F, 64.0F)).add(LootItem.lootTableItem(Items.IRON_CHAIN)))
            );
        }
    }
}
