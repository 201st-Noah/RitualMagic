package be.noah.ritual_magic.block.entity;

import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
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
        //System.out.println(pLevel.getBlockState(new BlockPos(x, y-1 , z)));
        if(updateStructure(pLevel, x,y,z) == 1){
            System.out.println("Jaaaaaaaaaaaaaaaaaaaaaaa");
        }
        else {
            System.out.println("Neiiiiiiiiiiiiiiiiiiiiiiin");
        }


    }
    private static int updateStructure(Level pLevel, int pX, int pY, int pZ){
        if (pLevel.getBlockState(new BlockPos(pX, pY-1, pZ )).is(ModBlocks.POLISHED_OBSIDIAN.get())) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
