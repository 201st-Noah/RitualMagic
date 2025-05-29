package be.noah.ritual_magic.client;

import be.noah.ritual_magic.Mana.ManaType;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {

    public static final IGuiOverlay MANA = (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        int x = 10;
        int y = 10;
        int barHeight = 8;
        int barWidth = 80;

        for (ManaType type : ManaType.values()) {
            int mana = ClientManaData.get(type);
            int max = ClientManaData.getMax(type);

            int filled = (int) ((mana / (float) max) * barWidth);

            // Background
            //guiGraphics.fill(x, y, x + barWidth, y + barHeight, 0xFF000000);  // black background

            // Draw filled portion
            guiGraphics.fill(x, y, x + filled, y + barHeight, type.getColor());  // type color

            // Draw text Label
            guiGraphics.drawString(gui.getFont(), type.getName() + ": " + mana + "/" + max, x + 2, y - 9, 0xFFFFFF);


            y += 15;  // Space between bars
        }
    };
}
