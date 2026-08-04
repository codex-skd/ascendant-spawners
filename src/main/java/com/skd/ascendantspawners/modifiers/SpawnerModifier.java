package com.skd.ascendantspawners.modifiers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.ascendantspawners.AscSpObjects;
import com.skd.ascendantspawners.block.SpawnerTile;
import com.skd.ascendantspawners.compat.SpawnerRecipeCache;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;

public record SpawnerModifier(Ingredient mainHand, Optional<Ingredient> offHand, boolean consumesOffhand, List<StatModifier<?>> statModifiers)
    implements Recipe<RecipeInput> {
    public static final MapCodec<SpawnerModifier> CODEC = RecordCodecBuilder.mapCodec(
        inst -> inst.group(
                Ingredient.CODEC.fieldOf("mainhand").forGetter(SpawnerModifier::mainHand),
                Ingredient.CODEC.optionalFieldOf("offhand").forGetter(SpawnerModifier::offHand),
                Codec.BOOL.optionalFieldOf("consumes_offhand", false).forGetter(SpawnerModifier::consumesOffhand),
                StatModifier.CODEC.listOf().fieldOf("stat_changes").forGetter(SpawnerModifier::statModifiers)
            )
            .apply(inst, SpawnerModifier::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SpawnerModifier> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC,
        SpawnerModifier::mainHand,
        Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC,
        SpawnerModifier::offHand,
        ByteBufCodecs.BOOL,
        SpawnerModifier::consumesOffhand,
        StatModifier.STREAM_CODEC.apply(ByteBufCodecs.list()),
        SpawnerModifier::statModifiers,
        SpawnerModifier::new
    );
    public static final RecipeSerializer<SpawnerModifier> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    public SpawnerModifier(Ingredient mainHand, Optional<Ingredient> offHand, boolean consumesOffhand, List<StatModifier<?>> statModifiers) {
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.consumesOffhand = consumesOffhand;
        this.statModifiers = ImmutableList.copyOf(statModifiers);
    }

    public boolean matches(SpawnerTile tile, ItemStack mainhand, ItemStack offhand) {
        if (this.mainHand.test(mainhand)) {
            return this.offHand.isEmpty() ? true : this.offHand.get().test(offhand);
        }
        return false;
    }

    public boolean apply(SpawnerTile tile) {
        boolean success = false;
        for (StatModifier<?> m : this.statModifiers) {
            if (m.apply(tile)) {
                success = true;
                tile.setChanged();
            }
        }
        return success;
    }

    @Override
    public boolean consumesOffhand() {
        return this.offHand.isPresent() && this.consumesOffhand;
    }

    @Deprecated
    @Override
    public boolean matches(RecipeInput container, Level level) {
        return false;
    }

    @Deprecated
    @Override
    public ItemStack assemble(RecipeInput container) {
        return ItemStack.EMPTY;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of();
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return AscSpObjects.SPAWNER_MODIFIER.get();
    }

    @Nullable
    public static SpawnerModifier findMatch(SpawnerTile tile, ItemStack mainhand, ItemStack offhand) {
        Stream<SpawnerModifier> modifiers = SpawnerRecipeCache.getRecipes().stream();
        if (tile.getLevel() instanceof ServerLevel sl) {
            modifiers = sl.recipeAccess().recipeMap().byType(AscSpObjects.SPAWNER_MODIFIER.get()).stream().map(RecipeHolder::value);
        }
        return modifiers.sorted((r1, r2) -> r1.offHand.isEmpty() ? (r2.offHand.isEmpty() ? 0 : 1) : -1)
            .filter(r -> r.matches(tile, mainhand, offhand))
            .findFirst()
            .orElse(null);
    }
}
