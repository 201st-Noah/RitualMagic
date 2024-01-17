package be.noah.ritual_magic.item.armor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DwarvenArmor extends ArmorItem {
    //Reduces and removes the negativ effects like slowness, not able to swim, falldamage = x2
    private static final String OPTIMISING_COUNT = "OptimisingCount";
    //makes armor stronger, less damage, absorption hearts
    private static final String REFORGE_COUNT = "ReforgeCount";
    private static final String MAGIC_CAPACITY = "MagicCapacity";
    //gives strength, speed, regeneration,(maby also saturation)
    private static final String MAGIC_LV = "MagicLv";

    public DwarvenArmor(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
    private void setReforgeCount(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(REFORGE_COUNT, value);
    }
    private int getReforgeCount(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(REFORGE_COUNT) : 0;
    }
    private void setMagicCapacity(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(MAGIC_CAPACITY, value);
    }
    private int getMagicCapacity(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(MAGIC_CAPACITY) : 0;
    }
    private void setOptimisingCount(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(OPTIMISING_COUNT, value);
    }
    private int getOptimisingCount(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(OPTIMISING_COUNT) : 0;
    }
}
