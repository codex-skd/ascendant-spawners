package com.skd.ascendantspawners.compat;

import com.skd.ascendantspawners.AscendantSpawners;
import com.skd.ascendantspawners.block.SpawnerTile;
import com.skd.ascendantspawners.stats.SpawnerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter.ScopedCollector;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class SpawnerClientProvider implements IBlockComponentProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpawnerClientProvider.class);

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (Minecraft.getInstance().hasControlDown()) {
            SpawnerTile tile = new SpawnerTile(BlockPos.ZERO, Blocks.SPAWNER.defaultBlockState());
            ScopedCollector reporter = new ScopedCollector(LOGGER);

            try {
                ValueInput input = TagValueInput.create(reporter, accessor.getLevel().registryAccess(), accessor.getServerData());
                tile.loadCustomOnly(input);
            }
            catch (Throwable ex) {
                try {
                    reporter.close();
                }
                catch (Throwable ex2) {
                    ex.addSuppressed(ex2);
                }

                throw ex;
            }

            reporter.close();
            SpawnerStats.generateTooltip(tile, tooltip::add);
        }
        else {
            tooltip.add(AscendantSpawners.lang("misc", "ctrl_stats"));
        }
    }

    @Override
    public Identifier getUid() {
        return AscendantSpawners.loc("spawner");
    }
}
