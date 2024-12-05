package net.danygames2014.nyatec.world.feature;

import net.danygames2014.nyalib.world.structure.CollisionType;
import net.danygames2014.nyalib.world.structure.TreeStructure;
import net.danygames2014.nyatec.NyaTec;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;

import java.util.Random;

public class RubberTreeFeature extends Feature {
    TreeStructure rubberTreeStructure;
    int rarity;

    public RubberTreeFeature(World world, int rarity) {
        this.rubberTreeStructure = new TreeStructure(world, NyaTec.rubberLog.getDefaultState(), CollisionType.REPLACE_BLOCK);
        this.rarity = rarity;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        return generate(world, random, x, y, z, false);
    }

    public boolean generate(World world, Random random, int x, int y, int z, boolean fromSapling) {
        if (random.nextInt(rarity) == 0) {
            if (world.getBlockState(x, y - 1, z).isIn(TagKey.of(BlockRegistry.INSTANCE.getKey(), NyaTec.NAMESPACE.id("rubber_tree_generates_on")))) {
                return rubberTreeStructure.generate(world, x, y, z, random.nextInt(5, 9));
            }
        }

        return false;
    }
}
