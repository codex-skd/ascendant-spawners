package com.skd.ascendantspawners.mixin;

import com.skd.ascendantspawners.block.SpawnerItem;
import java.util.function.Function;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = Items.class, remap = false)
public class ItemsMixin {
   // Not a static final field: mixin-merged static fields are appended to the END of the target's
   // <clinit>, but Items.<clinit> registers "spawner" very early in its own body — so a field would
   // still be null at that point. Compute the key inline on each call instead.

   @ModifyVariable(
      method = "registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;",
      at = @At("HEAD"),
      ordinal = 0,
      argsOnly = true
   )
   private static Function<Properties, Item> asc_replaceSpawnerFactory(
      Function<Properties, Item> factory, ResourceKey<Item> key, Function<Properties, Item> factory2, Properties props
   ) {
      return ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("spawner")).equals(key)
         ? p -> new SpawnerItem(Blocks.SPAWNER, p)
         : factory;
   }
}
