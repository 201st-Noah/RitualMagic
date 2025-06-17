package be.noah.ritual_magic.client;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.items.armor.SoulEaterArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public final class SoulEaterArmorRenderer extends GeoArmorRenderer<SoulEaterArmor> {

    public SoulEaterArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(RitualMagic.MODID, "armor/soul_eater_armor")));
    }

}
