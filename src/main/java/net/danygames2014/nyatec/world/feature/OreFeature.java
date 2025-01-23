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

        double spreadX1 = (float) (x + 8) + ((MathHelper.sin(spreadShape) * (float) this.oreCount) / 8.0F);
        double spreadX2 = (float) (x + 8) - ((MathHelper.sin(spreadShape) * (float) this.oreCount) / 8.0F);
        double spreadZ1 = (float) (z + 8) + ((MathHelper.cos(spreadShape) * (float) this.oreCount) / 8.0F);
        double spreadZ2 = (float) (z + 8) - ((MathHelper.cos(spreadShape) * (float) this.oreCount) / 8.0F);
        double spreadY1 = y + random.nextInt(3) + 2;
        double spreadY2 = y + random.nextInt(3) + 2;

        for (int oreIndex = 0; oreIndex <= this.oreCount; ++oreIndex) {
            double oreOffsetX = spreadX1 + (((spreadX2 - spreadX1) * (double) oreIndex) / (double) this.oreCount);
            double oreOffsetY = spreadY1 + (spreadY2 - spreadY1) * (double) oreIndex / (double) this.oreCount;
            double oreOffsetZ = spreadZ1 + (spreadZ2 - spreadZ1) * (double) oreIndex / (double) this.oreCount;
            double veinSizeModifier = random.nextDouble() * (double) this.oreCount / (double) 16.0F;
            double halfWidth = (double) (MathHelper.sin((float) oreIndex * (float) Math.PI / (float) this.oreCount) + 1.0F) * veinSizeModifier + (double) 1.0F;
            double halfHeight = (double) (MathHelper.sin((float) oreIndex * (float) Math.PI / (float) this.oreCount) + 1.0F) * veinSizeModifier + (double) 1.0F;
            int startX = MathHelper.floor(oreOffsetX - halfWidth / (double) 2.0F);
            int startY = MathHelper.floor(oreOffsetY - halfHeight / (double) 2.0F);
            int startZ = MathHelper.floor(oreOffsetZ - halfWidth / (double) 2.0F);
            int endX = MathHelper.floor(oreOffsetX + halfWidth / (double) 2.0F);
            int endY = MathHelper.floor(oreOffsetY + halfHeight / (double) 2.0F);
            int endZ = MathHelper.floor(oreOffsetZ + halfWidth / (double) 2.0F);

            for (int oreX = startX; oreX <= endX; ++oreX) {
                double wtfX = ((double) oreX + (double) 0.5F - oreOffsetX) / (halfWidth / (double) 2.0F);
                if (wtfX * wtfX < (double) 1.0F) {
                    
                    for (int oreY = startY; oreY <= endY; ++oreY) {
                        double wtfY = ((double) oreY + (double) 0.5F - oreOffsetY) / (halfHeight / (double) 2.0F);
                        if (wtfX * wtfX + wtfY * wtfY < (double) 1.0F) {
                            
                            for (int oreZ = startZ; oreZ <= endZ; ++oreZ) {
                                double wtfZ = ((double) oreZ + (double) 0.5F - oreOffsetZ) / (halfWidth / (double) 2.0F);
                                if (wtfX * wtfX + wtfY * wtfY + wtfZ * wtfZ < (double) 1.0F) {
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
