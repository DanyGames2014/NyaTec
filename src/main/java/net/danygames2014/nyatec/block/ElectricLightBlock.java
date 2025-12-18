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

public abstract class ElectricLightBlock extends EnergyConsumerBlockTemplate {
    public static final EnumProperty<LightLevel> LIGHT_LEVEL = EnumProperty.of("light_level", LightLevel.class);
    
    public ElectricLightBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setLuminance(state -> state.get(LIGHT_LEVEL).lightLevel);
        setDefaultState(getDefaultState().with(LIGHT_LEVEL, LightLevel.OFF));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIGHT_LEVEL);
    }
}
