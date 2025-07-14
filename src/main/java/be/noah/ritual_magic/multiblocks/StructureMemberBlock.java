package be.noah.ritual_magic.multiblocks;

import be.noah.ritual_magic.blocks.RitualBaseBlock;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public interface StructureMemberBlock {

    List<MultiBlockStructure> getPossibleStructures();

    default void findAndMarkControllerDirty(Level level, BlockPos origin) {
        if (level.isClientSide) return;

        int radius = getMaxStructureRadius();
        BlockPos.betweenClosedStream(origin.offset(-radius, -radius, -radius), origin.offset(radius, radius, radius))
                .filter(pos -> level.getBlockState(pos).getBlock() instanceof RitualBaseBlock<?>)
                .forEach(pos -> {
                    BlockEntity be = level.getBlockEntity(pos);
                    if (be instanceof RitualBaseBlockEntity controller) {
                            controller.setStructureDirty();
                    }
                });
    }

    default int getMaxStructureRadius() {
        return getPossibleStructures().stream()
                .mapToInt(MultiBlockStructure::getMaxSize) //TODO Luka, implement getMaxSize in MultiBlockStructure
                .max()
                .orElse(8); // fallback
    }
}
