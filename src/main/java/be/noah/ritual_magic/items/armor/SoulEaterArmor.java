package be.noah.ritual_magic.items.armor;

import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.items.LeveldMagicArmor;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

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
                            }
                        }
                    }
                }

                if(this.hasLeggings(player)){

                }
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
