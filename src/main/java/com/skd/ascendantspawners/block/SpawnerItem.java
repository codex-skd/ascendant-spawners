package com.skd.ascendantspawners.block;

import com.skd.ascendantspawners.AscendantSpawners;
import com.skd.ascendantspawners.stats.SpawnerStats;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jspecify.annotations.Nullable;

public class SpawnerItem extends BlockItem {
    public SpawnerItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    public String getCreatorModId(Provider registries, ItemStack itemStack) {
        return AscendantSpawners.MODID;
    }

    @Override
    public Component getName(ItemStack stack) {
        TypedEntityData<BlockEntityType<?>> data = (TypedEntityData<BlockEntityType<?>>) stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data != null) {
            CompoundTag tag = data.getUnsafe();
            if (tag.contains("SpawnData")) {
                try {
                    String name = tag.getCompound("SpawnData").map(sd -> sd.getCompound("entity").map(ent -> ent.getStringOr("id", "")).orElse("")).orElse("");
                    EntityType<?> t = BuiltInRegistries.ENTITY_TYPE.get(Identifier.tryParse(name)).map(ref -> (EntityType<?>) ref.value()).orElse(null);
                    if (t != null && (t != BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.withDefaultNamespace("pig")) || "minecraft:pig".equals(name))) {
                        MobCategory cat = t.getCategory();
                        ChatFormatting color = switch (cat) {
                            case AMBIENT, CREATURE -> ChatFormatting.DARK_GREEN;
                            case MONSTER -> ChatFormatting.RED;
                            case WATER_AMBIENT, UNDERGROUND_WATER_CREATURE, WATER_CREATURE, AXOLOTLS -> ChatFormatting.BLUE;
                            default -> ChatFormatting.WHITE;
                        };
                        return AscendantSpawners.lang("item", "spawner", Component.translatable(t.getDescriptionId())).withStyle(color);
                    }
                    return super.getName(stack);
                }
                catch (Exception ignored) {
                }
            }
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag tooltipFlag) {
        if (stack.has(DataComponents.BLOCK_ENTITY_DATA)) {
            if (Minecraft.getInstance().hasShiftDown()) {
                TypedEntityData<BlockEntityType<?>> data = (TypedEntityData<BlockEntityType<?>>) stack.get(DataComponents.BLOCK_ENTITY_DATA);
                SpawnerTile tooltipTile = new SpawnerTile(BlockPos.ZERO, Blocks.SPAWNER.defaultBlockState());
                data.loadInto(tooltipTile, context.registries());
                SpawnerStats.generateTooltip(tooltipTile, tooltip);
            }
            else {
                tooltip.accept(AscendantSpawners.lang("misc", "shift_stats").withStyle(ChatFormatting.GRAY));
            }
        }
    }

    @Override
    public boolean shouldPrintOpWarning(ItemStack stack, @Nullable Player player) {
        return false;
    }
}
