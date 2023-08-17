package be.noah.ritual_magic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;

import static net.minecraft.world.Containers.dropContents;

public class ItemHolderBlockEntity extends BlockEntity {

    private final ItemStackHandler inventory = new ItemStackHandler(1);
    private final LazyOptional<IItemHandlerModifiable> optional = LazyOptional.of(()-> this.inventory);
    public ItemHolderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ITEM_HOLDER.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.inventory.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", this.inventory.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        this.optional.invalidate();
    }
    public ItemStackHandler getInventory() {
        return inventory;
    }
    public static void dropItemStack(Level pLevel, double pX, double pY, double pZ, ItemStack pStack) {
        double d0 = (double) EntityType.ITEM.getWidth();
        double d1 = 1.0D - d0;
        double d2 = d0 / 2.0D;
        double d3 = Math.floor(pX) + pLevel.random.nextDouble() * d1 + d2;
        double d4 = Math.floor(pY) + pLevel.random.nextDouble() * d1;
        double d5 = Math.floor(pZ) + pLevel.random.nextDouble() * d1 + d2;
        while(!pStack.isEmpty()) {
            System.out.println(pStack.getCount());
            ItemEntity itementity = new ItemEntity(pLevel, d3, d4, d5, pStack.split(pLevel.random.nextInt(21) + 10));
            float f = 0.05F;
            itementity.setDeltaMovement(pLevel.random.triangle(0.0D, 0.11485000171139836D), pLevel.random.triangle(0.2D, 0.11485000171139836D), pLevel.random.triangle(0.0D, 0.11485000171139836D));
            pLevel.addFreshEntity(itementity);
        }

    }
}
