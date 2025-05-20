package be.noah.ritual_magic.block.custom;

import be.noah.ritual_magic.Multiblocks.MultiBlockLayer;
import be.noah.ritual_magic.Multiblocks.MultiBlockStructure;
import be.noah.ritual_magic.Multiblocks.MultiblockBaseEntityBlock;
import be.noah.ritual_magic.block.entity.AncientAnvilBlockEntity;
import be.noah.ritual_magic.block.entity.ForgeBlockEntity;
import be.noah.ritual_magic.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends MultiblockBaseEntityBlock {
    public enum Tier {BEGINNER, BASIC, ADVANCED}
    private static final MultiBlockStructure structureT1 = MultiBlockStructure.forgeT1();
    private static final MultiBlockStructure structureT2 = MultiBlockStructure.forgeT2();
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,16,16);
    private final Tier tier;
    public ForgeBlock(Tier tier, Properties pProperties) {
        super(pProperties);
        this.tier = tier;
    }
    public MultiBlockStructure getStructure() {
        switch (tier){
            case BEGINNER -> {return new  MultiBlockStructure(new MultiBlockLayer(Blocks.AIR,0,0,0));}
            case BASIC -> {return structureT1;}
            case ADVANCED -> {return  structureT2;}
            default -> {return null;}
        }
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL ;
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ForgeBlockEntity( pPos, pState);
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof ForgeBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (ForgeBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ForgeBlockEntity) {
                ((ForgeBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.FORGE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}

