package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.client.ManaHudOverlay;
import be.noah.ritual_magic.client.VoidShieldHudOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RitualMagic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        // Register your custom HUD overlay here
        event.registerAboveAll("void_shield", VoidShieldHudOverlay.HUD_VOID_SHIELD);
        event.registerAboveAll("mana", ManaHudOverlay.MANA);
    }
}
