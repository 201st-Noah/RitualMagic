package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.blocks.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
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
        blockWithItem(ModBlocks.FORGE_T0);
        blockWithItem(ModBlocks.B_SOUL_FARMLAND);
        blockWithItem(ModBlocks.I_SOUL_FARMLAND);
        blockWithItem(ModBlocks.A_SOUL_FARMLAND);
        blockWithItem(ModBlocks.U_SOUL_FARMLAND);
        blockWithItem(ModBlocks.UNSTABLE_MAGMA_BLOCK);
        simpleBlockWithItem(ModBlocks.ANCIENT_ANVIL.get(), new ModelFile.UncheckedModelFile(modLoc("block/ancient_anvil")));
        simpleBlockWithItem(ModBlocks.B_NEXUS_PEDESTAL.get(), new ModelFile.UncheckedModelFile(modLoc("block/b_nexus_pedestal")));
        simpleBlockWithItem(ModBlocks.INFUSION.get(), new ModelFile.UncheckedModelFile(modLoc("block/infusion")));
        fireBlockWithItem(ModBlocks.DRAGON_FIRE);
        simpleBlockWithItem(ModBlocks.ICE_SPIKE.get(), models().cross("ice_spike_block", modLoc("block/ice_spike_block")).renderType("cutout"));
        pointedDripstoneLikeBlockWithItem(ModBlocks.POINTED_ICICLE);
    }

    private void pointedDripstoneLikeBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        // Generate all the model files first
        assert blockRegistryObject.getId() != null;
        String name = blockRegistryObject.getId().getPath();
        ModelFile tipMergeUp = models()
                .withExistingParent(
                        "block/" + name + "_up_tip_merge",
                        mcLoc("block/pointed_dripstone_up_tip_merge")
                )
                .texture("cross", modLoc("block/" + name + "_up_tip_merge"))
                .renderType("translucent");
        ModelFile tipMergeDown = models()
                .withExistingParent(
                        "block/" + name + "_down_tip_merge",
                        mcLoc("block/pointed_dripstone_down_tip_merge")
                )
                .texture("cross", modLoc("block/" + name + "_down_tip_merge"))
                .renderType("translucent");
        ModelFile tipUp = models()
                .withExistingParent(
                        "block/" + name + "_up_tip",
                        mcLoc("block/pointed_dripstone_up_tip")
                )
                .texture("cross", modLoc("block/" + name + "_up_tip"))
                .renderType("translucent");
        ModelFile tipDown = models()
                .withExistingParent(
                        "block/" + name + "_down_tip",
                        mcLoc("block/pointed_dripstone_down_tip")
                )
                .texture("cross", modLoc("block/" + name + "_down_tip"))
                .renderType("translucent");
        ModelFile frustumUp = models()
                .withExistingParent(
                        "block/" + name + "_up_frustum",
                        mcLoc("block/pointed_dripstone_up_frustum")
                )
                .texture("cross", modLoc("block/" + name + "_up_frustum"))
                .renderType("translucent");
        ModelFile frustumDown = models()
                .withExistingParent(
                        "block/" + name + "_down_frustum",
                        mcLoc("block/pointed_dripstone_down_frustum")
                )
                .texture("cross", modLoc("block/" + name + "_down_frustum"))
                .renderType("translucent");
        ModelFile middleUp = models()
                .withExistingParent(
                        "block/" + name + "_up_middle",
                        mcLoc("block/pointed_dripstone_up_middle")
                )
                .texture("cross", modLoc("block/" + name + "_up_middle"))
                .renderType("translucent");
        ModelFile middleDown = models()
                .withExistingParent(
                        "block/" + name + "_down_middle",
                        mcLoc("block/pointed_dripstone_down_middle")
                )
                .texture("cross", modLoc("block/" + name + "_down_middle"))
                .renderType("translucent");
        ModelFile baseUp = models()
                .withExistingParent(
                        "block/" + name + "_up_base",
                        mcLoc("block/pointed_dripstone_up_base")
                )
                .texture("cross", modLoc("block/" + name + "_up_base"))
                .renderType("translucent");
        ModelFile baseDown = models()
                .withExistingParent(
                        "block/" + name + "_down_base",
                        mcLoc("block/pointed_dripstone_down_base")
                )
                .texture("cross", modLoc("block/" + name + "_down_base"))
                .renderType("translucent");

        getVariantBuilder(blockRegistryObject.get()).forAllStatesExcept(
                state -> {
                    Direction direction = state.getValue(BlockStateProperties.VERTICAL_DIRECTION);
                    DripstoneThickness thickness = state.getValue(BlockStateProperties.DRIPSTONE_THICKNESS);

                    ModelFile model = switch (thickness) {
                        case TIP_MERGE -> direction == Direction.UP ? tipMergeUp : tipMergeDown;
                        case TIP -> direction == Direction.UP ? tipUp : tipDown;
                        case FRUSTUM -> direction == Direction.UP ? frustumUp : frustumDown;
                        case MIDDLE -> direction == Direction.UP ? middleUp : middleDown;
                        case BASE -> direction == Direction.UP ? baseUp : baseDown;
                    };

                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .build();
                },
                BlockStateProperties.WATERLOGGED
        );

        // Generate item model with specific display settings
        itemModels().getBuilder("item/" + name)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/" + name))
                .renderType("translucent")
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(0, 100, 0)
                .translation(-1, -1, 0)
                .scale(0.9f, 0.9f, 0.9f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 100, 0)
                .translation(0, -2, 0)
                .scale(0.9f, 0.9f, 0.9f)
                .end()
                .end();
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void fireBlock(RegistryObject<Block> blockRegistryObject) {
        String name = blockRegistryObject.getId().getPath();

        ModelFile floor0 = models().singleTexture(
                "block/" + name + "_floor0",
                new ResourceLocation("block/template_fire_floor"),
                "fire",
                new ResourceLocation(RitualMagic.MODID, "block/" + name + "_0")
        ).renderType("cutout"),
                floor1 = models().singleTexture(
                        "block/" + name + "_floor1",
                        new ResourceLocation("block/template_fire_floor"),
                        "fire",
                        new ResourceLocation(RitualMagic.MODID, "block/" + name + "_1")
                ).renderType("cutout"),
                side0 = models().singleTexture(
                        "block/" + name + "_side0",
                        new ResourceLocation("block/template_fire_side"),
                        "fire",
                        new ResourceLocation(RitualMagic.MODID, "block/" + name + "_0")
                ).renderType("cutout"),
                side1 = models().singleTexture(
                        "block/" + name + "_side1",
                        new ResourceLocation("block/template_fire_side"),
                        "fire",
                        new ResourceLocation(RitualMagic.MODID, "block/" + name + "_1")
                ).renderType("cutout"),
                side_alt0 = models().singleTexture(
                        "block/" + name + "_side_alt0",
                        new ResourceLocation("block/template_fire_side_alt"),
                        "fire",
                        new ResourceLocation(RitualMagic.MODID, "block/" + name + "_0")
                ).renderType("cutout"),
                side_alt1 = models().singleTexture(
                        "block/" + name + "_side_alt1",
                        new ResourceLocation("block/template_fire_side_alt"),
                        "fire",
                        new ResourceLocation(RitualMagic.MODID, "block/" + name + "_1")
                ).renderType("cutout");

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

    private void fireBlockItem(RegistryObject<Block> blockRegistryObject) {
        String name = blockRegistryObject.getId().getPath();

        models().singleTexture("item/" + name,
                new ResourceLocation("block/template_fire_floor"),
                "fire",
                new ResourceLocation(
                        RitualMagic.MODID, "block/" + name + "_0"
                ));
    }

    private void fireBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        fireBlock(blockRegistryObject);
        fireBlockItem(blockRegistryObject);
    }

}
