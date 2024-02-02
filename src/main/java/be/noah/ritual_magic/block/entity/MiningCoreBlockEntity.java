package be.noah.ritual_magic.block.entity;

import be.noah.ritual_magic.Multiblocks.MultiBlockLayer;
import be.noah.ritual_magic.Multiblocks.MultiBlockStructure;
import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MiningCoreBlockEntity extends BlockEntity {
    public static MultiBlockLayer getMultiBlockLayer(){
        MultiBlockLayer layer = new MultiBlockLayer(ModBlocks.DWARVEN_STEEL_BLOCK.get());
        layer.addCircle(6,0,0,0, 0);
        return layer;
    }
    public MiningCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MINING_CORE.get(), pPos, pBlockState);
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState){
        int x = pPos.getX();
        int y = pPos.getY(); //Höhe (ja ich brauch das)
        int z = pPos.getZ();
        boolean isCorrect = false;
        if (pLevel.getGameTime() % 2L == 0L) {
            isCorrect = updateStructure(pLevel, x, y, z);

            //System.out.println(pLevel.getBlockState(new BlockPos(x, y-1 , z)));
            if (isCorrect) {
                System.out.println("Jaaaaaaaaaaaaaaaaaaaaaaa");
            } else {
                System.out.println("Neiiiiiiiiiiiiiiiiiiiiiiin");
            }
        }
    }
    private boolean updateStructure(Level pLevel, int pX, int pY, int pZ){
        return MultiBlockStructure.test().checkStructure(3,pLevel,pX,pY,pZ);
    }
}
