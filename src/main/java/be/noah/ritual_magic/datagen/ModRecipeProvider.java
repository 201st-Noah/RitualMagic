package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.ModBlocks;
import be.noah.ritual_magic.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, List.of(ModBlocks.DWARVEN_DEBRIS.get()), RecipeCategory.MISC ,ModItems.DWARVEN_SCRAP.get(), 0.25f, 200,"dwarven_scrap");
        oreBlasting(pWriter, List.of(ModBlocks.DWARVEN_DEBRIS.get()), RecipeCategory.MISC ,ModItems.DWARVEN_SCRAP.get(), 0.25f, 100,"dwarven_scrap");

        oreSmelting(pWriter, List.of(ModBlocks.ATLANTIAN_DEBRIS.get()), RecipeCategory.MISC ,ModItems.ATLANTIAN_SCRAP.get(), 0.25f, 200,"atlantian_scrap");
        oreBlasting(pWriter, List.of(ModBlocks.ATLANTIAN_DEBRIS.get()), RecipeCategory.MISC ,ModItems.ATLANTIAN_SCRAP.get(), 0.25f, 100,"atlantian_scrap");

        oreSmelting(pWriter, List.of(ModBlocks.PETRIFIED_DRAGON_SCALE.get()), RecipeCategory.MISC ,ModItems.DRAGON_SCALE.get(), 0.25f, 200,"dragon_scale");
        oreBlasting(pWriter, List.of(ModBlocks.PETRIFIED_DRAGON_SCALE.get()), RecipeCategory.MISC ,ModItems.DRAGON_SCALE.get(), 0.25f, 100,"dragon_scale");

    }
    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, RitualMagic.MODID + ":" +getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
