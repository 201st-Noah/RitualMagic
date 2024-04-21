package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;

import be.noah.ritual_magic.item.armor.DwarvenArmor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

import static be.noah.ritual_magic.item.armor.DwarvenArmor.getPurity;

@Mod.EventBusSubscriber(modid = RitualMagic.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    private static final Random random = new Random();
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
            Item head = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
            Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
            Item legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
            Item feet = player.getItemBySlot(EquipmentSlot.FEET).getItem();
            //((DwarvenArmor) feet).setPurity(player.getItemBySlot(EquipmentSlot.FEET),32);

            int chance = 0;
            if(chest instanceof DwarvenArmor) {chance = chance + (getPurity(player.getItemBySlot(EquipmentSlot.CHEST)));}
            if(feet instanceof DwarvenArmor) {chance = chance + (getPurity(player.getItemBySlot(EquipmentSlot.FEET)));}
            if(head instanceof DwarvenArmor) {chance = chance + (getPurity(player.getItemBySlot(EquipmentSlot.HEAD)));}
            if(legs instanceof DwarvenArmor) {chance = chance + (getPurity(player.getItemBySlot(EquipmentSlot.LEGS)));}

            System.out.println(chance);
            if (attacker instanceof LivingEntity livingAttacker) {
                if(random.nextInt(101) < chance){
                    livingAttacker.knockback(0.5D, player.getX() - attacker.getX(), player.getZ() - attacker.getZ());
                    return true;
                }
            }
            if (attacker instanceof Arrow){
                return random.nextInt(101) < 2 * chance;
            }
        }
        return false;
    }
}
