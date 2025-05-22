package be.noah.ritual_magic.entities.client;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.entities.HomingProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class HomingProjectileRenderer extends EntityRenderer<HomingProjectile> {
    private static final float SCALE = 1.0F;
    private static final int ROTATION_SPEED = 10;
    private final ItemRenderer itemRenderer;

    public HomingProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
    }

    @Override
    public void render(HomingProjectile entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Position
        poseStack.translate(0.0D, 0.25D, 0.0D);

        // Rotation für Flugrichtung
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));

        // Spinanimation
        poseStack.mulPose(Axis.ZP.rotationDegrees(
                ((float)entity.tickCount + partialTicks) * ROTATION_SPEED));

        // Skalierung
        poseStack.scale(SCALE, SCALE, SCALE);

        // Render mit Eisblock als Basis
        this.itemRenderer.renderStatic(
                Items.BLUE_ICE.getDefaultInstance(),
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HomingProjectile entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}