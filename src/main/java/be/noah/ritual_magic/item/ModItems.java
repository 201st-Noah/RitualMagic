package be.noah.ritual_magic.item;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RitualMagic.MODID);

    //Simple Items
    public static final RegistryObject<Item> DRAGON_SCALE = ITEMS.register("dragon_scale", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.RITUAL_MAGIC_TAB)));
    public static final RegistryObject<Item> DRAGON_PLATE = ITEMS.register("dragon_plate", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.RITUAL_MAGIC_TAB)));
    public static final RegistryObject<Item> ATLANTIAN_STEEL_INGOT = ITEMS.register("atlantian_steel_ingot", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.RITUAL_MAGIC_TAB)));
    public static final RegistryObject<Item> ATLANTIAN_SCRAP = ITEMS.register("atlantian_scrap", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.RITUAL_MAGIC_TAB)));
    public static final RegistryObject<Item> DWARVEN_SCRAP = ITEMS.register("dwarven_scrap", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.RITUAL_MAGIC_TAB)));
    public static final RegistryObject<Item> WARDEN_CORE = ITEMS.register("warden_core", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.RITUAL_MAGIC_TAB)));
    public static final RegistryObject<Item> DWARVEN_STEEL_INGOT = ITEMS.register("dwarven_steel_ingot", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.RITUAL_MAGIC_TAB)));
    public static final RegistryObject<Item> LOST_SOUL = ITEMS.register("lost_soul", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.RITUAL_MAGIC_TAB)));

    //Block Items

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
