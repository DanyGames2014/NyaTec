package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.energy.template.block.EnergyConsumerBlockTemplate;
import net.danygames2014.nyatec.block.entity.ElectricLightBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.util.Identifier;

import java.awt.*;

public abstract class ElectricLightBlock extends EnergyConsumerBlockTemplate {
    public static final EnumProperty<LightLevel> LIGHT_LEVEL = EnumProperty.of("light_level", LightLevel.class);
    public final int color;
    public final int offColor;
    
    public ElectricLightBlock(Identifier identifier, Material material, int color) {
        super(identifier, material);
        setLuminance(state -> state.get(LIGHT_LEVEL).lightLevel);
        setDefaultState(getDefaultState().with(LIGHT_LEVEL, LightLevel.OFF));
        
        this.color = color;
        Color onColor = new Color(color);
        this.offColor = new Color((int) (onColor.getRed() * 0.9), (int) (onColor.getGreen() * 0.9), (int) (onColor.getBlue() * 0.9), onColor.getAlpha()).getRGB();
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIGHT_LEVEL);
    }
}
