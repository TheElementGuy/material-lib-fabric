package com.github.theelementguy.tegmatlibf.client.data;

import com.github.theelementguy.tegmatlibf.core.*;
import com.github.theelementguy.tegmatlibf.data.ModelExceptionValues;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibModelProvider extends FabricModelProvider {

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	List<ItemModelGenerators.TrimModelData> trimMaterials = new ArrayList<>(List.of(new ItemModelGenerators.TrimModelData("quartz", 0.1F, Map.of()), new ItemModelGenerators.TrimModelData("iron", 0.2F,Map.of(ArmorMaterials.IRON, "iron_darker")), new ItemModelGenerators.TrimModelData("netherite", 0.3F,Map.of(ArmorMaterials.NETHERITE, "netherite_darker")), new ItemModelGenerators.TrimModelData("redstone", 0.4F,Map.of()), new ItemModelGenerators.TrimModelData("copper", 0.5F,Map.of()), new ItemModelGenerators.TrimModelData("gold", 0.6F,Map.of(ArmorMaterials.GOLD, "gold_darker")), new ItemModelGenerators.TrimModelData("emerald", 0.7F,Map.of()), new ItemModelGenerators.TrimModelData("diamond", 0.8F,Map.of(ArmorMaterials.DIAMOND, "diamond_darker")), new ItemModelGenerators.TrimModelData("lapis", 0.9F,Map.of()), new ItemModelGenerators.TrimModelData("amethyst", 1.0F,Map.of())));

	protected String MOD_ID;

	public TEGMatLibModelProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output);
		this.MATERIALS = materials::getMaterials;
		this.MOD_ID = materials.getModID();
		fillTrimMaterials();
	}

	private void fillTrimMaterials() {
		for (int i = 0; i < MATERIALS.get().size(); i++) {
			trimMaterials.add(new ItemModelGenerators.TrimModelData(MATERIALS.get().get(i).getBaseName(), 1 + 0.5f * (i + 1), Map.of()));
		}
	}

	public void generateArmorTrims(ItemModelGenerators generators, ArmorItem armorItem) {
		if (armorItem.getType().hasTrims()) {
			ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(armorItem);
			ResourceLocation resourceLocation2 = TextureMapping.getItemTexture(armorItem);
			ResourceLocation resourceLocation3 = TextureMapping.getItemTexture(armorItem, "_overlay");
			if (armorItem.getMaterial().is(ArmorMaterials.LEATHER)) {
				ModelTemplates.TWO_LAYERED_ITEM.create(resourceLocation, TextureMapping.layered(resourceLocation2, resourceLocation3), generators.output, (resourceLocationx, map) -> generators.generateBaseArmorTrimTemplate(resourceLocationx, map, armorItem.getMaterial()));
			} else {
				ModelTemplates.FLAT_ITEM.create(resourceLocation, TextureMapping.layer0(resourceLocation2), generators.output, (resourceLocationx, map) -> generators.generateBaseArmorTrimTemplate(resourceLocationx, map, armorItem.getMaterial()));
			}

			for(ItemModelGenerators.TrimModelData trimModelData : trimMaterials) {
				String string = trimModelData.name(armorItem.getMaterial());
				ResourceLocation resourceLocation4 = generators.getItemModelForTrimMaterial(resourceLocation, string);
				String var10000 = armorItem.getType().getName();
				String string2 = var10000 + "_trim_" + string;
				ResourceLocation resourceLocation5 = ResourceLocation.withDefaultNamespace(string2).withPrefix("trims/items/");
				if (armorItem.getMaterial().is(ArmorMaterials.LEATHER)) {
					generators.generateLayeredItem(resourceLocation4, resourceLocation2, resourceLocation3, resourceLocation5);
				} else {
					generators.generateLayeredItem(resourceLocation4, resourceLocation2, resourceLocation5);
				}
			}

		}
	}

	public void generateArmorTrims(ItemModelGenerators generators, Item armorItem) {
		if (armorItem instanceof ArmorItem actualArmorItem) {
			generateArmorTrims(generators, actualArmorItem);
		}
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModels) {
		for (MaterialConfiguration config : MATERIALS.get()) {

			blockModels.createTrivialBlock(config.getBaseBlock(), translate(config.applyException(config.getBaseName() + "_block", ModelExceptionValues.CUBE)));

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) config;

					blockModels.createTrivialCube(ironMatConfig.getRawBlock());
					blockModels.createTrivialCube(ironMatConfig.getOre());
					blockModels.createTrivialCube(ironMatConfig.getDeepslateOre());
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
					blockModels.createTrivialCube(diamondMatConfig.getOre());
					blockModels.createTrivialCube(diamondMatConfig.getDeepslateOre());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) config;

					blockModels.createTrivialCube(cubicMatConfig.getRawBlock());
					blockModels.createTrivialCube(cubicMatConfig.getOre());
					blockModels.createTrivialCube(cubicMatConfig.getDeepslateOre());
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
					blockModels.createTrivialCube(netherDiamondMatConfig.getNetherOre());
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
					blockModels.createTrivialCube(endDiamondMatConfig.getEndOre());
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration ironMatConfig = (EndIronTypeMaterialConfiguration) config;

					blockModels.createTrivialCube(ironMatConfig.getRawBlock());
					blockModels.createTrivialCube(ironMatConfig.getEndOre());
				}
				case SAND_DIAMOND -> {
					SandDiamondTypeMaterialConfiguration sandDiamondMatConfig = (SandDiamondTypeMaterialConfiguration) config;
					blockModels.createTrivialCube(sandDiamondMatConfig.getSandOre());
					blockModels.createTrivialCube(sandDiamondMatConfig.getGravelOre());
				}
			}

		}
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModels) {
		for (MaterialConfiguration config : MATERIALS.get()) {

			itemModels.generateFlatItem(config.getBaseItem(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(config.getSword(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateFlatItem(config.getAxe(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateFlatItem(config.getPickaxe(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateFlatItem(config.getShovel(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateFlatItem(config.getHoe(), ModelTemplates.FLAT_HANDHELD_ITEM);

			if (config.getHorseArmor().isUsing()) {
				itemModels.generateFlatItem(config.getHorseArmor().get().get().asItem(), ModelTemplates.FLAT_ITEM);
			}
			
			generateArmorTrims(itemModels, config.getHelmet());
			generateArmorTrims(itemModels, config.getChestplate());
			generateArmorTrims(itemModels, config.getLeggings());
			generateArmorTrims(itemModels, config.getBoots());

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) config;

					itemModels.generateFlatItem(ironMatConfig.getRawItem(), ModelTemplates.FLAT_ITEM);
					itemModels.generateFlatItem(ironMatConfig.getNugget(), ModelTemplates.FLAT_ITEM);
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) config;

					itemModels.generateFlatItem(cubicMatConfig.getRawItem(), ModelTemplates.FLAT_ITEM);
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration ironMatConfig = (EndIronTypeMaterialConfiguration) config;

					itemModels.generateFlatItem(ironMatConfig.getRawItem(), ModelTemplates.FLAT_ITEM);
					itemModels.generateFlatItem(ironMatConfig.getNugget(), ModelTemplates.FLAT_ITEM);
				}
				case SAND_DIAMOND -> {
					SandDiamondTypeMaterialConfiguration sandDiamondMatConfig = (SandDiamondTypeMaterialConfiguration) config;
				}
			}

		}

		generateArmorTrims(itemModels, Items.LEATHER_HELMET);
		generateArmorTrims(itemModels, Items.LEATHER_CHESTPLATE);
		generateArmorTrims(itemModels, Items.LEATHER_LEGGINGS);
		generateArmorTrims(itemModels, Items.LEATHER_BOOTS);


		generateArmorTrims(itemModels, Items.CHAINMAIL_HELMET);
		generateArmorTrims(itemModels, Items.CHAINMAIL_CHESTPLATE);
		generateArmorTrims(itemModels, Items.CHAINMAIL_LEGGINGS);
		generateArmorTrims(itemModels, Items.CHAINMAIL_BOOTS);


		generateArmorTrims(itemModels, Items.IRON_HELMET);
		generateArmorTrims(itemModels, Items.IRON_CHESTPLATE);
		generateArmorTrims(itemModels, Items.IRON_LEGGINGS);
		generateArmorTrims(itemModels, Items.IRON_BOOTS);


		generateArmorTrims(itemModels, Items.DIAMOND_HELMET);
		generateArmorTrims(itemModels, Items.DIAMOND_CHESTPLATE);
		generateArmorTrims(itemModels, Items.DIAMOND_LEGGINGS);
		generateArmorTrims(itemModels, Items.DIAMOND_BOOTS);
		generateArmorTrims(itemModels, Items.GOLDEN_HELMET);
		generateArmorTrims(itemModels, Items.GOLDEN_CHESTPLATE);
		generateArmorTrims(itemModels, Items.GOLDEN_LEGGINGS);
		generateArmorTrims(itemModels, Items.GOLDEN_BOOTS);
		generateArmorTrims(itemModels, Items.NETHERITE_HELMET);
		generateArmorTrims(itemModels, Items.NETHERITE_CHESTPLATE);
		generateArmorTrims(itemModels, Items.NETHERITE_LEGGINGS);
		generateArmorTrims(itemModels, Items.NETHERITE_BOOTS);

		generateArmorTrims(itemModels, Items.TURTLE_HELMET);
	}

	private TexturedModel.Provider translate(ModelExceptionValues value) {
		return switch (value) {
			case CUBE -> TexturedModel.CUBE;
			case CUBE_TOP -> TexturedModel.CUBE_TOP;
			case CUBE_TOP_BOTTOM -> TexturedModel.CUBE_TOP_BOTTOM;
			case COLUMN -> TexturedModel.COLUMN;
		};
	}
}
