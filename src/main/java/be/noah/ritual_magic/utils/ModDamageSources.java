package be.noah.ritual_magic.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class ModDamageSources {
    public static DamageSource sacrifice(Level level) {
        return create(level, ModDamageTypes.SACRIFICE);
    }

    private static DamageSource create(Level level, ResourceKey<DamageType> key) {
        Holder<DamageType> holder = level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolder(key)
                .orElseThrow(() -> new IllegalStateException("DamageType not found: " + key.location()));
        return new DamageSource(holder);
    }
}
