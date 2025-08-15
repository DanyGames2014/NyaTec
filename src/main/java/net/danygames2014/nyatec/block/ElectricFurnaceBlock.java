package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.energy.template.block.EnergyConsumerBlockTemplate;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.entity.ElectricFurnaceBlockEntity;
import net.danygames2014.nyatec.screen.handler.ElectricFurnaceScreenHandler;
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

public class ElectricFurnaceBlock extends EnergyConsumerBlockTemplate implements DropInventoryOnBreak {
    public ElectricFurnaceBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new ElectricFurnaceBlockEntity();
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.HORIZONTAL_FACING);
        builder.add(Properties.LIT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return super.getDefaultState().with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, context.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (world.getBlockEntity(x, y, z) instanceof ElectricFurnaceBlockEntity electricFurnace) {
            GuiHelper.openGUI(player, NyaTec.NAMESPACE.id("electric_furnace"), electricFurnace, new ElectricFurnaceScreenHandler(player, electricFurnace));
            return true;
        }
        return false;
    }
}
