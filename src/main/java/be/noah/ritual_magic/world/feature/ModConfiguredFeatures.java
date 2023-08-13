package be.noah.ritual_magic.world.feature;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.ModBlocks;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, RitualMagic.MODID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> DWARVEN_DEBRIS_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.DEEPSLATE), ModBlocks.DWARVEN_DEBRIS.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> ATLANTIAN_DEBRIS_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.GRAVEL), ModBlocks.ATLANTIAN_DEBRIS.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> END_BLOCKS = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), ModBlocks.PETRIFIED_DRAGON_SCALE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?,?>> DWARVEN_DEBRIS = CONFIGURED_FEATURES.register("dwarven_debris",
            ()-> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DWARVEN_DEBRIS_ORES.get(),3)));
    public static final RegistryObject<ConfiguredFeature<?,?>> ATLANTIAN_DEBRIS = CONFIGURED_FEATURES.register("atlantian_debris",
            ()-> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ATLANTIAN_DEBRIS_ORES.get(),3)));
    public static final RegistryObject<ConfiguredFeature<?,?>> PETRIFIED_DRAGON_SCALE = CONFIGURED_FEATURES.register("petrified_dragon_scale",
            ()-> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(END_BLOCKS.get(),8)));

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }
}
