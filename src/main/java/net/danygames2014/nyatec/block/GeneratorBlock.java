package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.block.DropInventoryOnBreak;
import net.danygames2014.nyalib.energy.template.block.EnergySourceBlockTemplate;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.entity.GeneratorBlockEntity;
import net.danygames2014.nyatec.screen.handler.GeneratorScreenHandler;
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

public class GeneratorBlock extends EnergySourceBlockTemplate implements DropInventoryOnBreak {
    public GeneratorBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new GeneratorBlockEntity();
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
        if (world.getBlockEntity(x, y, z) instanceof GeneratorBlockEntity generator) {
            if (player.isSneaking()) {
                player.sendMessage(generator.getEnergyStored() + "/" + generator.getEnergyCapacity());
            } else {
                GuiHelper.openGUI(player, NyaTec.NAMESPACE.id("generator"), generator, new GeneratorScreenHandler(player, generator));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldDropInventory(World world, int x, int y, int z) {
        return true;
    }
}
