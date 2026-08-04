package com.skd.ascendantspawners.stats;

import com.mojang.serialization.Codec;
import com.skd.ascendantspawners.block.SpawnerTile;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

class VanillaStat implements SpawnerStat<Integer> {
    private static final Codec<Integer> SHORT_INT = Codec.intRange(-32768, 32767);
    protected final Function<SpawnerTile, Integer> getter;
    protected final BiConsumer<SpawnerTile, Integer> setter;

    VanillaStat(Function<SpawnerTile, Integer> getter, BiConsumer<SpawnerTile, Integer> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public Codec<Integer> valueCodec() {
        return SHORT_INT;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, Integer> valueStreamCodec() {
        return ByteBufCodecs.INT;
    }

    @Override
    public Integer getValue(SpawnerTile spawner) {
        return this.getter.apply(spawner);
    }

    @Override
    public void setValue(SpawnerTile spawner, Integer value) {
        this.setter.accept(spawner, value);
    }

    @Override
    public Component getTooltip(SpawnerTile spawner) {
        return SpawnerStat.createTooltip(this, this.getValue(spawner).toString());
    }

    @Override
    public boolean applyModifier(SpawnerTile spawner, Integer value, Optional<Integer> min, Optional<Integer> max) {
        Integer old = this.getValue(spawner);
        this.setValue(spawner, this.clamp(old + value, min, max));
        return old != this.getValue(spawner);
    }

    @Override
    public String toString() {
        return "SpawnerStat{%s}".formatted(SpawnerStats.REGISTRY.getKey(this));
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
}
