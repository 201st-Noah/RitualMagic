package be.noah.ritual_magic.worldgen;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DWARVEN_DEBRIS_PLACED_KEY = registerKey("dwarven_debris_placed");
    public static final ResourceKey<PlacedFeature> ATLANTIAN_DEBRIS_PLACED_KEY = registerKey("atlantian_debris_placed");
    public static final ResourceKey<PlacedFeature> PETRIFIED_DRAGON_SCALE_PLACED_KEY = registerKey("petrified_dragon_scale_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, DWARVEN_DEBRIS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DWARVEN_DEBRIS_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(-20))));
        register(context, ATLANTIAN_DEBRIS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ATLANTIAN_DEBRIS_KEY),
                ModOrePlacement.commonOrePlacement(16,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(50))));
        register(context, PETRIFIED_DRAGON_SCALE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PETRIFIED_DRAGON_SCALE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(20), VerticalAnchor.absolute(40))));
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(RitualMagic.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
