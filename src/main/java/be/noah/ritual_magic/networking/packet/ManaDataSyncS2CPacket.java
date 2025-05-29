package be.noah.ritual_magic.networking.packet;

import be.noah.ritual_magic.client.ClientManaData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ManaDataSyncS2CPacket {
    private final UUID playerId;
    private final CompoundTag manaData;

    public ManaDataSyncS2CPacket(UUID playerId, CompoundTag manaData) {
        this.playerId = playerId;
        this.manaData = manaData;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public CompoundTag getManaData() {
        return manaData;
    }

    public static void encode(ManaDataSyncS2CPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerId);
        buf.writeNbt(msg.manaData);
    }

    public static ManaDataSyncS2CPacket decode(FriendlyByteBuf buf) {
        UUID playerId = buf.readUUID();
        CompoundTag tag = buf.readNbt();
        return new ManaDataSyncS2CPacket(playerId, tag);
    }

    public static void handle(ManaDataSyncS2CPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Called only on the client
            ClientManaData.set(msg.getPlayerId(), msg.getManaData());
        });
        ctx.get().setPacketHandled(true);
    }
}
