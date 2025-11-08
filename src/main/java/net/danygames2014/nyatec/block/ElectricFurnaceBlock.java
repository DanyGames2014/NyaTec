package net.danygames2014.nyatec.block;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.entity.ElectricFurnaceBlockEntity;
import net.danygames2014.nyatec.screen.handler.ElectricFurnaceScreenHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;

public class ElectricFurnaceBlock extends BaseRecipeMachineBlock {
    public ElectricFurnaceBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new ElectricFurnaceBlockEntity();
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
