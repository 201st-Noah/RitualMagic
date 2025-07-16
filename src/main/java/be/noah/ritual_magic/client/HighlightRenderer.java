package be.noah.ritual_magic.client;

import be.noah.ritual_magic.RitualMagic;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
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
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        if (ClientHighlightManager.HIGHLIGHTED_BLOCKS.isEmpty()) return;

        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        Vec3 camPos = camera.getPosition();

        // Setup render state
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableCull();

        // Apply the view matrix
        RenderSystem.getModelViewMatrix().set(poseStack.last().pose());

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();

        // Start drawing
        buffer.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        // Render all highlighted blocks
        for (BlockPos pos : ClientHighlightManager.HIGHLIGHTED_BLOCKS) {
            AABB box = new AABB(pos).inflate(0.002D);
            drawBox(buffer, box, camPos, 0f, 0.8f, 1f, 1f);
        }

        // Finish rendering
        tessellator.end();

        // Restore render state
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();

    }


    private static void drawBox(BufferBuilder buffer, AABB box, Vec3 cam, float r, float g, float b, float a) {
        // Adjust box coordinates relative to camera position
        double minX = box.minX - cam.x;
        double minY = box.minY - cam.y;
        double minZ = box.minZ - cam.z;
        double maxX = box.maxX - cam.x;
        double maxY = box.maxY - cam.y;
        double maxZ = box.maxZ - cam.z;

        // Bottom face
        buffer.vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, minY, minZ).color(r, g, b, a).endVertex();

        // Top face
        buffer.vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();

        // Vertical edges
        buffer.vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
    }


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ClientHighlightManager.tick();
        }
    }
}