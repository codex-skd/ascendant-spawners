package com.skd.ascendantspawners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.skd.ascendantspawners.AscSpConfig.ConfigPayload;
import com.skd.ascendantspawners.block.AscendantSpawnerTile;
import com.skd.ascendantspawners.stats.SpawnerStats;
import com.skd.commontoolkit.network.PayloadHelper;
import com.skd.commontoolkit.tabs.TabFillingRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(AscendantSpawners.MODID)
public class AscendantSpawners {

    public static final String MODID = "ascendant_spawners";
    public static final Logger LOGGER = LogManager.getLogger("Apotheosis : Spawner");

    public AscendantSpawners(IEventBus bus) {
        bus.register(this);
        AscSpObjects.bootstrap(bus);
        NeoForge.EVENT_BUS.register(new AscSpEvents());
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            ObfuscationReflectionHelper.<BlockEntityType<?>, BlockEntityType.BlockEntitySupplier<?>>setPrivateValue(BlockEntityType.class, BlockEntityType.MOB_SPAWNER, AscendantSpawnerTile::new, "factory");
            AscSpConfig.load();
            TabFillingRegistry.registerSimple(Items.SPAWNER, CreativeModeTabs.TOOLS_AND_UTILITIES);
            PayloadHelper.registerPayload(new ConfigPayload.Provider());
        });
    }

    @SubscribeEvent
    public void regs(NewRegistryEvent e) {
        e.register(SpawnerStats.REGISTRY);
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static MutableComponent lang(String type, String path, Object... args) {
        return Component.translatable(type + "." + MODID + "." + path, args);
    }

}
