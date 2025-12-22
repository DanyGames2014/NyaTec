package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.block.ElectricLightBlock;
import net.danygames2014.nyatec.block.LightLevel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.client.color.block.BlockColorProvider;
import net.modificationstation.stationapi.api.client.color.item.ItemColorProvider;
import net.modificationstation.stationapi.api.client.event.color.block.BlockColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.model.BlockModelPredicateProviderRegistryEvent;
import net.modificationstation.stationapi.api.client.event.render.model.ItemModelPredicateProviderRegistryEvent;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ClientListener {

    @EventListener
    public void registerBlockModelPredicates(BlockModelPredicateProviderRegistryEvent event) {
        event.registry.register(
                NyaTec.rubberLeaves,
                NyaTec.NAMESPACE.id("graphics"),
                (blockState, blockView, blockPos, i) -> {
                    return Minecraft.isFancyGraphicsEnabled() ? 1.0F : 0.0F;
                }
        );
    }

    @EventListener
    public void registerItemModelPredicates(ItemModelPredicateProviderRegistryEvent event) {
        event.registry.register(
                NyaTec.battery,
                NyaTec.NAMESPACE.id("charge_level"),
                (stack, world, entity, seed) -> {
                    return stack.getStationNbt().getInt("energy");
                }
        );
    }

    @EventListener
    public void registerBlockColors(BlockColorsRegisterEvent event) {
        for (var lightBlock : ElectricLightBlock.LIGHT_BLOCKS) {
            event.blockColors.registerColorProvider(new BlockColorProvider() {
                @Override
                public int getColor(BlockState state, @Nullable BlockView world, @Nullable BlockPos pos, int tintIndex) {
                    if (tintIndex == 0 && state.getBlock() instanceof ElectricLightBlock light) {
                        if (state.get(ElectricLightBlock.LIGHT_LEVEL) == LightLevel.OFF) {
                            return light.offColor;
                        }

                        return light.color;
                    }

                    return 0;
                }
            }, lightBlock);
        }
    }

    @EventListener
    public void registerItemColors(ItemColorsRegisterEvent event) {
        for (var lightBlock : ElectricLightBlock.LIGHT_BLOCKS) {
            event.itemColors.register(new ItemColorProvider() {
                @Override
                public int getColor(ItemStack stack, int tintIndex) {
                    if (tintIndex == 0 && stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ElectricLightBlock lightBlock) {
                        return lightBlock.offColor;
                    }

                    return 0;
                }
            }, lightBlock);
        }
    }
}
