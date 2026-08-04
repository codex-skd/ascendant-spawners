package com.skd.ascendantspawners.compat;

import com.skd.ascendantspawners.block.SpawnerBlock;
import com.skd.ascendantspawners.block.SpawnerTile;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class SpawnerJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration reg) {
        reg.registerBlockDataProvider(new SpawnerServerDataProvider(), SpawnerTile.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(new SpawnerClientProvider(), SpawnerBlock.class);
    }
}
