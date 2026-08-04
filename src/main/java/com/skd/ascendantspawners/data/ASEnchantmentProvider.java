package com.skd.ascendantspawners.data;

import com.skd.ascendantspawners.AscSpObjects;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ASEnchantmentProvider {
    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        context.register(
            AscSpObjects.CAPTURING_ENCH,
            Enchantment.enchantment(
                    Enchantment.definition(
                        items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                        2,
                        3,
                        Enchantment.dynamicCost(15, 10),
                        Enchantment.constantCost(200),
                        1,
                        new EquipmentSlotGroup[] { EquipmentSlotGroup.MAINHAND }
                    )
                )
                .withSpecialEffect(AscSpObjects.CAPTURING, 0.005F)
                .build(AscSpObjects.CAPTURING_ENCH.identifier())
        );
    }
}
