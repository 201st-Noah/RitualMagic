package be.noah.ritual_magic.client;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.items.armor.IceArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import software.bernie.geckolib.model.GeoModel;

public class IceArmorModel extends GeoModel<IceArmorItem> {
    private LivingEntity wearer;

    public void setWearingEntity(LivingEntity entity) {
        this.wearer = entity;
    }

    @Override
    public ResourceLocation getModelResource(IceArmorItem item) {
        return new ResourceLocation(RitualMagic.MODID, "geo/item/armor/ice_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IceArmorItem item) {
        if (shouldMakeInvisible(wearer)) {
            return new ResourceLocation(RitualMagic.MODID, "textures/item/armor/empty.png"); // Transparent texture
        }
        return new ResourceLocation(RitualMagic.MODID, "textures/item/armor/ice_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IceArmorItem animatable) {
        return null;
    }

    private boolean shouldMakeInvisible(LivingEntity wearer) {
        if (wearer == null) return false;
        return wearer.hasEffect(MobEffects.INVISIBILITY);
    }
}
