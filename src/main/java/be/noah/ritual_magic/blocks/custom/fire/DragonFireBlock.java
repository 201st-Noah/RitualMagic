package be.noah.ritual_magic.blocks.custom.fire;

import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/*
    This works the fire can be placed on the right blocks, but no flint and steel usage possible( as intended),
    the textures of the soul_fire are added in the right place just the colors need to be changed.
    And none of the Model or Blockstate stuff is done I have no clue how to do it with the Datagen,
    but in the end the generated file should look like the soul_fire stuff (viel spass Luka)
 */
public class DragonFireBlock extends BaseFireBlock {
    public DragonFireBlock(BlockBehaviour.Properties p_56653_) {
        super(p_56653_, 4.0F);
    }

    public static boolean canSurviveOnBlock(BlockState pState) {
        return pState.is(ModTags.Blocks.DRAGON_FIRE_BASE_BLOCK);
    }

    public static BlockState getState(BlockGetter pReader, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pReader.getBlockState(blockpos);
        if (DragonFireBlock.canSurviveOnBlock(blockstate)) {
            return ModBlocks.DRAGON_FIRE.get().defaultBlockState();
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return this.canSurvive(pState, pLevel, pCurrentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSurviveOnBlock(pLevel.getBlockState(pPos.below()));
    }

    @Override
    protected boolean canBurn(BlockState pState) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return getState(pContext.getLevel(), pContext.getClickedPos());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
