package be.noah.ritual_magic.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class IceSpikeBlock extends Block {

    private static final Random random = new Random();

    public IceSpikeBlock(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {

        pEntity.setIsInPowderSnow(true);
        if (!pLevel.isClientSide && pEntity instanceof LivingEntity) {
            double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
            double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
            if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
                pEntity.hurt(pLevel.damageSources().freeze(), 6.0F);
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, random.nextInt(0, 400) + 400);
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.getBrightness(LightLayer.BLOCK, pPos) > 11 - pState.getLightBlock(pLevel, pPos)) {
            this.melt(pState, pLevel, pPos);
        }

    }

    protected void melt(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.dimensionType().ultraWarm()) {
            pLevel.removeBlock(pPos, false);
        } else {
            pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
            pLevel.neighborChanged(pPos, Blocks.AIR.defaultBlockState().getBlock(), pPos);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL,
                1.0F, 1.0F);
        level.removeBlock(pos, false);
    }
}
