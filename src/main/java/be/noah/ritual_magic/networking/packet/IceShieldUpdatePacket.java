package be.noah.ritual_magic.networking.packet;

import be.noah.ritual_magic.client.ClientIceShieldHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class IceShieldUpdatePacket {
    private final UUID playerId;
    private final int hitsLeft;

    public IceShieldUpdatePacket(UUID playerId, int hitsLeft) {
        this.playerId = playerId;
        this.hitsLeft = hitsLeft;
    }

    public static void encode(IceShieldUpdatePacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerId);
        buf.writeInt(msg.hitsLeft);
    }

    public static IceShieldUpdatePacket decode(FriendlyByteBuf buf) {
        return new IceShieldUpdatePacket(buf.readUUID(), buf.readInt());
    }

    public static void handle(IceShieldUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientIceShieldHandler.handleUpdate(msg.playerId, msg.hitsLeft);
        });
        ctx.get().setPacketHandled(true);
    }
}
