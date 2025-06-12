package be.noah.ritual_magic.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;


public interface LeveldMagicArmor extends LeveldMagicItem {

    default boolean hasBoots(Player player) {
        return isArmorInSlot(player, ArmorItem.Type.BOOTS);
    }

    default boolean hasLeggings(Player player) {
        return isArmorInSlot(player, ArmorItem.Type.LEGGINGS);
    }

    default boolean hasChestplate(Player player) {
        return isArmorInSlot(player, ArmorItem.Type.CHESTPLATE);
    }

    default boolean hasHelmet(Player player) {
        return isArmorInSlot(player, ArmorItem.Type.HELMET);
    }

    default boolean hasFullSet(Player player) {
        return hasBoots(player) && hasLeggings(player) && hasChestplate(player) && hasHelmet(player);
    }

    default boolean isArmorInSlot(Player player, ArmorItem.Type type) {
        ItemStack stack = player.getInventory().getArmor(type.getSlot().getIndex());
        return !stack.isEmpty() && stack.getItem().getClass() == this.getClass();
    }

    default int bootLevel(Player player) {
        return getItemLevel(player.getInventory().getArmor(0));
    }
    default int leggingsLevel(Player player) {
        return getItemLevel(player.getInventory().getArmor(1));
    }
    default int chestPlateLevel(Player player) {
        return getItemLevel(player.getInventory().getArmor(2));
    }
    default int HelmetLevel(Player player) {
        return getItemLevel(player.getInventory().getArmor(3));
    }
}
