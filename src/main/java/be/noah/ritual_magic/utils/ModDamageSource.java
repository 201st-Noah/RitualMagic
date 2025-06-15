package be.noah.ritual_magic.utils;


import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageSource extends DamageSource {

    public ModDamageSource(Holder<DamageType> damageTypeHolder) {
        super(damageTypeHolder);
    }
}
