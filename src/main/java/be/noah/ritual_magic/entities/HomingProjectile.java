package be.noah.ritual_magic.entities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class HomingProjectile extends ThrowableProjectile {
    private Entity target;
    private static final float SPEED = 0.7F;
    private static final float MAX_LIFE_TICKS = 400; // 10 seconds
    private int lifeTicks = 0;
    private Vec3 direction;


    public HomingProjectile(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public HomingProjectile(EntityType<? extends ThrowableProjectile> type, double x, double y, double z, Level worldIn) {
        super(type, x, y, z, worldIn);
    }

    public HomingProjectile(EntityType<? extends ThrowableProjectile> type, LivingEntity shooter, Level worldIn) {
        super(type, shooter, worldIn);
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerable()) {
            return false;
        }
        // Reagiere nur auf Projektile
        if (source.getDirectEntity() instanceof net.minecraft.world.entity.projectile.AbstractArrow) {
            if (!this.level().isClientSide) {
                this.discard(); // Zerstöre das Projektil
            }
            return true;
        }
        return false;
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
            return;
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
            entity.hurt(this.level().damageSources().magic(), 8.0F);
            // Remove the projectile
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData() {
        // No synced data needed for basic functionality
    }
}