package be.noah.ritual_magic.client;

import be.noah.ritual_magic.networking.ModMessages;
import be.noah.ritual_magic.networking.packet.IceShieldUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class IceShieldSyncHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;

        ServerPlayer player = (ServerPlayer) event.player;
        int shieldValue = getShieldFromNBT(player);

        ModMessages.sendToAllTracking(new IceShieldUpdatePacket(player.getUUID(), shieldValue), player);
    }

    private static int getShieldFromNBT(ServerPlayer player) {
        if (player.getPersistentData().contains("void_shield")) {
            return player.getPersistentData().getInt("void_shield");
        }
        return 0;
    }
}
