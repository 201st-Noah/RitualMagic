package be.noah.ritual_magic.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IceSpikeBlock extends Block {

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
            if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                pEntity.hurt(pLevel.damageSources().freeze(), 6.0F);
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }
}
