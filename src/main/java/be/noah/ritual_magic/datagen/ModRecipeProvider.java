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

        //Armor Material Ingots
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DWARVEN_STEEL_INGOT.get())
                .requires(ModItems.DWARVEN_SCRAP.get(), 4)
                .requires(Items.IRON_INGOT, 4)
                .unlockedBy(getHasName(ModItems.DWARVEN_SCRAP.get()), has(ModItems.DWARVEN_SCRAP.get()))
                .group("dwarven_steel_ingot")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "dwarven_steel_ingot_from_scrap"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DWARVEN_STEEL_INGOT.get(), 9)
                .requires(ModBlocks.DWARVEN_STEEL_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DWARVEN_STEEL_INGOT.get()), has(ModItems.DWARVEN_STEEL_INGOT.get()))
                .group("dwarven_steel_ingot")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "dwarven_steel_ingot_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ATLANTIAN_STEEL_INGOT.get())
                .requires(ModItems.ATLANTIAN_SCRAP.get(), 4)
                .requires(Items.COPPER_INGOT, 4)
                .unlockedBy(getHasName(ModItems.ATLANTIAN_SCRAP.get()), has(ModItems.ATLANTIAN_SCRAP.get()))
                .group("atlantian_steel_ingot")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "atlantian_steel_ingot_from_scrap"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ATLANTIAN_STEEL_INGOT.get(), 9)
                .requires(ModBlocks.ATLANTIAN_STEEL_BLOCK.get())
                .unlockedBy(getHasName(ModItems.ATLANTIAN_STEEL_INGOT.get()), has(ModItems.ATLANTIAN_STEEL_INGOT.get()))
                .group("atlantian_steel_ingot")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "atlantian_steel_ingot_from_block"));
        // TODO change recipe
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PURE_NETHERITE_INGOT.get())
                .requires(Items.NETHERITE_SCRAP, 4)
                .requires(Items.GHAST_TEAR, 4)
                .unlockedBy(getHasName(ModItems.ATLANTIAN_SCRAP.get()), has(ModItems.ATLANTIAN_SCRAP.get()))
                .group("pure_netherite_ingot")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "pure_netherite_ingot_from_scrap"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PURE_NETHERITE_INGOT.get(), 9)
                .requires(ModBlocks.PURE_NETHERITE_BLOCK.get())
                .unlockedBy(getHasName(ModItems.PURE_NETHERITE_INGOT.get()), has(ModItems.PURE_NETHERITE_INGOT.get()))
                .group("pure_netherite_ingot")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "pure_netherite_ingot_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DRAGON_PLATE.get())
                .requires(ModItems.DRAGON_SCALE.get(), 4)
                .requires(Items.CHORUS_FLOWER, 4)
                .unlockedBy(getHasName(ModItems.DRAGON_SCALE.get()), has(ModItems.DRAGON_SCALE.get()))
                .group("dragon_plate")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "dragon_plate_from_scrap"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DRAGON_PLATE.get(), 9)
                .requires(ModBlocks.DRAGON_SCALE_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DRAGON_PLATE.get()), has(ModItems.DRAGON_PLATE.get()))
                .group("dragon_plate")
                .save(pWriter, new ResourceLocation(RitualMagic.MODID, "dragon_plate_from_block"));

        // Armor Plates
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DWARVEN_STEEL_ARMOR_PLATE.get())
                .pattern("AEA")
                .pattern("EWE")
                .pattern("AEA")
                .define('A', ModItems.DWARVEN_STEEL_INGOT.get())
                .define('E', Items.ECHO_SHARD)
                .define('W', ModItems.WARDEN_CORE.get())
                .unlockedBy(getHasName(ModItems.DWARVEN_STEEL_INGOT.get()), has(ModItems.DWARVEN_STEEL_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ATLANTIAN_STEEL_ARMOR_PLATE.get())
                .pattern("IAI")
                .pattern("AHA")
                .pattern("IAI")
                .define('A', ModItems.ATLANTIAN_STEEL_INGOT.get())
                .define('H', Items.HEART_OF_THE_SEA)
                .define('I', ModItems.ICE_SHARD.get())
                .unlockedBy(getHasName(ModItems.ATLANTIAN_STEEL_INGOT.get()), has(ModItems.ATLANTIAN_STEEL_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PURE_NETHERITE_ARMOR_PLATE.get())
                .pattern("PBP")
                .pattern("BNB")
                .pattern("PBP")
                .define('P', ModItems.PURE_NETHERITE_INGOT.get())
                .define('N', Items.NETHER_STAR)
                .define('B', Items.BLAZE_ROD)
                .unlockedBy(getHasName(ModItems.PURE_NETHERITE_INGOT.get()), has(ModItems.PURE_NETHERITE_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DRAGON_SCALE_ARMOR_PLATE.get())
                .pattern("DED")
                .pattern("EHE")
                .pattern("DED")
                .define('E', Items.END_ROD)
                .define('H', Items.DRAGON_HEAD)
                .define('D', ModItems.DRAGON_PLATE.get())
                .unlockedBy(getHasName(ModItems.DRAGON_PLATE.get()), has(ModItems.DRAGON_PLATE.get()))
                .save(pWriter);

        //Armor Material Blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DWARVEN_STEEL_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.DWARVEN_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.DWARVEN_STEEL_INGOT.get()), has(ModItems.DWARVEN_STEEL_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ATLANTIAN_STEEL_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.ATLANTIAN_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ATLANTIAN_STEEL_INGOT.get()), has(ModItems.ATLANTIAN_STEEL_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PURE_NETHERITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.PURE_NETHERITE_INGOT.get())
                .unlockedBy(getHasName(ModItems.PURE_NETHERITE_INGOT.get()), has(ModItems.PURE_NETHERITE_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DRAGON_SCALE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.DRAGON_PLATE.get())
                .unlockedBy(getHasName(ModItems.DRAGON_PLATE.get()), has(ModItems.DRAGON_PLATE.get()))
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
                .pedestalItems(ModItems.PURE_NETHERITE_INGOT.get())
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
        new InfusionRecipeBuilder().input(ModItems.SPEER.get())
                .pedestalItems(ModItems.DRAGON_PLATE.get())
                .result(ModItems.SPEER.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DRACONIC")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        //TODO remove DwarvenArmor at some point for transition to Smeltery
        new InfusionRecipeBuilder().input(ModItems.DWARVEN_STEEL_BOOTS.get())
                .pedestalItems(ModItems.DWARVEN_STEEL_INGOT.get())
                .result(ModItems.DWARVEN_STEEL_BOOTS.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DWARVEN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.DWARVEN_STEEL_LEGGINGS.get())
                .pedestalItems(ModItems.DWARVEN_STEEL_INGOT.get())
                .result(ModItems.DWARVEN_STEEL_LEGGINGS.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DWARVEN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.DWARVEN_STEEL_CHESTPLATE.get())
                .pedestalItems(ModItems.DWARVEN_STEEL_INGOT.get())
                .result(ModItems.DWARVEN_STEEL_CHESTPLATE.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DWARVEN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.DWARVEN_STEEL_HELMET.get())
                .pedestalItems(ModItems.DWARVEN_STEEL_INGOT.get())
                .result(ModItems.DWARVEN_STEEL_HELMET.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DWARVEN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.SOUL_EATER_HELMET.get())
                .pedestalItems(ModItems.PURE_NETHERITE_INGOT.get())
                .result(ModItems.SOUL_EATER_HELMET.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DWARVEN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.SOUL_EATER_CHESTPLATE.get())
                .pedestalItems(ModItems.PURE_NETHERITE_INGOT.get())
                .result(ModItems.SOUL_EATER_CHESTPLATE.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DWARVEN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.SOUL_EATER_LEGGINGS.get())
                .pedestalItems(ModItems.PURE_NETHERITE_INGOT.get())
                .result(ModItems.SOUL_EATER_LEGGINGS.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DWARVEN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
        new InfusionRecipeBuilder().input(ModItems.SOUL_EATER_BOOTS.get())
                .pedestalItems(ModItems.PURE_NETHERITE_INGOT.get())
                .result(ModItems.SOUL_EATER_BOOTS.get())
                .minLevel(0)
                .maxLevel(90)
                .levelGain(10)
                .manaCost(0)
                .manaType("DWARVEN")
                .minBlockTier("BASIC")
                .preserveMainItem(true)
                .save(pWriter);
    }
}
