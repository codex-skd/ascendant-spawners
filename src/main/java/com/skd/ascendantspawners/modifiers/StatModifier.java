package com.skd.ascendantspawners.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.ascendantspawners.block.SpawnerTile;
import com.skd.ascendantspawners.stats.SpawnerStat;
import com.skd.ascendantspawners.stats.SpawnerStats;
import io.netty.buffer.ByteBuf;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;

public record StatModifier<T>(SpawnerStat<T> stat, T value, Optional<T> min, Optional<T> max, StatModifier.Mode mode) {
    private static final Map<SpawnerStat<?>, MapCodec<StatModifier<?>>> CODEC_CACHE = new ConcurrentHashMap<>();
    private static final Map<SpawnerStat<?>, StreamCodec<RegistryFriendlyByteBuf, StatModifier<?>>> STREAM_CODEC_CACHE = new ConcurrentHashMap<>();
    public static final Codec<StatModifier<?>> CODEC = Codec.lazyInitialized(
        () -> SpawnerStats.REGISTRY.byNameCodec().dispatch(StatModifier::stat, StatModifier::modifierCodec)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, StatModifier<?>> STREAM_CODEC = ByteBufCodecs.registry(SpawnerStats.REGISTRY_KEY)
        .dispatch(StatModifier::stat, StatModifier::modifierStreamCodec);

    public StatModifier(SpawnerStat<T> stat, T value) {
        this(stat, value, Optional.empty(), Optional.empty(), StatModifier.Mode.ADD);
    }

    public boolean apply(SpawnerTile tile) {
        return switch (this.mode) {
            case ADD -> this.stat.applyModifier(tile, this.value, this.min, this.max);
            case SET -> {
                if (this.stat.getValue(tile) == this.value) {
                    yield false;
                }
                else {
                    this.stat.setValue(tile, this.value);
                    yield true;
                }
            }
        };
    }

    public String getFormattedValue() {
        return this.stat.formatValue(this.value);
    }

    @SuppressWarnings("unchecked")
    public static <T> MapCodec<StatModifier<T>> modifierCodec(SpawnerStat<T> stat) {
        return (MapCodec<StatModifier<T>>) (MapCodec<?>) CODEC_CACHE.computeIfAbsent(stat, s -> (MapCodec<StatModifier<?>>) createModifierCodec((SpawnerStat<?>) s));
    }

    @SuppressWarnings("unchecked")
    public static <T> StreamCodec<RegistryFriendlyByteBuf, StatModifier<T>> modifierStreamCodec(SpawnerStat<T> stat) {
        return (StreamCodec<RegistryFriendlyByteBuf, StatModifier<T>>) (StreamCodec<RegistryFriendlyByteBuf, ?>) STREAM_CODEC_CACHE.computeIfAbsent(stat, s -> (StreamCodec<RegistryFriendlyByteBuf, StatModifier<?>>) createModifierStreamCodec((SpawnerStat<?>) s));
    }

    private static <T> MapCodec<StatModifier<T>> createModifierCodec(SpawnerStat<T> stat) {
        return RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    stat.valueCodec().fieldOf("value").forGetter(StatModifier::value),
                    stat.valueCodec().optionalFieldOf("min").forGetter(StatModifier::min),
                    stat.valueCodec().optionalFieldOf("max").forGetter(StatModifier::max),
                    StatModifier.Mode.CODEC.optionalFieldOf("mode", StatModifier.Mode.ADD).forGetter(StatModifier::mode)
                )
                .apply(inst, (value, min, max, mode) -> new StatModifier<>(stat, value, min, max, mode))
        );
    }

    private static <T> StreamCodec<RegistryFriendlyByteBuf, StatModifier<T>> createModifierStreamCodec(SpawnerStat<T> stat) {
        return StreamCodec.composite(
            stat.valueStreamCodec(),
            StatModifier::value,
            ByteBufCodecs.optional(stat.valueStreamCodec()),
            StatModifier::min,
            ByteBufCodecs.optional(stat.valueStreamCodec()),
            StatModifier::max,
            StatModifier.Mode.STREAM_CODEC,
            StatModifier::mode,
            (value, min, max, mode) -> new StatModifier<>(stat, value, min, max, mode)
        );
    }

    public enum Mode implements StringRepresentable {
        ADD("add"),
        SET("set");

        public static final IntFunction<StatModifier.Mode> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), OutOfBoundsStrategy.ZERO);
        public static final Codec<StatModifier.Mode> CODEC = StringRepresentable.fromValues(StatModifier.Mode::values);
        public static final StreamCodec<ByteBuf, StatModifier.Mode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Enum::ordinal);

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
