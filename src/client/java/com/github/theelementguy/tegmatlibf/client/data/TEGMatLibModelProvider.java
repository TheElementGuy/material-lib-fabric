package com.github.theelementguy.tegmatlibf.client.data;

import com.github.theelementguy.tegmatlibf.core.*;
import com.github.theelementguy.tegmatlibf.data.ModelExceptionValues;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static net.minecraft.client.data.models.ItemModelGenerators.*;

public class TEGMatLibModelProvider extends FabricModelProvider {

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	public final List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIAL_MODELS = new ArrayList<>(List.of(new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.QUARTZ, TrimMaterials.QUARTZ), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.IRON, TrimMaterials.IRON), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.NETHERITE, TrimMaterials.NETHERITE), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.REDSTONE, TrimMaterials.REDSTONE), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.COPPER, TrimMaterials.COPPER), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.GOLD, TrimMaterials.GOLD), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.EMERALD, TrimMaterials.EMERALD), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.DIAMOND, TrimMaterials.DIAMOND), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.LAPIS, TrimMaterials.LAPIS), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.AMETHYST, TrimMaterials.AMETHYST), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.RESIN, TrimMaterials.RESIN)));

	protected String MOD_ID;

	public TEGMatLibModelProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output);
		this.MATERIALS = materials::getMaterials;
		this.MOD_ID = materials.getModID();
		ArrayList<ItemModelGenerators.TrimMaterialData> trimMaterialsToAdd = new ArrayList<>();
		for (MaterialConfiguration config : MATERIALS.get()) {
			MaterialConfiguration concrete;
			trimMaterialsToAdd.add(new ItemModelGenerators.TrimMaterialData(config.getMaterialAssetGroup(), config.getTrimMaterial()));
		}
		TRIM_MATERIAL_MODELS.addAll(trimMaterialsToAdd);
	}

	public void generateTrimmableItemWithModdedMaterials(ItemModelGenerators geners, Item armor, ResourceKey<EquipmentAsset> equipmentAssetId, boolean hasDyedLayer) {
		String path = armor.getDescriptionId();
		Identifier slotTrimPrefix = (path.contains("helmet")) ? TRIM_PREFIX_HELMET : ((path.contains("chestplate")) ? TRIM_PREFIX_CHESTPLATE : ((path.contains("leggings")) ? TRIM_PREFIX_LEGGINGS : ItemModelGenerators.TRIM_PREFIX_BOOTS));
		Identifier modelLocation = ModelLocationUtils.getModelLocation(armor);
		Material itemTexture = TextureMapping.getItemTexture(armor);
		Material overlayTexture = TextureMapping.getItemTexture(armor, "_overlay");
		List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> cases = new ArrayList<>(TRIM_MATERIAL_MODELS.size());

		for (ItemModelGenerators.TrimMaterialData material : TRIM_MATERIAL_MODELS) {
			Identifier trimModelLocation = modelLocation.withSuffix("_" + material.assets().base().suffix() + "_trim");
			Material trimOverlayTexture = new Material(slotTrimPrefix.withSuffix("_" + material.assets().assetId(equipmentAssetId).suffix()));
			ItemModel.Unbaked trimModel;
			if (hasDyedLayer) {
				geners.generateLayeredItem(trimModelLocation, itemTexture, overlayTexture, trimOverlayTexture);
				trimModel = ItemModelUtils.tintedModel(trimModelLocation, new Dye(-6265536));
			} else {
				geners.generateLayeredItem(trimModelLocation, itemTexture, trimOverlayTexture);
				trimModel = ItemModelUtils.plainModel(trimModelLocation);
			}

			cases.add(ItemModelUtils.when(material.materialKey(), trimModel));
		}

		ItemModel.Unbaked untrimmedModel;
		if (hasDyedLayer) {
			ModelTemplates.TWO_LAYERED_ITEM.create(modelLocation, TextureMapping.layered(itemTexture, overlayTexture), geners.modelOutput);
			untrimmedModel = ItemModelUtils.tintedModel(modelLocation, new Dye(-6265536));
		} else {
			ModelTemplates.FLAT_ITEM.create(modelLocation, TextureMapping.layer0(itemTexture), geners.modelOutput);
			untrimmedModel = ItemModelUtils.plainModel(modelLocation);
		}

		geners.itemModelOutput.accept(armor, ItemModelUtils.select(new TrimMaterialProperty(), untrimmedModel, cases));
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
			itemModels.generateSpear(config.getSpear());

			if (config.getHorseArmor().isUsing()) {
				itemModels.generateFlatItem(config.getHorseArmor().get().get().asItem(), ModelTemplates.FLAT_ITEM);
			}
			if (config.getNautilusArmor().isUsing()) {
				itemModels.generateFlatItem(config.getNautilusArmor().get().get().asItem(), ModelTemplates.FLAT_ITEM);
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

		generateTrimmableItemWithModdedMaterials(itemModels, Items.COPPER_HELMET, EquipmentAssets.COPPER, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.COPPER_CHESTPLATE, EquipmentAssets.COPPER, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.COPPER_LEGGINGS, EquipmentAssets.COPPER, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.COPPER_BOOTS, EquipmentAssets.COPPER, false);
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
