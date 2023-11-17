package be.noah.ritual_magic.block.entity;

import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SoulForgeBlockEntity extends BlockEntity {
    public SoulForgeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SOUL_FORGE.get(), pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, ConduitBlockEntity pBlockEntity){

    }
    private static boolean structureOK(Level pLevel, BlockPos pPos, List<BlockPos> pPositions){
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();

        BlockPos checkPos = new BlockPos(x+0, y+0, z+0);
        BlockState blockState = pLevel.getBlockState(checkPos);
        if (blockState.getBlock() != Blocks.OBSIDIAN) {
            return false;
        }


        return false;
    }
}
