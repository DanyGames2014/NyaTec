package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.energy.template.block.EnergyConsumerBlockTemplate;
import net.danygames2014.nyatec.block.entity.ElectricLightBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.Identifier;

public class ElectricLightBlock extends EnergyConsumerBlockTemplate {
    public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);
    
    public ElectricLightBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setLuminance(state -> state.get(LIGHT_LEVEL));
        setDefaultState(getDefaultState().with(LIGHT_LEVEL, 0));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIGHT_LEVEL);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new ElectricLightBlockEntity();
    }
//
//    @Override
//    public float getLuminance(BlockView blockView, int x, int y, int z) {
//        if (blockView.getBlockEntity(x, y, z) instanceof ElectricLightBlockEntity lightBlockEntity) {
//            return lightBlockEntity.luminance;    
//        }
//        
//        return super.getLuminance(blockView, x, y, z);
//    }
    
    
}
