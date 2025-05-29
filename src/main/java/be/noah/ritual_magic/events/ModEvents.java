package be.noah.ritual_magic.events;

import be.noah.ritual_magic.Mana.ManaNetworkData;
import be.noah.ritual_magic.Mana.ManaPool;
import be.noah.ritual_magic.Mana.ManaType;
import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.item.custom.DwarvenPickAxe;
import be.noah.ritual_magic.networking.ModMessages;
import be.noah.ritual_magic.networking.packet.ManaDataSyncS2CPacket;
import be.noah.ritual_magic.networking.packet.VoidShieldDataSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = RitualMagic.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onPickaxeUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof DwarvenPickAxe dwarvenPickAxe && player instanceof ServerPlayer serverPlayer) {
            ServerLevel serverLevel = serverPlayer.serverLevel();
            ManaNetworkData data = ManaNetworkData.get(serverLevel);
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for(BlockPos pos : DwarvenPickAxe.getBlocksToBeDestroyed(dwarvenPickAxe.getDigAoe(), initialBlockPos, serverPlayer)) {
                if(pos == initialBlockPos || !dwarvenPickAxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }
                if (data.consume(player.getUUID(), ManaType.DWARVEN, 1) || serverPlayer.isCreative()) {
                    HARVESTED_BLOCKS.add(pos);
                    serverPlayer.gameMode.destroyBlock(pos);
                    HARVESTED_BLOCKS.remove(pos);
                }
                else{
                    break;
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
                ManaNetworkData.get(level).syncDirty(level);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ServerLevel level = player.serverLevel();
        ManaNetworkData data = ManaNetworkData.get(level);
        ManaPool pool = data.getOrCreate(player.getUUID());

        CompoundTag tag = pool.serialize();

        // ✅ Send the sync packet
        ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(player.getUUID(), tag), player);
    }
}