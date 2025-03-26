package net.danygames2014.nyatec.screen.handler;

import net.danygames2014.nyatec.block.entity.GeneratorBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;

public class GeneratorScreenHandler extends ScreenHandler {
    public GeneratorBlockEntity blockEntity;
    public PlayerEntity player;
    
    public GeneratorScreenHandler(PlayerEntity player, GeneratorBlockEntity blockEntity) {
        this.player = player;
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
