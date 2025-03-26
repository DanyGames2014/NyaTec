package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.energy.template.block.EnergySourceBlockTemplate;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.entity.GeneratorBlockEntity;
import net.danygames2014.nyatec.screen.handler.GeneratorScreenHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;

public class GeneratorBlock extends EnergySourceBlockTemplate {
    public GeneratorBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new GeneratorBlockEntity();
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
}
