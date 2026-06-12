package com.github.theelementguy.tegmatlibf.data;

import com.github.theelementguy.tegmatlibf.core.*;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static net.minecraft.data.recipes.RecipeProvider.getHasName;

public class TEGMatLibRecipeProvider extends FabricRecipeProvider {

	private Supplier<List<MaterialConfiguration>> MATERIALS;

	private final String MOD_ID;

	public TEGMatLibRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output, provider);
		MATERIALS = materials::getMaterials;
		MOD_ID = materials.getModID();
	}

	@Override
	protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {

		return new RecipeProvider(registries, output) {
			@Override
			public void buildRecipes() {
				for (MaterialConfiguration config : MATERIALS.get()) {

					blockRecipe(config.getBaseItem(), config.getBaseBlock().asItem());

					swordRecipe(config.getSword(), config.getBaseItem(), "has_" + config.getBaseName());
					axeRecipe(config.getAxe(), config.getBaseItem(), "has_" + config.getBaseName());
					pickaxeRecipe(config.getPickaxe(), config.getBaseItem(), "has_" + config.getBaseName());
					shovelRecipe(config.getShovel(), config.getBaseItem(), "has_" + config.getBaseName());
					hoeRecipe(config.getHoe(), config.getBaseItem(), "has_" + config.getBaseName());
					spearRecipe(config.getSpear(), config.getBaseItem(), "has_" + config.getBaseName());

					helmetRecipe(config.getHelmet(), config.getBaseItem(), "has_" + config.getBaseName());
					chestplateRecipe(config.getChestplate(), config.getBaseItem(), "has_" + config.getBaseName());
					leggingsRecipe(config.getLeggings(), config.getBaseItem(), "has_" + config.getBaseName());
					bootsRecipe(config.getBoots(), config.getBaseItem(), "has_" + config.getBaseName());

					switch (config.getType()) {
						case IRON -> {
							IronTypeMaterialConfiguration ironConfig = (IronTypeMaterialConfiguration) config;
							blockRecipe(ironConfig.getRawItem(), ironConfig.getRawBlock().asItem());
							nuggetRecipe(ironConfig.getBaseItem(), ironConfig.getNugget());
							allOreSmelting(ironConfig.getBaseItem(), List.of(ironConfig.getOre(), ironConfig.getDeepslateOre(), ironConfig.getRawItem()), ironConfig.getSmeltingExperience(), ironConfig.getBaseName());
						}
						case DIAMOND -> {
							DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
							allOreSmelting(diamondMatConfig.getBaseItem(), List.of(diamondMatConfig.getOre(), diamondMatConfig.getDeepslateOre()), diamondMatConfig.getSmeltingExperience(), diamondMatConfig.getBaseName());
						}
						case CUBIC_ZIRCONIA -> {
							CubicZirconiaTypeMaterialConfiguration cubicConfig = (CubicZirconiaTypeMaterialConfiguration) config;
							blockRecipe(cubicConfig.getRawItem(), cubicConfig.getRawBlock().asItem());
							allOreSmelting(cubicConfig.getBaseItem(), List.of(cubicConfig.getOre(), cubicConfig.getDeepslateOre(), cubicConfig.getRawItem()), cubicConfig.getSmeltingExperience(), cubicConfig.getBaseName());
						}
						case NETHER_DIAMOND -> {
							NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
							allOreSmelting(netherDiamondMatConfig.getBaseItem(), List.of(netherDiamondMatConfig.getNetherOre()), netherDiamondMatConfig.getSmeltingExperience(), netherDiamondMatConfig.getBaseName());
						}
						case END_DIAMOND -> {
							EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
							allOreSmelting(endDiamondMatConfig.getBaseItem(), List.of(endDiamondMatConfig.getEndOre()), endDiamondMatConfig.getSmeltingExperience(), endDiamondMatConfig.getBaseName());
						}
						case END_IRON -> {
							EndIronTypeMaterialConfiguration endIronMatConfig = (EndIronTypeMaterialConfiguration) config;
							allOreSmelting(endIronMatConfig.getBaseItem(), List.of(endIronMatConfig.getEndOre()), endIronMatConfig.getSmeltingExperience(), endIronMatConfig.getBaseName());
						}
						case SAND_DIAMOND -> {
							SandDiamondTypeMaterialConfiguration sandDiamondMatConfig = (SandDiamondTypeMaterialConfiguration) config;
							allOreSmelting(sandDiamondMatConfig.getBaseItem(), List.of(sandDiamondMatConfig.getSandOre(), sandDiamondMatConfig.getGravelOre()), sandDiamondMatConfig.getSmeltingExperience(), sandDiamondMatConfig.getBaseName());
						}
					}

				}
			}

			private void oreSmelting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
				oreCooking(SmeltingRecipe::new, pIngredients, pCategory, CookingBookCategory.BLOCKS, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
			}

			private void oreBlasting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
				oreCooking(BlastingRecipe::new, pIngredients, pCategory, CookingBookCategory.BLOCKS, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
			}

			private void swordRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.COMBAT, result).pattern(" I ").pattern(" I ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void axeRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.TOOLS, result).pattern("II ").pattern("IS ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void pickaxeRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.TOOLS, result).pattern("III").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void shovelRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.TOOLS, result).pattern(" I ").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void hoeRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.TOOLS, result).pattern("II ").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void spearRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.COMBAT, result).pattern("  I").pattern(" S ").pattern("S  ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void helmetRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.COMBAT, result).pattern("III").pattern("I I").pattern("   ").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void chestplateRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.COMBAT, result).pattern("I I").pattern("III").pattern("III").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void leggingsRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.COMBAT, result).pattern("III").pattern("I I").pattern("I I").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void bootsRecipe(Item result, Item ingredient, String advancementName) {
				shaped(RecipeCategory.COMBAT, result).pattern("   ").pattern("I I").pattern("I I").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(output);
			}

			private void blockRecipe(Item material, Item block) {
				shapeless(RecipeCategory.MISC, material, 9).requires(block).unlockedBy("has_" + getItemName(material), has(block)).save(output);

				shapeless(RecipeCategory.MISC, block).requires(material, 9).unlockedBy("has_" + getItemName(material) + "_block", has(block)).save(output);
			}

			private void nuggetRecipe(Item material, Item nugget) {
				shapeless(RecipeCategory.MISC, material, 9).requires(nugget).unlockedBy("has_" + getItemName(material), has(nugget)).save(output, MOD_ID + ":" + getItemName(material) + "_ingot_from_nugget");

				shapeless(RecipeCategory.MISC, nugget).requires(material, 9).unlockedBy("has_" + getItemName(material) + "_nugget", has(nugget)).save(output);
			}

			private void allOreSmelting(Item material, List<ItemLike> smeltables, float experience, String group) {
				oreSmelting(smeltables, RecipeCategory.MISC, material, experience, 200, group);
				oreBlasting(smeltables, RecipeCategory.MISC, material, experience, 100, group);
			}
		};

	}



	@Override
	public String getName() {
		return MOD_ID + " recipes";
	}
}
