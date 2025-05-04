package net.danygames2014.nyatec.compat.whatsthis;

import net.danygames2014.nyalib.energy.EnergyStorage;
import net.danygames2014.nyatec.NyaTec;
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
    }
}
