package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.items.armor.SoulEaterArmor;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = RitualMagic.MODID, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        Entity entity = camera.getEntity();

        if (entity instanceof Player player) {
            ItemStack helmet = player.getInventory().getArmor(3);
            if(helmet.getItem() instanceof SoulEaterArmor soulEaterArmor && isPlayerHeadInLava(player)) {
                float itemLevel = soulEaterArmor.getItemLevel(helmet);
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(itemLevel);
                event.setCanceled(true);
            }
        }
    }

    public static boolean isPlayerHeadInLava(Player player) {
        Level level = player.level();
        Vec3 eyePos = player.getEyePosition(1.0F);
        BlockPos eyeBlockPos = BlockPos.containing(eyePos);
        FluidState fluidState = level.getFluidState(eyeBlockPos);
        return fluidState.is(Fluids.LAVA);
    }
}
