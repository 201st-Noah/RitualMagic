package be.noah.ritual_magic.entities.client;

import be.noah.ritual_magic.entities.ThrownDwarvenAxe;
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
import org.jetbrains.annotations.NotNull;

//NOT WORKING there is nothing rendering but also no crash, so I leave it for now :(

public class ThrownDwarvenAxeRenderer<T extends ThrownDwarvenAxe> extends EntityRenderer<T> {
    private static final int degreesPerTick = 24;
    private final ItemRenderer itemRenderer;

    public ThrownDwarvenAxeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Position the axe
        poseStack.translate(0.0D, 0.25D, 0.0D);

        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot() + 90F));

        // Add spinning animation around the throwing axis
        poseStack.mulPose(Axis.ZP.rotationDegrees(((float)entity.tickCount + partialTicks) * degreesPerTick));

        // Render the item
        this.itemRenderer.renderStatic(
                entity.getItem(),
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T t) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
