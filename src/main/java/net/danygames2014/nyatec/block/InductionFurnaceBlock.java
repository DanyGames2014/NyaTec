package net.danygames2014.nyatec.block;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.entity.InductionFurnaceBlockEntity;
import net.danygames2014.nyatec.screen.handler.InductionFurnaceScreenHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;

public class InductionFurnaceBlock extends BaseRecipeMachineBlock {
    public InductionFurnaceBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new InductionFurnaceBlockEntity();
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (world.getBlockEntity(x, y, z) instanceof InductionFurnaceBlockEntity inductionFurnace) {
            GuiHelper.openGUI(player, NyaTec.NAMESPACE.id("induction_furnace"), inductionFurnace, new InductionFurnaceScreenHandler(player, inductionFurnace));
            return true;
        }
        return false;
    }
}
