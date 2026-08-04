package com.skd.ascendantspawners.compat;

import com.skd.ascendantspawners.AscendantSpawners;
import com.skd.ascendantspawners.block.SpawnerTile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter.ScopedCollector;
import net.minecraft.world.level.storage.TagValueOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class SpawnerServerDataProvider implements IServerDataProvider<BlockAccessor> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpawnerServerDataProvider.class);

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor access) {
        if (access.getBlockEntity() instanceof SpawnerTile spw) {
            ScopedCollector reporter = new ScopedCollector(spw.problemPath(), LOGGER);

            try {
                TagValueOutput output = TagValueOutput.createWithContext(reporter, access.getLevel().registryAccess());
                spw.saveCustomOnly(output);
                CompoundTag result = output.buildResult();
                result.keySet().forEach(key -> tag.put(key, result.get(key)));
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
        }
    }

    @Override
    public Identifier getUid() {
        return AscendantSpawners.loc("spawner");
    }
}
