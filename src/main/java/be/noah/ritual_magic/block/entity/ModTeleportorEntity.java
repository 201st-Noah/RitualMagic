package be.noah.ritual_magic.block.entity;

import be.noah.ritual_magic.Multiblocks.MultiBlockLayer;
import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ModTeleportorEntity extends BlockEntity {
    public boolean MULTIBLOCK_OK = false;
    public static MultiBlockLayer getMultiBlockLayer(){
        MultiBlockLayer layer = new MultiBlockLayer(ModBlocks.DWARVEN_STEEL_BLOCK.get());
        layer.addCircle(3,0,0,0, 2);
        return layer;
    }
    public ModTeleportorEntity( BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOD_TELEPORTER.get(), pPos, pBlockState);
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState){
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();
        if (pLevel.getGameTime() % 79L == 0L) {
            if (updateStructure(pLevel, x, y, z)) {
                this.MULTIBLOCK_OK = true;
            } else {
                this.MULTIBLOCK_OK = false;
            }
        }

    }
    private boolean updateStructure(Level pLevel, int pX, int pY, int pZ){
        return ModTeleportorEntity.getMultiBlockLayer().checkLayer(pLevel, pX, pY, pZ);
    }
}
