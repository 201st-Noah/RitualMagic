package be.noah.ritual_magic.effect;

import be.noah.ritual_magic.entities.HomingProjectile;
import be.noah.ritual_magic.entities.ModEntities;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class IceRain extends MobEffect {

    private static final Random random = new Random();

    protected IceRain(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.HARMFUL, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        int randX = 0;
        int randZ = 0;
        if (pAmplifier > 0) {
            randX = random.nextInt(-((int)Math.sqrt(pAmplifier)), (int)Math.sqrt(pAmplifier));
            randZ = random.nextInt(-((int)Math.sqrt(pAmplifier)), (int)Math.sqrt(pAmplifier));
        }
        Vec3 spikePos = new Vec3(pLivingEntity.getX() + randX, pLivingEntity.getY() + 20, pLivingEntity.getZ() + randZ);
        HomingProjectile projectile = new HomingProjectile(ModEntities.HOMING_PROJECTILE.get(), pLivingEntity.level());
        projectile.setPos(spikePos.x, spikePos.y, spikePos.z);
        projectile.setOwner(null); // might cause weird kill Message
        projectile.setTarget(pLivingEntity);
        projectile.setNoGravity(true);
        projectile.setDelayRange(1000, 1000);
        projectile.shoot(0, -1, 0, 1.0F, 0.0F);
        pLivingEntity.level().addFreshEntity(projectile);
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        if (pAmplifier <= 0) {return (pDuration % 20) == 0;}
        return pDuration % (21 - pAmplifier) == 0;
    }


}
