package be.noah.ritual_magic.Multiblocks;

import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockStructure {
    private List<MultiBlockLayer> layers;
    public MultiBlockStructure(List<MultiBlockLayer> layers){
        this.layers = layers;
    }
    public MultiBlockStructure(MultiBlockLayer layer){
        this.layers = new ArrayList<>();
        this.layers.add(layer);
    }
    public boolean checkStructure(int symmetry,Level pLevel, int pX, int pY, int pZ){
        boolean res = true;
        for (MultiBlockLayer layer : layers) {
            res = res && layer.checkLayer(symmetry,pLevel,pX,pY,pZ);
            if(!res)
                return false;
        }
        return res;
    }
    public void buildStructure(Level pLevel, int pX, int pY, int pZ){
        for(MultiBlockLayer layer : layers){
            if(layer.buildLayer(pLevel, pX, pY, pZ)){
                break;
            }
        }
    }
    public static MultiBlockStructure pyramid(Block block, int h){
        MultiBlockLayer layer = new MultiBlockLayer(block);
        for(int i = 1; i < h; i++){
            layer.addSquare(MultiBlockLayer.Mode.CENTER, i*2+1, i*2+1, 0, -i, 0, 2);
        }
        return new MultiBlockStructure(layer);
    }

    public static MultiBlockStructure getTeleporterStruct(){
        MultiBlockLayer layer0 = new MultiBlockLayer(ModBlocks.DWARVEN_STEEL_BLOCK.get());
        layer0.addCircle(3,0,0,0, 2);
        MultiBlockLayer layer1 = new MultiBlockLayer(ModBlocks.PETRIFIED_DRAGON_SCALE.get());
        layer1.addCircle(6,0,0,0, 2);
        layer1.deletePos(6,0,0);
        layer1.deletePos(-6,0,0);
        layer1.deletePos(0,0,6);
        layer1.deletePos(0,0,-6);
        MultiBlockLayer layer2 = new MultiBlockLayer(ModBlocks.PETRIFIED_DRAGON_SCALE.get());
        layer2.addCircle(6,0,0,0, 1);
        layer2.deletePos(0,6,0);
        layer2.deletePos(0,-6,0);
        layer2.deletePos(0,0,6);
        layer2.deletePos(0,0,-6);
        MultiBlockLayer layer3 = new MultiBlockLayer(ModBlocks.PETRIFIED_DRAGON_SCALE.get());
        layer3.addCircle(6,0,0,0, 0);
        layer3.deletePos(0,6,0);
        layer3.deletePos(0,-6,0);
        layer3.deletePos(6,0,0);
        layer3.deletePos(-6,0,0);
        return new MultiBlockStructure(List.of(layer0,layer1,layer2,layer3));
    }

    public static MultiBlockStructure test(){
        MultiBlockLayer layer0 = new MultiBlockLayer(ModBlocks.DWARVEN_STEEL_BLOCK.get());
        layer0.addSquare(MultiBlockLayer.Mode.CENTER,3,3,0,0,0,1);
        layer0.addSquare(MultiBlockLayer.Mode.CENTER,3,3,4,0,0,1);
        layer0.addSquare(MultiBlockLayer.Mode.CENTER,3,3,2,-2,0,2);
        layer0.addSquare(MultiBlockLayer.Mode.CENTER,3,3,2,0,-2,0);
        layer0.addSquare(MultiBlockLayer.Mode.CENTER,3,3,2,0,2,0);
        return new MultiBlockStructure(List.of(layer0));
    }
}
