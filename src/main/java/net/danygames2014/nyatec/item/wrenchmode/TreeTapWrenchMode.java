package net.danygames2014.nyatec.item.wrenchmode;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.RubberLogBlock;
import net.danygames2014.nyatec.block.RubberSpot;
import net.danygames2014.uniwrench.api.WrenchMode;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

public class TreeTapWrenchMode extends WrenchMode {
    private final Random random;
    
    public TreeTapWrenchMode(Identifier identifier) {
        super(identifier);
        this.random = new Random();
    }

    @Override
    public boolean wrenchRightClick(ItemStack stack, PlayerEntity player, boolean isSneaking, World world, int x, int y, int z, int side, WrenchMode wrenchMode) {
        BlockState state = world.getBlockState(x, y, z);

        if (state.contains(RubberLogBlock.RUBBER_SPOT) && state.contains(RubberLogBlock.FULL_SPOT)) {
            if (state.get(RubberLogBlock.RUBBER_SPOT).side != side) {
                return false;
            }
            
            int latexAmount = 0;
            if (state.get(RubberLogBlock.FULL_SPOT)) {
                latexAmount = random.nextInt(3) + 1;
                state = state.with(RubberLogBlock.FULL_SPOT, false);
            } else {
                latexAmount = random.nextInt(2);
                if (random.nextInt(2) == 0) {
                    state = state.with(RubberLogBlock.RUBBER_SPOT, RubberSpot.NONE);
                }
            }

            Direction dir = Direction.byId(side);
            for (int i = 0; i < latexAmount; i++) {
                ItemEntity item = new ItemEntity(world, x + 0.5 + (dir.getOffsetX() * 0.5),y + 0.5,z + 0.5 + (dir.getOffsetZ() * 0.5), new ItemStack(NyaTec.latex));
                world.spawnEntity(item);
            }
            
            world.setBlockStateWithNotify(x, y, z, state);
            return true;
        }
        
        return super.wrenchRightClick(stack, player, isSneaking, world, x, y, z, side, wrenchMode);
    }
}
