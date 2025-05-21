package be.noah.ritual_magic.item;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.item.armor.DwarvenArmor;
import be.noah.ritual_magic.item.armor.ModArmorMaterials;
import be.noah.ritual_magic.item.custom.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RitualMagic.MODID);

    //Simple Items
    public static final RegistryObject<Item> DRAGON_SCALE = ITEMS.register("dragon_scale", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRAGON_PLATE = ITEMS.register("dragon_plate", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ATLANTIAN_STEEL_INGOT = ITEMS.register("atlantian_steel_ingot", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ATLANTIAN_SCRAP = ITEMS.register("atlantian_scrap", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_SCRAP = ITEMS.register("dwarven_scrap", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WARDEN_CORE = ITEMS.register("warden_core", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_STEEL_INGOT = ITEMS.register("dwarven_steel_ingot", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_TEMPLATE = ITEMS.register("dwarven_template", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LOST_SOUL = ITEMS.register("lost_soul", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_STEEL_ARMOR_PLATE = ITEMS.register("dwarven_steel_armor_plate", ()-> new ArmorPlate(ModToolTiers.DWARVEN,new Item.Properties()));
    public static final RegistryObject<Item> PURE_NETHERITE = ITEMS.register("pure_netherite", ()-> new Item(new Item.Properties()));

    //Advanced Items
    public static final RegistryObject<Item> TORCH = ITEMS.register("torch", ()-> new ModTorch(new Item.Properties()));
    public static final RegistryObject<Item> SPEER = ITEMS.register("speer", ()-> new Speer(ModToolTiers.DRACONIC,80, -3.5f,new Item.Properties()));
    public static final RegistryObject<Item> DRAWEN_AXE = ITEMS.register("dwarven_axe", ()-> new DwarvenAxe(ModToolTiers.DWARVEN,80,-3.5f,new Item.Properties()));
    public static final RegistryObject<Item> BUILDERSTAFF = ITEMS.register("builderstaff", ()-> new BuildersStaff(new Item.Properties()));
    public static final RegistryObject<Item> NETHER_SCEPTRE = ITEMS.register("nether_scepter", ()-> new NetherScepter(new Item.Properties()));
    public static final RegistryObject<Item> ICE_SWORD = ITEMS.register("ice_sword", ()-> new IceSword(new Item.Properties()));

    //Armors
    public static final RegistryObject<Item> DWAREN_STEEL_HELMET = ITEMS.register("dwarven_steel_helmet", ()-> new DwarvenArmor(ModArmorMaterials.DWARVEN_STEEL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> DWAREN_STEEL_CHESTPLATE = ITEMS.register("dwarven_steel_chestplate", ()-> new DwarvenArmor(ModArmorMaterials.DWARVEN_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> DWAREN_STEEL_LEGGINGS = ITEMS.register("dwarven_steel_leggings", ()-> new DwarvenArmor(ModArmorMaterials.DWARVEN_STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> DWAREN_STEEL_BOOTS = ITEMS.register("dwarven_steel_boots", ()-> new DwarvenArmor(ModArmorMaterials.DWARVEN_STEEL, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
