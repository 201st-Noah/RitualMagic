package be.noah.ritual_magic.utils;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> SACRIFICE =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(RitualMagic.MODID, "sacrifice"));

}
