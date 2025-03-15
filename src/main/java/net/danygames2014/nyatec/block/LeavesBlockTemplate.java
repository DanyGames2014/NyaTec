package net.danygames2014.nyatec.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

public class LeavesBlockTemplate extends TemplateBlock {
    public int saplingDropChance; // 1 in x chance to drop sapling
    public ItemStack saplingItemStack;
    public Block logBlock;

    /**
     * @param identifier       The identifier of the block
     * @param material         The material of the block
     * @param saplingItemStack The sapling that these leaves can drop
     * @param logBlock         The block that these leaves will consider a log (for decay purposes)
     */
    public LeavesBlockTemplate(Identifier identifier, Material material, ItemStack saplingItemStack, Block logBlock) {
        super(identifier, material);
        this.saplingItemStack = saplingItemStack;
        this.logBlock = logBlock;
        this.saplingDropChance = 20;
        this.setTickRandomly(true);
    }

    /**
     * @param identifier        The identifier of the block
     * @param material          The material of the block
     * @param saplingItemStack  The sapling that these leaves can drop
     * @param logBlock          The block that these leaves will consider a log (for decay purposes)
     * @param saplingDropChance The 1/x chance of dropping a sapling when decaying or being broken. (4 = 1/4 chance = 25%)
     */
    public LeavesBlockTemplate(Identifier identifier, Material material, ItemStack saplingItemStack, Block logBlock, int saplingDropChance) {
        this(identifier, material, saplingItemStack, logBlock);
        this.saplingDropChance = saplingDropChance;
    }
    
    public LeavesBlockTemplate setSaplingDropChance(int saplingDropChance){
        this.saplingDropChance = saplingDropChance;
        return this;
    }

    // Rendering
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public boolean isOpaque() {
        return !Minecraft.isFancyGraphicsEnabled();
    }

    // Drop
    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return saplingItemStack.itemId;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return random.nextInt(saplingDropChance) == 0 ? saplingItemStack.count : 0;
    }

    @Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        if (!world.isRemote && playerEntity.getHand() != null && playerEntity.getHand().itemId == Item.SHEARS.id) {
            playerEntity.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStack(world, x, y, z, new ItemStack(this, 1));
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta);
        }

    }

    // Decay Logic
    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        if (!world.isRemote) {
            updateDecay(world, x, y, z, world.random, true);
        }
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            updateDecay(world, x, y, z, random, false);
        }
    }

    @Override
    public void onBlockPlaced(World world, int x, int y, int z, BlockState replacedState) {
        if (!world.isRemote) {
            updateDecay(world, x, y, z, world.random, true);
        }
    }

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity placer) {
        if (!world.isRemote) {
            // If the player is sneaking, make the leaves immune to decay
            if (placer.isSneaking()) {
                world.setBlockMetaWithoutNotifyingNeighbors(x, y, z, 15);
                return;
            }

            updateDecay(world, x, y, z, world.random, true);
        }
    }

    public void updateDecay(World world, int x, int y, int z, Random random, boolean dontDecay) {
        int prevDecayValue = world.getBlockMeta(x, y, z);
        int decayValue = 0;

        // 15 means this leaf is immune from decay
        if (prevDecayValue == 15) {
            return;
        }

        for (Direction dir : Direction.values()) {
            // Get the state
            BlockState state = world.getBlockState(x + dir.getOffsetX(), y + dir.getOffsetY(), z + dir.getOffsetZ());

            // If its leaves and the decay value is larger than the current one, take it minus one
            // If its 15, then means its an immune leaf, ignore those
            if (state.isOf(this)) {
                int leavesMeta = world.getBlockMeta(x + dir.getOffsetX(), y + dir.getOffsetY(), z + dir.getOffsetZ());
                if (leavesMeta != 15 && leavesMeta > decayValue) {
                    decayValue = leavesMeta - 1;
                }
            }

            // If its log, set the decay value to 3
            if (state.isOf(logBlock)) {
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
        if (prevDecayValue != decayValue) {
            world.setBlockMeta(x, y, z, decayValue);
        }
    }

    public void decay(World world, int x, int y, int z, Random random) {
        world.setBlockStateWithNotify(x, y, z, States.AIR.get());
        if (this.getDroppedItemCount(random) > 0) {
            this.dropStack(world, x, y, z, new ItemStack(saplingItemStack.getItem(), saplingItemStack.count));
        }
    }
}
