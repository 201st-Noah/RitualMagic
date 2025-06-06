package be.noah.ritual_magic.block.entity;

import be.noah.ritual_magic.block.custom.InfusionBlock;
import be.noah.ritual_magic.block.custom.RitualPedestalBlock;
import be.noah.ritual_magic.recipe.InfusionRecipe;
import be.noah.ritual_magic.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InfusionBlockEntity extends BlockEntity {
    private static final int SLOT = 0;
    protected final ContainerData data;
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged(); // Mark the block entity as dirty when inventory changes
        }
    };
    private final BlockPos pos;
    private final int progress = 0;
    private final int maxProgress = 78;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private UUID owner;
    private float rotation;

    public InfusionBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.INFUSION.get(), pPos, pBlockState);
        this.pos = pPos;
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return 0;
            }

            @Override
            public void set(int pIndex, int pValue) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfusionBlockEntity be) {
        if (level.isClientSide()) return;
        be.tryCraft();
    }

    public void tryCraft() {
        if (level == null || level.isClientSide()) return;

        SimpleContainer center = new SimpleContainer(1);
        center.setItem(0, itemStackHandler.getStackInSlot(0));
        List<InfusionRecipe> recipes = level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.INFUSION_TYPE.get());


        for (InfusionRecipe recipe : recipes) {
            if (!recipe.matches(center, level, (InfusionBlock) this.getBlockState().getBlock())) continue;

            List<ItemStack> pedestalItems = new ArrayList<>();
            BlockPos.betweenClosedStream(worldPosition.offset(-3, -1, -3), worldPosition.offset(3, 1, 3)).forEach(pos -> {
                if (level.getBlockEntity(pos) instanceof RitualPedestalBlockEntity pedestal &&
                        (((RitualPedestalBlock) pedestal.getBlockState().getBlock()).getManaType()) == ((InfusionBlock) this.getBlockState().getBlock()).getManaType()) {
                    ItemStack s = pedestal.getItemStackHandler().getStackInSlot(0);
                    if (!s.isEmpty()) pedestalItems.add(s);
                }
            });

            if (recipe.pedestalItemsMatch(pedestalItems) && recipe.canConsumeMana(this, (ServerLevel) this.level)) {
                ItemStack result = recipe.assemble(center, level.registryAccess());
                itemStackHandler.setStackInSlot(0, result);
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);

                // Clear pedestals
                for (BlockPos pos : BlockPos.betweenClosed(worldPosition.offset(-3, -1, -3), worldPosition.offset(3, 1, 3))) {
                    if (level.getBlockEntity(pos) instanceof RitualPedestalBlockEntity pedestal) {
                        pedestal.getItemStackHandler().setStackInSlot(0, ItemStack.EMPTY);
                        pedestal.setChanged();
                        level.sendBlockUpdated(pos, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
                    }
                }
                level.playSound(null, worldPosition, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                break;
            }
        }
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
        setChanged();
    }

    public float getRotation() {
        rotation += 0.5f;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public void clearContents() {
        itemStackHandler.setStackInSlot(0, ItemStack.EMPTY);
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

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemStackHandler.serializeNBT());
        if (owner != null) {
            pTag.putUUID("Owner", owner);
        }
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.hasUUID("Owner")) {
            owner = pTag.getUUID("Owner");
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }


        return super.getCapability(cap, side);
    }

    @Override
    @NotNull
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        assert pkt.getTag() != null;
        load(pkt.getTag());
    }
}
