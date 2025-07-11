package net.danygames2014.nyatec.screen;

import net.danygames2014.nyatec.block.entity.ElectricFurnaceBlockEntity;
import net.danygames2014.nyatec.screen.handler.ElectricFurnaceScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.opengl.GL11;

public class ElectricFurnaceScreen extends HandledScreen {
    public ElectricFurnaceBlockEntity blockEntity;
    
    public ElectricFurnaceScreen(PlayerEntity player, ElectricFurnaceBlockEntity blockEntity) {
        super(new ElectricFurnaceScreenHandler(player, blockEntity));
        this.blockEntity = blockEntity;
        this.backgroundHeight = 165;
    }

    @Override
    protected void drawForeground() {
        textRenderer.draw("Electric Furnace", 50, 6, 4210752);
        textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int bgTextureId = minecraft.textureManager.getTextureId("/assets/nyatec/stationapi/textures/gui/electric_furnace.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(bgTextureId);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(x, y, 0, 0, backgroundWidth, backgroundHeight);

        if (blockEntity.energy > 0) {
            int lightingProgress = (int) (((float) blockEntity.energy / (float) blockEntity.getEnergyCapacity()) * 13F);
            drawTexture(x + 59, y + 36 + (13 - lightingProgress), 179, 13 - lightingProgress, 7, lightingProgress + 1);
        }
        
        if (blockEntity.progress > 0) {
            int cookProgress = (int) (((float) blockEntity.progress / blockEntity.maxProgress) * 22F);
            drawTexture(x + 80, y + 35, 177, 14, cookProgress, 16);
        }
    }
}
