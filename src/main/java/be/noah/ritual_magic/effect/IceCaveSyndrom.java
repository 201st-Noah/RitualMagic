package be.noah.ritual_magic.effect;

import be.noah.ritual_magic.entities.HomingProjectile;
import be.noah.ritual_magic.entities.ModEntities;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class IceCaveSyndrom extends MobEffect {

    protected IceCaveSyndrom(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.HARMFUL, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        Vec3 spikePos = new Vec3(pLivingEntity.getX(), pLivingEntity.getY() + 20, pLivingEntity.getZ());
        HomingProjectile projectile = new HomingProjectile(ModEntities.HOMING_PROJECTILE.get(), pLivingEntity.level());
        projectile.setPos(spikePos.x, spikePos.y, spikePos.z);
        projectile.setOwner(null); // might cause weird kill Message
        projectile.setTarget(pLivingEntity);
        projectile.setNoGravity(true);
        projectile.setDelayRange(1000, 1000);
        // Richtung zum Ziel
        Vec3 direction = new Vec3(
                pLivingEntity.getX() - spikePos.x,
                (pLivingEntity.getY() + pLivingEntity.getBbHeight() / 2) - spikePos.y,
                pLivingEntity.getZ() - spikePos.z
        ).normalize();

        projectile.shoot(0, -1, 0, 1.0F, 0.0F);
        pLivingEntity.level().addFreshEntity(projectile);
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 20 == 0;
        //return true;
    }


}
