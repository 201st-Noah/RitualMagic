package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.entities.ThrownDwarvenAxe;
import be.noah.ritual_magic.Mana.ManaType;
import be.noah.ritual_magic.item.LeveldMagicItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class DwarvenAxe extends AxeItem implements LeveldMagicItem {
    public DwarvenAxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else if (EnchantmentHelper.getRiptide(itemstack) > 0 && !pPlayer.isInWaterOrRain()) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeCharged;
            if (i >= 10) {
                ThrownDwarvenAxe thrownDwarvenAxe = new ThrownDwarvenAxe(pLevel, player, pStack);
                thrownDwarvenAxe.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0F);
                if (player.getAbilities().instabuild) {
                    thrownDwarvenAxe.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
                pLevel.addFreshEntity(thrownDwarvenAxe);
                pLevel.playSound((Player)null, thrownDwarvenAxe, SoundEvents.ANVIL_FALL, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(pStack);
                }
            }
        }
    }
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public ManaType getType() {
        return ManaType.DWARVEN;
    }

    @Override
    public int getItemLevelCap() {
        return 16;
    }
}
