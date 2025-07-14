package net.danygames2014.nyatec.screen;

import net.danygames2014.nyatec.block.entity.BatteryBoxBlockEntity;
import net.danygames2014.nyatec.block.entity.GeneratorBlockEntity;
import net.danygames2014.nyatec.screen.handler.BatteryBoxScreenHandler;
import net.danygames2014.nyatec.screen.handler.GeneratorScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.opengl.GL11;

public class BatteryBoxScreen extends HandledScreen {
    public BatteryBoxBlockEntity blockEntity;

    public BatteryBoxScreen(PlayerEntity player, BatteryBoxBlockEntity blockEntity) {
        super(new BatteryBoxScreenHandler(player, blockEntity));
        this.blockEntity = blockEntity;
        this.backgroundHeight = 165;
    }

    @Override
    protected void drawForeground() {
        int energy = blockEntity != null ? blockEntity.getEnergyStored() : 0;

        textRenderer.draw("Energy : " + energy, 7, 28, 0x404040);

        textRenderer.draw("Battery Box", 60, 6, 4210752);
        textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    protected void drawBackground(float tickDelta) {
        int bgTextureId = minecraft.textureManager.getTextureId("/assets/nyatec/stationapi/textures/gui/energy_storage.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(bgTextureId);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(x, y, 0, 0, backgroundWidth, backgroundHeight);

        if (blockEntity.energy > 0) {
            int energyProgress = (int) (((float) blockEntity.energy / (float) blockEntity.getEnergyCapacity()) * 24F);
            drawTexture(x + 79, y + 34, 176, 14, energyProgress, 16);
        }
    }
}
