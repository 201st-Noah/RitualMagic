package be.noah.ritual_magic.networking.packet;

import be.noah.ritual_magic.client.ClientHighlightManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockHighlightS2CPacket {
    private final List<BlockPos> positions;

    public BlockHighlightS2CPacket(List<BlockPos> positions) {
        this.positions = positions;
    }

    public static void encode(BlockHighlightS2CPacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.positions.size());
        for (BlockPos pos : msg.positions) {
            buf.writeBlockPos(pos);
        }
    }

    public static BlockHighlightS2CPacket decode(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        List<BlockPos> positions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            positions.add(buf.readBlockPos());
        }
        return new BlockHighlightS2CPacket(positions);
    }

    public static void handle(BlockHighlightS2CPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHighlightManager.highlightBlocks(msg.positions, 400); // 5 seconds at 20 TPS
        });
        ctx.get().setPacketHandled(true);
    }
}
