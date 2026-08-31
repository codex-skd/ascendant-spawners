package com.skd.ascendantspawners.stats;

import com.skd.ascendantspawners.block.AscendantSpawnerTile;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public abstract class CustomStat<T> implements SpawnerStat<T> {

    private final T defaultValue;

    public CustomStat(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getValue(AscendantSpawnerTile spawner) {
        return (T) spawner.getStatsMap().getOrDefault(this, this.defaultValue);
    }

    @Override
    public void setValue(AscendantSpawnerTile spawner, T value) {
        spawner.getStatsMap().put(this, value);
    }

    @Override
    public final Component getTooltip(AscendantSpawnerTile spawner) {
        return this.getValue(spawner) == this.defaultValue ? CommonComponents.EMPTY : this.getTooltipImpl(spawner);
    }

    @Override
    public String toString() {
        return "SpawnerStat{%s}".formatted(SpawnerStats.REGISTRY.getKey(this));
    }

    public abstract Component getTooltipImpl(AscendantSpawnerTile spawner);

}
