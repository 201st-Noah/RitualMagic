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

    private static final ResourceLocation TEXTURE = new ResourceLocation(RitualMagic.MODID, "textures/gui/void_shield.png");

    public static final IGuiOverlay HUD_VOID_SHIELD = ((gui, guiGraphics, partialTicks, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        CompoundTag data = player.getPersistentData();
        //if (!data.contains("void_shield")) return;

        int hitsLeft = data.getInt("void_shield");
        int maxHits = 8;

        // Icon size and spacing
        int iconSize = 16;
        int spacing = 4;

        // Position on screen (bottom-center)
        int totalWidth = maxHits * iconSize + (maxHits - 1) * spacing;
        int xStart = (width - totalWidth) / 2;
        int y = height - 80; // move up from armor bar (~60+ pixels from bottom)

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);

        // Render one icon per hit left
        for (int i = 0; i < 10; i++) {
            if(hitsLeft > i){
                int x = xStart + i * (iconSize + spacing);
                int v = (i < hitsLeft) ? 0 : 16; // top or bottom half of the 16x32 texture
                guiGraphics.blit(
                        TEXTURE,         // your texture
                        x, y,            // screen x/y position
                        0, v,            // texture x/y (u/v)
                        iconSize, iconSize, // width/height to draw
                        16, 32           // total texture size (uSize, vSize)
                );
            }
        }
    });
}
