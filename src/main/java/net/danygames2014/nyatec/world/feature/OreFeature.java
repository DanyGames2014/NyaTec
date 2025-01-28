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

        double xMin = (float) (x + 8) + MathHelper.sin(spreadShape) * (float) this.oreCount / 8.0F;
        double xMax = (float) (x + 8) - MathHelper.sin(spreadShape) * (float) this.oreCount / 8.0F;
        double zMin = (float) (z + 8) + MathHelper.cos(spreadShape) * (float) this.oreCount / 8.0F;
        double zMax = (float) (z + 8) - MathHelper.cos(spreadShape) * (float) this.oreCount / 8.0F;
        double yMin = y + random.nextInt(3) + 2;
        double yMax = y + random.nextInt(3) + 2;

        for (int oreIndex = 0; oreIndex <= this.oreCount; ++oreIndex) {
            double centerX = xMin + (xMax - xMin) * (double) oreIndex / (double) this.oreCount;
            double centerY = yMin + (yMax - yMin) * (double) oreIndex / (double) this.oreCount;
            double centerZ = zMin + (zMax - zMin) * (double) oreIndex / (double) this.oreCount;
            double size = random.nextDouble() * (double) this.oreCount / (double) 16.0F;
            double horizontalModifier = (double) (MathHelper.sin((float) oreIndex * (float) Math.PI / (float) this.oreCount) + 1.0F) * size + (double) 1.0F;
            double verticalModifier = (double) (MathHelper.sin((float) oreIndex * (float) Math.PI / (float) this.oreCount) + 1.0F) * size + (double) 1.0F;
            int startX = MathHelper.floor(centerX - horizontalModifier / (double) 2.0F);
            int startY = MathHelper.floor(centerY - verticalModifier / (double) 2.0F);
            int startZ = MathHelper.floor(centerZ - horizontalModifier / (double) 2.0F);
            int endX = MathHelper.floor(centerX + horizontalModifier / (double) 2.0F);
            int endY = MathHelper.floor(centerY + verticalModifier / (double) 2.0F);
            int endZ = MathHelper.floor(centerZ + horizontalModifier / (double) 2.0F);

            for (int oreX = startX; oreX <= endX; ++oreX) {
                double sqDistanceX = ((double) oreX + (double) 0.5F - centerX) / (horizontalModifier / (double) 2.0F);
                if (sqDistanceX * sqDistanceX < (double) 1.0F) {
                    for (int oreY = startY; oreY <= endY; ++oreY) {
                        double sqDistanceY = ((double) oreY + (double) 0.5F - centerY) / (verticalModifier / (double) 2.0F);
                        if (sqDistanceX * sqDistanceX + sqDistanceY * sqDistanceY < (double) 1.0F) {
                            for (int oreZ = startZ; oreZ <= endZ; ++oreZ) {
                                double sqDistanceZ = ((double) oreZ + (double) 0.5F - centerZ) / (horizontalModifier / (double) 2.0F);
                                if (sqDistanceX * sqDistanceX + sqDistanceY * sqDistanceY + sqDistanceZ * sqDistanceZ < (double) 1.0F) {
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
