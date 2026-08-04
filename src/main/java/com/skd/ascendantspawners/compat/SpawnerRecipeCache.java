package com.skd.ascendantspawners.compat;

import com.skd.ascendantspawners.AscSpObjects;
import com.skd.ascendantspawners.modifiers.SpawnerModifier;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;

public class SpawnerRecipeCache {
    private static volatile List<SpawnerModifier> RECIPES = List.of();

    private SpawnerRecipeCache() {
    }

    public static void rebuildFromMap(RecipeMap map) {
        Collection<RecipeHolder<SpawnerModifier>> holders = map.byType(AscSpObjects.SPAWNER_MODIFIER.get());
        RECIPES = holders.stream()
            .sorted(Comparator.comparing(h -> h.id().identifier(), (id1, id2) -> -id1.compareNamespaced(id2)))
            .<SpawnerModifier>map(RecipeHolder::value)
            .toList();
    }

    public static List<SpawnerModifier> getRecipes() {
        return RECIPES;
    }
}
