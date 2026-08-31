package com.skd.ascendantspawners.compat;

import com.skd.ascendantspawners.AscendantSpawners;
import com.skd.ascendantspawners.block.AscendantSpawnerBlock;
import com.skd.ascendantspawners.block.AscendantSpawnerTile;
import com.skd.ascendantspawners.stats.SpawnerStats;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class SpawnerJadePlugin implements IWailaPlugin, IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public void register(IWailaCommonRegistration reg) {
        reg.registerBlockDataProvider(this, AscendantSpawnerTile.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(this, AscendantSpawnerBlock.class);
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (Screen.hasControlDown()) {
            AscendantSpawnerTile tile = new AscendantSpawnerTile(BlockPos.ZERO, Blocks.SPAWNER.defaultBlockState());
            tile.loadAdditional(accessor.getServerData(), accessor.getLevel().registryAccess());
            SpawnerStats.generateTooltip(tile, tooltip::add);
        }
        else {
            tooltip.add(AscendantSpawners.lang("misc", "ctrl_stats"));
        }
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor access) {
        if (access.getBlockEntity() instanceof AscendantSpawnerTile spw) {
            spw.saveAdditional(tag, access.getLevel().registryAccess());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return AscendantSpawners.loc("spawner");
    }

}
