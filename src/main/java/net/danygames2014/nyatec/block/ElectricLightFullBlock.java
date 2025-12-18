package net.danygames2014.nyatec.block;

import net.danygames2014.nyatec.block.entity.ElectricLightBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.util.Identifier;

public class ElectricLightFullBlock extends ElectricLightBlock {
    public ElectricLightFullBlock(Identifier identifier, Material material, String color) {
        super(identifier, material);
        TemplateBlockRegistry.registerCubeLamp(identifier, Identifier.of("nyatec:block/light/cube/" + color));
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new ElectricLightBlockEntity();
    }
}
