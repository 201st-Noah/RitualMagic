package be.noah.ritual_magic.datagen.loot;

import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.DWARVEN_DEBRIS.get());
        this.dropSelf(ModBlocks.ATLANTIAN_DEBRIS.get());
        this.dropSelf(ModBlocks.SOUL_BRICKS.get());
        this.dropSelf(ModBlocks.MINING_CORE.get());
        this.dropSelf(ModBlocks.DWARVEN_DEBRIS.get());
        this.dropSelf(ModBlocks.POLISHED_OBSIDIAN.get());
        this.dropSelf(ModBlocks.PETRIFIED_DRAGON_SCALE.get());
        this.dropSelf(ModBlocks.DWARVEN_STEEL_BLOCK.get());
        this.dropSelf(ModBlocks.ANCIENT_ANVIL.get());
        this.dropSelf(ModBlocks.FORGE_T0.get());
        this.dropSelf(ModBlocks.FORGE_T1.get());
        this.dropSelf(ModBlocks.FORGE_T2.get());

    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
