package com.skd.ascendantspawners.stats;

import com.mojang.serialization.Codec;
import com.skd.ascendantspawners.block.SpawnerTile;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class LevelStat extends CustomStat<Integer> {
    public LevelStat(Integer defaultValue) {
        super(defaultValue);
    }

    @Override
    public Codec<Integer> valueCodec() {
        return Codec.INT;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, Integer> valueStreamCodec() {
        return ByteBufCodecs.INT;
    }

    @Override
    public boolean applyModifier(SpawnerTile spawner, Integer value, Optional<Integer> min, Optional<Integer> max) {
        Integer old = this.getValue(spawner);
        this.setValue(spawner, this.clamp(old + value, min, max));
        return old != this.getValue(spawner);
    }

    private Integer clamp(Integer value, Optional<Integer> min, Optional<Integer> max) {
        if (min.isPresent()) {
            value = Math.max(value, min.get());
        }
        if (max.isPresent()) {
            value = Math.min(value, max.get());
        }
        return value;
    }

    @Override
    public Component getTooltipImpl(SpawnerTile spawner) {
        return SpawnerStat.createTooltip(this, this.formatValue(this.getValue(spawner)));
    }

    @Override
    public String formatValue(Integer value) {
        return value < 0
            ? Component.literal("-").append(Component.translatable("enchantment.level." + Math.abs(value))).getString()
            : Component.translatable("enchantment.level." + Math.abs(value)).getString();
    }
}
