package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.Mana.ManaType;
import be.noah.ritual_magic.item.LeveldMagicItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class Trident extends SwordItem implements LeveldMagicItem {
    /*
    Abilitys:
        Water jet: gives much knockback + makes Enemys Drowning on land while attacked with it
        Normal Throwable, on block hit, brings block to you (maybe entitys too)
        Can create a Water vortex, witch can move entirtys Around and drown them (works on land, but better in water)
     */


    public Trident(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public ManaType getManaType() {
        return ManaType.ATLANTIAN;
    }

    @Override
    public int getItemLevelCap() {
        return 16;
    }
}
