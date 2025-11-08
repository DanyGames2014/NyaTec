package net.danygames2014.nyatec.block;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.entity.MaceratorBlockEntity;
import net.danygames2014.nyatec.screen.handler.MaceratorScreenHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;

public class MaceratorBlock extends BaseRecipeMachineBlock {
    public MaceratorBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new MaceratorBlockEntity();
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (world.getBlockEntity(x, y, z) instanceof MaceratorBlockEntity macerator) {
            GuiHelper.openGUI(player, NyaTec.NAMESPACE.id("macerator"), macerator, new MaceratorScreenHandler(player, macerator));
            return true;
        }
        return false;
    }
}
