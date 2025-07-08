package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.mana.ManaType;
import be.noah.ritual_magic.mana.MetalToManaValueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MetalExtractionBlockEntity extends RitualBaseBlockEntity {

    protected final ContainerData data;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private int progress = 0;
    private static final int MAX_PROGRESS = 20;
    public final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return MetalToManaValueRegistry.getManaValue(stack.getItem()) > 0;
        }
    };

    public MetalExtractionBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.METAL_EXTRACTION_RITUAL.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return 0;
            }

            @Override
            public void set(int pIndex, int pValue) {}

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Override
    public void tick() {
        if (level.isClientSide()) return;
        ItemStack input = this.itemStackHandler.getStackInSlot(0);
        int manaValue = MetalToManaValueRegistry.getManaValue(input.getItem());

        if (!input.isEmpty() && manaValue > 0 && !ifFullMana(this.level, this.getOwner())) {
            this.progress++;

            if (this.progress >= getProgress()) {
                input.shrink(1);
                this.itemStackHandler.setStackInSlot(0, input);
                this.addMana(this.level, this.getOwner(), manaValue);
                this.progress = 0;
                this.setChanged();
            }
        } else {
            this.progress = 0;
        }
    }

    private int getProgress() {
        switch (getBlockTier(this.level, this.worldPosition)) {
            case INTERMEDIATE -> {return 40;}
            case ADVANCED -> {return 20;}
            case ULTIMATE -> {return 10;}
            default -> {return 100;}
        }
    }

    @Override
    public @NotNull ManaType getManaType() {
        return ManaType.DWARVEN;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemStackHandler.serializeNBT());
        tag.putInt("progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemStackHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("progress");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}
