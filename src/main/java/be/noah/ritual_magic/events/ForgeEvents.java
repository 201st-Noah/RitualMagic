package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.item.ModItems;
import be.noah.ritual_magic.item.armor.DwarvenArmor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RitualMagic.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onLivingHurt(LivingAttackEvent event)
    {
        Entity entity = event.getEntity();
        Entity attacker = event.getSource().getDirectEntity();
        float damage = event.getAmount();
        event.setCanceled(onAttackOrHurt(entity, attacker, damage));
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        Entity entity = event.getEntity();
        Entity attacker = event.getSource().getDirectEntity();
        float damage = event.getAmount();

        event.setCanceled(onAttackOrHurt(entity, attacker, damage));
    }

    private static boolean onAttackOrHurt(Entity entity, Entity attacker, float damage)
    {
        if(entity instanceof Player player)
        {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            if(stack.is(ModItems.DWAREN_STEEL_CHESTPLATE.get()))
            {
                DwarvenArmor.setPurity(stack,(int) damage);
                if(attacker instanceof LivingEntity livingAttacker)
                    livingAttacker.knockback(0.5D, player.getX() - attacker.getX(), player.getZ() - attacker.getZ());
                return true;
            }
        }
        return false;
    }
}
