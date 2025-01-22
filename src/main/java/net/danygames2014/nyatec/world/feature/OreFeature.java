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
        double spreadX1 = x + ((MathHelper.sin(spreadShape) * this.oreCount) / 8.0D);
        double spreadX2 = x - ((MathHelper.sin(spreadShape) * this.oreCount) / 8.0D);
        double spreadZ1 = z + ((MathHelper.cos(spreadShape) * this.oreCount) / 8.0D);
        double spreadZ2 = z - ((MathHelper.cos(spreadShape) * this.oreCount) / 8.0D);
        double spreadY1 = y + random.nextInt(3) + 2;
        double spreadY2 = y + random.nextInt(3) + 2;

        for (int oreIndex = 0; oreIndex <= this.oreCount; oreIndex++) {
            double oreX = spreadX1 + (spreadX2 - spreadX1) * ((double) oreIndex / this.oreCount);
            double oreY = spreadY1 + (spreadY2 - spreadY1) * ((double) oreIndex / this.oreCount);
            double oreZ = spreadZ1 + (spreadZ2 - spreadZ1) * ((double) oreIndex / this.oreCount);

            placeOre(world, MathHelper.floor(oreX), MathHelper.floor(oreY), MathHelper.floor(oreZ));
            
//            double veinSizeModifier = random.nextDouble() * (this.oreCount / 16.0D);
//            double halfWidth = (MathHelper.sin((float) (oreIndex * Math.PI / this.oreCount)) + 1.0D) * veinSizeModifier + 1.0D;
//            double halfHeight = (MathHelper.sin((float) (oreIndex * Math.PI / this.oreCount)) + 1.0D) * veinSizeModifier + 1.0D;
//
//            double distX = (oreX + 0.5D - oreX) / (halfWidth / 2.0D);
//            if (distX * distX < 1.0D) {
//
//                double distY = (oreY + 0.5D - oreY) / (halfHeight / 2.0D);
//                if (distX * distX + distY * distY < 1.0D) {
//
//                    double distZ = (oreZ + 0.5D - oreZ) / (halfWidth / 2.0D);
//                    if (distX * distX + distY * distY + distZ * distZ < 1.0D) {
//                        
//                    }
//                }
//            }
        }

        return true;
    }

    public void placeOre(World world, int x, int y, int z) {
        if (world.getBlockState(x, y, z).isIn(TagKey.of(BlockRegistry.INSTANCE.getKey(), NyaTec.NAMESPACE.id("replaceable_by_ores")))) {
            world.setBlockState(x, y, z, this.oreBlockState);
        }
    }
}
