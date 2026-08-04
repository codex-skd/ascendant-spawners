package com.skd.ascendantspawners.mixin;

import com.skd.ascendantspawners.block.SpawnerBlock;
import java.util.function.Function;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = Blocks.class, remap = false)
public class BlocksMixin {
   @ModifyArg(
      method = "<clinit>",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"
      ),
      slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=spawner"), to = @At(value = "CONSTANT", args = "stringValue=creaking_heart")),
      index = 1,
      require = 1
   )
   private static Function<Properties, Block> asc_overrideSpawnerBlock(Function<Properties, Block> original) {
      return SpawnerBlock::new;
   }
}
