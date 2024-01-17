package be.noah.ritual_magic.item.armor;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    DWARVEN_STEEL("dwarven_steel", 64, new int[]{ 5, 7, 5, 4 }, 12,
            SoundEvents.ARMOR_EQUIP_IRON, 5f, 1f, () -> Ingredient.of(ModItems.DWARVEN_STEEL_INGOT.get())),
    ATLANTIAN_STEEL("atlantian_steel", 50, new int[]{ 5, 7, 5, 4 }, 24,
            SoundEvents.ARMOR_EQUIP_IRON, 1f, 0.1f, () -> Ingredient.of(ModItems.ATLANTIAN_STEEL_INGOT.get())),
    PURE_NETHERITE("pure_netherite", 50, new int[]{ 5, 7, 5, 4 }, 32,
            SoundEvents.ARMOR_EQUIP_IRON, 1f, 0.3f, () -> Ingredient.of(ModItems.DWARVEN_STEEL_INGOT.get())),
    DRAGON_SCALE("dragon_scale", 40, new int[]{ 5, 7, 5, 4 }, 32,
            SoundEvents.ARMOR_EQUIP_IRON, 1f, 0f, () -> Ingredient.of(ModItems.DRAGON_PLATE.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return RitualMagic.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
