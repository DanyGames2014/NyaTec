package net.danygames2014.nyatec.world.structure;

import net.danygames2014.nyalib.world.structure.CollisionType;
import net.danygames2014.nyalib.world.structure.TreeStructure;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.RubberLogBlock;
import net.danygames2014.nyatec.block.RubberSpot;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

public class RubberTreeStructure extends TreeStructure {
    public int latexSpotsGenerated = 0;
    
    public RubberTreeStructure(World world, BlockState trunkBlockState, CollisionType trunkCollisionType) {
        super(world, trunkBlockState, trunkCollisionType);
    }

    @Override
    public void setState(World world, int x, int y, int z, BlockState state) {
        if (state.isOf(NyaTec.rubberLog)){
            if (latexSpotsGenerated < NyaTec.WORLDGEN_CONFIG.rubberTree.maximumLatexSpots) {
                if (random.nextInt(NyaTec.WORLDGEN_CONFIG.rubberTree.latexSpotChance) == 0) {
                    state = state.
                            with(RubberLogBlock.RUBBER_SPOT, RubberSpot.getRandomSide(random)).
                            with(RubberLogBlock.FULL_SPOT, true);

                    latexSpotsGenerated++;
                }
            }
        }
        
        super.setState(world, x, y, z, state);
    }
}
