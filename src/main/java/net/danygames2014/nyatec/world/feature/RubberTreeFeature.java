package net.danygames2014.nyatec.world.feature;

import net.danygames2014.nyalib.world.structure.CollisionType;
import net.danygames2014.nyalib.world.structure.Structure;
import net.danygames2014.nyalib.world.structure.TreeStructure;
import net.danygames2014.nyatec.NyaTec;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class RubberTreeFeature extends Feature {
    Structure rubberTreeStructure;

    public RubberTreeFeature(World world) {
        this.rubberTreeStructure = new TreeStructure(world, NyaTec.rubberLog.getDefaultState(), CollisionType.DONT_PLACE);
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        return false;
    }
}
