package com.skd.ascendantspawners;

import com.skd.ascendantspawners.block.SpawnerTile;
import com.skd.ascendantspawners.data.ASEnchantmentProvider;
import com.skd.ascendantspawners.data.ASLootProvider;
import com.skd.ascendantspawners.data.ASRecipeProvider;
import com.skd.ascendantspawners.stats.SpawnerStats;
import com.skd.commontoolkit.datagen.DataGenBuilder;
import com.skd.commontoolkit.network.PayloadHelper;
import com.skd.commontoolkit.tabs.TabFillingRegistry;
import java.lang.reflect.Field;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent.Client;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(AscendantSpawners.MODID)
public class AscendantSpawners {
    public static final String MODID = "ascendant_spawners";
    public static final Logger LOGGER = LoggerFactory.getLogger("Ascendant : Spawner");

    public AscendantSpawners(IEventBus bus) {
        bus.register(this);
        AscSpObjects.bootstrap(bus);
        NeoForge.EVENT_BUS.register(new AscSpEvents());
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            replaceMobSpawnerFactory();
            AscSpConfig.load();
            TabFillingRegistry.registerSimple(Items.SPAWNER, new ResourceKey[] { CreativeModeTabs.TOOLS_AND_UTILITIES });
            PayloadHelper.registerPayload(new AscSpConfig.ConfigPayload.Provider());
        });
    }

    @SubscribeEvent
    public void regs(NewRegistryEvent e) {
        e.register(SpawnerStats.REGISTRY);
    }

    @SubscribeEvent
    public void data(Client event) {
        DataProvider.INDENT_WIDTH.set(4);
        DataGenBuilder.create(new String[] { AscendantSpawners.MODID })
            .registry(Registries.ENCHANTMENT, ASEnchantmentProvider::bootstrap)
            .provider(ASRecipeProvider::new)
            .provider(ASLootProvider::create)
            .build(event);
    }

    public static Identifier loc(String path) {
        return Identifier.fromNamespaceAndPath(AscendantSpawners.MODID, path);
    }

    public static MutableComponent lang(String type, String path, Object... args) {
        return Component.translatable(type + ".ascendant_spawners." + path, args);
    }

    @SuppressWarnings("unchecked")
    private static void replaceMobSpawnerFactory() {
        try {
            Field f = BlockEntityType.class.getDeclaredField("factory");
            f.setAccessible(true);
            f.set(BuiltInRegistries.BLOCK_ENTITY_TYPE.getValue(Identifier.withDefaultNamespace("mob_spawner")), (BlockEntityType.BlockEntitySupplier<SpawnerTile>) SpawnerTile::new);
        }
        catch (ReflectiveOperationException ex) {
            throw new RuntimeException("Failed to replace the MobSpawner BlockEntityType factory!", ex);
        }
    }
}
