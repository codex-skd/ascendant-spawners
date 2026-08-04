package com.skd.ascendantspawners.stats;

import com.mojang.serialization.Codec;
import com.skd.ascendantspawners.AscendantSpawners;
import com.skd.ascendantspawners.block.SpawnerTile;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public interface SpawnerStat<T> {
    Codec<T> valueCodec();

    StreamCodec<? super RegistryFriendlyByteBuf, T> valueStreamCodec();

    T getValue(SpawnerTile spawner);

    void setValue(SpawnerTile spawner, T value);

    Component getTooltip(SpawnerTile spawner);

    boolean applyModifier(SpawnerTile spawner, T value, Optional<T> min, Optional<T> max);

    default Identifier getId() {
        return SpawnerStats.REGISTRY.getKey(this);
    }

    default MutableComponent name() {
        return Component.translatable(this.getId().toLanguageKey("stat"));
    }

    default MutableComponent desc() {
        return Component.translatable(this.getId().toLanguageKey("stat", "desc"));
    }

    default String formatValue(T value) {
        return value.toString();
    }

    static Component createTooltip(SpawnerStat<?> stat, MutableComponent value) {
        return AscendantSpawners.lang("misc", "value_concat", stat.name(), value.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.GREEN);
    }

    static Component createTooltip(SpawnerStat<?> stat, String value) {
        return createTooltip(stat, Component.literal(value));
    }
}
