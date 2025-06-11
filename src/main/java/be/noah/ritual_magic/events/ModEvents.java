package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = RitualMagic.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_STONE_BLOCKS = new HashSet<>();
    private static final Set<BlockPos> HARVESTED_WOOD_BLOCKS = new HashSet<>();
    //private static final Set<BlockPos> HARVESTED_LEAF_BLOCKS = new HashSet<>();

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onDiggerToolUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();
        if (player instanceof ServerPlayer serverPlayer) {
            MinecraftServer minecraftServer = serverPlayer.getServer();

            if (mainHandItem.getItem() instanceof DwarvenPickAxe dwarvenPickAxe && dwarvenPickAxe.getDigAoe() != 0) {
                ManaNetworkData data = ManaNetworkData.get(minecraftServer);
                BlockPos initialBlockPos = event.getPos();
                if (HARVESTED_STONE_BLOCKS.contains(initialBlockPos)) {
                    return;
                }

                for (BlockPos pos : DwarvenPickAxe.getBlocksToBeDestroyed(dwarvenPickAxe.getDigAoe(), initialBlockPos, serverPlayer)) {
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