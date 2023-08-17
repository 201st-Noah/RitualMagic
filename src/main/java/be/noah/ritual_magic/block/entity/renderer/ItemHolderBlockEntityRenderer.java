package be.noah.ritual_magic.block.entity.renderer;

import be.noah.ritual_magic.block.entity.ItemHolderBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.datafix.fixes.ChunkPalettedStorageFix;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class ItemHolderBlockEntityRenderer implements BlockEntityRenderer<ItemHolderBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public ItemHolderBlockEntityRenderer(BlockEntityRendererProvider.Context context){
        this.context = context;
    }

    @Override
    public void render(ItemHolderBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        /*ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getInventory().getStackInSlot(0);
        pPoseStack.pushPose();
        pPoseStack.scale(1,1,1);
        pPoseStack.translate(0.5,0.75,0.5);

        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
        pPoseStack.popPose();*/
        ///final BlockRenderDispatcher dispatcher = this.context.getBlockRenderDispatcher();
        final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        final LocalPlayer player = Minecraft.getInstance().player;
        final ItemHolderBlockEntity entity = (ItemHolderBlockEntity) pBlockEntity;

        itemRenderer.renderStatic(player, entity.getInventory().getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, pPoseStack, pBufferSource,
                Minecraft.getInstance().level, pPackedOverlay, pPackedLight, 0);
    }
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
