package be.noah.ritual_magic.entities.client;

import be.noah.ritual_magic.entities.ThrownDwarvenAxe;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;

//NOT WORKING there is nothing rendering but also no crash, so I leave it for now :(

public class ThrownDwarvenAxeRenderer<T extends ThrownDwarvenAxe> extends EntityRenderer {
    private static final int degreesPerTick = 24;
    private ItemRenderer itemRenderer = null;

    public ThrownDwarvenAxeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return null;
    }

    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.25, 0.0);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F - entity.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        poseStack.mulPose(Axis.ZP.rotationDegrees(((float)entity.tickCount + partialTicks) * 24.0F % 360.0F));
        this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        poseStack.popPose();
    }
}
