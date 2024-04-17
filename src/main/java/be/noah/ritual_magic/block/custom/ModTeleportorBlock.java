package be.noah.ritual_magic.block.custom;

import be.noah.ritual_magic.Multiblocks.MultiBlockStructure;
import be.noah.ritual_magic.Multiblocks.MultiblockBaseEntityBlock;
import be.noah.ritual_magic.block.entity.ModBlockEntities;
import be.noah.ritual_magic.block.entity.ModTeleportorEntity;
import be.noah.ritual_magic.worldgen.dimension.ModDimensions;
import be.noah.ritual_magic.worldgen.portal.ModTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ModTeleportorBlock extends MultiblockBaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,16,16);
    private static final MultiBlockStructure structure = MultiBlockStructure.getTeleporterStruct();
    public ModTeleportorBlock(Properties pProperties) {
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
        return RenderShape.MODEL ;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.MOD_TELEPORTER.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));

    }
        @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if(blockentity instanceof  ModTeleportorEntity) {
            if (pPlayer.canChangeDimensions() && ((ModTeleportorEntity) blockentity).MULTIBLOCK_OK) {
                handleInfinityPortal(pPlayer, pPos);
                return InteractionResult.SUCCESS;
            } else {return InteractionResult.CONSUME;}
        }else {return InteractionResult.CONSUME;}
    }

    private void handleInfinityPortal(Entity player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.INFINITY_NEXUS_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.INFINITY_NEXUS_LEVEL_KEY;

            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == ModDimensions.INFINITY_NEXUS_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, false));
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ModTeleportorEntity(pPos, pState);
    }
}
