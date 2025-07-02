package be.noah.ritual_magic.datagen.multiblock;

import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MultiblockStructureBuilder {
    private final String name;
    private final ResourceLocation base;
    //    private Symmetry symmetry;
    private final Set<BlockBinding> bindings = new HashSet<>();
    private final Map<Vec3i, BlockBinding> blocks = new TreeMap<>();

    public MultiblockStructureBuilder(@NotNull String name, @NotNull ResourceLocation base) {
        this.name = name;
        this.base = base;
    }

    @NotNull
    public MultiblockStructureBuilder addBlock(@NotNull BlockBinding binding, @NotNull Vec3i pos, Symmetry symmetry) {
        bindings.add(binding);

        switch (symmetry) {
            case NONE -> blocks.put(pos, binding);
            case X -> {
                blocks.put(pos, binding);
                blocks.put(new Vec3i(-pos.getX(), pos.getY(), pos.getZ()), binding);
            }
            case Y -> {
                blocks.put(pos, binding);
                blocks.put(new Vec3i(pos.getX(), -pos.getY(), pos.getZ()), binding);
            }
            case Z -> {
                blocks.put(pos, binding);
                blocks.put(new Vec3i(pos.getX(), pos.getY(), -pos.getZ()), binding);
            }
            case XY -> {
                blocks.put(pos, binding);
                blocks.put(new Vec3i(-pos.getX(), pos.getY(), pos.getZ()), binding);
                blocks.put(new Vec3i(pos.getX(), -pos.getY(), pos.getZ()), binding);
                blocks.put(new Vec3i(-pos.getX(), -pos.getY(), pos.getZ()), binding);
            }
            case XZ -> {
                blocks.put(pos, binding);
                blocks.put(new Vec3i(-pos.getX(), pos.getY(), pos.getZ()), binding);
                blocks.put(new Vec3i(pos.getX(), pos.getY(), -pos.getZ()), binding);
                blocks.put(new Vec3i(-pos.getX(), pos.getY(), -pos.getZ()), binding);
            }
            case YZ -> {
                blocks.put(pos, binding);
                blocks.put(new Vec3i(pos.getX(), -pos.getY(), pos.getZ()), binding);
                blocks.put(new Vec3i(pos.getX(), pos.getY(), -pos.getZ()), binding);
                blocks.put(new Vec3i(pos.getX(), -pos.getY(), -pos.getZ()), binding);
            }
            case XYZ -> {
                blocks.put(pos, binding);
                blocks.put(new Vec3i(-pos.getX(), pos.getY(), pos.getZ()), binding);
                blocks.put(new Vec3i(pos.getX(), -pos.getY(), pos.getZ()), binding);
                blocks.put(new Vec3i(pos.getX(), pos.getY(), -pos.getZ()), binding);
                blocks.put(new Vec3i(-pos.getX(), -pos.getY(), pos.getZ()), binding);
                blocks.put(new Vec3i(-pos.getX(), pos.getY(), -pos.getZ()), binding);
                blocks.put(new Vec3i(pos.getX(), -pos.getY(), -pos.getZ()), binding);
                blocks.put(new Vec3i(-pos.getX(), -pos.getY(), -pos.getZ()), binding);
            }
        }

        return this;
    }

    @NotNull
    public MultiblockStructureBuilder addBlock(@NotNull BlockBinding binding, @NotNull Vec3i pos) {
        return addBlock(binding, pos, Symmetry.NONE);
    }

    public enum Symmetry {
        NONE,
        X,
        Y,
        Z,
        XY,
        XZ,
        YZ,
        XYZ,
    }
}
