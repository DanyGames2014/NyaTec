package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.energy.EnergyConductor;
import net.danygames2014.nyalib.network.*;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.uniwrench.api.Wrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
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
public class CableBlockTemplate extends TemplateBlock implements NetworkNodeComponent, EnergyConductor, Wrenchable {
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
    public boolean canConnectTo(World world, int x, int y, int z, @Nullable Network network, Direction dir) {
        BlockState other = world.getBlockState(x + dir.getOffsetX(), y + dir.getOffsetY(), z + dir.getOffsetZ());
        if (other.getBlock() instanceof NetworkComponent component) {
            return component.getNetworkTypes().contains(NetworkType.ENERGY);
        }
        return false;
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
        if (!player.isSneaking()) {
            ArrayList<Network> networks = NetworkManager.getAt(world.dimension, x, y, z, this.getNetworkTypes());
            StringBuilder sb = new StringBuilder();
            sb.append("This block (x:" + x + " y:" + y + " z:" + z + ") is in networks:");
            for (var net : networks) {
                sb.append(" " + net.getId());
            }
            player.sendMessage(sb.toString());
            return true;
        }

        return super.onUse(world, x, y, z, player);
    }
    
    // Energy Conductor
    @Override
    public int getBreakdownVoltage(World world, NetworkComponentEntry networkComponentEntry) {
        return 500;
    }

    @Override
    public int getBreakdownPower(World world, NetworkComponentEntry networkComponentEntry) {
        return 2000;
    }

    @Override
    public void onBreakdownVoltage(World world, NetworkComponentEntry networkComponentEntry, int voltage) {
        
    }

    @Override
    public void onBreakdownPower(World world, NetworkComponentEntry networkComponentEntry, int voltage, int power) {
        for (int particle = 0; particle < 4; particle++) {
            Vec3i pos = networkComponentEntry.pos();
            ParticleHelper.addParticle(world, "smoke", pos.x + 0.5D + (world.random.nextDouble() - 0.5D), pos.y + 0.5D, pos.z + 0.5D + (world.random.nextDouble() - 0.5D));
            world.setBlockState(pos.x, pos.y, pos.z, States.AIR.get());
        }
    }
}
