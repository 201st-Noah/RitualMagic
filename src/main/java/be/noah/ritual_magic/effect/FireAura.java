package be.noah.ritual_magic.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class FireAura extends MobEffect {
    private static final Random random = new Random();

    protected FireAura(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        BlockPos center = pLivingEntity.blockPosition();

        List<LivingEntity> nearbyEntities = pLivingEntity.level().getEntitiesOfClass(
                LivingEntity.class,
                new AABB(center).inflate(pAmplifier),
                entity -> entity != pLivingEntity
        );
        for (LivingEntity entity : nearbyEntities) {
            entity.setSecondsOnFire(1);
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20, 1, false, false, false));
        }
        spawnFireParticlesAround(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    private static void spawnFireParticlesAround(LivingEntity pLivingEntity, int radius) {
        Level level = pLivingEntity.level();
        Vec3 playerPos = pLivingEntity.position();

        int particles = radius * radius / 6 ; // Snowflake count

        for (int i = 0; i < particles; i++) {
            // Random spherical coordinates
            double theta = random.nextDouble() * 2 * Math.PI;    // angle around Y axis
            double phi = random.nextDouble() * Math.PI;          // angle from Y axis
            double distance = random.nextDouble() * radius;

            // Convert spherical to Cartesian coordinates
            double x = playerPos.x + distance * Math.sin(phi) * Math.cos(theta);
            double y = playerPos.y + distance * Math.cos(phi);
            double z = playerPos.z + distance * Math.sin(phi) * Math.sin(theta);

            level.addParticle(ParticleTypes.DRIPPING_LAVA, x, y, z, 0, -0.000, 0);
        }
    }
}
