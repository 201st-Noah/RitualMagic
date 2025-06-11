package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.items.armor.DwarvenArmor;
import be.noah.ritual_magic.items.custom.DwarvenAxe;
import be.noah.ritual_magic.items.custom.DwarvenPickAxe;
import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaPool;
import be.noah.ritual_magic.mana.ManaType;
import be.noah.ritual_magic.networking.ModMessages;
import be.noah.ritual_magic.networking.packet.ManaDataSyncS2CPacket;
import be.noah.ritual_magic.networking.packet.VoidShieldDataSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

import static be.noah.ritual_magic.items.armor.DwarvenArmor.getPurity;

@Mod.EventBusSubscriber(modid = RitualMagic.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    private static final Random random = new Random();
    private static final String VOID_SHIELD_TAG = "void_shield";
    private static final Set<BlockPos> HARVESTED_STONE_BLOCKS = new HashSet<>();
    private static final Set<BlockPos> HARVESTED_WOOD_BLOCKS = new HashSet<>();

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onDiggerToolUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();
        if (player instanceof ServerPlayer serverPlayer) {
            MinecraftServer minecraftServer = serverPlayer.getServer();

            if (mainHandItem.getItem() instanceof DwarvenPickAxe dwarvenPickAxe && dwarvenPickAxe.getDigAoe(mainHandItem) != 0) {
                ManaNetworkData data = ManaNetworkData.get(minecraftServer);
                BlockPos initialBlockPos = event.getPos();
                if (HARVESTED_STONE_BLOCKS.contains(initialBlockPos)) {
                    return;
                }

                for (BlockPos pos : DwarvenPickAxe.getBlocksToBeDestroyed(dwarvenPickAxe.getDigAoe(mainHandItem), initialBlockPos, serverPlayer)) {
                    if (pos == initialBlockPos || !dwarvenPickAxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }
                    if (data.consume(player.getUUID(), ManaType.DWARVEN, 1) || serverPlayer.isCreative()) {
                        HARVESTED_STONE_BLOCKS.add(pos);
                        serverPlayer.gameMode.destroyBlock(pos);
                        HARVESTED_STONE_BLOCKS.remove(pos);
                    } else {
                        break;
                    }
                }
            } else if (mainHandItem.getItem() instanceof DwarvenAxe dwarvenAxe && player.isShiftKeyDown()) {
                ManaNetworkData data = ManaNetworkData.get(minecraftServer);
                BlockPos initialBlockPos = event.getPos();
                if (HARVESTED_WOOD_BLOCKS.contains(initialBlockPos)) {
                    return;
                }
                List<BlockPos> logList = new ArrayList<>();
                int maxLogs = (dwarvenAxe.getItemLevel(mainHandItem) * 3) + 15;
                for (BlockPos pos : DwarvenAxe.getLogsToBeDestroyed(maxLogs, initialBlockPos, serverPlayer)) {
                    if (pos == initialBlockPos || !dwarvenAxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }
                    if (serverPlayer.isCreative() || data.consume(player.getUUID(), ManaType.DWARVEN, 1)) {
                        HARVESTED_WOOD_BLOCKS.add(pos);
                        serverPlayer.gameMode.destroyBlock(pos);
                        HARVESTED_WOOD_BLOCKS.remove(pos);
                        logList.add(pos);
                    } else {
                        break;
                    }
                }
                for (BlockPos pos : DwarvenAxe.getLeavesToBeDestroyed(logList, serverPlayer)) {
                    serverPlayer.gameMode.destroyBlock(pos);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingAttackEvent event) {
        Entity entity = event.getEntity();
        Entity attacker = event.getSource().getDirectEntity();
        float damage = event.getAmount();
        event.setCanceled(onAttackOrHurt(entity, attacker, damage));
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        Entity attacker = event.getSource().getDirectEntity();
        float damage = event.getAmount();

        event.setCanceled(onAttackOrHurt(entity, attacker, damage));
    }

    private static boolean onAttackOrHurt(Entity entity, Entity attacker, float damage) {
        if (entity instanceof Player player) {
            Item head = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
            Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
            Item legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
            Item feet = player.getItemBySlot(EquipmentSlot.FEET).getItem();
            //((DwarvenArmor) feet).setPurity(player.getItemBySlot(EquipmentSlot.FEET),32);

            int chance = 0;
            if (chest instanceof DwarvenArmor) {
                chance = chance + (getPurity(player.getItemBySlot(EquipmentSlot.CHEST)));
            }
            if (feet instanceof DwarvenArmor) {
                chance = chance + (getPurity(player.getItemBySlot(EquipmentSlot.FEET)));
            }
            if (head instanceof DwarvenArmor) {
                chance = chance + (getPurity(player.getItemBySlot(EquipmentSlot.HEAD)));
            }
            if (legs instanceof DwarvenArmor) {
                chance = chance + (getPurity(player.getItemBySlot(EquipmentSlot.LEGS)));
            }

            //System.out.println(chance);
            if (attacker instanceof LivingEntity livingAttacker) {
                if (random.nextInt(101) < chance) {
                    livingAttacker.knockback(0.5D, player.getX() - attacker.getX(), player.getZ() - attacker.getZ());
                    return true;
                }
            }
            if (attacker instanceof Arrow) {
                return random.nextInt(101) < 2 * chance;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        CompoundTag data = player.getPersistentData();
        if (!data.contains(VOID_SHIELD_TAG)) return;

        int hitsLeft = data.getInt(VOID_SHIELD_TAG);

        if (hitsLeft > 0) {
            event.setCanceled(true);  // cancel damage
            data.putInt(VOID_SHIELD_TAG, hitsLeft - 1);
            player.level().playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1f, 1f);
        }

        if (hitsLeft <= 1) {
            data.remove(VOID_SHIELD_TAG);
            player.level().playSound(null, player.blockPosition(), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 1f, 1f);
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("Void Shield Depleted"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.player instanceof ServerPlayer serverPlayer) {
            CompoundTag tag = serverPlayer.getPersistentData();
            int hitsLeft = tag.getInt("void_shield");
            ModMessages.sendToPlayer(new VoidShieldDataSyncS2CPacket(hitsLeft), serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (ServerLevel level : event.getServer().getAllLevels()) {
                ManaNetworkData.get(level.getServer()).syncDirty(level);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        MinecraftServer minecraftServer = player.getServer();
        ManaNetworkData data = ManaNetworkData.get(minecraftServer);
        ManaPool pool = data.getOrCreate(player.getUUID());

        CompoundTag tag = pool.serialize();

        // ✅ Send the sync packet
        ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(player.getUUID(), tag), player);
    }
}
