package com.skd.ascendantspawners.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.skd.ascendantspawners.block.SpawnerItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemStack.class, targets = "net.neoforged.neoforge.common.tooltip.VanillaDataComponentTooltips")
public class ItemStackMixin {
   @WrapOperation(
      method = {"addDetailsToTooltip", "getTooltipLines", "addDetailsToTooltipComponents", "lambda$collectVanillaAppenders$1"},
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z", ordinal = 0, remap = false),
      remap = false,
      require = 1
   )
   private static boolean asc_skipVanillaSpawnerTooltip(ItemStack self, Object predicate, Operation<Boolean> original) {
      return self.getItem() instanceof SpawnerItem ? false : (Boolean)original.call(new Object[]{self, predicate});
   }
}
