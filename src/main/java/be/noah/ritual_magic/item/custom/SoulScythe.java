package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.Mana.ManaType;
import be.noah.ritual_magic.item.LeveldMagicItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class SoulScythe extends HoeItem implements LeveldMagicItem {
    /*
    Abilitys:
        When Leftcklick on air sent Horizontal short ranged wave, witch deel damage, has quite wide angel
        NWhen used on Soulsand makes new farmland witch doesent need water for crops to grow (maybe makes them grow faster)
        When enemy is killed, it gives Hellish Mana back to the player
        Maybe Creates instant ghost like Copys of the Mobs you killed
    */

    public SoulScythe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public ManaType getManaType() {
        return ManaType.HELLISH;
    }

    @Override
    public int getItemLevelCap() {
        return 0;
    }
}
