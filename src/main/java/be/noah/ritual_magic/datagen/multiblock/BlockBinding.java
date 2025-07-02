package be.noah.ritual_magic.datagen.multiblock;

import net.minecraft.world.level.block.Block;

import java.util.Set;

public record BlockBinding(Set<Block> blocks, char symbol) {
    public BlockBinding(Block block, char symbol) {
        this(Set.of(block), symbol);
    }
}
