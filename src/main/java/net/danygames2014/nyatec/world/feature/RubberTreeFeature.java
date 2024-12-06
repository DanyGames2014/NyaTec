package net.danygames2014.nyatec.world.feature;

import net.danygames2014.nyalib.world.structure.CollisionType;
import net.danygames2014.nyalib.world.structure.TreeStructure;
import net.danygames2014.nyatec.NyaTec;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;

import java.util.Random;

public class RubberTreeFeature extends Feature {
    public TreeStructure structure;
    int rarity;

    public RubberTreeFeature(World world, int rarity) {
        this.structure = new TreeStructure(world, NyaTec.rubberLog.getDefaultState(), CollisionType.REPLACE_BLOCK);
        this.rarity = rarity;
        BlockState leavesState = NyaTec.rubberLeaves.getDefaultState();
    
        
        // These have to be placed in a certain order in order to prevent accidental decay
        
        // Layer 3
        structure.addBlock(0,0,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,0,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,0,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(0,0,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(0,0,-1, leavesState, CollisionType.DONT_PLACE);

        // Layer 2
        structure.addBlock(0,1,0, leavesState, CollisionType.DONT_PLACE);
        
        // Layer 1
        structure.addBlock(0,2,0, leavesState, CollisionType.DONT_PLACE);
        
        // Layer 4
        structure.addBlock(0,-1,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(0,-1,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-1,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-1,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-1,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-1,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-1,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-1,1, leavesState, CollisionType.DONT_PLACE);

        structure.addBlock(0,-1,2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(0,-1,-2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(2,-1,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-2,-1,0, leavesState, CollisionType.DONT_PLACE);
        
        // Layer 5
        structure.addBlock(0,-2,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(0,-2,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-2,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-2,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-2,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-2,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-2,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-2,1, leavesState, CollisionType.DONT_PLACE);

        structure.addBlock(0,-2,2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-2,2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-2,2, leavesState, CollisionType.DONT_PLACE);
        
        structure.addBlock(0,-2,-2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-2,-2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-2,-2, leavesState, CollisionType.DONT_PLACE);
        
        structure.addBlock(2,-2,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(2,-2,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(2,-2,-1, leavesState, CollisionType.DONT_PLACE);
        
        structure.addBlock(-2,-2,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-2,-2,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-2,-2,-1, leavesState, CollisionType.DONT_PLACE);
        
        // Layer 6
        structure.addBlock(0,-3,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(0,-3,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-3,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-3,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-3,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-3,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-3,-1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-3,1, leavesState, CollisionType.DONT_PLACE);

        structure.addBlock(0,-3,2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-3,2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-3,2, leavesState, CollisionType.DONT_PLACE);

        structure.addBlock(0,-3,-2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(1,-3,-2, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-1,-3,-2, leavesState, CollisionType.DONT_PLACE);

        structure.addBlock(2,-3,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(2,-3,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(2,-3,-1, leavesState, CollisionType.DONT_PLACE);

        structure.addBlock(-2,-3,0, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-2,-3,1, leavesState, CollisionType.DONT_PLACE);
        structure.addBlock(-2,-3,-1, leavesState, CollisionType.DONT_PLACE);
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        return generate(world, random, x, y, z, false);
    }

    public boolean generate(World world, Random random, int x, int y, int z, boolean fromSapling) {
        if (random.nextInt(rarity) == 0) {
            if (world.getBlockState(x, y - 1, z).isIn(TagKey.of(BlockRegistry.INSTANCE.getKey(), NyaTec.NAMESPACE.id("rubber_tree_generates_on")))) {
                return structure.generate(world, x, y, z, random.nextInt(5, 9));
            }
        }

        return false;
    }
}
