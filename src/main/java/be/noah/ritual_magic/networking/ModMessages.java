package be.noah.ritual_magic.networking;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.networking.packet.BlockHighlightS2CPacket;
import be.noah.ritual_magic.networking.packet.ManaDataSyncS2CPacket;
import be.noah.ritual_magic.networking.packet.IceShieldUpdatePacket;
import be.noah.ritual_magic.networking.packet.VoidShieldDataSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1.0";
    public static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(RitualMagic.MODID, "messages"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        INSTANCE.registerMessage(
                id(),
                BlockHighlightS2CPacket.class,
                BlockHighlightS2CPacket::encode,
                BlockHighlightS2CPacket::decode,
                BlockHighlightS2CPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        INSTANCE.registerMessage(
                id(),
                VoidShieldDataSyncS2CPacket.class,
                VoidShieldDataSyncS2CPacket::encode,
                VoidShieldDataSyncS2CPacket::decode,
                VoidShieldDataSyncS2CPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        INSTANCE.registerMessage(
                id(),
                ManaDataSyncS2CPacket.class,
                ManaDataSyncS2CPacket::encode,
                ManaDataSyncS2CPacket::decode,
                ManaDataSyncS2CPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        INSTANCE.registerMessage(
                id(),
                IceShieldUpdatePacket.class,
                IceShieldUpdatePacket::encode,
                IceShieldUpdatePacket::decode,
                IceShieldUpdatePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToAllTracking(Object message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), message);
    }
}
