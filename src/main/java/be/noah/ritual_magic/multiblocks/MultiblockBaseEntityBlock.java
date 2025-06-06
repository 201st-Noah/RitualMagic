package be.noah.ritual_magic.multiblocks;

import net.minecraft.world.level.block.BaseEntityBlock;

public abstract class MultiblockBaseEntityBlock extends BaseEntityBlock {
    protected MultiblockBaseEntityBlock(Properties pProperties) {
        super(pProperties);
    }

    public abstract MultiBlockStructure getStructure();
}
