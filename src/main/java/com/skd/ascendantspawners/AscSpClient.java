package com.skd.ascendantspawners;

import com.skd.ascendantspawners.compat.SpawnerRecipeCache;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = AscendantSpawners.MODID, value = Dist.CLIENT)
public class AscSpClient {
    public static void init() {
        NeoForge.EVENT_BUS.register(AscSpClient.class);
    }

    @SubscribeEvent
    public static void recipesReceived(RecipesReceivedEvent e) {
        if (e.getRecipeTypes().contains(AscSpObjects.SPAWNER_MODIFIER.get())) {
            SpawnerRecipeCache.rebuildFromMap(e.getRecipeMap());
        }
    }
}
