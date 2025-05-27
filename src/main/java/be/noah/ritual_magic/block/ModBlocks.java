package be.noah.ritual_magic.block;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.custom.*;
import be.noah.ritual_magic.block.custom.fire.DragonFireBlock;
import be.noah.ritual_magic.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
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
    public static final RegistryObject<Block> DWARVEN_DEBRIS = registerBlock("dwarven_debris", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(50f,1200.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ATLANTIAN_DEBRIS = registerBlock("atlantian_debris", ()-> new GravelBlock(BlockBehaviour.Properties.copy(Blocks.SAND).strength(3f).sound(SoundType.GRAVEL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PETRIFIED_DRAGON_SCALE = registerBlock("petrified_dragon_scale", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).strength(3f,9.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_OBSIDIAN = registerBlock("polished_obsidian", ()->new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> SOUL_BRICKS = registerBlock("soul_bricks", ()->new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(5.0F, 1200.0F)));
    public static final RegistryObject<Block> DWARVEN_STEEL_BLOCK = registerBlock("dwarven_steel_block", ()->new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> DRAGON_FIRE = registerBlock("dragon_fire_block",()-> new DragonFireBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().instabreak().lightLevel((p_152605_) -> 15).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY).noLootTable()));
    public static final RegistryObject<Block> ICE_SPIKE = registerBlock("ice_spike_block", ()-> new IceSpikeBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).strength(5f,3.0f).requiresCorrectToolForDrops().noCollission()));
    public static final RegistryObject<Block> POINTED_ICICLE = registerBlock("pointed_icicle",
            ()-> new PointedIcicleBlock(BlockBehaviour.Properties.copy(Blocks.POINTED_DRIPSTONE)));

    //Block Entities
    public static final RegistryObject<Block> FORGE_T0 = registerBlock("forge_t0", () -> new ForgeBlock(ForgeBlock.Tier.BEGINNER,BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).strength(5f,1200.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> FORGE_T1 = registerBlock("forge_t1", () -> new ForgeBlock(ForgeBlock.Tier.BASIC,BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).strength(25f,1200.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> FORGE_T2 = registerBlock("forge_t2", () -> new ForgeBlock(ForgeBlock.Tier.ADVANCED,BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).strength(150f,1200.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOD_TELEPORTER = registerBlock("mod_portal", () -> new ModTeleporterBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noLootTable().noOcclusion()));
    public static final RegistryObject<Block> MINING_CORE = registerBlock("mining_core",()->new MiningCoreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> ANCIENT_ANVIL = registerBlock("ancient_anvil", ()->new AncientAnvilBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

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
