package net.danygames2014.nyatec.block;

import net.danygames2014.nyatec.NyaTec;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

public class RubberLeavesBlock extends TemplateBlock {
    public RubberLeavesBlock(Identifier identifier, Material material) {
        super(identifier, material);
        this.setTickRandomly(true);
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return NyaTec.rubberLeaves.asItem().id;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return !Minecraft.isFancyGraphicsEnabled();
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            updateDecay(world, x, y, z, random, false);
        }
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isRemote) {
            updateDecay(world, x, y, z, world.random, false);
            return true;
        }
        return false;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity placer) {
        if (!world.isRemote) {
            // If the player is sneaking, make the leaves immune to decay
            if(placer.isSneaking()){
                world.setBlockMetaWithoutNotifyingNeighbors(x,y,z,15);
                return;
            }
            
            updateDecay(world, x, y, z, world.random, true);
        }
    }

    public void updateDecay(World world, int x, int y, int z, Random random, boolean dontDecay) {
        int prevDecayValue = world.getBlockMeta(x, y, z);
        int decayValue = 0;

        // 15 means this leaf is immune from decay
        if(prevDecayValue == 15){
            return;
        }

        for (Direction dir : Direction.values()) {
            // Get the state
            BlockState state = world.getBlockState(x + dir.getOffsetX(), y + dir.getOffsetY(), z + dir.getOffsetZ());

            // If its leaves and the decay value is larger than the current one, take it minus one
            if (state.isOf(NyaTec.rubberLeaves)) {
                int leavesMeta = world.getBlockMeta(x + dir.getOffsetX(), y + dir.getOffsetY(), z + dir.getOffsetZ());
                if (leavesMeta > decayValue) {
                    decayValue = leavesMeta - 1;
                }
            }

            // If its log, set the decay value to 5
            if (state.isOf(NyaTec.rubberLog)) {
                decayValue = 3;
                break;
            }
        }

        // If the decay value is zero and the leaf can decay, decay it
        if (decayValue <= 0 && !dontDecay) {
            decay(world, x, y, z, random);
            return;
        }

        // If the decay value hasnt changed, dont bother
        if(prevDecayValue != decayValue) {
            world.setBlockMetaWithoutNotifyingNeighbors(x, y, z, decayValue);
        }
    }
    
    public void decay(World world, int x, int y, int z, Random random) {
        world.setBlockStateWithNotify(x, y, z, States.AIR.get());
    }
}
