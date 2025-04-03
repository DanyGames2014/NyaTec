package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.energy.template.block.EnergyConsumerBlockTemplate;
import net.danygames2014.nyatec.block.entity.EnergyTrashCanBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class EnergyTrashCanBlock extends EnergyConsumerBlockTemplate {
    public EnergyTrashCanBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new EnergyTrashCanBlockEntity();
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isRemote) {
            if (world.getBlockEntity(x, y, z) instanceof EnergyTrashCanBlockEntity trashCanBlockEntity) {
                if (player.isSneaking()) {
                    trashCanBlockEntity.switchMode();
                } else {
                    player.sendMessage("Mode: " + (trashCanBlockEntity.isFastMode() ? "Fast" : "Slow"));
                }

                return true;
            }
        }

        return super.onUse(world, x, y, z, player);
    }
}
