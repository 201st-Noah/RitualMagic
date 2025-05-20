package be.noah.ritual_magic.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class RiftOpener {
    private final ServerLevel level;
    private final Vec3 direction;
    private final BlockPos origin;
    private int currentStep = 0;
    private final int maxSteps = 40;

    public RiftOpener(ServerLevel level, BlockPos origin, Vec3 direction) {
        this.level = level;
        this.origin = origin;
        this.direction = direction.normalize();
    }

    public void tick() {
        if (currentStep >= maxSteps) return;

        int length = maxSteps;
        int width = getWidth(currentStep, length);
        int depth = getDepth(currentStep, length);

        Vec3 offset = direction.scale(currentStep);
        BlockPos center = origin.offset((int) offset.x, 0, (int) offset.z);

        RandomSource rand = level.getRandom();

        for (int x = -width; x <= width; x++) {
            for (int z = -width; z <= width; z++) {
                if (x * x + z * z <= width * width + rand.nextInt(2)) {
                    BlockPos surface = center.offset(x, 0, z);
                    BlockPos top = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, surface);

                    for (int y = 0; y < depth; y++) {
                        BlockPos digPos = top.below(y);
                        level.setBlockAndUpdate(digPos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }

        currentStep++;
    }

    private int getWidth(int step, int total) {
        double progress = (double) step / total;
        return (int) (2 + 6 * Math.sin(Math.PI * progress));
    }

    private int getDepth(int step, int total) {
        double progress = (double) step / total;
        return (int) (2 + 4 * Math.sin(Math.PI * progress));
    }

    public boolean isDone() {
        return currentStep >= maxSteps;
    }
}
