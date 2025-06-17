package be.noah.ritual_magic.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class UnstableMagmaBlock extends MagmaBlock {

    private static final Random random = new Random();

    public UnstableMagmaBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.POINTED_DRIPSTONE_DRIP_LAVA, SoundSource.NEUTRAL,
                1.0F, 1.0F);
        level.removeBlock(pos, false);
        level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, random.nextInt(0, 100) + 200);
        }
    }

    @Override
    public boolean isPossibleToRespawnInThis(BlockState state) {
        return false;
    }
}
