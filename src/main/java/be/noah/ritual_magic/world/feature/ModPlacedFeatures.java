package be.noah.ritual_magic.world.feature;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, RitualMagic.MODID);

    public static final RegistryObject<PlacedFeature> DWARVEN_DEBRIS_PLACED = PLACED_FEATURES.register("dwarven_debris_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.DWARVEN_DEBRIS.getHolder().get(),
                    commonOrePlacement(3,
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(-32)))));
    public static final RegistryObject<PlacedFeature> PETRIFIED_DRAGON_SCALE_PLACED = PLACED_FEATURES.register("petrified_dragon_scale_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.PETRIFIED_DRAGON_SCALE.getHolder().get(),
                    commonOrePlacement(2,
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(10),VerticalAnchor.absolute(30)))));
    public static final RegistryObject<PlacedFeature> ATLANTIAN_DEBRIS_PLACED = PLACED_FEATURES.register("atlantian_debris_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.ATLANTIAN_DEBRIS.getHolder().get(),
                    commonOrePlacement(6,
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(35),VerticalAnchor.absolute(55)))));



    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }
    private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }
    private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }

    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}
