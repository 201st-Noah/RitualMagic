package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.FORGE_T0.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SOUL_BRICKS.get(),
                        ModBlocks.FORGE_T1.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DWARVEN_DEBRIS.get(),
                        ModBlocks.ATLANTIAN_DEBRIS.get(),
                        ModBlocks.PETRIFIED_DRAGON_SCALE.get(),
                        ModBlocks.MINING_CORE.get(),
                        ModBlocks.POLISHED_OBSIDIAN.get(),
                        ModBlocks.DWARVEN_STEEL_BLOCK.get(),
                        ModBlocks.ANCIENT_ANVIL.get(),
                        ModBlocks.FORGE_T2.get(),
                        ModBlocks.ICE_SPIKE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DWARVEN_DEBRIS.get(),
                        ModBlocks.PETRIFIED_DRAGON_SCALE.get(),
                        ModBlocks.MINING_CORE.get(),
                        ModBlocks.POLISHED_OBSIDIAN.get(),
                        ModBlocks.DWARVEN_STEEL_BLOCK.get(),
                        ModBlocks.ANCIENT_ANVIL.get(),
                        ModBlocks.FORGE_T0.get(),
                        ModBlocks.FORGE_T1.get(),
                        ModBlocks.FORGE_T2.get(),
                        ModBlocks.ICE_SPIKE.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.ATLANTIAN_DEBRIS.get());

        this.tag(ModTags.Blocks.DRAGON_FIRE_BASE_BLOCK)
                .add(Blocks.END_STONE,
                        Blocks.END_STONE_BRICKS,
                        ModBlocks.PETRIFIED_DRAGON_SCALE.get());
    }
}
