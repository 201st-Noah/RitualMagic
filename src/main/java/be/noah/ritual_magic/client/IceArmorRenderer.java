package be.noah.ritual_magic.client;

import be.noah.ritual_magic.items.armor.IceArmorItem;

import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class IceArmorRenderer extends GeoArmorRenderer<IceArmorItem> {
    public IceArmorRenderer() {
        super(new IceArmorModel());
    }

}
