package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.multiblocks.MultiBlockStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class ModTeleporterEntity extends BlockEntity {
    private static final MultiBlockStructure structure = MultiBlockStructure.getTeleporterStruct();
    public boolean MULTIBLOCK_OK = false;

    public ModTeleporterEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOD_TELEPORTER.get(), pPos, pBlockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();
        if (pLevel.getGameTime() % 79L == 0L) {
            this.MULTIBLOCK_OK = updateStructure(pLevel, x, y, z);
        }

    }

    private boolean updateStructure(Level pLevel, int pX, int pY, int pZ) {
        return structure.checkStructure(pLevel, pX, pY, pZ);
    }
}
