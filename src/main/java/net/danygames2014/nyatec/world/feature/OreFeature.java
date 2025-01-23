package net.danygames2014.nyatec.world.feature;

import net.danygames2014.nyatec.NyaTec;
import net.minecraft.block.Block;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;

import java.util.Random;

@SuppressWarnings("DuplicatedCode")
public class OreFeature extends Feature {
    private final BlockState oreBlockState;
    private final int oreCount;

    public OreFeature(Block oreBlock, int oreCount) {
        this.oreBlockState = oreBlock.getDefaultState();
        this.oreCount = oreCount;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        // Determines the "shape" in terms of X and Z spread
        // High Value = High Sine = Wide X Spread
        // Low Value = High Cosine = Wide Z Spread
        float spreadShape = random.nextFloat() * (float) Math.PI;

        double spreadX1 = (x + 8) + ((MathHelper.sin(spreadShape) * this.oreCount) / 8.0D);
        double spreadX2 = (x + 8) - ((MathHelper.sin(spreadShape) * this.oreCount) / 8.0D);
        double spreadZ1 = (z + 8) + ((MathHelper.cos(spreadShape) * this.oreCount) / 8.0D);
        double spreadZ2 = (z + 8) - ((MathHelper.cos(spreadShape) * this.oreCount) / 8.0D);
        double spreadY1 = y + random.nextInt(3) + 2;
        double spreadY2 = y + random.nextInt(3) + 2;

        for (int oreIndex = 0; oreIndex <= this.oreCount; ++oreIndex) {
            double oreOffsetX = spreadX1 + (((spreadX2 - spreadX1) * oreIndex) / this.oreCount);
            double oreOffsetY = spreadY1 + (spreadY2 - spreadY1) * oreIndex / this.oreCount;
            double oreOffsetZ = spreadZ1 + (spreadZ2 - spreadZ1) * oreIndex / this.oreCount;
            double veinSizeModifier = random.nextDouble() * this.oreCount / 16.0D;
             double halfWidth = (MathHelper.sin(oreIndex * (float) Math.PI / this.oreCount) + 1.0D) * veinSizeModifier + 1.0D;
            double halfHeight = (MathHelper.sin(oreIndex * (float) Math.PI / this.oreCount) + 1.0D) * veinSizeModifier + 1.0D;
            int startX = MathHelper.floor(oreOffsetX - halfWidth / 2.0D);
            int startY = MathHelper.floor(oreOffsetY - halfHeight / 2.0D);
            int startZ = MathHelper.floor(oreOffsetZ - halfWidth / 2.0D);
            int endX = MathHelper.floor(oreOffsetX + halfWidth / 2.0D);
            int endY = MathHelper.floor(oreOffsetY + halfHeight / 2.0D);
            int endZ = MathHelper.floor(oreOffsetZ + halfWidth / 2.0D);

            for (int oreX = startX; oreX <= endX; ++oreX) {
                double wtfX = (oreX + 0.5D - oreOffsetX) / (halfWidth / 2.0D);
                if (wtfX * wtfX < 1.0D) {
                    for (int oreY = startY; oreY <= endY; ++oreY) {
                        double wtfY = (oreY + 0.5D - oreOffsetY) / (halfHeight / 2.0D);
                        if (wtfX * wtfX + wtfY * wtfY < 1.0D) {
                            for (int oreZ = startZ; oreZ <= endZ; ++oreZ) {
                                double wtfZ = (oreZ + 0.5D - oreOffsetZ) / (halfWidth / 2.0D);
                                if (wtfX * wtfX + wtfY * wtfY + wtfZ * wtfZ < 1.0D) {
                                    placeOre(world, oreX, oreY, oreZ);
                                } 
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public void placeOre(World world, int x, int y, int z) {
        if (world.getBlockState(x, y, z).isIn(TagKey.of(BlockRegistry.INSTANCE.getKey(), NyaTec.NAMESPACE.id("replaceable_by_ores")))) {
            world.setBlockState(x, y, z, this.oreBlockState);
        }
    }
}
