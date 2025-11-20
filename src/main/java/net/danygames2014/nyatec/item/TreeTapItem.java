package net.danygames2014.nyatec.item;

import net.danygames2014.nyatec.init.WrenchModeListener;
import net.danygames2014.uniwrench.item.WrenchBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class TreeTapItem extends WrenchBase {
    public TreeTapItem(Identifier identifier) {
        super(identifier);
        this.setMaxDamage(16);
        this.addWrenchMode(WrenchModeListener.treeTapMode);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {
        boolean result = super.useOnBlock(stack, player, world, x, y, z, side);

        if (result) {
            stack.damage(1, player);
        }

        return result;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[]{
                originalTooltip
        };
    }
}
