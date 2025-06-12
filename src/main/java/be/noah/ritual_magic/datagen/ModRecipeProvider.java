package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.datagen.recipe.InfusionRecipeBuilder;
import be.noah.ritual_magic.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    protected static void oreSmelting(
            @NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            List<ItemLike> pIngredients,
            @NotNull RecipeCategory pCategory,
            @NotNull ItemLike pResult,
            float pExperience,
            int pCookingTIme,
            @NotNull String pGroup
    ) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.SMELTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTIme,
                pGroup,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(
            @NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            @NotNull List<ItemLike> pIngredients,
            @NotNull RecipeCategory pCategory,
            @NotNull ItemLike pResult,
            float pExperience,
            int pCookingTime,
            @NotNull String pGroup
    ) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.BLASTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_blasting"
        );
    }

    protected static void oreCooking(
            @NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            @NotNull RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
            @NotNull List<ItemLike> pIngredients,
            @NotNull RecipeCategory pCategory,
            @NotNull ItemLike pResult,
            float pExperience,
            int pCookingTime,
            @NotNull String pGroup,
            String pRecipeName
    ) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(
                            Ingredient.of(itemlike),
                            pCategory,
                            pResult,
                            pExperience,
                            pCookingTime,
                            pCookingSerializer
                    )
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(
                            pFinishedRecipeConsumer,
                            RitualMagic.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike)
                    );
        }

    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter,
                List.of(ModBlocks.DWARVEN_DEBRIS.get()),
                RecipeCategory.MISC,
                ModItems.DWARVEN_SCRAP.get(),
                0.25f,
                200,
                "dwarven_scrap");
        oreBlasting(pWriter,
                List.of(ModBlocks.DWARVEN_DEBRIS.get()),
                RecipeCategory.MISC,
                ModItems.DWARVEN_SCRAP.get(),
                0.25f,
                100,
                "dwarven_scrap");

        oreSmelting(pWriter,
                List.of(ModBlocks.ATLANTIAN_DEBRIS.get()),
                RecipeCategory.MISC, ModItems.ATLANTIAN_SCRAP.get(),
                0.25f,
                200,
                "atlantian_scrap");
        oreBlasting(pWriter,
                List.of(ModBlocks.ATLANTIAN_DEBRIS.get()),
                RecipeCategory.MISC, ModItems.ATLANTIAN_SCRAP.get(),
                0.25f,
                100,
                "atlantian_scrap");

        oreSmelting(pWriter,
                List.of(ModBlocks.PETRIFIED_DRAGON_SCALE.get()),
                RecipeCategory.MISC,
                ModItems.DRAGON_SCALE.get(),
                0.25f,
                200,
                "dragon_scale");
        oreBlasting(pWriter,
                List.of(ModBlocks.PETRIFIED_DRAGON_SCALE.get()),
                RecipeCategory.MISC,
                ModItems.DRAGON_SCALE.get(),
                0.25f,
                100,
                "dragon_scale");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DWARVEN_TEMPLATE.get())
                .requires(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)
                .requires(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)
                .requires(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)
                .requires(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 1)
                .unlockedBy(getHasName(ModItems.DWARVEN_STEEL_INGOT.get()), has(ModItems.DWARVEN_STEEL_INGOT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DWARVEN_STEEL_INGOT.get())
                .requires(ModItems.DWARVEN_SCRAP.get(), 4)
                .requires(ModItems.WARDEN_CORE.get(), 1)
                .requires(Items.IRON_INGOT, 4)
                .unlockedBy(getHasName(ModItems.DWARVEN_SCRAP.get()), has(ModItems.DWARVEN_SCRAP.get()))
                .group("dwarven_steel_ingot")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "dwarven_steel_ingot_from_scrap"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DWARVEN_STEEL_INGOT.get(), 9)
                .requires(ModBlocks.DWARVEN_STEEL_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DWARVEN_STEEL_INGOT.get()), has(ModItems.DWARVEN_STEEL_INGOT.get()))
                .group("dwarven_steel_ingot")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "dwarven_steel_ingot_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DWARVEN_STEEL_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.DWARVEN_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.DWARVEN_STEEL_INGOT.get()), has(ModItems.DWARVEN_STEEL_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.HEART_OF_THE_SEA, 2)
                .pattern("ITI")
                .pattern("NHN")
                .pattern("ITI")
                .define('T', Items.SCUTE)
                .define('N', Items.NAUTILUS_SHELL)
                .define('I', Items.BLUE_ICE)
                .define('H', Items.HEART_OF_THE_SEA)
                .unlockedBy(getHasName(Items.HEART_OF_THE_SEA), has(Items.HEART_OF_THE_SEA))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BUILDERSTAFF.get(), 1)
                .pattern("  I")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Items.STICK)
                .define('I', ModItems.DWARVEN_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.DWARVEN_STEEL_INGOT.get()), has(ModItems.DWARVEN_STEEL_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TORCH.get(), 1)
                .pattern(" C ")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COAL_BLOCK)
                .unlockedBy(getHasName(Items.COAL_BLOCK), has(Items.COAL_BLOCK))
                .save(pWriter);

        new InfusionRecipeBuilder().input(ModItems.ICE_SWORD_HILT.get())
                .pedestalItems(
                        ModItems.SHARP_ICE_SHARD.get(),
                        ModItems.SHARP_ICE_SHARD.get(),
                        ModItems.SHARP_ICE_SHARD.get(),
                        ModItems.SHARP_ICE_SHARD.get()
                )
                .result(ModItems.ICE_SWORD.get())
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .preserveMainItem(false)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.ICE_SWORD.get())
                .pedestalItems(ModItems.ICE_SHARD.get())
                .result(ModItems.ICE_SWORD.get())
                .minLevel(0)
                .maxLevel(99)
                .levelGain(1)
                .manaCost(0)
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.ICE_SWORD.get())
                .pedestalItems(ModItems.ATLANTIAN_STEEL_INGOT.get())
                .result(ModItems.ICE_SWORD.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.ICE_SHARD.get())
                .pedestalItems(
                        ModItems.ICE_SHARD.get(),
                        ModItems.ICE_SHARD.get(),
                        ModItems.ICE_SHARD.get(),
                        ModItems.ICE_SHARD.get(),
                        ModItems.ICE_SHARD.get(),
                        ModItems.ICE_SHARD.get(),
                        ModItems.ICE_SHARD.get(),
                        ModItems.ICE_SHARD.get()
                )
                .result(ModItems.SHARP_ICE_SHARD.get())
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.ATLANTIAN_STEEL_INGOT.get())
                .pedestalItems(
                        ModItems.ATLANTIAN_STEEL_INGOT.get(),
                        ModItems.ATLANTIAN_STEEL_INGOT.get(),
                        ModItems.ATLANTIAN_STEEL_INGOT.get(),
                        ModItems.SHARP_ICE_SHARD.get()
                )
                .result(ModItems.ICE_SWORD_HILT.get())
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.SOUL_SCYTHE.get())
                .pedestalItems(ModItems.PURE_NETHERITE.get())
                .result(ModItems.SOUL_SCYTHE.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("HELLISH")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.ICE_CHESTPLATE.get())
                .pedestalItems(ModItems.ATLANTIAN_STEEL_INGOT.get())
                .result(ModItems.ICE_CHESTPLATE.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.ICE_HELMET.get())
                .pedestalItems(ModItems.ATLANTIAN_STEEL_INGOT.get())
                .result(ModItems.ICE_HELMET.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.ICE_LEGGINGS.get())
                .pedestalItems(ModItems.ATLANTIAN_STEEL_INGOT.get())
                .result(ModItems.ICE_LEGGINGS.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.ICE_BOOTS.get())
                .pedestalItems(ModItems.ATLANTIAN_STEEL_INGOT.get())
                .result(ModItems.ICE_BOOTS.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("ATLANTIAN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
    }
}
