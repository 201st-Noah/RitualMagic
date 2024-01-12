package be.noah.ritual_magic.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModTorch extends Item {

    private int burnTime = 0;
    private enum Typ {FIRE, SOULFIRE, DRAGONFIRE, DEEPSEEFIRE}
    private boolean ON = false;
    public ModTorch(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return super.getBarColor(pStack);
    }
    @Override
    public int getBarWidth(ItemStack pStack) {
        return super.getBarWidth(pStack);
    }
    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }
    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
