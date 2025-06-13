package be.noah.ritual_magic.items;

import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface LeveldMagicItem {


    ManaType getManaType();

    int getItemLevelCap();

    default boolean consumeMana(Player player, int amount) {
        ServerLevel serverLevel = ((ServerPlayer) player).serverLevel();
        ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
        return data.consume(player.getUUID(), getManaType(), amount);
    }
    default void addMana(Player player, int amount) {
        ServerLevel serverLevel = ((ServerPlayer) player).serverLevel();
        ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
        data.add(player.getUUID(), getManaType(), amount);
    }

    default int getItemLevel(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt("item_level");
    }

    default void setItemLevel(ItemStack stack, int level) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("item_level", level);
    }
    default int getItemMode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt("item_mode");
    }

    default void setItemMode(ItemStack stack, int level) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("item_mode", level);
    }

    default int getItemAoe(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt("item_aoe");
    }

    default void setItemAoe(ItemStack stack, int level) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("item_aoe", level);
    }

    default void addItemLevel(ItemStack stack, int amount) {
        int current = getItemLevel(stack);
        setItemLevel(stack, current + amount);
    }

    default void appendLevelTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal("Level: " + getItemLevel(stack)));
    }


    default int lvlLinear(ItemStack stack, float variable) {
        return (int)(this.getItemLevel(stack) / variable);
    }

    default int lvlLinear(ItemStack stack, float variable, int max) {
        return Math.min(Math.round(this.getItemLevel(stack) / variable), max);
    }

    default int lvlLinear(ItemStack stack, float variable, int max, int startVal) {
        return Math.max(Math.round(this.getItemLevel(stack) / variable) + startVal, max);
    }
}
