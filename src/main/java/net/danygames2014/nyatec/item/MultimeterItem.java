package net.danygames2014.nyatec.item;

import net.danygames2014.nyalib.network.Network;
import net.danygames2014.nyalib.network.NetworkManager;
import net.danygames2014.nyalib.network.NetworkType;
import net.danygames2014.nyalib.network.energy.EnergyNetwork;
import net.danygames2014.nyatec.init.WrenchModeListener;
import net.danygames2014.uniwrench.api.WrenchMode;
import net.danygames2014.uniwrench.item.WrenchBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class MultimeterItem extends WrenchBase {
    public MultimeterItem(Identifier identifier) {
        super(identifier);
        this.addWrenchMode(WrenchModeListener.flowMode);
        this.addWrenchMode(WrenchModeListener.batteryMode);
        this.addWrenchMode(WrenchModeListener.debugMode);
    }

    @Override
    public boolean wrenchRightClick(ItemStack stack, PlayerEntity player, boolean isSneaking, World world, int x, int y, int z, int side, WrenchMode wrenchMode) {
        if (wrenchMode == WrenchModeListener.flowMode) {
            if (!world.isRemote) {
                Network net = NetworkManager.getAt(world.dimension, x, y, z, NetworkType.ENERGY.getIdentifier());
                if (net instanceof EnergyNetwork energyNet) {
                    EnergyNetwork.EnergyFlowEntry flowEntry = energyNet.getFlowEntry(x, y, z);
                    if (flowEntry != null) {
                        player.sendMessage(flowEntry.energyFlow + " EU/t");
                        return true;
                    }
                }
            }
        }
        
        return super.wrenchRightClick(stack, player, isSneaking, world, x, y, z, side, wrenchMode);
    }
}
