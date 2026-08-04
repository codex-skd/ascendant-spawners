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
   private static final ResourceKey<Item> SPAWNER_KEY = ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("spawner"));

   @ModifyVariable(
      method = "registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;",
      at = @At("HEAD"),
      ordinal = 0,
      argsOnly = true
   )
   private static Function<Properties, Item> asc_replaceSpawnerFactory(
      Function<Properties, Item> factory, ResourceKey<Item> key, Function<Properties, Item> factory2, Properties props
   ) {
      return SPAWNER_KEY.equals(key)
         ? p -> new SpawnerItem(Blocks.SPAWNER, p)
         : factory;
   }
}
