package com.skd.ascendantspawners;

import com.mojang.datafixers.util.Pair;
import com.skd.ascendantspawners.block.SpawnerTile;
import com.skd.ascendantspawners.stats.SpawnerStats;
import com.skd.commontoolkit.events.ResourceReloadEvent;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSplitEvent;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent.Result;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;
import net.neoforged.neoforge.network.PacketDistributor;

public class AscSpEvents {
    private static final MethodHandle dropFromLootTable;

    @SubscribeEvent
    public void handleCapturing(LivingDropsEvent e) {
        Entity killer = e.getSource().getEntity();
        LivingEntity killed = e.getEntity();
        if (killer instanceof LivingEntity living) {
            Pair<Float, Integer> capturingPair = EnchantmentHelper.getHighestLevel(living.getWeaponItem(), AscSpObjects.CAPTURING);
            if (capturingPair == null || killed.is(AscSpObjects.BLACKLISTED_FROM_SPAWNERS)) {
                return;
            }

            if (killed.level().getRandom().nextFloat() < capturingPair.getSecond() * capturingPair.getFirst()) {
                SpawnEggItem.byId(killed.getType()).ifPresent(eggItem -> {
                    ItemStack egg = new ItemStack(eggItem);
                    e.getDrops().add(new ItemEntity(killed.level(), killed.getX(), killed.getY(), killed.getZ(), egg));
                });
            }
        }
    }

    @SubscribeEvent
    public void handleEchoing(LivingDropsEvent e) throws Throwable {
        int echoes = e.getEntity().getPersistentData().getIntOr(SpawnerStats.ECHOING.getId().toString(), 0);
        if (echoes > 0) {
            e.getEntity().captureDrops(new ArrayList<>());

            for (int i = 0; i < echoes; i++) {
                dropFromLootTable.invoke(e.getEntity(), e.getEntity().level(), e.getSource(), true);
            }

            e.getDrops().addAll(e.getEntity().captureDrops(null));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void handleEchoingXp(LivingExperienceDropEvent e) {
        int echoes = e.getEntity().getPersistentData().getIntOr(SpawnerStats.ECHOING.getId().toString(), 0);
        if (echoes > 0) {
            e.setDroppedExperience(e.getDroppedExperience() * (1 + echoes));
        }
    }

    @SubscribeEvent
    public void handleUseItem(RightClickBlock e) {
        if (e.getLevel().getBlockEntity(e.getPos()) instanceof SpawnerTile) {
            ItemStack s = e.getItemStack();
            if (s.getItem() instanceof SpawnEggItem) {
                EntityType<?> type = SpawnEggItem.getType(s);
                if (type.builtInRegistryHolder().is(AscSpObjects.BLACKLISTED_FROM_SPAWNERS)) {
                    e.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void handleTooltips(ItemTooltipEvent e) {
        ItemStack s = e.getItemStack();
        if (s.getItem() instanceof SpawnEggItem) {
            EntityType<?> type = SpawnEggItem.getType(s);
            if (type.builtInRegistryHolder().is(AscSpObjects.BLACKLISTED_FROM_SPAWNERS)) {
                e.getToolTip().add(AscendantSpawners.lang("misc", "banned").withStyle(ChatFormatting.GRAY));
            }
        }
    }

    @SubscribeEvent
    public void tickDumbMobs(Pre e) {
        if (e.getEntity() instanceof Mob mob && !mob.level().isClientSide() && mob.isNoAi() && mob.getPersistentData().getBooleanOr("ascendant_spawners:movable", false)) {
            mob.setNoAi(false);
            mob.travel(new Vec3(mob.xxa, mob.zza, mob.yya));
            mob.setNoAi(true);
        }
    }

    @SubscribeEvent
    public void dumbMobsCantTeleport(EntityTeleportEvent e) {
        if (e.getEntity().getPersistentData().getBooleanOr("ascendant_spawners:movable", false)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void reload(ResourceReloadEvent e) {
        if (e.getSide().isServer()) {
            AscSpConfig.load();
        }
    }

    @SubscribeEvent
    public void sync(OnDatapackSyncEvent e) {
        e.sendRecipes(new RecipeType<?>[] { AscSpObjects.SPAWNER_MODIFIER.get() });
        e.getRelevantPlayers().forEach(p -> PacketDistributor.sendToPlayer(p, new AscSpConfig.ConfigPayload(), new CustomPacketPayload[0]));
    }

    @SubscribeEvent
    public void split(MobSplitEvent e) {
        if (e.getParent().isNoAi()) {
            boolean isMoveable = e.getParent().getPersistentData().getBooleanOr("ascendant_spawners:movable", false);
            if (isMoveable) {
                e.getChildren().forEach(mob -> mob.getPersistentData().putBoolean("ascendant_spawners:movable", true));
            }
        }
    }

    @SubscribeEvent
    public void onDespawn(MobDespawnEvent e) throws Throwable {
        Mob mob = e.getEntity();
        boolean isPeaceful = e.getLevel().getDifficulty() == Difficulty.PEACEFUL;
        if (!isPeaceful || mob.getType().isAllowedInPeaceful()) {
            if (EntitySpawnReason.isSpawner(mob.getSpawnType()) && AscSpConfig.entityDespawnDelay >= mob.tickCount) {
                e.setResult(Result.DENY);
            }
        }
    }

    static {
        try {
            Method m = LivingEntity.class.getDeclaredMethod("dropFromLootTable", ServerLevel.class, DamageSource.class, boolean.class);
            m.setAccessible(true);
            dropFromLootTable = MethodHandles.lookup().unreflect(m);
        }
        catch (NoSuchMethodException | IllegalAccessException ex) {
            throw new RuntimeException("LivingEntity#dropFromLootTable not located!", ex);
        }
    }
}
