package com.skd.ascendantspawners.stats;

import com.mojang.serialization.Codec;
import com.skd.ascendantspawners.block.SpawnerTile;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class PercentageStat extends CustomStat<Float> {
    public PercentageStat(Float defaultValue) {
        super(defaultValue);
    }

    @Override
    public Codec<Float> valueCodec() {
        return Codec.floatRange(-1.0F, 1.0F);
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, Float> valueStreamCodec() {
        return ByteBufCodecs.FLOAT;
    }

    @Override
    public Component getTooltipImpl(SpawnerTile spawner) {
        return SpawnerStat.createTooltip(this, this.formatValue(this.getValue(spawner)));
    }

    @Override
    public boolean applyModifier(SpawnerTile spawner, Float value, Optional<Float> min, Optional<Float> max) {
        Float old = this.getValue(spawner);
        this.setValue(spawner, this.clamp(old + value, min, max));
        return Math.abs(old - this.getValue(spawner)) > 1.0E-4F;
    }

    @Override
    public String formatValue(Float value) {
        return ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value * 100.0F) + "%";
    }

    private Float clamp(Float value, Optional<Float> min, Optional<Float> max) {
        if (min.isPresent()) {
            value = Math.max(value, min.get());
        }
        if (max.isPresent()) {
            value = Math.min(value, max.get());
        }
        return value;
    }
}
