package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RitualMagic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(Tags.Blocks.ORES)
                .add(ModBlocks.DWARVEN_DEBRIS.get(),
                        ModBlocks.ATLANTIAN_DEBRIS.get(),
                        ModBlocks.PETRIFIED_DRAGON_SCALE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SOUL_BRICKS.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DWARVEN_DEBRIS.get(),
                        ModBlocks.ATLANTIAN_DEBRIS.get(),
                        ModBlocks.PETRIFIED_DRAGON_SCALE.get(),
                        ModBlocks.MINING_CORE.get(),
                        ModBlocks.POLISHED_OBSIDIAN.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DWARVEN_DEBRIS.get(),
                        ModBlocks.PETRIFIED_DRAGON_SCALE.get(),
                        ModBlocks.MINING_CORE.get(),
                        ModBlocks.POLISHED_OBSIDIAN.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.ATLANTIAN_DEBRIS.get());
    }
}
