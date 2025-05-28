package be.noah.ritual_magic.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VoidShieldDataSyncS2CPacket {
    private final int hitsLeft;

    public VoidShieldDataSyncS2CPacket(int hitsLeft) {
        this.hitsLeft = hitsLeft;
    }

    public static void encode(VoidShieldDataSyncS2CPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.hitsLeft);
    }

    public static VoidShieldDataSyncS2CPacket decode(FriendlyByteBuf buf) {
        return new VoidShieldDataSyncS2CPacket(buf.readInt());
    }

    public static void handle(VoidShieldDataSyncS2CPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Update player persistent data
            CompoundTag tag = Minecraft.getInstance().player.getPersistentData();
            tag.putInt("void_shield", msg.hitsLeft);
        });
        ctx.get().setPacketHandled(true);
    }
}
