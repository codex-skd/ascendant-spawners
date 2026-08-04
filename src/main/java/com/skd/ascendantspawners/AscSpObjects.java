package com.skd.ascendantspawners;

import com.skd.commontoolkit.registry.DeferredHelper;
import com.mojang.serialization.Codec;
import com.skd.ascendantspawners.advancements.ModifierTrigger;
import com.skd.ascendantspawners.advancements.SpawnEggItemPredicate;
import com.skd.ascendantspawners.modifiers.SpawnerModifier;
import com.skd.ascendantspawners.stats.SpawnerStats;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AscSpObjects {
    private static final DeferredHelper HELPER = DeferredHelper.create(AscendantSpawners.MODID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<SpawnerModifier>> SPAWNER_MODIFIER = HELPER.recipe("spawner_modifier", () -> new RecipeType<SpawnerModifier>() {});
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SpawnerModifier>> SPAWNER_MODIFIER_SERIALIZER = HELPER.recipeSerializer("spawner_modifier", () -> SpawnerModifier.SERIALIZER);
    public static final DataComponentType<Float> CAPTURING = HELPER.enchantmentEffect("capturing", builder -> builder.persistent(Codec.floatRange(0.001F, 1.0F)));
    public static final ResourceKey<Enchantment> CAPTURING_ENCH = ResourceKey.create(Registries.ENCHANTMENT, AscendantSpawners.loc("capturing"));
    public static final ModifierTrigger MODIFIER_TRIGGER = HELPER.criteriaTrigger("spawner_modifier", new ModifierTrigger());
    public static final TagKey<EntityType<?>> BLACKLISTED_FROM_SPAWNERS = TagKey.create(Registries.ENTITY_TYPE, AscendantSpawners.loc("blacklisted_from_spawners"));
    public static final ResourceKey<LootTable> UNSTABLE_SPAWNER_LOOT = ResourceKey.create(Registries.LOOT_TABLE, AscendantSpawners.loc("gameplay/unstable_spawner"));

    public static void bootstrap(IEventBus bus) {
        bus.register(HELPER);
        SpawnerStats.bootstrap();
    }

    static {
        HELPER.componentPredicate("spawn_egg", SpawnEggItemPredicate.CODEC.codec());
    }
}
