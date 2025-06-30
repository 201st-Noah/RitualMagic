package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.entities.LavaMinion;
import be.noah.ritual_magic.entities.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = RitualMagic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LAVA_MINION.get(), LavaMinion.createAttributes().build());
    }
}