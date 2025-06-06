package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.items.LeveldMagicItem;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.world.item.Item;

public class VoidShuriken extends Item implements LeveldMagicItem {
    /*
    Abilitys:
        Standart: can be Thrown, do Damage to entity, and come back
        Can on blockHit spawn dimensionalRift, for traveling to adiffrent dimension
        Can on blockHit spawn VoidRift, sucks nearby entitys in and gives stupid damage

     */
    public VoidShuriken(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ManaType getManaType() {
        return ManaType.DRACONIC;
    }

    @Override
    public int getItemLevelCap() {
        return 0;
    }
}
