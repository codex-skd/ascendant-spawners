package com.skd.ascendantspawners.advancements;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.predicates.DataComponentPredicate;

public class SpawnEggItemPredicate implements DataComponentPredicate {
    public static final MapCodec<SpawnEggItemPredicate> CODEC = MapCodec.unit(SpawnEggItemPredicate::new);

    public boolean matches(DataComponentGetter components) {
        return components.has(DataComponents.ENTITY_DATA);
    }
}
