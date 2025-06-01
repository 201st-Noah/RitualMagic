package be.noah.ritual_magic.client;

import be.noah.ritual_magic.RitualMagic;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;


public class VoidShieldHudOverlay {

    private static final ResourceLocation TEXTURE = new ResourceLocation(RitualMagic.MODID, "textures/gui/void_shield9.png");

    public static final IGuiOverlay HUD_VOID_SHIELD = ((gui, guiGraphics, partialTicks, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        if(player.isCreative()) return;
        CompoundTag data = player.getPersistentData();
        int hitsLeft = data.getInt("void_shield");
        int maxHits = 10;

        int iconSize = 9;
        int spacing = -1;
        int totalWidth = maxHits * iconSize;
        int xStart = (width - totalWidth -92) / 2;

        int baseY = height - 40;
        if (player.getMaxHealth() > 20) baseY -= 10;
        if (player.getAbsorptionAmount() > 0) baseY -= 10;
        if (player.getArmorValue() > 0) baseY -= 10;

        int y = baseY - 10;

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);

        for (int i = 0; i < 8; i++) {
            if(hitsLeft > i){
                int x = xStart + i * (iconSize + spacing);
                int v = (i < hitsLeft) ? 0 : 8;
                guiGraphics.blit(
                        TEXTURE,
                        x, y,
                        0, v,
                        iconSize, iconSize,
                        9, 9
                );
            }
        }
    });
}
