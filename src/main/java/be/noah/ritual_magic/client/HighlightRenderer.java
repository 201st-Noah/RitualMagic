package be.noah.ritual_magic.client;

import be.noah.ritual_magic.RitualMagic;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RitualMagic.MODID, value = Dist.CLIENT)
public class HighlightRenderer {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;
        if (ClientHighlightManager.HIGHLIGHTED_BLOCKS.isEmpty()) return;

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        Camera camera = event.getCamera();
        Vec3 camPos = camera.getPosition();

        // 🔁 Disable depth test so outlines are visible through walls
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        for (BlockPos pos : ClientHighlightManager.HIGHLIGHTED_BLOCKS) {
            AABB box = AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos))
                    .move(-camPos.x, -camPos.y, -camPos.z);
            LevelRenderer.renderLineBox(
                    poseStack,
                    buffer.getBuffer(RenderType.lines()),
                    box,
                    0f, 0.8f, 1f, 1.0f
            );
        }

        buffer.endBatch(RenderType.lines());

        // ✅ Restore depth test to avoid breaking rendering after this
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

   @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ClientHighlightManager.tick();
        }
    }
}