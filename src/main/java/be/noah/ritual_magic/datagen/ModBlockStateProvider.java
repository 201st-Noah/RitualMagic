package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RitualMagic.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.DWARVEN_DEBRIS);
        blockWithItem(ModBlocks.ATLANTIAN_DEBRIS);
        blockWithItem(ModBlocks.PETRIFIED_DRAGON_SCALE);
        blockWithItem(ModBlocks.POLISHED_OBSIDIAN);
        blockWithItem(ModBlocks.DWARVEN_STEEL_BLOCK);

        simpleBlockWithItem(ModBlocks.ANCIENT_ANVIL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/ancient_anvil")));
//        simpleBlockWithItem(ModBlocks.DRAGON_FIRE.get(),
//                new ModelFile.UncheckedModelFile(modLoc("block/dragon_fire_block")));
        fireBlock(ModBlocks.DRAGON_FIRE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void fireBlock(RegistryObject<Block> blockRegistryObject){
        String name = blockRegistryObject.getId().getPath();

        ModelFile floor0 = models().singleTexture(
                "block/" + name + "_floor0",
                new ResourceLocation("block/template_fire_floor"),
                "fire",
                new ResourceLocation(RitualMagic.MODID, "block/" + name + "_0")
        ), floor1 = models().singleTexture(
                "block/" + name + "_floor1",
                new ResourceLocation("block/template_fire_floor"),
                "fire",
                new ResourceLocation(RitualMagic.MODID, "block/" + name + "_1")
        ), side0 = models().singleTexture(
                "block/" + name + "_side0",
                new ResourceLocation("block/template_fire_side"),
                "fire",
                new ResourceLocation(RitualMagic.MODID, "block/" + name + "_0")
        ), side1 = models().singleTexture(
                "block/" + name + "_side1",
                new ResourceLocation("block/template_fire_side"),
                "fire",
                new ResourceLocation(RitualMagic.MODID, "block/" + name + "_1")
        ), side_alt0 = models().singleTexture(
                "block/" + name + "_side_alt0",
                new ResourceLocation("block/template_fire_side_alt"),
                "fire",
                new ResourceLocation(RitualMagic.MODID, "block/" + name + "_0")
        ), side_alt1 = models().singleTexture(
                "block/" + name + "_side_alt1",
                new ResourceLocation("block/template_fire_side_alt"),
                "fire",
                new ResourceLocation(RitualMagic.MODID, "block/" + name + "_1")
        );

        getMultipartBuilder(blockRegistryObject.get())
                .part()
                    .modelFile(floor0)
                    .nextModel()
                    .modelFile(floor1)
                    .addModel()
                    .end()
                .part()
                    .modelFile(side0)
                    .nextModel()
                    .modelFile(side1)
                    .nextModel()
                    .modelFile(side_alt0)
                    .nextModel()
                    .modelFile(side_alt1)
                    .addModel()
                    .end()
                .part()
                    .modelFile(side0)
                    .rotationY(90)
                    .nextModel()
                    .modelFile(side1)
                    .rotationY(90)
                    .nextModel()
                    .modelFile(side_alt0)
                    .rotationY(90)
                    .nextModel()
                    .modelFile(side_alt1)
                    .rotationY(90)
                    .addModel()
                    .end()
                .part()
                    .modelFile(side0)
                    .rotationY(180)
                    .nextModel()
                    .modelFile(side1)
                    .rotationY(180)
                    .nextModel()
                    .modelFile(side_alt0)
                    .rotationY(180)
                    .nextModel()
                    .modelFile(side_alt1)
                    .rotationY(180)
                    .addModel()
                    .end()
                .part()
                    .modelFile(side0)
                    .rotationY(270)
                    .nextModel()
                    .modelFile(side1)
                    .rotationY(270)
                    .nextModel()
                    .modelFile(side_alt0)
                    .rotationY(270)
                    .nextModel()
                    .modelFile(side_alt1)
                    .rotationY(270)
                    .addModel()
                    .end();
    }
}
