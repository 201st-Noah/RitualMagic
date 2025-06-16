package be.noah.ritual_magic.blocks;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RitualBaseBlock<T extends RitualBaseBlockEntity>  extends BaseEntityBlock {

    private final RegistryObject<BlockEntityType<T>> blockEntityType;
    private final BlockEntityTicker<T> ticker;
    private BlockTier tier;

    protected RitualBaseBlock(BlockTier tier, Properties pProperties, RegistryObject<BlockEntityType<T>>  blockEntityType) {
        super(pProperties);
        this.blockEntityType = blockEntityType;
        this.ticker = RitualBaseBlockEntity.createTicker();
        this.tier = tier;
    }

    @NotNull
    public BlockTier getBlockTier(){
        return tier;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide && pPlacer instanceof Player player) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof RitualBaseBlockEntity blockEntity) {
                blockEntity.setOwner(player.getUUID());
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public <E extends BlockEntity> BlockEntityTicker<E> getTicker(Level level, BlockState state, BlockEntityType<E> type) {
        if (type != blockEntityType.get()) return null;
        @SuppressWarnings("unchecked")
        BlockEntityTicker<E> tickerCast = (BlockEntityTicker<E>) ticker;
        return tickerCast;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

}
