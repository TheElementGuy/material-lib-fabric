package com.github.theelementguy.tegmatlibf.util;

import com.github.theelementguy.tegmatlibf.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TEGMatLibCreativeModeTabFiller {

	/**
	 * Automatically fills the inventory in creative mode.
	 * @param materialHolder A {@link FullyConfiguredMaterialHolder} with the materials <b>in order of how they would appear relatively in the inventory</b>.
	 */
	public static void build(FullyConfiguredMaterialHolder materialHolder) {
		//TODO: proper ordering
		List<MaterialConfiguration> materials = materialHolder.getMaterials();
		String modID = materialHolder.getModID();
		ResourceKey<@NotNull CreativeModeTab> currentTab = CreativeModeTabs.INGREDIENTS;
		for (MaterialConfiguration m : materials) {
			TEGMatLibUtil.inventoryAddAfter(m.getBaseItem(), m.getItemBefore(), currentTab);
			switch (m.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + ironMatConfig.getRawBefore(), modID), currentTab);
					TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getNugget(), TEGMatLibUtil.getItemFromKey(ironMatConfig.getRawBefore() + "_nugget", modID), currentTab);
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(cubicMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + cubicMatConfig.getRawBefore(), modID), currentTab);
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration endIronMatConfig = (EndIronTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(endIronMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + endIronMatConfig.getRawBefore(), modID), currentTab);
					TEGMatLibUtil.inventoryAddAfter(endIronMatConfig.getNugget(), TEGMatLibUtil.getItemFromKey(endIronMatConfig.getRawBefore() + "_nugget", modID), currentTab);
				}
			}
		}
		currentTab = CreativeModeTabs.BUILDING_BLOCKS;
		for (MaterialConfiguration m : materials) {
			TEGMatLibUtil.inventoryAddAfter(m.getBaseBlock(), m.getBlockBefore(), currentTab);
		}
		currentTab = CreativeModeTabs.NATURAL_BLOCKS;
		for (MaterialConfiguration m : materials) {
			switch (m.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + ironMatConfig.getOreBefore() + "_ore", modID), currentTab);
					TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getDeepslateOre(), ironMatConfig.getOre(), currentTab);
					TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + ironMatConfig.getRawBefore() + "_block", modID), currentTab);
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(diamondMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + diamondMatConfig.getOreBefore() + "_ore", modID), currentTab);
					TEGMatLibUtil.inventoryAddAfter(diamondMatConfig.getDeepslateOre(), diamondMatConfig.getOre(), currentTab);
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(cubicMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + cubicMatConfig.getOreBefore() + "_ore", modID), currentTab);
					TEGMatLibUtil.inventoryAddAfter(cubicMatConfig.getDeepslateOre(), cubicMatConfig.getOre(), currentTab);
					TEGMatLibUtil.inventoryAddAfter(cubicMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + cubicMatConfig.getRawBefore() + "_block", modID), currentTab);
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(netherDiamondMatConfig.getNetherOre(), TEGMatLibUtil.getBlockFromKey("nether_" + netherDiamondMatConfig.getOreBefore() + "_ore", modID), currentTab);
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(endDiamondMatConfig.getEndOre(), TEGMatLibUtil.getBlockFromKey(endDiamondMatConfig.getOreBefore(), modID), currentTab);
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration endIronMatConfig = (EndIronTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(endIronMatConfig.getEndOre(), TEGMatLibUtil.getBlockFromKey(endIronMatConfig.getOreBefore(), modID), currentTab);
					TEGMatLibUtil.inventoryAddAfter(endIronMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + endIronMatConfig.getRawBefore() + "_block", modID), currentTab);
				}
				case SAND_DIAMOND -> {
					SandDiamondTypeMaterialConfiguration sandDiamondTypeMatConfig = (SandDiamondTypeMaterialConfiguration) m;
					TEGMatLibUtil.inventoryAddAfter(sandDiamondTypeMatConfig.getSandOre(), TEGMatLibUtil.getBlockFromKey(sandDiamondTypeMatConfig.getOreBefore(), modID), currentTab);
					TEGMatLibUtil.inventoryAddAfter(sandDiamondTypeMatConfig.getGravelOre(), sandDiamondTypeMatConfig.getSandOre(), currentTab);
				}
			}
		}
		currentTab = CreativeModeTabs.COMBAT;
		for (MaterialConfiguration m : materials) {
			TEGMatLibUtil.setAddAfter(m.getBaseName(), m.getToolsBefore(), m.getArmorBefore(), currentTab, modID);
			if (m.getHorseArmor().isUsing()) {
				TEGMatLibUtil.inventoryAddAfter(m.getHorseArmor().get().get(), TEGMatLibUtil.getItemFromKey(m.getAnimalArmorBefore() + "_horse_armor", materialHolder.getModID()), currentTab);
			}
			if (m.getNautilusArmor().isUsing()) {
				if (Objects.equals(m.getAnimalArmorBefore(), "leather")) {
					TEGMatLibUtil.inventoryAddBefore(Items.COPPER_NAUTILUS_ARMOR, m.getNautilusArmor().get().get(), currentTab);
				} else {
					TEGMatLibUtil.inventoryAddAfter(m.getNautilusArmor().get().get(), TEGMatLibUtil.getItemFromKey(m.getAnimalArmorBefore() + "_nautilus_armor", materialHolder.getModID()), currentTab);
				}
			}
		}
		currentTab = CreativeModeTabs.TOOLS_AND_UTILITIES;
		for (MaterialConfiguration m : materials) {
			TEGMatLibUtil.toolsAddAfter(m.getBaseName(), m.getToolsBefore(), currentTab, modID);
		}
	}

}
