package be.noah.ritual_magic.items.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArmorPlate extends TieredItem {
    private static final String PURITY = "Purity";
    private static final String EXPERIENCE = "Experience";
    private static final String TEMPERATURE = "Temperature";
    private static final String ELEMENTAL_AFFINITY = "ElementalAffinity";

    public ArmorPlate(Tier toolTier, Properties pProperties) {
        super(toolTier, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Purity: " + getPurity(pStack)));
        pTooltipComponents.add(Component.literal("Temperature: " + getTemperature(pStack)));
        pTooltipComponents.add(Component.literal("ElementalAffinity: " + getElementalAffinity(pStack)));
        pTooltipComponents.add(Component.literal("Experience: " + getExperience(pStack)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    private int getPurity(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(PURITY) : 0;
    }

    private void setPurity(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(PURITY, value);
    }

    private int getExperience(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(EXPERIENCE) : 0;
    }

    private void setExperience(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(EXPERIENCE, value);
    }

    private int getTemperature(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(TEMPERATURE) : 0;
    }

    private void setTemperature(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(TEMPERATURE, value);
    }

    private int getElementalAffinity(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(ELEMENTAL_AFFINITY) : 0;
    }

    private void setTElementalAffinity(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(ELEMENTAL_AFFINITY, value);
    }

}
