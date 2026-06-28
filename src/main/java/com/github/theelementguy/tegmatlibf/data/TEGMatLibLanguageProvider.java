package com.github.theelementguy.tegmatlibf.data;

import com.github.theelementguy.tegmatlibf.core.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibLanguageProvider extends FabricLanguageProvider {

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibLanguageProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output, "en_us", provider);
		this.MATERIALS = materials::getMaterials;
	}

	@Override
	public void generateTranslations(HolderLookup.@NotNull Provider provider, @NotNull TranslationBuilder translationBuilder) {

		for (MaterialConfiguration config : MATERIALS.get()) {

			translationBuilder.add(config.getSword(), config.getHumanReadableName() + " Sword");
			translationBuilder.add(config.getAxe(), config.getHumanReadableName() + " Axe");
			translationBuilder.add(config.getPickaxe(), config.getHumanReadableName() + " Pickaxe");
			translationBuilder.add(config.getShovel(), config.getHumanReadableName() + " Shovel");
			translationBuilder.add(config.getHoe(), config.getHumanReadableName() + " Hoe");

			translationBuilder.add(config.getHelmet(), config.getHumanReadableName() + " Helmet");
			translationBuilder.add(config.getChestplate(), config.getHumanReadableName() + " Chestplate");
			translationBuilder.add(config.getLeggings(), config.getHumanReadableName() + " Leggings");
			translationBuilder.add(config.getBoots(), config.getHumanReadableName() + " Boots");

			if (config.getHorseArmor().isUsing()) {
				translationBuilder.add(config.getHorseArmor().get().get().asItem(), config.getHumanReadableName() + " Horse Armor");
			}

			translationBuilder.add(config.getBaseBlock(), "Block of " + config.getHumanReadableName());

			translationBuilder.add(Util.makeDescriptionId("trim_material", config.getTrimMaterial().location()), config.getHumanReadableName());

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) config;
					translationBuilder.add(ironMatConfig.getBaseItem(), ironMatConfig.getHumanReadableName() + " Ingot");
					translationBuilder.add(ironMatConfig.getRawItem(), "Raw " + ironMatConfig.getHumanReadableName());
					translationBuilder.add(ironMatConfig.getRawBlock(), "Block of Raw " + ironMatConfig.getHumanReadableName());
					translationBuilder.add(ironMatConfig.getOre(), ironMatConfig.getHumanReadableName() + " Ore");
					translationBuilder.add(ironMatConfig.getDeepslateOre(), "Deepslate " + ironMatConfig.getHumanReadableName() + " Ore");
					translationBuilder.add(ironMatConfig.getNugget(), ironMatConfig.getHumanReadableName() + " Nugget");
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
					translationBuilder.add(diamondMatConfig.getBaseItem(), diamondMatConfig.getHumanReadableName());
					translationBuilder.add(diamondMatConfig.getOre(), diamondMatConfig.getHumanReadableName() + " Ore");
					translationBuilder.add(diamondMatConfig.getDeepslateOre(), "Deepslate " + diamondMatConfig.getHumanReadableName() + " Ore");
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) config;
					translationBuilder.add(cubicMatConfig.getBaseItem(), cubicMatConfig.getHumanReadableName());
					translationBuilder.add(cubicMatConfig.getRawItem(), "Raw " + cubicMatConfig.getHumanReadableName());
					translationBuilder.add(cubicMatConfig.getRawBlock(), "Block of Raw " + cubicMatConfig.getHumanReadableName());
					translationBuilder.add(cubicMatConfig.getOre(), cubicMatConfig.getHumanReadableName() + " Ore");
					translationBuilder.add(cubicMatConfig.getDeepslateOre(), "Deepslate " + cubicMatConfig.getHumanReadableName() + " Ore");
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
					translationBuilder.add(netherDiamondMatConfig.getBaseItem(), netherDiamondMatConfig.getHumanReadableName());
					translationBuilder.add(netherDiamondMatConfig.getNetherOre(), "Nether " + netherDiamondMatConfig.getHumanReadableName() + " Ore");
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
					translationBuilder.add(endDiamondMatConfig.getBaseItem(), endDiamondMatConfig.getHumanReadableName());
					translationBuilder.add(endDiamondMatConfig.getEndOre(), "End " + endDiamondMatConfig.getHumanReadableName() + " Ore");
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration ironMatConfig = (EndIronTypeMaterialConfiguration) config;
					translationBuilder.add(ironMatConfig.getBaseItem(), ironMatConfig.getHumanReadableName() + " Ingot");
					translationBuilder.add(ironMatConfig.getRawItem(), "Raw " + ironMatConfig.getHumanReadableName());
					translationBuilder.add(ironMatConfig.getRawBlock(), "Block of Raw " + ironMatConfig.getHumanReadableName());
					translationBuilder.add(ironMatConfig.getEndOre(), "End " + ironMatConfig.getHumanReadableName() + " Ore");
					translationBuilder.add(ironMatConfig.getNugget(), ironMatConfig.getHumanReadableName() + " Nugget");
				}
				case SAND_DIAMOND -> {
					SandDiamondTypeMaterialConfiguration sandDiamondMatConfig = (SandDiamondTypeMaterialConfiguration) config;
					translationBuilder.add(sandDiamondMatConfig.getBaseItem(), sandDiamondMatConfig.getHumanReadableName());
					translationBuilder.add(sandDiamondMatConfig.getSandOre(), "Sand " + sandDiamondMatConfig.getHumanReadableName() + " Ore");
					translationBuilder.add(sandDiamondMatConfig.getGravelOre(), "Gravel " + sandDiamondMatConfig.getHumanReadableName() + " Ore");
				}
			}

		}

	}
}
