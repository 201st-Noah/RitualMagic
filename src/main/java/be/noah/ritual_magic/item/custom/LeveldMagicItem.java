package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.Mana.ManaType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface LeveldMagicItem {


    ManaType getType();

    int getItemLevelCap();

    default int getItemLevel(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt("item_level");
    }

    default void setItemLevel(ItemStack stack, int level) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("item_level", level);
    }

    default void addItemLevel(ItemStack stack, int amount) {
        int current = getItemLevel(stack);
        setItemLevel(stack, current + amount);
    }

    default void appendLevelTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal("Level: " + getItemLevel(stack)));
    }
}
