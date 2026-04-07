package net.danygames2014.nyatec.mixin;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.item.SingleUseEnergyItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RedstoneItem;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(RedstoneItem.class)
public class RedstoneItemMixin implements SingleUseEnergyItem {
    @Override
    public int getEnergyValue(ItemStack stack) {
        return NyaTec.MACHINE_CONFIG.redstoneEnergyValue;
    }
}
