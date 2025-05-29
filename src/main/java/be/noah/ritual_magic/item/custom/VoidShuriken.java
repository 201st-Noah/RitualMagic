package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.Mana.ManaType;
import be.noah.ritual_magic.item.LeveldMagicItem;
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
    public ManaType getType() {
        return null;
    }

    @Override
    public int getItemLevelCap() {
        return 0;
    }
}
