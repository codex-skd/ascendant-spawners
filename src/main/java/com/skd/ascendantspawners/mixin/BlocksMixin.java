package com.skd.ascendantspawners.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.skd.ascendantspawners.block.AscendantSpawnerBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

@Mixin(value = Blocks.class, remap = false)
public class BlocksMixin {

    @Redirect(at = @At(value = "NEW", target = "net/minecraft/world/level/block/SpawnerBlock"), method = "<clinit>", require = 1)
    private static SpawnerBlock apoth_overrideSpawnerBlock(BlockBehaviour.Properties properties) {
        return new AscendantSpawnerBlock(properties);
    }

}
