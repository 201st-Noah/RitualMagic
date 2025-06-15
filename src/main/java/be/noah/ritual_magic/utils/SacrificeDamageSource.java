package be.noah.ritual_magic.utils;


import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class SacrificeDamageSource extends DamageSource {

    public SacrificeDamageSource(Holder<DamageType> damageTypeHolder) {
        super(damageTypeHolder);
    }
}
