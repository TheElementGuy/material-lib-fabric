package com.github.theelementguy.tegmatlibf.data;

import com.github.theelementguy.tegmatlibf.core.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static net.minecraft.data.recipes.RecipeProvider.getHasName;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;

public class TEGMatLibRecipeProvider extends FabricRecipeProvider {

	private Supplier<List<MaterialConfiguration>> MATERIALS;

	private final String MOD_ID;

	public TEGMatLibRecipeProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(dataOutput, provider);
		MATERIALS = materials::getMaterials;
		MOD_ID = materials.getModID();
	}

	@Override
	public void buildRecipes(RecipeOutput recipeOutput) {

		for (MaterialConfiguration config : MATERIALS.get()) {

			blockRecipe(config.getBaseItem(), config.getBaseBlock().asItem(), recipeOutput);

			swordRecipe(config.getSword(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);
			axeRecipe(config.getAxe(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);
			pickaxeRecipe(config.getPickaxe(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);
			shovelRecipe(config.getShovel(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);
			hoeRecipe(config.getHoe(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);

			helmetRecipe(config.getHelmet(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);
			chestplateRecipe(config.getChestplate(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);
			leggingsRecipe(config.getLeggings(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);
			bootsRecipe(config.getBoots(), config.getBaseItem(), "has_" + config.getBaseName(), recipeOutput);

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironConfig = (IronTypeMaterialConfiguration) config;
					blockRecipe(ironConfig.getRawItem(), ironConfig.getRawBlock().asItem(), recipeOutput);
					nuggetRecipe(ironConfig.getBaseItem(), ironConfig.getNugget(), List.of(ironConfig.getSword(), ironConfig.getAxe(), ironConfig.getPickaxe(), ironConfig.getShovel(), ironConfig.getHoe(), ironConfig.getHelmet(), ironConfig.getChestplate(), ironConfig.getLeggings(), ironConfig.getBoots(), ironConfig.getHorseArmor().get().orElse(null)), recipeOutput);
					allOreSmelting(ironConfig.getBaseItem(), List.of(ironConfig.getOre(), ironConfig.getDeepslateOre(), ironConfig.getRawItem()), ironConfig.getSmeltingExperience(), ironConfig.getBaseName(), recipeOutput);
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
					allOreSmelting(diamondMatConfig.getBaseItem(), List.of(diamondMatConfig.getOre(), diamondMatConfig.getDeepslateOre()), diamondMatConfig.getSmeltingExperience(), diamondMatConfig.getBaseName(), recipeOutput);
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicConfig = (CubicZirconiaTypeMaterialConfiguration) config;
					blockRecipe(cubicConfig.getRawItem(), cubicConfig.getRawBlock().asItem(), recipeOutput);
					allOreSmelting(cubicConfig.getBaseItem(), List.of(cubicConfig.getOre(), cubicConfig.getDeepslateOre(), cubicConfig.getRawItem()), cubicConfig.getSmeltingExperience(), cubicConfig.getBaseName(), recipeOutput);
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
					allOreSmelting(netherDiamondMatConfig.getBaseItem(), List.of(netherDiamondMatConfig.getNetherOre()), netherDiamondMatConfig.getSmeltingExperience(), netherDiamondMatConfig.getBaseName(), recipeOutput);
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
					allOreSmelting(endDiamondMatConfig.getBaseItem(), List.of(endDiamondMatConfig.getEndOre()), endDiamondMatConfig.getSmeltingExperience(), endDiamondMatConfig.getBaseName(), recipeOutput);
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration endIronMatConfig = (EndIronTypeMaterialConfiguration) config;
					allOreSmelting(endIronMatConfig.getBaseItem(), List.of(endIronMatConfig.getEndOre()), endIronMatConfig.getSmeltingExperience(), endIronMatConfig.getBaseName(), recipeOutput);
					nuggetRecipe(endIronMatConfig.getBaseItem(), endIronMatConfig.getNugget(), List.of(endIronMatConfig.getSword(), endIronMatConfig.getAxe(), endIronMatConfig.getPickaxe(), endIronMatConfig.getShovel(), endIronMatConfig.getHoe(), endIronMatConfig.getHelmet(), endIronMatConfig.getChestplate(), endIronMatConfig.getLeggings(), endIronMatConfig.getBoots(), endIronMatConfig.getHorseArmor().get().orElse(null)), recipeOutput);
				}
				case SAND_DIAMOND -> {
					SandDiamondTypeMaterialConfiguration sandDiamondMatConfig = (SandDiamondTypeMaterialConfiguration) config;
					allOreSmelting(sandDiamondMatConfig.getBaseItem(), List.of(sandDiamondMatConfig.getSandOre(), sandDiamondMatConfig.getGravelOre()), sandDiamondMatConfig.getSmeltingExperience(), sandDiamondMatConfig.getBaseName(), recipeOutput);
				}
			}

		}

	}

	protected void oreSmelting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup, RecipeOutput recipeOutput) {
		oreCooking(RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting", recipeOutput);
	}

	protected void oreBlasting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, RecipeOutput recipeOutput) {
		oreCooking(RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting", recipeOutput);
	}

	protected <T extends AbstractCookingRecipe> void oreCooking(RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName, RecipeOutput recipeOutput) {
		for(ItemLike itemlike : pIngredients) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(recipeOutput, MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
		}
	}

	protected void swordRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.COMBAT, result).pattern(" I ").pattern(" I ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void axeRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.TOOLS, result).pattern("II ").pattern("IS ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void pickaxeRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.TOOLS, result).pattern("III").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void shovelRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.TOOLS, result).pattern(" I ").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void hoeRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.TOOLS, result).pattern("II ").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void spearRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.COMBAT, result).pattern("  I").pattern(" S ").pattern("S  ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void helmetRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.COMBAT, result).pattern("III").pattern("I I").pattern("   ").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void chestplateRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.COMBAT, result).pattern("I I").pattern("III").pattern("III").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void leggingsRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.COMBAT, result).pattern("III").pattern("I I").pattern("I I").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void bootsRecipe(Item result, Item ingredient, String advancementName, RecipeOutput recipeOutput) {
		shaped(RecipeCategory.COMBAT, result).pattern("   ").pattern("I I").pattern("I I").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(recipeOutput);
	}

	protected void blockRecipe(Item material, Item block, RecipeOutput recipeOutput) {
		shapeless(RecipeCategory.MISC, material, 9).requires(block).unlockedBy("has_" + getItemName(material), has(block)).save(recipeOutput);

		shapeless(RecipeCategory.MISC, block).requires(material, 9).unlockedBy("has_" + getItemName(material) + "_block", has(block)).save(recipeOutput);
	}

	protected void nuggetRecipe(Item material, Item nugget, List<@Nullable ItemLike> equipment, RecipeOutput recipeOutput) {
		shapeless(RecipeCategory.MISC, nugget, 9).requires(material).unlockedBy("has_" + getItemName(material), has(nugget)).save(recipeOutput);

		shapeless(RecipeCategory.MISC, material).requires(nugget, 9).unlockedBy("has_" + getItemName(material) + "_nugget", has(nugget)).save(recipeOutput, MOD_ID + ":" + getItemName(material) + "_from_nugget");

		for (ItemLike i : equipment) {
			if (i != null) {
				SimpleCookingRecipeBuilder.smelting(Ingredient.of(i), RecipeCategory.MISC, nugget, 0.1f, 200).unlockedBy("has_" + getItemName(material), has(material)).save(recipeOutput, MOD_ID + ":" + getItemName(nugget) + "_from_smelting_" + getItemName(i));
				SimpleCookingRecipeBuilder.blasting(Ingredient.of(i), RecipeCategory.MISC, nugget, 0.1f, 100).unlockedBy("has_" + getItemName(material), has(material)).save(recipeOutput, MOD_ID + ":" + getItemName(nugget) + "_from_blasting_" + getItemName(i));
			}
		}
	}

	protected void allOreSmelting(Item material, List<ItemLike> smeltables, float experience, String group, RecipeOutput recipeOutput) {
		oreSmelting(smeltables, RecipeCategory.MISC, material, experience, 200, group, recipeOutput);
		oreBlasting(smeltables, RecipeCategory.MISC, material, experience, 100, group, recipeOutput);
	}
}
