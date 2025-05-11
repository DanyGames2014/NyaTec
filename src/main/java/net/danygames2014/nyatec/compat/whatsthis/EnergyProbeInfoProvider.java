package net.danygames2014.nyatec.compat.whatsthis;

import net.danygames2014.nyalib.energy.EnergyStorage;
import net.danygames2014.nyalib.network.Network;
import net.danygames2014.nyalib.network.NetworkManager;
import net.danygames2014.nyalib.network.NetworkType;
import net.danygames2014.nyalib.network.energy.EnergyNetwork;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.CableBlockTemplate;
import net.danygames2014.whatsthis.api.IProbeHitData;
import net.danygames2014.whatsthis.api.IProbeInfo;
import net.danygames2014.whatsthis.api.IProbeInfoProvider;
import net.danygames2014.whatsthis.api.ProbeMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

public class EnergyProbeInfoProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return NyaTec.NAMESPACE.id("energy").toString();
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, PlayerEntity playerEntity, World world, BlockState state, IProbeHitData iProbeHitData) {
        BlockPos pos = iProbeHitData.getPos();
        
        if(world.getBlockEntity(pos.x, pos.y, pos.z) instanceof EnergyStorage energyStorage) {
            probeInfo.progress(
                energyStorage.getEnergyStored(), 
                energyStorage.getEnergyCapacity(), 
                probeInfo.defaultProgressStyle().suffix(" EU")
                .borderColor(0xFFF5A9B8)
                .filledColor(0xFF5BCEFA)
                .alternateFilledColor(0xFFFFFFFF)
            );
        }
        
        if(world.getBlockState(pos).getBlock() instanceof CableBlockTemplate) {
            Network net = NetworkManager.getAt(world.dimension, pos.x, pos.y, pos.z, NetworkType.ENERGY.getIdentifier());
            if (net instanceof EnergyNetwork energyNet) {
                EnergyNetwork.EnergyFlowEntry flowEntry = energyNet.getFlowEntry(pos.x, pos.y, pos.z);
                if (flowEntry != null) {
                    probeInfo.progress(
                        flowEntry.energyFlow, 
                        flowEntry.conductor.getBreakdownPower(world, flowEntry.componentEntry),
                        probeInfo.defaultProgressStyle().suffix(" EU/t")
                        .borderColor(0xFFF5A9B8)
                        .filledColor(0xFF5BCEFA)
                        .alternateFilledColor(0xFFFFFFFF)
                    );
                }
            }
        }
    }
}
