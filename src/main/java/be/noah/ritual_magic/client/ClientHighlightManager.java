package be.noah.ritual_magic.client;

import net.minecraft.core.BlockPos;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClientHighlightManager {
    public static final Set<BlockPos> HIGHLIGHTED_BLOCKS = new HashSet<>();
    public static int ticksLeft = 0;

    public static void highlightBlocks(Collection<BlockPos> positions, int durationTicks) {
        HIGHLIGHTED_BLOCKS.clear();
        HIGHLIGHTED_BLOCKS.addAll(positions);
        ticksLeft = durationTicks;
    }

    public static void tick() {
        if (ticksLeft > 0) {
            ticksLeft--;
            if (ticksLeft == 0) HIGHLIGHTED_BLOCKS.clear();
        }
    }
}
