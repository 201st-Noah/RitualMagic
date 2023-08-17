package be.noah.ritual_magic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
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
        super.saveAdditional(pTag);
        pTag.put("inventory", this.inventory.serializeNBT());
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
    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
    public void drops() {
        SimpleContainer inventory1 = new SimpleContainer(inventory.getSlots());
        inventory.setStackInSlot(0, inventory.getStackInSlot(0));
    }

}
