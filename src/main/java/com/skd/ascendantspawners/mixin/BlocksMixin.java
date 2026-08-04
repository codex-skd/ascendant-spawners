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
   // 26.2 changed Blocks.register() from a String-keyed overload to one keyed by
   // net.minecraft.references.BlockItemId, and vanilla no longer registers blocks by string
   // literal (it reads static BlockItemIds.* fields instead) — so the original 26.1.2 mixin's
   // @At("CONSTANT", stringValue=...) slice boundaries and target descriptor no longer match.
   // SPAWNER is still registered immediately before CREAKING_HEART (verified against the real
   // 26.2 Blocks.<clinit> bytecode), so the slice is now bounded by those two static field reads.
   @ModifyArg(
      method = "<clinit>",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/block/Blocks;register(Lnet/minecraft/references/BlockItemId;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"
      ),
      slice = @Slice(
         from = @At(value = "FIELD", target = "Lnet/minecraft/references/BlockItemIds;SPAWNER:Lnet/minecraft/references/BlockItemId;"),
         to = @At(value = "FIELD", target = "Lnet/minecraft/references/BlockItemIds;CREAKING_HEART:Lnet/minecraft/references/BlockItemId;")
      ),
      index = 1,
      require = 1
   )
   private static Function<Properties, Block> asc_overrideSpawnerBlock(Function<Properties, Block> original) {
      return SpawnerBlock::new;
   }
}
