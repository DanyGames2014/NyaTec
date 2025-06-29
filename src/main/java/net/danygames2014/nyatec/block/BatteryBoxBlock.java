package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.energy.template.block.EnergySourceBlockTemplate;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.entity.BatteryBoxBlockEntity;
import net.danygames2014.nyatec.block.entity.template.EnergySourceConsumerBlockEntityTemplate;
import net.danygames2014.nyatec.block.template.EnergySourceConsumerBlockTemplate;
import net.danygames2014.nyatec.screen.handler.BatteryBoxScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.Identifier;

public class BatteryBoxBlock extends EnergySourceConsumerBlockTemplate {
    public BatteryBoxBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new BatteryBoxBlockEntity();
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return super.getDefaultState().with(Properties.FACING, context.getPlayerLookDirection().getOpposite());
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (world.getBlockEntity(x, y, z) instanceof BatteryBoxBlockEntity batteryBox) {
            if (player.isSneaking()) {
                player.sendMessage(batteryBox.getEnergyStored() + "/" + batteryBox.getEnergyCapacity());
            } else {
                GuiHelper.openGUI(player, NyaTec.NAMESPACE.id("battery_box"), batteryBox, new BatteryBoxScreenHandler(player, batteryBox));
            }
            return true;
        }
        return false;
    }
}
