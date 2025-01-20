package net.danygames2014.nyatec.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class RubberLeavesBlock extends LeavesBlockTemplate {
    public RubberLeavesBlock(Identifier identifier, Material material, ItemStack saplingItemStack, Block logBlock) {
        super(identifier, material, saplingItemStack, logBlock);
    }
}
