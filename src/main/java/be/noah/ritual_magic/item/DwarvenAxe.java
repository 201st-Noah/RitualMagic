package be.noah.ritual_magic.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;

public class DwarvenAxe extends AxeItem {
    public DwarvenAxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }



}
