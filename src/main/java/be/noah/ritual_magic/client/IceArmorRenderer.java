package be.noah.ritual_magic.client;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.items.armor.IceArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class IceArmorRenderer extends GeoArmorRenderer<IceArmorItem> {
    public IceArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(RitualMagic.MODID, "armor/ice_armor")));
    }
}
