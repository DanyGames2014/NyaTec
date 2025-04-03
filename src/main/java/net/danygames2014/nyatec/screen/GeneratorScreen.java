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
        int energy = blockEntity != null ? blockEntity.getEnergyStored() : 69;
        textRenderer.draw("Energy : " + energy, 7, 28, 0x404040);
    }

    protected void drawBackground(float tickDelta) {
        int bgTextureId = minecraft.textureManager.getTextureId("/assets/nyatec/stationapi/textures/gui/generator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(bgTextureId);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(x, y, 0, 0, backgroundWidth, backgroundHeight);
    }
}
