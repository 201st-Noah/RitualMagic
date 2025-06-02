package be.noah.ritual_magic.entities;

import be.noah.ritual_magic.block.custom.IceSpikeBlock;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class HomingProjectile extends ThrowableProjectile {
    private Entity target;
    private static final float SPEED = 1.4F;
    private static final float MAX_LIFE_TICKS = 400;
    private int lifeTicks = 0;
    private Vec3 direction;
    private int delayTicks;
    private boolean isDelayed = true;
    private static final Random random = new Random();
    private int minDelay; // Minimale Verzögerung
    private int maxDelay;
    private int damage;

    public HomingProjectile(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.delayTicks = 0;
        this.minDelay = 0;
        this.maxDelay = 200; // Standard: 1 Sekunde max
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    //not working
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerable()) {
            return false;
        }
        // Reagiere nur auf Projektile
        if (source.getDirectEntity() instanceof net.minecraft.world.entity.projectile.AbstractArrow) {
            if (!this.level().isClientSide) {
                playDestroySound();
                this.discard(); // Zerstöre das Projektil
            }
            return true;
        }
        return false;
    }

    public void setDelayRange(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.delayTicks = random.nextInt(maxDelay - minDelay + 1) + minDelay;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    private void playDestroySound() {
        if (!this.level().isClientSide) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL,
                    1.0F, 1.0F);
        }
    }


    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);}

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        this.direction = new Vec3(x, y, z).normalize();
    }

    @Override
    public void tick() {
        super.tick();

        lifeTicks++;
        if (lifeTicks > MAX_LIFE_TICKS) {
            this.discard();
            playDestroySound();
            return;
        }

        // Wenn noch in Verzögerung
        if (isDelayed) {
            if (delayTicks > 0) {
                delayTicks--;
                return;
            }
            isDelayed = false;
        }

        if (!this.level().isClientSide && target != null && target.isAlive()) {
            Vec3 currentPos = this.position();
            Vec3 targetPos = target.position().add(0, target.getBbHeight() * 0.5, 0);

            // Berechne die neue Richtung zum Ziel
            Vec3 targetDirection = targetPos.subtract(currentPos).normalize();

            // Interpoliere sanft zwischen aktueller und gewünschter Richtung
            direction = lerp(direction, targetDirection, 0.1);

            // Setze die Bewegung in die aktuelle Richtung
            this.setDeltaMovement(direction.scale(SPEED));

            // Aktualisiere die Rotation des Projektils
            this.setYRot((float) (Math.atan2(direction.x, direction.z) * 180.0F / Math.PI));
            this.setXRot((float) (Math.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * 180.0F / Math.PI));
        } else if (!this.level().isClientSide && target != null && (!target.isAlive())) {
            this.discard();
            playDestroySound();
        }
    }

    private Vec3 lerp(Vec3 start, Vec3 end, double fraction) {
        double x = start.x + (end.x - start.x) * fraction;
        double y = start.y + (end.y - start.y) * fraction;
        double z = start.z + (end.z - start.z) * fraction;
        return new Vec3(x, y, z).normalize();
    }


    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            // Deal damage to the hit entity
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        200, 1, false, false));
            }
            entity.hurt(this.level().damageSources().playerAttack((Player) this.getOwner()), damage);
            // Remove the projectile
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 0.7F, 1.0F);
            this.discard();

        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            if(!(this.level().getBlockState(result.getBlockPos()).getBlock() instanceof IceSpikeBlock)) {
                playDestroySound();
                this.discard();
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        // No synced data needed for basic functionality
    }
}