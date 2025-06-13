package be.noah.ritual_magic.events;

import be.noah.ritual_magic.items.LeveldMagicItem;
import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class TelekinesisHandler {
    public static final Map<UUID, GrabbedEntity> grabbedEntities = new HashMap<>();
    public record GrabbedEntity(LivingEntity target, double distance) { }
    private static boolean NOMANA = true;

    public static void startGrab(Player player, LivingEntity target) {
        Vec3 playerEyePos = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 targetCenter = target.position().add(0, target.getBbHeight() / 2.0, 0);
        double distance = playerEyePos.distanceTo(targetCenter);
        grabbedEntities.put(player.getUUID(), new GrabbedEntity(target, distance));
    }

    public static void endGrab(Player player) {
        grabbedEntities.remove(player.getUUID());
    }

    public static boolean isGrabbing(Player player) {
        return grabbedEntities.containsKey(player.getUUID());
    }

    public static void tickAll(Level level) {
        Iterator<Map.Entry<UUID, GrabbedEntity>> iterator = grabbedEntities.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, GrabbedEntity> entry = iterator.next();
            Player player = level.getPlayerByUUID(entry.getKey());
            GrabbedEntity grab = entry.getValue();
            if (player == null || grab.target.isRemoved() || !grab.target.isAlive()) {
                iterator.remove();
                continue;
            }
            if (!player.isUsingItem()) {
                iterator.remove();
                continue;
            }
            ServerLevel serverLevel = ((ServerPlayer) player).serverLevel();
            ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof LeveldMagicItem) {
                if(!(NOMANA || data.consume(player.getUUID(), ManaType.DRACONIC,  ((LeveldMagicItem) heldItem.getItem()).getItemLevel(heldItem)))){return;}
            }

            Vec3 eyePos = player.position().add(0, player.getEyeHeight(), 0);

            double yaw = Math.toRadians(-player.getYRot());
            double pitch = Math.toRadians(-player.getXRot());

            double x = grab.distance * Math.cos(pitch) * Math.sin(yaw);
            double y = grab.distance * Math.sin(pitch);
            double z = grab.distance * Math.cos(pitch) * Math.cos(yaw);

            // Desired center position:
            Vec3 intendedCenterPos = eyePos.add(x, y, z);

            // Actual entity center position:
            Vec3 entityPos = grab.target.position().add(0, grab.target.getBbHeight() / 2.0, 0);

            // Calculate difference vector
            Vec3 toTarget = intendedCenterPos.subtract(entityPos);

            // Apply velocity towards target with strength factor
            double maxSpeed = 2;  // Max speed per tick
            if(player.getMainHandItem().getItem() instanceof LeveldMagicItem leveldMagicItem) {
                maxSpeed = leveldMagicItem.getItemLevel(player.getMainHandItem());
            }
            Vec3 motion = toTarget.normalize().scale(Math.min(toTarget.length(), maxSpeed));

            // Apply velocity
            grab.target.setDeltaMovement(motion);
            grab.target.hasImpulse = true;
        }
    }
}
