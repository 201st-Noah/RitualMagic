package be.noah.ritual_magic.block.entity;

import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MiningCoreBlockEntity extends BlockEntity {

    public MiningCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MINING_CORE.get(), pPos, pBlockState);
    }
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState){
        int x = pPos.getX();
        int y = pPos.getY(); //Höhe (ja ich brauch das)
        int z = pPos.getZ();
        boolean isCorrect = false;
        if (pLevel.getGameTime() % 81L == 0L) {
            isCorrect = updateStructure(pLevel, x, y, z);

            //System.out.println(pLevel.getBlockState(new BlockPos(x, y-1 , z)));
            if (isCorrect) {
                System.out.println("Jaaaaaaaaaaaaaaaaaaaaaaa");
            } else {
                System.out.println("Neiiiiiiiiiiiiiiiiiiiiiiin");
            }
        }

    }
    private static boolean updateStructure(Level pLevel, int pX, int pY, int pZ){
        Block layer1 = ModBlocks.POLISHED_OBSIDIAN.get();
        if (pLevel.getBlockState(new BlockPos(pX+2, pY, pZ-1 )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX+2, pY, pZ )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX+2, pY, pZ-1 )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX-2, pY, pZ-1)).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX-2, pY, pZ )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX-2, pY, pZ+1 )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX-1, pY, pZ+2 )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX, pY, pZ+2 )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX+1, pY, pZ+2 )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX-1, pY, pZ-2 )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX, pY, pZ-2 )).is(layer1) &&
                pLevel.getBlockState(new BlockPos(pX+1, pY, pZ-2 )).is(layer1)) {
            return true;
        }
        else {
            return false;
        }
    }
}
