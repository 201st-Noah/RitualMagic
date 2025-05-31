package be.noah.ritual_magic.block.custom;

import be.noah.ritual_magic.block.entity.AncientAnvilBlockEntity;
import be.noah.ritual_magic.block.entity.RitualPedestalBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.levelgen.structure.Structure.simpleCodec;

public class RitualPedestalBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

    public RitualPedestalBlock(Properties pProperties) {
        super(pProperties);
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
        return new RitualPedestalBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof RitualPedestalBlockEntity) {
                ((RitualPedestalBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack pStack = pPlayer.getItemInHand(pHand);
        if(pLevel.getBlockEntity(pPos) instanceof RitualPedestalBlockEntity RitualPedestalBlockEntity) {
            if(RitualPedestalBlockEntity.getItemStackHandler().getStackInSlot(0).isEmpty() && !pStack.isEmpty()) {
                RitualPedestalBlockEntity.getItemStackHandler().insertItem(0, pStack.copy(), false);
                pStack.shrink(1);
                if (pLevel != null && !pLevel.isClientSide) {
                    pLevel.sendBlockUpdated(pPos, pState, pState, 3);
                }
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if(pStack.isEmpty()) {
                ItemStack stackOnPedestal = RitualPedestalBlockEntity.getItemStackHandler().extractItem(0, 1, false);
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, stackOnPedestal);
                RitualPedestalBlockEntity.clearContents();
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
