package be.noah.ritual_magic.item.custom;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class RiftManager {
    private static final List<RiftOpener> activeRifts = new ArrayList<>();

    public static void addRift(RiftOpener rift) {
        activeRifts.add(rift);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Iterator<RiftOpener> iterator = activeRifts.iterator();
            while (iterator.hasNext()) {
                RiftOpener rift = iterator.next();
                rift.tick();
                if (rift.isDone()) {
                    iterator.remove();
                }
            }
        }
    }
}
