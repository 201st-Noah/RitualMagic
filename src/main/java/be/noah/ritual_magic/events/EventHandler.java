package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RitualMagic.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player){return;}
        event.setCanceled("sacrifice".equals(event.getSource().getMsgId()));
    }
}
