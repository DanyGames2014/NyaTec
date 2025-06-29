package net.danygames2014.nyatec.block.template;

import net.danygames2014.nyalib.energy.template.block.entity.EnergySourceBlockEntityTemplate;
import net.danygames2014.nyalib.network.Network;
import net.danygames2014.nyalib.network.NetworkEdgeComponent;
import net.danygames2014.nyalib.network.NetworkType;
import net.danygames2014.nyatec.block.entity.template.EnergySourceConsumerBlockEntityTemplate;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class EnergySourceConsumerBlockTemplate extends TemplateBlockWithEntity implements NetworkEdgeComponent {
    public EnergySourceConsumerBlockTemplate(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected abstract BlockEntity createBlockEntity();

    @Override
    public NetworkType getNetworkType() {
        return NetworkType.ENERGY;
    }

    @Override
    public void onAddedToNet(World world, int x, int y, int z, Network network) {
        if (world.getBlockEntity(x, y, z) instanceof EnergySourceConsumerBlockEntityTemplate energySource) {
            energySource.addedToNet(world, x, y, z, network);
        }
    }

    @Override
    public void onRemovedFromNet(World world, int x, int y, int z, Network network) {
        if (world.getBlockEntity(x, y, z) instanceof EnergySourceConsumerBlockEntityTemplate energySource) {
            energySource.removedFromNet(world, x, y, z, network);
        }
    }

    @Override
    public void update(World world, int x, int y, int z, Network network) {
        if (world.getBlockEntity(x, y, z) instanceof EnergySourceConsumerBlockEntityTemplate energySource) {
            energySource.update(world, x, y, z, network);
        }
    }
}
