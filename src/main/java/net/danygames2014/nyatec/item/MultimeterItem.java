package net.danygames2014.nyatec.item;

import net.danygames2014.nyalib.network.Network;
import net.danygames2014.nyalib.network.NetworkManager;
import net.danygames2014.nyalib.network.NetworkType;
import net.danygames2014.nyalib.network.energy.EnergyNetwork;
import net.danygames2014.nyatec.init.WrenchModeListener;
import net.danygames2014.uniwrench.item.WrenchBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class MultimeterItem extends WrenchBase {
    public MultimeterItem(Identifier identifier) {
        super(identifier);
        this.addWrenchMode(WrenchModeListener.FLOW_MODE);
        this.addWrenchMode(WrenchModeListener.BATTERY_MODE);
    }
    
    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        if (!world.isRemote) {
            Network net = NetworkManager.getAt(world.dimension, x, y, z, NetworkType.ENERGY.getIdentifier());
            if (net instanceof EnergyNetwork energyNet) {
                EnergyNetwork.EnergyFlowEntry flowEntry = energyNet.getFlowEntry(x, y, z);
                if (flowEntry != null) {
                    user.sendMessage(flowEntry.energyFlow + " EU/t");
                }
            }
        }

        return true;
    }
}
