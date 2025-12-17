package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.block.RotateableBlockTemplate;
import net.danygames2014.nyatec.NyaTec;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;
import java.util.Random;

public class RubberLogBlock extends RotateableBlockTemplate {
    public static final EnumProperty<RubberSpot> RUBBER_SPOT = EnumProperty.of("rubber_spot", RubberSpot.class);
    public static final BooleanProperty FULL_SPOT = BooleanProperty.of("full_spot");
    private final Random dropRandom;
    
    public RubberLogBlock(Identifier identifier, Material material) {
        super(identifier, material);
        this.setDefaultState(super.getDefaultState().with(RUBBER_SPOT, RubberSpot.NONE).with(FULL_SPOT, false));
        this.setTickRandomly(true);
        dropRandom = new Random();
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(RUBBER_SPOT);
        builder.add(FULL_SPOT);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (random.nextInt(100) == 0) {
            BlockState state = world.getBlockState(x, y, z);
            
            if (!state.isOf(this)) {
                return;
            }
            
            if (state.get(RUBBER_SPOT) == RubberSpot.NONE) {
                return;
            }
            
            if (!state.get(FULL_SPOT)) {
                state = state.with(FULL_SPOT, true);
                world.setBlockStateWithNotify(x, y, z, state);
            }
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, BlockState state, int meta) {
        if (state.isOf(this) && state.get(RUBBER_SPOT) != RubberSpot.NONE && state.get(FULL_SPOT)) {
            if (dropRandom.nextInt(2) > 0) {
                this.dropStack(world, x, y, z, new ItemStack(NyaTec.latex, 1));
            }
        }
        
        super.afterBreak(world, player, x, y, z, state, meta);
    }
}
