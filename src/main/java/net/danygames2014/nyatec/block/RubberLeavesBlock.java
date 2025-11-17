package net.danygames2014.nyatec.block;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.template.LeavesBlockTemplate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class RubberLeavesBlock extends LeavesBlockTemplate {
    public RubberLeavesBlock(Identifier identifier, Material material, Block logBlock) {
        super(identifier, material, logBlock);
    }

    ItemStack saplingStack;
    @Override
    public ItemStack getSaplingItemStack() {
        if (saplingStack == null) {
            saplingStack = new ItemStack(NyaTec.rubberSapling.asItem());
        }
        
        return saplingStack;
    }
}
