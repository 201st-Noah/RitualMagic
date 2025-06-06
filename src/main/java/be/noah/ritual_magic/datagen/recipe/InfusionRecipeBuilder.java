package be.noah.ritual_magic.datagen.recipe;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.recipes.ModRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InfusionRecipeBuilder {
    private Ingredient input;
    private List<Ingredient> pedestalItems;
    private Ingredient result;
    private int minLevel = 0, maxLevel = 0, levelGain = 0, manaCost = 0;
    private String manaType = null, minBlockTier = null;
    private boolean preserveMainItem = false;

    public InfusionRecipeBuilder input(Item input) {
        this.input = Ingredient.of(input);
        return this;
    }

    public InfusionRecipeBuilder pedestalItems(Item... items) {
        this.pedestalItems = Arrays.stream(items).map(Ingredient::of).collect(Collectors.toList());
        return this;
    }

    public InfusionRecipeBuilder result(Item result) {
        this.result = Ingredient.of(result);
        return this;
    }

    public InfusionRecipeBuilder minLevel(int v) {
        this.minLevel = v;
        return this;
    }

    public InfusionRecipeBuilder maxLevel(int v) {
        this.maxLevel = v;
        return this;
    }

    public InfusionRecipeBuilder levelGain(int v) {
        this.levelGain = v;
        return this;
    }

    public InfusionRecipeBuilder manaCost(int v) {
        this.manaCost = v;
        return this;
    }

    public InfusionRecipeBuilder manaType(String v) {
        this.manaType = v;
        return this;
    }

    public InfusionRecipeBuilder minBlockTier(String v) {
        this.minBlockTier = v;
        return this;
    }

    public InfusionRecipeBuilder preserveMainItem(boolean v) {
        this.preserveMainItem = v;
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        String path = "infusion/" + result.getItems()[0].getItem();
        if (levelGain > 0)
            path += "_leveling" + levelGain;
        save(consumer, new ResourceLocation(RitualMagic.MODID, path));
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(@NotNull JsonObject json) {
                json.addProperty("type", "ritual_magic:infusion");
                json.add("input", input.toJson());
                json.add("pedestal_items", pedestalItems.stream()
                        .map(Ingredient::toJson)
                        .collect(JsonArray::new, JsonArray::add, JsonArray::addAll));
                json.add("result", result.toJson());
                json.addProperty("min_level", minLevel);
                json.addProperty("max_level", maxLevel);
                json.addProperty("level_gain", levelGain);
                json.addProperty("mana_cost", manaCost);
                if (manaType == null)
                    throw new IllegalStateException("manaType must be set.");
                json.addProperty("mana_type", manaType);
                if (minBlockTier == null)
                    throw new IllegalStateException("minBlockTier must be set.");
                json.addProperty("min_block_tier", minBlockTier);
                json.addProperty("preserve_main_item", preserveMainItem);
            }

            @Override
            @NotNull
            public ResourceLocation getId() {
                return id;
            }

            @Override
            @NotNull
            public net.minecraft.world.item.crafting.RecipeSerializer<?> getType() {
                return ModRecipes.INFUSION_SERIALIZER.get();
            }

            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }
}