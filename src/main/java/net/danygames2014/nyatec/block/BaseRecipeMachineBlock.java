package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.block.DropInventoryOnBreak;
import net.danygames2014.nyalib.energy.template.block.EnergyConsumerBlockTemplate;
import net.danygames2014.uniwrench.api.WrenchMode;
import net.danygames2014.uniwrench.api.Wrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class BaseRecipeMachineBlock extends EnergyConsumerBlockTemplate implements DropInventoryOnBreak, Wrenchable {
    public BaseRecipeMachineBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected abstract BlockEntity createBlockEntity();

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

    // Wrenchable
    @Override
    public boolean wrenchRightClick(ItemStack stack, PlayerEntity player, boolean isSneaking, World world, int x, int y, int z, int side, WrenchMode wrenchMode) {
        if (wrenchMode == WrenchMode.MODE_WRENCH) {
            if (isSneaking) {
                if (!world.getBlockState(x, y, z).isOf(this)) {
                    return false;
                }

                int meta = world.getBlockMeta(x, y, z);
                world.setBlockStateWithNotify(x, y, z, States.AIR.get());
                this.dropStacks(world, x, y, z, meta);
                return true;
            }
        }
        
        return false;
    }
}
