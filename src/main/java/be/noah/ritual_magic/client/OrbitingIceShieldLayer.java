package be.noah.ritual_magic.client;

import be.noah.ritual_magic.RitualMagic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;

public class OrbitingIceShieldLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation SHIELD_TEXTURE = new ResourceLocation(RitualMagic.MODID, "textures/entity/ice_orbit_shield.png");
    //private static final int SHIELD_COUNT = 4;
    private static final float RADIUS = 0.7f;
    private static final float HEIGHT = 0.5f;

    public OrbitingIceShieldLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, T player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!ClientIceShieldHandler.hasShield(player.getUUID())) return;
        int shieldCount = 0;
        if (player.getPersistentData().contains("void_shield")) {
            shieldCount =  player.getPersistentData().getInt("void_shield");
        }
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(SHIELD_TEXTURE));

        float time = (player.level().getGameTime() + partialTick) * 0.05f;

        for (int i = 0; i < shieldCount; i++) {
            poseStack.pushPose();

            // Evenly space shields
            double angle = time + (2 * Math.PI / shieldCount) * i;

            double offsetX = Math.cos(angle) * RADIUS;
            double offsetZ = Math.sin(angle) * RADIUS;

            poseStack.translate(offsetX, HEIGHT, offsetZ);
            poseStack.mulPose(Axis.YP.rotationDegrees((float) -Math.toDegrees(angle) +90f));

            renderShieldQuad(poseStack, consumer, light);

            poseStack.popPose();
        }
    }

    private void renderShieldQuad(PoseStack poseStack, VertexConsumer consumer, int light) {
        Matrix4f matrix = poseStack.last().pose();

        float w = 0.4f;
        float h = 0.6f;

        // FRONT FACE
        consumer.vertex(matrix, -w, -h, 0f).color(1f, 1f, 1f, 0.8f).uv(0, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 0, -1).endVertex();
        consumer.vertex(matrix, w, -h, 0f).color(1f, 1f, 1f, 0.8f).uv(1, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 0, -1).endVertex();
        consumer.vertex(matrix, w, h, 0f).color(1f, 1f, 1f, 0.8f).uv(1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 0, -1).endVertex();
        consumer.vertex(matrix, -w, h, 0f).color(1f, 1f, 1f, 0.8f).uv(0, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 0, -1).endVertex();

    }
}
