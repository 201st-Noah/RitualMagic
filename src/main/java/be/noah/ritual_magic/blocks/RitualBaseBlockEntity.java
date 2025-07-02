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

    public RitualBaseBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public static BlockTier getBlockTier(Level pLevel, BlockPos pPos) {
        if (pLevel == null) return null;
        BlockState state = pLevel.getBlockState(pPos);
        Block block = state.getBlock();
        if (block instanceof RitualBaseBlock ritualBlock) {
            return ritualBlock.getBlockTier();
        }
        return BlockTier.BASIC;
    }

    public static <T extends RitualBaseBlockEntity> BlockEntityTicker<T> createTicker() {
        return (level, pos, state, blockEntity) -> {
            blockEntity.tick();
        };
    }

    public abstract void tick();

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

    @Nullable
    public MultiBlockStructure getStructure() {
        return null;
    }

    public boolean consumeMana(Level level, UUID owner, int amount) {
        ServerLevel serverLevel = (ServerLevel) level;
        ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
        return data.consume(owner, getManaType(), amount);
    }

    public void addMana(Level level, UUID owner, int amount) {
        ServerLevel serverLevel = (ServerLevel) level;
        ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
        data.add(owner, getManaType(), amount);
    }

    public boolean structureIsOk(Level level, BlockPos position) {
        if (getStructure() == null)
            return true;
        return getStructure().checkStructure(level, position.getX(), position.getY(), position.getZ());
    }
}
