package be.noah.ritual_magic.blocks.custom;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.entity.MiningCoreBlockEntity;
import be.noah.ritual_magic.multiblocks.MultiBlockStructure;
import be.noah.ritual_magic.multiblocks.MultiblockBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MiningCoreBlock extends MultiblockBaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    private static final MultiBlockStructure structure = null;// MultiBlockStructure.forgeT2();  // todo: to correct

    public MiningCoreBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public MultiBlockStructure getStructure() {
        return structure;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MiningCoreBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.MINING_CORE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));

    }
}
