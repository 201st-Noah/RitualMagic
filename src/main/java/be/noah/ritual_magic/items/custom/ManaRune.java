package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ManaRune extends Item {

    private final ManaType MANA_TYPE;
    private final int AMOUNT;

    public ManaRune(Properties pProperties, ManaType manaType, int amount) {
        super(pProperties);
        this.MANA_TYPE = manaType;
        this.AMOUNT = amount;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (pLevel.isClientSide()) return InteractionResultHolder.fail(itemstack);
        ServerLevel serverLevel = (ServerLevel) pLevel;
        ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
        data.increaseMax(pPlayer.getUUID(), MANA_TYPE, AMOUNT);
        itemstack.shrink(1);
        return InteractionResultHolder.success(itemstack);
    }
}
