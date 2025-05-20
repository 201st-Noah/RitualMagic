package be.noah.ritual_magic.entities;

import be.noah.ritual_magic.item.ModItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ThrownDwarvenAxe extends AbstractArrow {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK;
    private static final double RETURN_SPEED_MODIFIER = 5F;

    static {
        DATA_ITEM_STACK = SynchedEntityData.defineId(ThrownDwarvenAxe.class, EntityDataSerializers.ITEM_STACK);
        //DATA_RETURNING = SynchedEntityData.defineId(ThrownDwarvenAxe.class, EntityDataSerializers.f_135035_);
    }

    public int clientSideReturnAxeTickCount;
    private boolean dealtDamage;
    private ItemStack axeItem = new ItemStack(Items.NETHERITE_AXE);

    public ThrownDwarvenAxe(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public ThrownDwarvenAxe(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(ModEntities.THROWN_DWARVEN_AXE.get(), pShooter, pLevel);
        this.axeItem = pStack.copy();
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        if ( (this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptableReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * RETURN_SPEED_MODIFIER, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * RETURN_SPEED_MODIFIER;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnAxeTickCount == 0) {
                    this.playSound(SoundEvents.ANVIL_DESTROY, 10.0F, 1.0F);
                }

                ++this.clientSideReturnAxeTickCount;
            }
        }

        super.tick();
    }

    private boolean isAcceptableReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    protected @NotNull ItemStack getPickupItem() {
        return this.axeItem.copy();
    }

    protected boolean tryPickup(@NotNull Player pPlayer) {
        return super.tryPickup(pPlayer) || this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
    }

    protected float getWaterInertia() {
        return 0.99F;
    }

    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        float f = 80.0F;
        if (entity instanceof LivingEntity livingentity) {
            f += EnchantmentHelper.getDamageBonus(this.axeItem, livingentity.getMobType());
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, entity1 == null ? this : entity1);
        this.dealtDamage = true;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingEntity1) {
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingEntity1, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingEntity1);
                }

                this.doPostHurtEffects(livingEntity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void setItem(@NotNull ItemStack pStack) {
        if (!pStack.is(this.getDefaultItem()) || pStack.hasTag()) {
            this.entityData.set(DATA_ITEM_STACK, pStack.copy());
        }
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.entityData.get(DATA_ITEM_STACK);
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    protected Item getDefaultItem() {
        return ModItems.DRAWEN_AXE.get();
    }
}
