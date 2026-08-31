package com.skd.ascendantspawners.stats;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.mojang.serialization.Codec;

import com.skd.ascendantspawners.block.AscendantSpawnerTile;
import net.minecraft.network.chat.Component;

/**
 * Base class for implementation of vanilla stats, as they are all shorts backed by individual fields.
 */
class VanillaStat implements SpawnerStat<Integer> {

    private static final Codec<Integer> SHORT_INT = Codec.intRange(Short.MIN_VALUE, Short.MAX_VALUE);

    protected final Function<AscendantSpawnerTile, Integer> getter;
    protected final BiConsumer<AscendantSpawnerTile, Integer> setter;

    VanillaStat(Function<AscendantSpawnerTile, Integer> getter, BiConsumer<AscendantSpawnerTile, Integer> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public Codec<Integer> getValueCodec() {
        return SHORT_INT;
    }

    @Override
    public Integer getValue(AscendantSpawnerTile spawner) {
        return this.getter.apply(spawner);
    }

    @Override
    public void setValue(AscendantSpawnerTile spawner, Integer value) {
        this.setter.accept(spawner, value);
    }

    @Override
    public Component getTooltip(AscendantSpawnerTile spawner) {
        return SpawnerStat.createTooltip(this, this.getValue(spawner).toString());
    }

    @Override
    public boolean applyModifier(AscendantSpawnerTile spawner, Integer value, Optional<Integer> min, Optional<Integer> max) {
        Integer old = this.getValue(spawner);
        this.setValue(spawner, this.clamp(old + value, min, max));
        return old != this.getValue(spawner);
    }

    @Override
    public String toString() {
        return "SpawnerStat{%s}".formatted(SpawnerStats.REGISTRY.getKey(this));
    }

    private Integer clamp(Integer value, Optional<Integer> min, Optional<Integer> max) {
        if (min.isPresent()) value = Math.max(value, min.get());
        if (max.isPresent()) value = Math.min(value, max.get());
        return value;
    }

}
