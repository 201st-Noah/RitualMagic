package be.noah.ritual_magic.items;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier DRACONIC = TierSortingRegistry.registerTier(
            new ForgeTier(5, 10000, 5f, 4f, 25,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(ModItems.DRAGON_SCALE.get())),
            new ResourceLocation(RitualMagic.MODID, "draconic"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier ATLANTIAN = TierSortingRegistry.registerTier(
            new ForgeTier(5, 10000, 5f, 4f, 25,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(ModItems.ATLANTIAN_STEEL_INGOT.get())),
            new ResourceLocation(RitualMagic.MODID, "atlantian"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier DWARVEN = TierSortingRegistry.registerTier(
            new ForgeTier(5, 10000, 5f, 4f, 25,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(ModItems.DWARVEN_STEEL_INGOT.get())),
            new ResourceLocation(RitualMagic.MODID, "dwarven"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier HELLISH = TierSortingRegistry.registerTier(
            new ForgeTier(5, 10000, 5f, 4f, 25,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(ModItems.PURE_NETHERITE_INGOT.get())),
            new ResourceLocation(RitualMagic.MODID, "hellish"), List.of(Tiers.NETHERITE), List.of());
    //Ignore
    public static final Tier TEMP = TierSortingRegistry.registerTier(
            new ForgeTier(5, 10000, 5f, 4f, 25,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(ModItems.DRAGON_PLATE.get())),
            new ResourceLocation(RitualMagic.MODID, "temp"), List.of(Tiers.NETHERITE), List.of());
}
