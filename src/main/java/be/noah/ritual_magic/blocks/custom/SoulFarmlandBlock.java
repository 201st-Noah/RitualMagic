package be.noah.ritual_magic.blocks.custom;

import be.noah.ritual_magic.blocks.BlockTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;

public class SoulFarmlandBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private final BlockTier tier;

    public SoulFarmlandBlock(BlockTier tier, Properties pProperties) {
        super(pProperties);
        this.tier = tier;
    }

    public BlockTier getTier() {
        return tier;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.above());
        return !blockstate.isSolid() || blockstate.getBlock() instanceof FenceGateBlock || blockstate.getBlock() instanceof MovingPistonBlock;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return !this.defaultBlockState().canSurvive(pContext.getLevel(), pContext.getClickedPos()) ? Blocks.DIRT.defaultBlockState() : super.getStateForPlacement(pContext);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (!pLevel.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(pLevel, pPos, Blocks.DIRT.defaultBlockState(), pFallDistance, pEntity)) { // Forge: Move logic to Entity#canTrample
            turnToSoulSoil(pEntity, pState, pLevel, pPos);
        }

        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
    }

    public static void turnToSoulSoil(@Nullable Entity pEntity, BlockState pState, Level pLevel, BlockPos pPos) {
        BlockState blockstate = pushEntitiesUp(pState, Blocks.SOUL_SOIL.defaultBlockState(), pLevel, pPos);
        pLevel.setBlockAndUpdate(pPos, blockstate);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockPos abovePos = pPos.above();
        BlockState aboveState = pLevel.getBlockState(abovePos);
        if (aboveState.getBlock() instanceof BonemealableBlock) {
            aboveState.randomTick(pLevel, abovePos, pRandom);
        }
        pLevel.scheduleTick(pPos, this, getTickRate());
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        level.scheduleTick(pos, this, getTickRate());
    }

    private int getTickRate() {
        switch (this.tier){
            case BASIC: return 40;
            case INTERMEDIATE: return 20;
            case ADVANCED: return 10;
            case ULTIMATE: return 2;
            default: return 40;
        }
    }
}
