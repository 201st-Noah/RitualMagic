package be.noah.ritual_magic.multiblocks;

import be.noah.ritual_magic.RitualMagic;
import com.google.gson.Gson;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MultiBlockStructure {
    private final Block base;
    private final Vec3i basePos;
    private final Vec3i size;
    //    private final Symmetry;
    private final Map<Vec3i, Set<Block>> blocks;  // todo: possibility to use block tags, ...

    public MultiBlockStructure(Block base, Vec3i basePos, Vec3i size, Map<Vec3i, Set<Block>> blocks) {
        this.base = base;
        this.basePos = basePos;
        this.size = size;
        this.blocks = blocks;
    }

//    public static MultiBlockStructure pyramid(Block block, int h) {
//        MultiBlockLayer layer = new MultiBlockLayer(block);
//        for (int i = 1; i < h; i++) {
//            layer.addSquare(MultiBlockLayer.Mode.CENTER, i * 2 + 1, i * 2 + 1, 0, -i, 0, 2);
//        }
//        return new MultiBlockStructure(layer);
//    }
//
//    public static MultiBlockStructure getTeleporterStruct() {
//        MultiBlockLayer layer0 = new MultiBlockLayer(ModBlocks.DWARVEN_STEEL_BLOCK.get());
//        layer0.addCircle(3, 0, 0, 0, 2);
//        MultiBlockLayer layer1 = new MultiBlockLayer(ModBlocks.PETRIFIED_DRAGON_SCALE.get());
//        layer1.addCircle(6, 0, 0, 0, 2);
//        layer1.deletePos(6, 0, 0);
//        layer1.deletePos(-6, 0, 0);
//        layer1.deletePos(0, 0, 6);
//        layer1.deletePos(0, 0, -6);
//        MultiBlockLayer layer2 = new MultiBlockLayer(ModBlocks.PETRIFIED_DRAGON_SCALE.get());
//        layer2.addCircle(6, 0, 0, 0, 1);
//        layer2.deletePos(0, 6, 0);
//        layer2.deletePos(0, -6, 0);
//        layer2.deletePos(0, 0, 6);
//        layer2.deletePos(0, 0, -6);
//        MultiBlockLayer layer3 = new MultiBlockLayer(ModBlocks.PETRIFIED_DRAGON_SCALE.get());
//        layer3.addCircle(6, 0, 0, 0, 0);
//        layer3.deletePos(0, 6, 0);
//        layer3.deletePos(0, -6, 0);
//        layer3.deletePos(6, 0, 0);
//        layer3.deletePos(-6, 0, 0);
//        return new MultiBlockStructure(List.of(layer0, layer1, layer2, layer3));
//    }
//
//    public static MultiBlockStructure forgeT1() {
//        MultiBlockLayer layer0 = new MultiBlockLayer(Blocks.DEEPSLATE_BRICKS, 1, -1, 0);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 1, 3, 0, 1, 0, 1);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 1, 3, 2, 1, 0, 1);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 1, 3, 1, 1, -1, 0);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 1, 3, 1, 1, 1, 0);
//        return new MultiBlockStructure(List.of(layer0));
//    }
//
//    public static MultiBlockStructure forgeT2() {
//        MultiBlockLayer layer0 = new MultiBlockLayer(Blocks.DEEPSLATE_TILES);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 3, 5, 0, 2, 0, 1);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 3, 5, 4, 2, 0, 1);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 3, 3, 2, -1, 0, 2);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 3, 5, 2, 2, -2, 0);
//        layer0.addSquare(MultiBlockLayer.Mode.CENTER, 3, 5, 2, 2, 2, 0);
//        return new MultiBlockStructure(List.of(layer0));
//    }

    public Block getBase() {
        return base;
    }

    public Vec3i getBasePos() {
        return basePos;
    }

    public Vec3i getSize() {
        return size;
    }

    public boolean checkStructure(Level pLevel, int pX, int pY, int pZ) {
        Vec3i structurePos = new Vec3i(pX, pY, pZ);
        for (Map.Entry<Vec3i, Set<Block>> entry : blocks.entrySet()) {
            Vec3i entryPos = entry.getKey();
            BlockPos pos = new BlockPos(
                    structurePos.getX() + entryPos.getX() - basePos.getX(),
                    structurePos.getY() + entryPos.getY() - basePos.getY(),
                    structurePos.getZ() + entryPos.getZ() - basePos.getZ()
            );

            if (!entry.getValue().contains(pLevel.getBlockState(pos).getBlock()))
                return false;
        }

        return true;
    }

    public void buildStructure(Level pLevel, int pX, int pY, int pZ) {
        for (Vec3i pos : blocks.keySet())
            if (!buildBlock(pLevel, pX + pos.getX(), pY + pos.getY(), pZ + pos.getZ()))
                break;
    }

    private boolean buildBlock(Level pLevel, int pX, int pY, int pZ) {
        var pos = new Vec3i(pX, pY, pZ);
        var blockPos = new BlockPos(pos);
        var block = blocks.get(pos).iterator().next();
        var blockStateAtPos = pLevel.getBlockState(blockPos);

        if (blockStateAtPos.isAir()) {
            pLevel.setBlockAndUpdate(blockPos, block.defaultBlockState());
            return true;
        }

        return blockStateAtPos.is(block);
    }


    public static class Builder {
        //    private Symmetry;
        private final Map<Vec3i, Set<Block>> blocks = new TreeMap<>();
        private Block base;
        private Vec3i basePos;
        private Vec3i size;

        public Block getBase() {
            return base;
        }

        public Builder setBase(Block base) {
            this.base = base;
            return this;
        }

        public Vec3i getBasePos() {
            return basePos;
        }

        public Builder setBasePos(Vec3i basePos) {
            this.basePos = basePos;
            return this;
        }

        public Builder setBasePos(int x, int y, int z) {
            return setBasePos(new Vec3i(x, y, z));
        }

        public Vec3i getSize() {
            return size;
        }

        public Builder setSize(Vec3i size) {
            this.size = size;
            return this;
        }

        public Builder setSize(int x, int y, int z) {
            return setSize(new Vec3i(x, y, z));
        }

        public Builder addBlock(Vec3i pos, Set<Block> block) {
            if (pos.getX() < 0 || pos.getY() < 0 || pos.getZ() < 0 ||
                    (size != null &&
                            (pos.getX() >= size.getX() || pos.getY() >= size.getY() || pos.getZ() >= size.getZ())
                    )
            )
                throw new IllegalArgumentException("Block position " + pos + " is out of bounds for size " + size);

            blocks.put(pos, block);
            return this;
        }

        public Builder addBlock(int x, int y, int z, Set<Block> block) {
            return addBlock(new Vec3i(x, y, z), block);
        }

        public MultiBlockStructure build() throws IllegalStateException {
            if (base == null || basePos == null || blocks.isEmpty())
                throw new IllegalStateException("MultiBlockStructure is not fully defined");

            boolean baseFound = false;
            if (size == null) {
                int maxX = Integer.MIN_VALUE,
                        maxY = Integer.MIN_VALUE,
                        maxZ = Integer.MIN_VALUE;

                for (Vec3i pos : blocks.keySet()) {
                    if (pos.getX() > maxX)
                        maxX = pos.getX();
                    if (pos.getY() > maxY)
                        maxY = pos.getY();
                    if (pos.getZ() > maxZ)
                        maxZ = pos.getZ();
                    if (pos.equals(basePos))
                        baseFound = true;
                }

                size = new Vec3i(maxX + 1, maxY + 1, maxZ + 1); // +1 because positions are zero-based

            } else {
                for (Vec3i pos : blocks.keySet())
                    if (pos.equals(basePos)) {
                        baseFound = true;
                        break;
                    }
            }

            if (!baseFound)
                throw new IllegalStateException("Base block not found in the structure");

            return new MultiBlockStructure(base, basePos, size, blocks);
        }
    }

    public static class Loader {
        public final static char WILDCARD = '*';
        private final static Gson GSON = new Gson();

        @NotNull
        private static Block getBlockFromId(String blockId) throws IllegalArgumentException {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId));
            if (block == null)
                throw new IllegalArgumentException("Block not found: " + blockId);
            return block;
        }

        public MultiBlockStructure load(String path) throws IOException, IllegalArgumentException {
            var builder = new Builder();
            Map<Character, Set<Block>> blocks = new HashMap<>();
            Vector<Vector<String>> layers = new Vector<>();

            var fileReader = new FileReader(path);
            var jsonReader = GSON.newJsonReader(fileReader);

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                switch (name) {
                    case "base" -> {
                        String baseId = GSON.fromJson(jsonReader, String.class);

                        builder.setBase(getBlockFromId(baseId));
                    }

                    case "basePos" -> builder.setBasePos(GSON.fromJson(jsonReader, Vec3i.class));

                    case "bindings" -> {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String binding = jsonReader.nextName();
                            if (binding.length() != 1) {
                                throw new IllegalArgumentException(
                                        "Invalid binding '" + binding + "' in multiblock structure at " + path
                                );
                            }
                            String[] blockIds = GSON.fromJson(jsonReader, String[].class);
                            Set<Block> blockSet = new HashSet<>();
                            for (String blockId : blockIds)
                                blockSet.add(getBlockFromId(blockId));
                            blocks.put(binding.charAt(0), blockSet);
                        }
                        jsonReader.endObject();
                    }

                    case "size" -> builder.setSize(GSON.fromJson(jsonReader, Vec3i.class));

                    case "layers" -> layers = GSON.fromJson(jsonReader, Vector.class);

                    default -> {
                        RitualMagic.LOGGER.warn("Unrecognized multiblock structure key in \"{}\": {}", path, name);
                        jsonReader.skipValue(); // Skip unknown fields
                    }
                }
            }

            if (layers.size() != builder.getSize().getY())
                throw new IllegalArgumentException(
                        "Layer count mismatch in multiblock structure at " + path
                );

            jsonReader.close();
            fileReader.close();

            for (int y = 0; y < layers.size(); y++) {
                Vector<String> layer = layers.get(y);
                if (layer.size() != builder.getSize().getX())
                    throw new IllegalArgumentException(
                            "Layer width mismatch in multiblock structure at " + path
                    );

                for (int x = 0; x < layer.size(); x++) {
                    String row = layer.get(x);
                    if (row.length() != builder.getSize().getZ())
                        throw new IllegalArgumentException(
                                "Layer height mismatch in multiblock structure at " + path
                        );

                    for (int z = 0; z < row.length(); z++) {
                        char binding = row.charAt(z);
                        if (binding == WILDCARD)
                            // Skip wildcard positions
                            continue;

                        if (!blocks.containsKey(binding))
                            throw new IllegalArgumentException(
                                    "Binding '" + binding + "' not found in multiblock structure at " + path
                            );

                        builder.addBlock(x, y, z, blocks.get(binding));
                    }
                }
            }

            return builder.build();
        }
    }
}
