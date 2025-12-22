package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.block.JsonOverrideRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.modificationstation.stationapi.api.util.Identifier;

public class TemplateBlockRegistry {
    public static void registerCubeLamp(Identifier blockIdentifier) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            return;
        }

        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_off", cubeLampOff);
        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_reduced", cubeLampReduced);
        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_dim", cubeLampDim);
        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_full", cubeLampFull);

        JsonOverrideRegistry.registerItemModelOverride(blockIdentifier, itemJson.replace("PATH", getBlockModelPath(blockIdentifier) + "_off"));
        
        String lampState = cubeLampState;
        lampState = lampState.replace("OFF", getBlockModelPath(blockIdentifier) + "_off");
        lampState = lampState.replace("REDUCED", getBlockModelPath(blockIdentifier) + "_reduced");
        lampState = lampState.replace("DIM", getBlockModelPath(blockIdentifier) + "_dim");
        lampState = lampState.replace("FULL", getBlockModelPath(blockIdentifier) + "_full");
        JsonOverrideRegistry.registerBlockstateOverride(blockIdentifier, lampState);
    }

    private static String getBlockModelPath(String blockIdentifier) {
        return getBlockModelPath(Identifier.of(blockIdentifier));
    }

    private static String getBlockModelPath(Identifier blockIdentifier) {
        return blockIdentifier.namespace + ":block/" + blockIdentifier.path;
    }

    // Item
    private static final String itemJson = ("""
            {
              "parent": "PATH"
            }"""
    );

    // Cube Lamp
    private static final String cubeLampOff = ("""
            {
              "parent": "nyatec:block/template/light/cube/off",
              "textures": {
              }
            }"""
    );

    private static final String cubeLampReduced = ("""
            {
              "parent": "nyatec:block/template/light/cube/reduced",
              "textures": {
              }
            }"""
    );

    private static final String cubeLampDim = ("""
            {
              "parent": "nyatec:block/template/light/cube/dim",
              "textures": {
              }
            }"""
    );

    private static final String cubeLampFull = ("""
            {
              "parent": "nyatec:block/template/light/cube/full",
              "textures": {
              }
            }"""
    );

    private static final String cubeLampState = ("""
            {
              "variants": {
                "light_level=full": {
                  "model": "FULL"
                },
                "light_level=reduced": {
                  "model": "REDUCED"
                },
                "light_level=dim": {
                  "model": "DIM"
                },
                "light_level=off": {
                  "model": "OFF"
                }
              }
            }"""
    );
}
