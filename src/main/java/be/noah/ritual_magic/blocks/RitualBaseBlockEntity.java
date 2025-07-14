package be.noah.ritual_magic.blocks;

import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaType;
import be.noah.ritual_magic.multiblocks.MultiBlockStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class RitualBaseBlockEntity extends BlockEntity {

    private UUID owner;
    private MultiBlockStructure structure;
    private boolean structureIsOk;
    private boolean structureIsDirty;

    public RitualBaseBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public static BlockTier getBlockTier(Level pLevel, BlockPos pPos) {
        if(pLevel == null) return null;
        BlockState state = pLevel.getBlockState(pPos);
        Block block = state.getBlock();
        if (block instanceof RitualBaseBlock ritualBlock) {
            return ritualBlock.getBlockTier();
        }
        return BlockTier.BASIC;
    }

    public abstract void tick();

    public void baseTick() {
        if (level != null && !level.isClientSide && level.getGameTime() % 600 == 1) {
            updateStructure(level, worldPosition);
            System.out.println("Structure is " + structureIsOk() + " updatedPerformed Successfully at" + worldPosition);
        }
        tick();
    }

    public static <T extends RitualBaseBlockEntity> BlockEntityTicker<T> createTicker() {
        return (level, pos, state, blockEntity) -> {
                blockEntity.baseTick();
        };
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (owner != null) {
            tag.putUUID("Owner", owner);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.hasUUID("Owner")) {
            owner = tag.getUUID("Owner");
        } else {
            owner = null;
        }
    }

    @NotNull
    public abstract ManaType getManaType();

    public boolean consumeMana(Level level,UUID owner, int amount) {
        ServerLevel serverLevel = (ServerLevel) level;
        ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
        return data.consume(owner, getManaType(), amount);
    }
    public void addMana(Level level,UUID owner, int amount) {
        ServerLevel serverLevel = (ServerLevel) level;
        ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
        data.add(owner, getManaType(), amount);
    }
    public boolean ifFullMana(Level level,UUID owner) {
        ServerLevel serverLevel = (ServerLevel) level;
        ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
        return data.isFull(owner, getManaType());
    }


    //Multiblock Code

    public void loadMultiblockStructure() {
        //TODO: Implement this Method later
        MultiBlockStructure structure = null;
        //Loader loader = new Loader();
        //structure = loader.load(structureName);
        this.structure = structure;
    }

    @Nullable
    public MultiBlockStructure getStructure() {
        return structure;
    }

    public void updateStructure(Level level, BlockPos pos) {
        if (getStructure() == null) {
            structureIsOk = true;
            return;
        }
        structureIsOk = getStructure().checkStructure(0, level, pos);
        structureIsDirty = false;
    }

    public boolean structureIsOk() {
        if (getStructure() == null) return true;
        if(!isStructureDirty()) return structureIsOk;
        updateStructure(level, worldPosition);
        return structureIsOk;
    }

    public void setStructureDirty() {
        structureIsDirty = true;
    }

    public boolean isStructureDirty(){
        return structureIsDirty;
    }
}
