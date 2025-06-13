package be.noah.ritual_magic.client;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.items.armor.DwarvenArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DwarvenArmorRenderer extends GeoArmorRenderer<DwarvenArmor> {
    public DwarvenArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(RitualMagic.MODID, "armor/dwarven_armor")));
    }
}
