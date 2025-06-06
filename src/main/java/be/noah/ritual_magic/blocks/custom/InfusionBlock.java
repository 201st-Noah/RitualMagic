package be.noah.ritual_magic.blocks.custom;

import be.noah.ritual_magic.blocks.BlockTier;
import be.noah.ritual_magic.blocks.entity.InfusionBlockEntity;
import be.noah.ritual_magic.blocks.entity.ModBlockEntities;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class InfusionBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);
    private final ManaType type;
    private final BlockTier tier;

    public InfusionBlock(ManaType type, BlockTier tier, Properties pProperties) {
        super(pProperties);
        this.type = type;
        this.tier = tier;
    }

    public ManaType getManaType() {
        return type;
    }

    public BlockTier getTier() {
        return tier;
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
        return new InfusionBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof InfusionBlockEntity) {
                ((InfusionBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack pStack = pPlayer.getItemInHand(pHand);
        if (pLevel.getBlockEntity(pPos) instanceof InfusionBlockEntity InfusionBlockEntity) {
            if (InfusionBlockEntity.getItemStackHandler().getStackInSlot(0).isEmpty() && !pStack.isEmpty()) {
                ItemStack singleItem = pStack.copy();
                singleItem.setCount(1);
                InfusionBlockEntity.getItemStackHandler().insertItem(0, singleItem, false);
                pStack.shrink(1);
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if (pStack.isEmpty()) {
                ItemStack extracted = InfusionBlockEntity.getItemStackHandler().extractItem(0, 1, false);
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, extracted);
                InfusionBlockEntity.clearContents();
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            } else if (InfusionBlockEntity.getItemStackHandler().getStackInSlot(0).getItem() == pStack.getItem() && pStack.getItem().getMaxStackSize(pStack) > pStack.getCount()) {
                ItemStack extracted = InfusionBlockEntity.getItemStackHandler().extractItem(0, 1, false);
                if (!extracted.isEmpty()) {
                    pStack.grow(1);
                    InfusionBlockEntity.clearContents();
                    pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                }
            }
            if (!pLevel.isClientSide) {
                pLevel.sendBlockUpdated(pPos, pState, pState, 3);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.INFUSION.get(), InfusionBlockEntity::tick);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide && pPlacer instanceof Player player) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof InfusionBlockEntity blockEntity) {
                blockEntity.setOwner(player.getUUID());
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }
}
