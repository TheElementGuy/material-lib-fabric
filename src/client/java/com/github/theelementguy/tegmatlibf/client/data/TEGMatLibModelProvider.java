package com.github.theelementguy.tegmatlibf.client.data;

import com.github.theelementguy.tegmatlibf.core.*;
import com.github.theelementguy.tegmatlibf.data.ModelExceptionValues;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibModelProvider extends FabricModelProvider {

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	public static List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIAL_MODELS_WITH_MODDED = new ArrayList<>(List.of(new ItemModelGenerators.TrimMaterialData("quartz", TrimMaterials.QUARTZ, Map.of()), new ItemModelGenerators.TrimMaterialData("iron", TrimMaterials.IRON, Map.of(EquipmentAssets.IRON, "iron_darker")), new ItemModelGenerators.TrimMaterialData("netherite", TrimMaterials.NETHERITE, Map.of(EquipmentAssets.NETHERITE, "netherite_darker")), new ItemModelGenerators.TrimMaterialData("redstone", TrimMaterials.REDSTONE, Map.of()), new ItemModelGenerators.TrimMaterialData("copper", TrimMaterials.COPPER, Map.of()), new ItemModelGenerators.TrimMaterialData("gold", TrimMaterials.GOLD, Map.of(EquipmentAssets.GOLD, "gold_darker")), new ItemModelGenerators.TrimMaterialData("emerald", TrimMaterials.EMERALD, Map.of()), new ItemModelGenerators.TrimMaterialData("diamond", TrimMaterials.DIAMOND, Map.of(EquipmentAssets.DIAMOND, "diamond_darker")), new ItemModelGenerators.TrimMaterialData("lapis", TrimMaterials.LAPIS, Map.of()), new ItemModelGenerators.TrimMaterialData("amethyst", TrimMaterials.AMETHYST, Map.of()), new ItemModelGenerators.TrimMaterialData("resin", TrimMaterials.RESIN, Map.of())));

	protected String MOD_ID;

	public TEGMatLibModelProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output);
		this.MATERIALS = materials::getMaterials;
		this.MOD_ID = materials.getModID();
		ArrayList<ItemModelGenerators.TrimMaterialData> trimMaterialsToAdd = new ArrayList<>();
		for (MaterialConfiguration config : MATERIALS.get()) {
			MaterialConfiguration concrete;
			trimMaterialsToAdd.add(new ItemModelGenerators.TrimMaterialData(config.getBaseName(), config.getTrimMaterial(), Map.of()));
		}
		TRIM_MATERIAL_MODELS_WITH_MODDED.addAll(trimMaterialsToAdd);
	}

	public void generateTrimmableItemWithModdedMaterials(ItemModelGenerators gens, Item item, ResourceKey<EquipmentAsset> key, boolean dyeable) {
		if (item.getDescriptionId().contains("helmet")) {
			generateTrimmableItemWithModdedMaterials(item, key, "helmet", dyeable, gens);
		} else if (item.getDescriptionId().contains("chestplate")) {
			generateTrimmableItemWithModdedMaterials(item, key, "chestplate", dyeable, gens);
		} else if (item.getDescriptionId().contains("leggings")) {
			generateTrimmableItemWithModdedMaterials(item, key, "leggings", dyeable, gens);
		} else if (item.getDescriptionId().contains("boots")) {
			generateTrimmableItemWithModdedMaterials(item, key, "boots", dyeable, gens);
		} else {
			throw new IllegalArgumentException("item is not of type helmet, chestplate, leggings, or boots");
		}
	}

	public void generateTrimmableItemWithModdedMaterials(Item item, ResourceKey<EquipmentAsset> key, String name, boolean dyeable, ItemModelGenerators gens) {
		ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(item);
		ResourceLocation resourcelocation1 = TextureMapping.getItemTexture(item);
		ResourceLocation resourcelocation2 = TextureMapping.getItemTexture(item, "_overlay");
		List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> list = new ArrayList<>(TRIM_MATERIAL_MODELS_WITH_MODDED.size());

		for (ItemModelGenerators.TrimMaterialData itemmodelgenerators$trimmaterialdata : TRIM_MATERIAL_MODELS_WITH_MODDED) {
			ResourceLocation resourcelocation3 = resourcelocation.withSuffix("_" + itemmodelgenerators$trimmaterialdata.name() + "_trim");
			ResourceLocation resourcelocation4 = ResourceLocation.fromNamespaceAndPath(
					"minecraft", "trims/items/" + name + "_trim_" + itemmodelgenerators$trimmaterialdata.textureName(key)
			);
			ItemModel.Unbaked itemmodel$unbaked;
			if (dyeable) {
				gens.generateLayeredItem(resourcelocation3, resourcelocation1, resourcelocation2, resourcelocation4);
				itemmodel$unbaked = ItemModelUtils.tintedModel(resourcelocation3, new Dye(-6265536));
			} else {
				gens.generateLayeredItem(resourcelocation3, resourcelocation1, resourcelocation4);
				itemmodel$unbaked = ItemModelUtils.plainModel(resourcelocation3);
			}

			list.add(ItemModelUtils.when(itemmodelgenerators$trimmaterialdata.materialKey(), itemmodel$unbaked));

			System.out.println(resourcelocation3);
		}

		ItemModel.Unbaked itemmodel$unbaked1;
		if (dyeable) {
			ModelTemplates.TWO_LAYERED_ITEM.create(resourcelocation, TextureMapping.layered(resourcelocation1, resourcelocation2), gens.modelOutput);
			itemmodel$unbaked1 = ItemModelUtils.tintedModel(resourcelocation, new Dye(-6265536));
		} else {
			ModelTemplates.FLAT_ITEM.create(resourcelocation, TextureMapping.layer0(resourcelocation1), gens.modelOutput);
			itemmodel$unbaked1 = ItemModelUtils.plainModel(resourcelocation);
		}

		gens.itemModelOutput.accept(item, ItemModelUtils.select(new TrimMaterialProperty(), itemmodel$unbaked1, list));
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

			generateTrimmableItemWithModdedMaterials(itemModels, config.getHelmet(), config.getEquipmentAsset(), false);
			generateTrimmableItemWithModdedMaterials(itemModels, config.getChestplate(), config.getEquipmentAsset(), false);
			generateTrimmableItemWithModdedMaterials(itemModels, config.getLeggings(), config.getEquipmentAsset(), false);
			generateTrimmableItemWithModdedMaterials(itemModels, config.getBoots(), config.getEquipmentAsset(), false);

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

		generateTrimmableItemWithModdedMaterials(itemModels, Items.LEATHER_HELMET, EquipmentAssets.LEATHER, true);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.LEATHER_CHESTPLATE, EquipmentAssets.LEATHER, true);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.LEATHER_LEGGINGS, EquipmentAssets.LEATHER, true);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.LEATHER_BOOTS, EquipmentAssets.LEATHER, true);


		generateTrimmableItemWithModdedMaterials(itemModels, Items.CHAINMAIL_HELMET, EquipmentAssets.CHAINMAIL, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.CHAINMAIL_CHESTPLATE, EquipmentAssets.CHAINMAIL, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.CHAINMAIL_LEGGINGS, EquipmentAssets.CHAINMAIL, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.CHAINMAIL_BOOTS, EquipmentAssets.CHAINMAIL, false);


		generateTrimmableItemWithModdedMaterials(itemModels, Items.IRON_HELMET, EquipmentAssets.IRON, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.IRON_CHESTPLATE, EquipmentAssets.IRON, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.IRON_LEGGINGS, EquipmentAssets.IRON, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.IRON_BOOTS, EquipmentAssets.IRON, false);


		generateTrimmableItemWithModdedMaterials(itemModels, Items.DIAMOND_HELMET, EquipmentAssets.DIAMOND, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.DIAMOND_CHESTPLATE, EquipmentAssets.DIAMOND, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.DIAMOND_LEGGINGS, EquipmentAssets.DIAMOND, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.DIAMOND_BOOTS, EquipmentAssets.DIAMOND, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.GOLDEN_HELMET, EquipmentAssets.GOLD, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.GOLDEN_CHESTPLATE, EquipmentAssets.GOLD, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.GOLDEN_LEGGINGS, EquipmentAssets.GOLD, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.GOLDEN_BOOTS, EquipmentAssets.GOLD, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.NETHERITE_HELMET, EquipmentAssets.NETHERITE, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.NETHERITE_CHESTPLATE, EquipmentAssets.NETHERITE, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.NETHERITE_LEGGINGS, EquipmentAssets.NETHERITE, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.NETHERITE_BOOTS, EquipmentAssets.NETHERITE, false);

		generateTrimmableItemWithModdedMaterials(itemModels, Items.TURTLE_HELMET, EquipmentAssets.TURTLE_SCUTE, false);
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
