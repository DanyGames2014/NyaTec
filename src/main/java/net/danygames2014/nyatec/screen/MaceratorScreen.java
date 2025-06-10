package net.danygames2014.nyatec.screen;

import net.danygames2014.nyatec.block.entity.ElectricFurnaceBlockEntity;
import net.danygames2014.nyatec.block.entity.MaceratorBlockEntity;
import net.danygames2014.nyatec.screen.handler.MaceratorScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.opengl.GL11;

public class MaceratorScreen extends HandledScreen {
    public MaceratorBlockEntity macerator;
    
    public MaceratorScreen(PlayerEntity player, MaceratorBlockEntity macerator) {
        super(new MaceratorScreenHandler(player, macerator));
        this.macerator = macerator;
        this.backgroundHeight = 165;
    }

    @Override
    protected void drawForeground() {
        textRenderer.draw("Macerator", 50, 6, 4210752);
        textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int bgTextureId = minecraft.textureManager.getTextureId("/assets/nyatec/stationapi/textures/gui/macerator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(bgTextureId);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(x, y, 0, 0, backgroundWidth, backgroundHeight);

        if (macerator.energy > 0) {
            int lightingProgress = (int) (((float) macerator.energy / (float) macerator.getEnergyCapacity()) * 13F);
            drawTexture(x + 59, y + 36 + (13 - lightingProgress), 179, 13 - lightingProgress, 7, lightingProgress + 1);
        }
        
        if (macerator.progress > 0) {
            int cookProgress = (int) (((float) macerator.progress / macerator.maxProgress) * 22F);
            drawTexture(x + 80, y + 35, 177, 14, cookProgress, 16);
        }
    }
}
