package be.noah.ritual_magic.blocks;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.blocks.custom.*;
import be.noah.ritual_magic.blocks.custom.fire.DragonFireBlock;
import be.noah.ritual_magic.items.ModItems;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RitualMagic.MODID);

    //Simple Blocks
    public static final RegistryObject<Block> DWARVEN_DEBRIS = registerBlock("dwarven_debris", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(50f, 1200.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ATLANTIAN_DEBRIS = registerBlock("atlantian_debris", () -> new GravelBlock(BlockBehaviour.Properties.copy(Blocks.SAND).strength(3f).sound(SoundType.GRAVEL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PETRIFIED_DRAGON_SCALE = registerBlock("petrified_dragon_scale", () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).strength(3f, 9.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_OBSIDIAN = registerBlock("polished_obsidian", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> SOUL_BRICKS = registerBlock("soul_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(5.0F, 1200.0F)));
    public static final RegistryObject<Block> DWARVEN_STEEL_BLOCK = registerBlock("dwarven_steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> DRAGON_FIRE = registerBlock("dragon_fire_block", () -> new DragonFireBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().instabreak().lightLevel((p_152605_) -> 15).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY).noLootTable()));
    public static final RegistryObject<Block> ICE_SPIKE = registerBlock("ice_spike_block", () -> new IceSpikeBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).strength(5f, 3.0f).requiresCorrectToolForDrops().noCollission()));
    public static final RegistryObject<Block> POINTED_ICICLE = registerBlock("pointed_icicle", () -> new PointedIcicleBlock(BlockBehaviour.Properties.copy(Blocks.POINTED_DRIPSTONE)));
    public static final RegistryObject<Block> UNSTABLE_MAGMA_BLOCK = registerBlock("unstable_magma_block", () -> new UnstableMagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK).requiresCorrectToolForDrops()));
    // SoulFarmlandBlock
    public static final RegistryObject<Block> B_SOUL_FARMLAND = registerBlock("b_soul_farmland", () -> new SoulFarmlandBlock(BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL)));
    public static final RegistryObject<Block> I_SOUL_FARMLAND = registerBlock("i_soul_farmland", () -> new SoulFarmlandBlock(BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL)));
    public static final RegistryObject<Block> A_SOUL_FARMLAND = registerBlock("a_soul_farmland", () -> new SoulFarmlandBlock(BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL)));
    public static final RegistryObject<Block> U_SOUL_FARMLAND = registerBlock("u_soul_farmland", () -> new SoulFarmlandBlock(BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL)));

    //Block Entities
    public static final RegistryObject<Block> FORGE_T0 = registerBlock("forge_t0", () -> new ForgeBlock(ForgeBlock.Tier.BEGINNER, BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).strength(5f, 1200.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> FORGE_T1 = registerBlock("forge_t1", () -> new ForgeBlock(ForgeBlock.Tier.BASIC, BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).strength(25f, 1200.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> FORGE_T2 = registerBlock("forge_t2", () -> new ForgeBlock(ForgeBlock.Tier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).strength(150f, 1200.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOD_TELEPORTER = registerBlock("mod_portal", () -> new ModTeleporterBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noLootTable().noOcclusion()));
    public static final RegistryObject<Block> MINING_CORE = registerBlock("mining_core", () -> new MiningCoreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> ANCIENT_ANVIL = registerBlock("ancient_anvil", () -> new AncientAnvilBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    //Mana generating Rituals
    public static final RegistryObject<Block> B_SOUL_SACRIFICE = registerBlock("b_soul_sacrifice_block", () -> new SoulSacrificeBlock(BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_SOUL_SACRIFICE = registerBlock("i_soul_sacrifice_block", () -> new SoulSacrificeBlock(BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_SOUL_SACRIFICE = registerBlock("a_soul_sacrifice_block", () -> new SoulSacrificeBlock(BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_SOUL_SACRIFICE = registerBlock("u_soul_sacrifice_block", () -> new SoulSacrificeBlock(BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> B_WEATHER_RITUAL = registerBlock("b_weather_ritual", () -> new WeatherRitualBlock(BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_WEATHER_RITUAL = registerBlock("i_weather_ritual", () -> new WeatherRitualBlock(BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_WEATHER_RITUAL = registerBlock("a_weather_ritual", () -> new WeatherRitualBlock(BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_WEATHER_RITUAL = registerBlock("u_weather_ritual", () -> new WeatherRitualBlock(BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    // Infusion Pedestals
    public static final RegistryObject<Block> B_NEXUS_PEDESTAL = registerBlock("b_nexus_pedestal", () -> new RitualPedestalBlock(ManaType.NEXUS, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_NEXUS_PEDESTAL = registerBlock("i_nexus_pedestal", () -> new RitualPedestalBlock(ManaType.NEXUS, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_NEXUS_PEDESTAL = registerBlock("a_nexus_pedestal", () -> new RitualPedestalBlock(ManaType.NEXUS, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_NEXUS_PEDESTAL = registerBlock("u_nexus_pedestal", () -> new RitualPedestalBlock(ManaType.NEXUS, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> B_DWARVEN_PEDESTAL = registerBlock("b_dwarven_pedestal", () -> new RitualPedestalBlock(ManaType.DWARVEN, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_DWARVEN_PEDESTAL = registerBlock("i_dwarven_pedestal", () -> new RitualPedestalBlock(ManaType.DWARVEN, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_DWARVEN_PEDESTAL = registerBlock("a_dwarven_pedestal", () -> new RitualPedestalBlock(ManaType.DWARVEN, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_DWARVEN_PEDESTAL = registerBlock("u_dwarven_pedestal", () -> new RitualPedestalBlock(ManaType.DWARVEN, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> B_ATLANTIAN_PEDESTAL = registerBlock("b_atlantian_pedestal", () -> new RitualPedestalBlock(ManaType.ATLANTIAN, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_ATLANTIAN_PEDESTAL = registerBlock("i_atlantian_pedestal", () -> new RitualPedestalBlock(ManaType.ATLANTIAN, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_ATLANTIAN_PEDESTAL = registerBlock("a_atlantian_pedestal", () -> new RitualPedestalBlock(ManaType.ATLANTIAN, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_ATLANTIAN_PEDESTAL = registerBlock("u_atlantian_pedestal", () -> new RitualPedestalBlock(ManaType.ATLANTIAN, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> B_HELLISH_PEDESTAL = registerBlock("b_hellish_pedestal", () -> new RitualPedestalBlock(ManaType.HELLISH, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_HELLISH_PEDESTAL = registerBlock("i_hellish_pedestal", () -> new RitualPedestalBlock(ManaType.HELLISH, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_HELLISH_PEDESTAL = registerBlock("a_hellish_pedestal", () -> new RitualPedestalBlock(ManaType.HELLISH, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_HELLISH_PEDESTAL = registerBlock("u_hellish_pedestal", () -> new RitualPedestalBlock(ManaType.HELLISH, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> B_DRACONIC_PEDESTAL = registerBlock("b_draconic_pedestal", () -> new RitualPedestalBlock(ManaType.DRACONIC, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_DRACONIC_PEDESTAL = registerBlock("i_draconic_pedestal", () -> new RitualPedestalBlock(ManaType.DRACONIC, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_DRACONIC_PEDESTAL = registerBlock("a_draconic_pedestal", () -> new RitualPedestalBlock(ManaType.DRACONIC, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_DRACONIC_PEDESTAL = registerBlock("u_draconic_pedestal", () -> new RitualPedestalBlock(ManaType.DRACONIC, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    // Infusion Cores
    public static final RegistryObject<Block> B_NEXUS_INFUSION_CORE = registerBlock("b_nexus_infusion-core", () -> new InfusionBlock(ManaType.NEXUS, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_NEXUS_INFUSION_CORE = registerBlock("i_nexus_infusion-core", () -> new InfusionBlock(ManaType.NEXUS, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_NEXUS_INFUSION_CORE = registerBlock("a_nexus_infusion-core", () -> new InfusionBlock(ManaType.NEXUS, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_NEXUS_INFUSION_CORE = registerBlock("u_nexus_infusion-core", () -> new InfusionBlock(ManaType.NEXUS, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> B_DWARVEN_INFUSION_CORE = registerBlock("b_dwarven_infusion-core", () -> new InfusionBlock(ManaType.DWARVEN, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_DWARVEN_INFUSION_CORE = registerBlock("i_dwarven_infusion-core", () -> new InfusionBlock(ManaType.DWARVEN, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_DWARVEN_INFUSION_CORE = registerBlock("a_dwarven_infusion-core", () -> new InfusionBlock(ManaType.DWARVEN, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_DWARVEN_INFUSION_CORE = registerBlock("u_dwarven_infusion-core", () -> new InfusionBlock(ManaType.DWARVEN, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> B_ATLANTIAN_INFUSION_CORE = registerBlock("b_atlantian_infusion-core", () -> new InfusionBlock(ManaType.ATLANTIAN, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_ATLANTIAN_INFUSION_CORE = registerBlock("i_atlantian_infusion-core", () -> new InfusionBlock(ManaType.ATLANTIAN, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_ATLANTIAN_INFUSION_CORE = registerBlock("a_atlantian_infusion-core", () -> new InfusionBlock(ManaType.ATLANTIAN, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_ATLANTIAN_INFUSION_CORE = registerBlock("u_atlantian_infusion-core", () -> new InfusionBlock(ManaType.ATLANTIAN, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> B_HELLISH_INFUSION_CORE = registerBlock("b_hellish_infusion-core", () -> new InfusionBlock(ManaType.HELLISH, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_HELLISH_INFUSION_CORE = registerBlock("i_hellish_infusion-core", () -> new InfusionBlock(ManaType.HELLISH, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_HELLISH_INFUSION_CORE = registerBlock("a_hellish_infusion-core", () -> new InfusionBlock(ManaType.HELLISH, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_HELLISH_INFUSION_CORE = registerBlock("u_hellish_infusion-core", () -> new InfusionBlock(ManaType.HELLISH, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> B_DRACONIC_INFUSION_CORE = registerBlock("b_draconic_infusion-core", () -> new InfusionBlock(ManaType.DRACONIC, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> I_DRACONIC_INFUSION_CORE = registerBlock("i_draconic_infusion-core", () -> new InfusionBlock(ManaType.DRACONIC, BlockTier.INTERMEDIATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> A_DRACONIC_INFUSION_CORE = registerBlock("a_draconic_infusion-core", () -> new InfusionBlock(ManaType.DRACONIC, BlockTier.ADVANCED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> U_DRACONIC_INFUSION_CORE = registerBlock("u_draconic_infusion-core", () -> new InfusionBlock(ManaType.DRACONIC, BlockTier.ULTIMATE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    //Temp TODO remove later
    public static final RegistryObject<Block> INFUSION = registerBlock("infusion", () -> new InfusionBlock(ManaType.NEXUS, BlockTier.BASIC, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
