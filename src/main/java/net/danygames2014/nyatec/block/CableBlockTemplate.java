package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.network.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unused")
public class CableBlockTemplate extends TemplateBlock implements NetworkNodeComponent {
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty WEST = BooleanProperty.of("west");

    public static final HashMap<Direction, BooleanProperty> PROPERTY_LOOKUP = new HashMap<>();

    static {
        PROPERTY_LOOKUP.put(Direction.UP, UP);
        PROPERTY_LOOKUP.put(Direction.DOWN, DOWN);
        PROPERTY_LOOKUP.put(Direction.NORTH, NORTH);
        PROPERTY_LOOKUP.put(Direction.SOUTH, SOUTH);
        PROPERTY_LOOKUP.put(Direction.EAST, EAST);
        PROPERTY_LOOKUP.put(Direction.WEST, WEST);
    }

    public CableBlockTemplate(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState()
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false);
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);
        updateConnections(world, x, y, z);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);
        updateConnections(world, x, y, z);
    }

    public void updateConnections(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);

        for (Direction side : Direction.values()) {
            state = state.with(PROPERTY_LOOKUP.get(side), this.canConnectTo(world, x, y, z, null, side));
        }

        world.setBlockState(x, y, z, state);
    }

    // Network Node Component
    @Override
    public boolean canConnectTo(World world, int x, int y, int z, @Nullable Network network, Direction direction) {
        BlockState other = world.getBlockState(x + direction.getOffsetX(), y + direction.getOffsetY(), z + direction.getOffsetZ());
        return other.getBlock() instanceof CableBlockTemplate;
    }

    @Override
    public NetworkType getNetworkType() {
        return NetworkType.ENERGY;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if(player.isSneaking()) {
            ArrayList<Network> networks = NetworkManager.getAt(world.dimension, x, y, z, this.getNetworkTypes());
            player.sendMessage("This block is in networks:");
            for (var net : networks){
                player.sendMessage("NET " + net.getId() + " HASHCODE: " + net.hashCode());
            }
            return true;
        }
        
        return super.onUse(world, x, y, z, player);
    }
}
