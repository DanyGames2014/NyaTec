package net.danygames2014.nyatec.block;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.init.WorldGenListener;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class RubberSaplingBlock extends TemplateBlock {
    public RubberSaplingBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state) {
        return generateTree(world, world.random, x, y, z);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        generateTree(world, random, x, y, z);
    }

    public boolean generateTree(World world, Random random, int x, int y, int z) {
        if (world.isRemote) {
            return false;
        }

        if (world.getBlockState(x, y - 1, z).isIn(TagKey.of(BlockRegistry.INSTANCE.getKey(), NyaTec.NAMESPACE.id("rubber_tree_generates_on")))) {
            return WorldGenListener.rubberTreeFeature.generate(world, random, x, y, z, true);
        }
        return false;
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return world.getBlockState(x, y - 1, z).isIn(TagKey.of(BlockRegistry.INSTANCE.getKey(), NyaTec.NAMESPACE.id("rubber_tree_generates_on")));
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
