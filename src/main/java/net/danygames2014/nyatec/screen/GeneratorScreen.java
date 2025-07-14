package net.danygames2014.nyatec.screen;

import net.danygames2014.nyatec.block.entity.GeneratorBlockEntity;
import net.danygames2014.nyatec.screen.handler.GeneratorScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.opengl.GL11;

public class GeneratorScreen extends HandledScreen {
    public GeneratorBlockEntity blockEntity;

    public GeneratorScreen(PlayerEntity player, GeneratorBlockEntity blockEntity) {
        super(new GeneratorScreenHandler(player, blockEntity));
        this.blockEntity = blockEntity;
        this.backgroundHeight = 165;
    }

    @Override
    protected void drawForeground() {
        int energy = blockEntity != null ? blockEntity.getEnergyStored() : 0;
        int fuel = blockEntity != null ? blockEntity.fuel : 0;
        int currentRate = blockEntity != null ? blockEntity.currentRate : 0;

        textRenderer.draw("Energy : " + energy, 7, 28, 0x404040);
        textRenderer.draw("Fuel : " + fuel, 7, 40, 0x404040);
        textRenderer.draw("Current Rate : " + currentRate + "EU/t", 7, 52, 0x404040);

        textRenderer.draw("Generator", 60, 6, 4210752);
        textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    protected void drawBackground(float tickDelta) {
        int bgTextureId = minecraft.textureManager.getTextureId("/assets/nyatec/stationapi/textures/gui/generator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(bgTextureId);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(x, y, 0, 0, backgroundWidth, backgroundHeight);

        if (blockEntity.fuel > 0) {
            int flameProgress = (int) (((float) blockEntity.fuel / (float) blockEntity.fuelBuffer) * 14F);
            drawTexture(x + 66, y + 36 + (14 - flameProgress), 176, 14 - flameProgress, 14, flameProgress);
        }
        
        if (blockEntity.energy > 0) {
            int energyProgress = (int) (((float) blockEntity.energy / (float) blockEntity.getEnergyCapacity()) * 24F);
            drawTexture(x + 94, y + 35, 176, 14, energyProgress, 16);
        }
    }
}
