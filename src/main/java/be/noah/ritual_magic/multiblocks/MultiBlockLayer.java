package be.noah.ritual_magic.multiblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockLayer {
    private final Block layer;
    private final List<List<Integer>> coords;

    public MultiBlockLayer(Block layer, List<List<Integer>> coords) {
        this.layer = layer;
        this.coords = coords;
    }

    public MultiBlockLayer(Block layer) {
        this.layer = layer;
        this.coords = new ArrayList<>();

    }

    public MultiBlockLayer(Block layer, int x, int y, int z) {
        this.layer = layer;
        this.coords = new ArrayList<>();
        coords.add(List.of(x, y, z));
    }

    /*
    symmetry 1 := Completely symmetric on X and Z Axis
    symmetry 2 := Symmetric on X or Z Axis
    symmetry 3 := Asymmetric => check every direction (works always but inefficient)
     */
    public boolean checkLayer(int symmetry, Level pLevel, int pX, int pY, int pZ) {
        boolean resN = true;
        boolean resS = true;
        boolean resE = true;
        boolean resW = true;
        for (int i = 0; i < coords.size(); i++) {
            switch (symmetry) {
                case 1:
                    if (resN)
                        resN = pLevel.getBlockState(new BlockPos(coords.get(i).get(0) + pX, coords.get(i).get(1) + pY, coords.get(i).get(2) + pZ)).is(layer);
                    if (!resN) {
                        return false;
                    }
                    break;
                case 2:
                    if (resN)
                        resN = pLevel.getBlockState(new BlockPos(coords.get(i).get(0) + pX, coords.get(i).get(1) + pY, coords.get(i).get(2) + pZ)).is(layer);
                    if (resE)
                        resE = pLevel.getBlockState(new BlockPos(coords.get(i).get(2) + pX, coords.get(i).get(1) + pY, coords.get(i).get(0) + pZ)).is(layer);
                    if (!(resN || resE)) {
                        return false;
                    }
                    break;
                case 3:
                    if (resN)
                        resN = pLevel.getBlockState(new BlockPos(coords.get(i).get(0) + pX, coords.get(i).get(1) + pY, coords.get(i).get(2) + pZ)).is(layer);
                    if (resS)
                        resS = pLevel.getBlockState(new BlockPos(pX - coords.get(i).get(0), coords.get(i).get(1) + pY, pZ - coords.get(i).get(2))).is(layer);
                    if (resE)
                        resE = pLevel.getBlockState(new BlockPos(coords.get(i).get(2) + pX, coords.get(i).get(1) + pY, coords.get(i).get(0) + pZ)).is(layer);
                    if (resW)
                        resW = pLevel.getBlockState(new BlockPos(pX - coords.get(i).get(2), coords.get(i).get(1) + pY, pZ - coords.get(i).get(0))).is(layer);
                    if (!(resN || resS || resE || resW)) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public boolean buildLayer(Level pLevel, int pX, int pY, int pZ) {
        for (List<Integer> cord : coords) {
            BlockPos pos = new BlockPos(cord.get(0) + pX, cord.get(1) + pY, cord.get(2) + pZ);
            if (pLevel.getBlockState(pos).is(Blocks.AIR)) {
                System.out.print(".");
                pLevel.setBlockAndUpdate(pos, layer.defaultBlockState());
                return true;
            }
        }
        return false;
    }

    public Block getlayerBlock() {
        return layer;
    }

    public List<List<Integer>> getCoordsList() {
        return coords;
    }

    /*
    orientation 0 :=  X * Y Plane
    orientation 1 :=  Z * Y Plane
    orientation 2 :=  X * Z Plane (Horizontal)
     */
    public void addCircle(int radius, int offsetX, int offsetY, int offsetZ, int orientation) {
        for (int i = 0; i <= radius; i++) {
            for (int j = 0; j <= radius; j++) {
                if (!isException(radius, i, j)) {
                    if (Math.abs((double) radius - Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2))) <= 0.5) {
                        switch (orientation) {
                            case 0:
                                coords.add(List.of(i + offsetX, j + offsetY, offsetZ));
                                coords.add(List.of(-i + offsetX, j + offsetY, offsetZ));
                                coords.add(List.of(i + offsetX, -j + offsetY, offsetZ));
                                coords.add(List.of(-i + offsetX, -j + offsetY, offsetZ));
                                break;
                            case 1:
                                coords.add(List.of(offsetX, j + offsetY, i + offsetZ));
                                coords.add(List.of(offsetX, j + offsetY, -i + offsetZ));
                                coords.add(List.of(offsetX, -j + offsetY, i + offsetZ));
                                coords.add(List.of(offsetX, -j + offsetY, -i + offsetZ));
                                break;
                            case 2:
                                coords.add(List.of(i + offsetX, offsetY, j + offsetZ));
                                coords.add(List.of(-i + offsetX, offsetY, j + offsetZ));
                                coords.add(List.of(i + offsetX, offsetY, -j + offsetZ));
                                coords.add(List.of(-i + offsetX, offsetY, -j + offsetZ));
                                break;
                        }
                    }
                }
            }
        }
    }

    /*
    orientation 0 :=  X * Y Plane
    orientation 1 :=  Z * Y Plane
    orientation 2 :=  X * Z Plane (Horizontal)
    */
    public void addSquare(Mode mode, int dimX, int dimY, int offsetX, int offsetY, int offsetZ, int orientation) {
        int cenX = 0;
        int cenY = 0;
        if (mode == Mode.CENTER) {
            if (dimX % 2 == 1 && dimY % 2 == 1) {
                cenX = (dimX - 1) / 2;
                cenY = (dimY - 1) / 2;
            }
        }
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                switch (orientation) {
                    case 0:
                        coords.add(List.of(i + offsetX - cenX, j + offsetY - cenY, offsetZ));
                        break;
                    case 1:
                        coords.add(List.of(offsetX, j + offsetY - cenY, i + offsetZ - cenX));
                        break;
                    case 2:
                        coords.add(List.of(i + offsetX - cenX, offsetY, j + offsetZ - cenY));
                        break;
                }
            }
        }
        deletePos(0, 0, 0);
    }

    private boolean isException(int radius, int x, int y) {
        if (radius == 4) {
            return (x == 2 && y == 4) || (x == 4 && y == 2);
        } else if (radius == 6) {
            return x == 4 && y == 4;
        }
        return false;
    }

    public void deletePos(int x, int y, int z) {
        List<List<Integer>> clearList = new ArrayList<>();
        for (List<Integer> innerList : coords) {
            if (!(innerList.get(0) == x && innerList.get(1) == y && innerList.get(2) == z)) {
                clearList.add(innerList);
            }
        }
        coords.clear();
        coords.addAll(clearList);
    }

    private void deleteDuplicats() {

    }

    public enum Mode {CENTER, CORNER}
}
