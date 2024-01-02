package be.noah.ritual_magic.block;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.custom.MiningCoreBlock;
import be.noah.ritual_magic.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
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



    //Block Entitys
    public static final RegistryObject<Block> MINING_CORE = registerBlock("mining_core",()->new MiningCoreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    //public static final RegistryObject<Block> SOUL_FORGE = registerBlock("soul_forge", ()->new SoulForgeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));

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
