package be.noah.ritual_magic.entities.model;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.entities.LavaMinion;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LavaMinionModel extends GeoModel<LavaMinion> {

    @Override
    public ResourceLocation getModelResource(LavaMinion animatable) {
        return new ResourceLocation(RitualMagic.MODID, "geo/entity/lava_minion.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LavaMinion animatable) {
        return new ResourceLocation(RitualMagic.MODID, "textures/entity/lava_minion.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LavaMinion animatable) {
        return new ResourceLocation(RitualMagic.MODID, "animations/lava_minion.animation.json");
    }
}
