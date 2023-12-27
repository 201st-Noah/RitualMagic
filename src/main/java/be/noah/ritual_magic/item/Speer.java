package be.noah.ritual_magic.item;

import be.noah.ritual_magic.entities.BallLightning;
import be.noah.ritual_magic.entities.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
public class Speer extends SwordItem {

    private final int cooldowntime = 8;

    public Speer(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){

        ItemStack itemstack = player.getItemInHand(hand);

        if(!player.isShiftKeyDown()){
            if(!level.isClientSide()){
                BallLightning ballLightning = new BallLightning(ModEntities.BALL_LIGHTNING.get(), player, level);
                ballLightning.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 5.0F, 1.0F);
                level.addFreshEntity(ballLightning);
            }
            player.getCooldowns().addCooldown(this, cooldowntime);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

}
