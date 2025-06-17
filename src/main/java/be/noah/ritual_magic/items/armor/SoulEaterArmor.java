package be.noah.ritual_magic.items.armor;

import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.items.LeveldMagicArmor;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class SoulEaterArmor extends ArmorItem implements GeoItem, LeveldMagicArmor {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SoulEaterArmor(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public ManaType getManaType() {return ManaType.HELLISH;}

    @Override
    public int getItemLevelCap() {return 100;}

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {return this.cache;}

    @Override
    public boolean isDamageable(ItemStack stack) {return false;}

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        LeveldMagicArmor.super.appendLevelTooltip(stack, tooltip);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player) {
            if (!pLevel.isClientSide()) {

                //Boots let you walk over Lava
                if (this.hasBoots(player) && !player.isShiftKeyDown()) {
                    BlockPos center = player.blockPosition();

                    int radius = 2;
                    for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -1, -radius), center.offset(radius, -1, radius))) {
                        if (player.blockPosition().closerThan(pos, radius + 0.5)) {
                            BlockState state = pLevel.getBlockState(pos);

                            if (state.getFluidState().isSource() && state.getFluidState().is(Fluids.LAVA) && pLevel.getBlockState(pos.above()).isAir()) {
                                pLevel.setBlockAndUpdate(pos, ModBlocks.UNSTABLE_MAGMA_BLOCK.get().defaultBlockState());
                                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, (int) ((float)bootLevel(player)/20), false, false));
                            }
                        }
                    }
                }

                // flying in lava
                if(this.hasLeggings(player)){
                    if (player.isInLava()) {
                        player.getAbilities().mayfly = true;
                        player.getAbilities().flying = true;
                        player.getAbilities().setFlyingSpeed((0.1f * ((float) this.leggingsLevel(player) /100)) + 0.01f);
                        player.onUpdateAbilities();
                    } else {
                        if (player.getAbilities().mayfly && !player.isCreative() && !player.isSpectator()) {
                            player.getAbilities().mayfly = false;
                            player.getAbilities().flying = false;
                            player.getAbilities().setFlyingSpeed(0.05f);
                            player.onUpdateAbilities();
                        }
                    }
                } else {
                player.getAbilities().setFlyingSpeed(0.05f);
                player.onUpdateAbilities();
                }
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
