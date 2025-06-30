package be.noah.ritual_magic.entities.client;

import be.noah.ritual_magic.entities.LavaMinion;
import be.noah.ritual_magic.entities.model.LavaMinionModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LavaMinionRenderer extends GeoEntityRenderer<LavaMinion> {
    public LavaMinionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LavaMinionModel());
    }
}