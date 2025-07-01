package be.noah.ritual_magic.blocks.custom;

import be.noah.ritual_magic.blocks.*;
import be.noah.ritual_magic.blocks.entity.AntiTeleportRitualBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AntiTeleportRitualBlock extends RitualBaseBlock {

    public AntiTeleportRitualBlock(BlockTier tier, Properties pProperties) {
        super(tier, pProperties, ModBlockEntities.ANTI_TELEPORTATION_RITUAL);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AntiTeleportRitualBlockEntity(pPos, pState);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide && pPlacer instanceof Player player) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof RitualBaseBlockEntity blockEntity) {
                blockEntity.setOwner(player.getUUID());
                AntiTeleportBlockData.get((ServerLevel) pLevel).add(this.getBlockTier(), pPos);
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void onRemove(BlockState state, Level pLevel, BlockPos pPos, BlockState newState, boolean isMoving) {
        if (!pLevel.isClientSide) {
            AntiTeleportBlockData.get((ServerLevel) pLevel).remove(this.getBlockTier(), pPos);
        }
        super.onRemove(state, pLevel, pPos, newState, isMoving);
    }
}
