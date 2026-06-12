package com.github.theelementguy.tegmatlibf.core;

import com.github.theelementguy.tegmatlibf.core.tier.MineabilityTier;
import com.github.theelementguy.tegmatlibf.core.tier.MiningTier;
import com.github.theelementguy.tegmatlibf.data.ModelException;
import com.github.theelementguy.tegmatlibf.loot.PreLootModifierInfo;
import com.github.theelementguy.tegmatlibf.worldgen.OreGenHolder;
import com.github.theelementguy.tegmatlibf.worldgen.config.OreGenConfig;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
import java.util.function.Supplier;

/**
 * Subclass of {@link MaterialConfiguration} for End-diamond-type materials. Use the {@link EndDiamondTypeBuilder} for construction.
 * <p>Use this class for End materials with a diamond-type format: gem as the final product.</p>
 */
public class EndDiamondTypeMaterialConfiguration extends MaterialConfiguration {

	protected Block END_ORE_BLOCK;

	EndDiamondTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String toolsBefore, String armorBefore, Supplier<Item> itemBefore, Supplier<Block> blockBefore, String oreBefore, float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed, boolean usingHorseArmor, boolean usingNautilusArmor, String animalArmorBefore, List<PreLootModifierInfo> lootModifiers, List<ModelException> modelExceptions) {
		super(modId, baseName, humanReadableName, MaterialType.END_DIAMOND, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier, mineabilityTier, toolsBefore, armorBefore, itemBefore, blockBefore, oreBefore, swingDuration, damageMultiplier, delay, dismountMaxDuration, dismountMinSpeed, knockbackMaxDuration, knockbackMinSpeed, damageMaxDuration, damageMinSpeed, usingHorseArmor, usingNautilusArmor, animalArmorBefore, lootModifiers, modelExceptions);
	}

	@Override
	public void fillItems() {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME);
		fillBaseEquipment();
	}

	@Override
	public void fillBlocks() {
		END_ORE_BLOCK = registerSimpleBlock("end_" + BASE_NAME + "_ore", 4.5f, 9f, MapColor.SAND, SoundType.STONE);
		fillBaseBlock();
	}

	@Override
	public List<OreConfiguration.TargetBlockState> getOreStates() {
		return List.of(OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), END_ORE_BLOCK.defaultBlockState()));
	}

	@Override
	public List<Block> getBlocks() {
		return List.of(BLOCK, END_ORE_BLOCK);
	}

	public Block getEndOre() {
		return END_ORE_BLOCK;
	}

}
