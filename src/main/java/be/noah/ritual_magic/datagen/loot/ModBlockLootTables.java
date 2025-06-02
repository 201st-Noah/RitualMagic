package be.noah.ritual_magic.datagen.loot;

import be.noah.ritual_magic.block.ModBlocks;
import be.noah.ritual_magic.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
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

        this.add(ModBlocks.ICE_SPIKE.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.ICE_SHARD.get()))));
        this.dropSelf(ModBlocks.POINTED_ICICLE.get());
    }
    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
