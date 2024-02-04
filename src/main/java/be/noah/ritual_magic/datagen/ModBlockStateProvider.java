package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
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

        simpleBlockWithItem(ModBlocks.ANCIENT_ANVIL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/ancient_anvil")));

    }
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void fireblock(RegistryObject<Block> blockRegistryObject){

    }
}
