package be.noah.ritual_magic.item;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class ModToolTiers {
    private static Object ModTags;
    public static final Tier DRACONIC = TierSortingRegistry.registerTier(
            new ForgeTier(5, 10000, 5f, 4f, 25,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(ModItems.DRAGON_PLATE.get())),
            new ResourceLocation(RitualMagic.MODID, "dragon_plate"), List.of(Tiers.NETHERITE), List.of());
}
