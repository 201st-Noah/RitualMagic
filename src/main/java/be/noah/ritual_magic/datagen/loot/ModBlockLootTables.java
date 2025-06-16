package be.noah.ritual_magic.datagen.loot;

import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.items.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

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

        this.dropSelf(ModBlocks.B_NEXUS_PEDESTAL.get());
        this.dropSelf(ModBlocks.I_NEXUS_PEDESTAL.get());
        this.dropSelf(ModBlocks.A_NEXUS_PEDESTAL.get());
        this.dropSelf(ModBlocks.U_NEXUS_PEDESTAL.get());
        this.dropSelf(ModBlocks.B_DWARVEN_PEDESTAL.get());
        this.dropSelf(ModBlocks.I_DWARVEN_PEDESTAL.get());
        this.dropSelf(ModBlocks.A_DWARVEN_PEDESTAL.get());
        this.dropSelf(ModBlocks.U_DWARVEN_PEDESTAL.get());
        this.dropSelf(ModBlocks.B_ATLANTIAN_PEDESTAL.get());
        this.dropSelf(ModBlocks.I_ATLANTIAN_PEDESTAL.get());
        this.dropSelf(ModBlocks.A_ATLANTIAN_PEDESTAL.get());
        this.dropSelf(ModBlocks.U_ATLANTIAN_PEDESTAL.get());
        this.dropSelf(ModBlocks.B_HELLISH_PEDESTAL.get());
        this.dropSelf(ModBlocks.I_HELLISH_PEDESTAL.get());
        this.dropSelf(ModBlocks.A_HELLISH_PEDESTAL.get());
        this.dropSelf(ModBlocks.U_HELLISH_PEDESTAL.get());
        this.dropSelf(ModBlocks.B_DRACONIC_PEDESTAL.get());
        this.dropSelf(ModBlocks.I_DRACONIC_PEDESTAL.get());
        this.dropSelf(ModBlocks.A_DRACONIC_PEDESTAL.get());
        this.dropSelf(ModBlocks.U_DRACONIC_PEDESTAL.get());
        this.dropSelf(ModBlocks.B_NEXUS_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.I_NEXUS_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.A_NEXUS_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.U_NEXUS_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.B_DWARVEN_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.I_DWARVEN_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.A_DWARVEN_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.U_DWARVEN_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.B_ATLANTIAN_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.I_ATLANTIAN_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.A_ATLANTIAN_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.U_ATLANTIAN_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.B_HELLISH_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.I_HELLISH_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.A_HELLISH_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.U_HELLISH_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.B_DRACONIC_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.I_DRACONIC_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.A_DRACONIC_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.U_DRACONIC_INFUSION_CORE.get());
        this.dropSelf(ModBlocks.INFUSION.get());
        this.dropSelf(ModBlocks.POINTED_ICICLE.get());
        this.dropSelf(ModBlocks.B_SOUL_SACRIFICE.get());
        this.dropSelf(ModBlocks.I_SOUL_SACRIFICE.get());
        this.dropSelf(ModBlocks.A_SOUL_SACRIFICE.get());
        this.dropSelf(ModBlocks.U_SOUL_SACRIFICE.get());
        this.dropSelf(ModBlocks.B_WEATHER_RITUAL.get());
        this.dropSelf(ModBlocks.I_WEATHER_RITUAL.get());
        this.dropSelf(ModBlocks.A_WEATHER_RITUAL.get());
        this.dropSelf(ModBlocks.U_WEATHER_RITUAL.get());

        this.dropOther(ModBlocks.ICE_SPIKE.get(), ModItems.ICE_SHARD.get());
        this.dropOther(ModBlocks.B_SOUL_FARMLAND.get(), Items.SOUL_SOIL);
        this.dropOther(ModBlocks.I_SOUL_FARMLAND.get(), Items.SOUL_SOIL);
        this.dropOther(ModBlocks.A_SOUL_FARMLAND.get(), Items.SOUL_SOIL);
        this.dropOther(ModBlocks.U_SOUL_FARMLAND.get(), Items.SOUL_SOIL);
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
