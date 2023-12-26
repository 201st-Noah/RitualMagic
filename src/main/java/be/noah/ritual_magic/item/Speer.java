package be.noah.ritual_magic.item;

import be.noah.ritual_magic.entities.BallLightning;
import be.noah.ritual_magic.entities.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
public class Speer extends Item {

    public Speer(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){

        ItemStack itemstack = player.getItemInHand(hand);

        if(!player.isShiftKeyDown()){
            if(!level.isClientSide()){
                System.out.println("magic");
                BallLightning ballLightning = new BallLightning(ModEntities.BALL_LIGHTNING.get(), player, level);
                ballLightning.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 5.0F, 1.0F);
                level.addFreshEntity(ballLightning);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

}
