package be.noah.ritual_magic.items;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.items.armor.DwarvenArmor;
import be.noah.ritual_magic.items.armor.IceArmorItem;
import be.noah.ritual_magic.items.armor.ModArmorMaterials;
import be.noah.ritual_magic.items.armor.SoulEaterArmor;
import be.noah.ritual_magic.items.custom.*;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RitualMagic.MODID);

    //Simple Items
    public static final RegistryObject<Item> DRAGON_SCALE = ITEMS.register("dragon_scale", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRAGON_PLATE = ITEMS.register("dragon_plate", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ATLANTIAN_STEEL_INGOT = ITEMS.register("atlantian_steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ATLANTIAN_SCRAP = ITEMS.register("atlantian_scrap", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_SCRAP = ITEMS.register("dwarven_scrap", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WARDEN_CORE = ITEMS.register("warden_core", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_STEEL_INGOT = ITEMS.register("dwarven_steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_TEMPLATE = ITEMS.register("dwarven_template", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LOST_SOUL = ITEMS.register("lost_soul", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_STEEL_ARMOR_PLATE = ITEMS.register("dwarven_steel_armor_plate", () -> new ArmorPlate(ManaType.DWARVEN, new Item.Properties()));
    public static final RegistryObject<Item> ATLANTIAN_STEEL_ARMOR_PLATE = ITEMS.register("atlantian_steel_armor_plate", () -> new ArmorPlate(ManaType.ATLANTIAN, new Item.Properties()));
    public static final RegistryObject<Item> PURE_NETHERITE_ARMOR_PLATE = ITEMS.register("pure_netherite_armor_plate", () -> new ArmorPlate(ManaType.HELLISH, new Item.Properties()));
    public static final RegistryObject<Item> DRAGON_SCALE_ARMOR_PLATE = ITEMS.register("dragon_scale_armor_plate", () -> new ArmorPlate(ManaType.DRACONIC, new Item.Properties()));
    public static final RegistryObject<Item> PURE_NETHERITE_INGOT = ITEMS.register("pure_netherite_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ICE_SHARD = ITEMS.register("ice_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ICE_SWORD_HILT = ITEMS.register("ice_sword_hilt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHARP_ICE_SHARD = ITEMS.register("sharp_ice_shard", () -> new Item(new Item.Properties()));

    //Advanced Items
    public static final RegistryObject<Item> NEXUS_MANA_RUNE = ITEMS.register("nexus_mana_rune", () -> new ManaRune(new Item.Properties(), ManaType.NEXUS, 100));
    public static final RegistryObject<Item> DWARVEN_MANA_RUNE = ITEMS.register("dwarven_mana_rune", () -> new ManaRune(new Item.Properties(), ManaType.DWARVEN, 100));
    public static final RegistryObject<Item> DRACONIC_MANA_RUNE = ITEMS.register("draconic_mana_rune", () -> new ManaRune(new Item.Properties(), ManaType.DRACONIC, 100));
    public static final RegistryObject<Item> ATLANTIAN_MANA_RUNE = ITEMS.register("atlantian_mana_rune", () -> new ManaRune(new Item.Properties(), ManaType.ATLANTIAN, 100));
    public static final RegistryObject<Item> HELLISH_MANA_RUNE = ITEMS.register("hellish_mana_rune", () -> new ManaRune(new Item.Properties(), ManaType.HELLISH, 100));
    public static final RegistryObject<Item> TORCH = ITEMS.register("torch", () -> new ModTorch(new Item.Properties()));
    public static final RegistryObject<Item> SPEER = ITEMS.register("speer", () -> new Speer(ModToolTiers.DRACONIC, 80, -3.5f, new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_AXE = ITEMS.register("dwarven_axe", () -> new DwarvenAxe(ModToolTiers.DWARVEN, 80, -3.5f, new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_PICKAXE = ITEMS.register("dwarven_pickaxe", () -> new DwarvenPickAxe(ModToolTiers.DWARVEN, 80, -3.5f, new Item.Properties()));
    public static final RegistryObject<Item> BUILDERSTAFF = ITEMS.register("builderstaff", () -> new BuildersStaff(new Item.Properties()));
    public static final RegistryObject<Item> NETHER_SCEPTRE = ITEMS.register("nether_scepter", () -> new NetherScepter(new Item.Properties()));
    public static final RegistryObject<Item> ICE_SWORD = ITEMS.register("ice_sword", () -> new IceSword(ModToolTiers.ATLANTIAN, 3, 1, new Item.Properties()));
    public static final RegistryObject<Item> SOUL_SCYTHE = ITEMS.register("soul_scythe", () -> new SoulScythe(ModToolTiers.HELLISH, 30, 1, new Item.Properties()));

    //Armors
    public static final RegistryObject<Item> ICE_HELMET = ITEMS.register("ice_helmet", () -> new IceArmorItem(ModArmorMaterials.ATLANTIAN_STEEL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ICE_CHESTPLATE = ITEMS.register("ice_chestplate", () -> new IceArmorItem(ModArmorMaterials.ATLANTIAN_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ICE_LEGGINGS = ITEMS.register("ice_leggings", () -> new IceArmorItem(ModArmorMaterials.ATLANTIAN_STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ICE_BOOTS = ITEMS.register("ice_boots", () -> new IceArmorItem(ModArmorMaterials.ATLANTIAN_STEEL, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> SOUL_EATER_HELMET = ITEMS.register("soul_eater_helmet", () -> new SoulEaterArmor(ModArmorMaterials.PURE_NETHERITE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SOUL_EATER_CHESTPLATE = ITEMS.register("soul_eater_chestplate", () -> new SoulEaterArmor(ModArmorMaterials.PURE_NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SOUL_EATER_LEGGINGS = ITEMS.register("soul_eater_leggings", () -> new SoulEaterArmor(ModArmorMaterials.PURE_NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SOUL_EATER_BOOTS = ITEMS.register("soul_eater_boots", () -> new SoulEaterArmor(ModArmorMaterials.PURE_NETHERITE, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_STEEL_HELMET = ITEMS.register("dwarven_steel_helmet", () -> new DwarvenArmor(ModArmorMaterials.DWARVEN_STEEL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_STEEL_CHESTPLATE = ITEMS.register("dwarven_steel_chestplate", () -> new DwarvenArmor(ModArmorMaterials.DWARVEN_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_STEEL_LEGGINGS = ITEMS.register("dwarven_steel_leggings", () -> new DwarvenArmor(ModArmorMaterials.DWARVEN_STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_STEEL_BOOTS = ITEMS.register("dwarven_steel_boots", () -> new DwarvenArmor(ModArmorMaterials.DWARVEN_STEEL, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
