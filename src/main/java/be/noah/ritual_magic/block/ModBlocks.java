package be.noah.ritual_magic.block;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.custom.ItemHolderBlock;
import be.noah.ritual_magic.item.ModCreativeModeTab;
import be.noah.ritual_magic.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RitualMagic.MODID);

    //Simple Blocks
    public static final RegistryObject<Block> DWARVEN_DEBRIS = registerBlock("dwarven_debris", ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(50f,1200.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()), ModCreativeModeTab.RITUAL_MAGIC_TAB);
    public static final RegistryObject<Block> ATLANTIAN_DEBRIS = registerBlock("atlantian_debris", ()-> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND).strength(3f).sound(SoundType.GRAVEL).requiresCorrectToolForDrops()), ModCreativeModeTab.RITUAL_MAGIC_TAB);
    public static final RegistryObject<Block> PETRIFIED_DRAGON_SCALE = registerBlock("petrified_dragon_scale", ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3f,9.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()), ModCreativeModeTab.RITUAL_MAGIC_TAB);
    public static final RegistryObject<Block> POLISHED_OBSIDIAN = registerBlock("polished_obsidian", ()->new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)),ModCreativeModeTab.RITUAL_MAGIC_TAB);
    public static final RegistryObject<Block> SOUL_BRICKS = registerBlock("soul_bricks", ()->new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(5.0F, 1200.0F)),ModCreativeModeTab.RITUAL_MAGIC_TAB);

    //Block Entitys
    public static final RegistryObject<Block> ITEM_HOLDER = registerBlock("item_holder", ()-> new ItemHolderBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f,12.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()), ModCreativeModeTab.RITUAL_MAGIC_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
