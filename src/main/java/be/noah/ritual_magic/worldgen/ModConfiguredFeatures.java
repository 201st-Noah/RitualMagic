package be.noah.ritual_magic.worldgen;


import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DWARVEN_DEBRIS_KEY = registerKey("dwarven_debris");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ATLANTIAN_DEBRIS_KEY = registerKey("atlantian_debris");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_DRAGON_SCALE_KEY = registerKey("petrified_dragon_scale");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest gravleReplacables = new BlockMatchTest(Blocks.GRAVEL);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        register(context, DWARVEN_DEBRIS_KEY, Feature.ORE, new OreConfiguration(deepslateReplaceables,
                ModBlocks.DWARVEN_DEBRIS.get().defaultBlockState(), 4));
        register(context, ATLANTIAN_DEBRIS_KEY, Feature.ORE, new OreConfiguration(gravleReplacables,
                ModBlocks.ATLANTIAN_DEBRIS.get().defaultBlockState(), 5));
        register(context, PETRIFIED_DRAGON_SCALE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
                ModBlocks.PETRIFIED_DRAGON_SCALE.get().defaultBlockState(), 4));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(RitualMagic.MODID, name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
