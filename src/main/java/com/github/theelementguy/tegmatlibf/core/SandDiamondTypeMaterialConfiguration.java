package com.github.theelementguy.tegmatlibf.core;

import com.github.theelementguy.tegmatlibf.core.tier.MineabilityTier;
import com.github.theelementguy.tegmatlibf.core.tier.MiningTier;
import com.github.theelementguy.tegmatlibf.data.ModelException;
import com.github.theelementguy.tegmatlibf.loot.PreLootModifierInfo;
import com.github.theelementguy.tegmatlibf.util.TEGMatLibUtil;
import com.github.theelementguy.tegmatlibf.worldgen.OreGenHolder;
import com.github.theelementguy.tegmatlibf.worldgen.config.OreGenConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
import java.util.function.Supplier;

/**
 * Subclass of {@link MaterialConfiguration} for sand diamond-type materials. Use the {@link SandDiamondTypeBuilder} for construction.
 * <p>Use this class for overworld materials with a diamond-type format found in ocean sand: a gem as the final product.</p>
 */
public class SandDiamondTypeMaterialConfiguration extends MaterialConfiguration {

	protected Block SAND_ORE_BLOCK;
	protected Block GRAVEL_ORE_BLOCK;

	public SandDiamondTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String toolsBefore, String armorBefore, Supplier<Item> itemBefore, Supplier<Block> blockBefore, String oreBefore, float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed, boolean usingHorseArmor, boolean usingNautilusArmor, String animalArmorBefore, List<PreLootModifierInfo> lootModifiers, List<ModelException> modelExceptions) {
		super(modId, baseName, humanReadableName, MaterialType.SAND_DIAMOND, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier, mineabilityTier, toolsBefore, armorBefore, itemBefore, blockBefore, oreBefore, swingDuration, damageMultiplier, delay, dismountMaxDuration, dismountMinSpeed, knockbackMaxDuration, knockbackMinSpeed, damageMaxDuration, damageMinSpeed, usingHorseArmor, usingNautilusArmor, animalArmorBefore, lootModifiers, modelExceptions);
	}

	@Override
	public void fillItems() {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME);
		fillBaseEquipment();
	}

	@Override
	public void fillBlocks() {
		SAND_ORE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, TEGMatLibUtil.createBlockResourceKey("sand_" + BASE_NAME + "_ore", MOD_ID), new ColoredFallingBlock(new ColorRGBA(14406560), BlockBehaviour.Properties.of().destroyTime(1.5f).explosionResistance(1f).mapColor(MapColor.SAND).sound(SoundType.SAND).requiresCorrectToolForDrops().setId(TEGMatLibUtil.createBlockResourceKey("sand_" + BASE_NAME + "_ore", MOD_ID))));
		Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey("sand_" + BASE_NAME + "_ore", MOD_ID), new BlockItem(SAND_ORE_BLOCK, DEFAULT_PROPERTIES.get().setId(TEGMatLibUtil.createItemResourceKey("sand_" + BASE_NAME + "_ore", MOD_ID)).useBlockDescriptionPrefix()));
		GRAVEL_ORE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, TEGMatLibUtil.createBlockResourceKey("gravel_" + BASE_NAME + "_ore", MOD_ID), new ColoredFallingBlock(new ColorRGBA(14406560), BlockBehaviour.Properties.of().destroyTime(1.5f).explosionResistance(1f).mapColor(MapColor.STONE).sound(SoundType.GRAVEL).requiresCorrectToolForDrops().setId(TEGMatLibUtil.createBlockResourceKey("gravel_" + BASE_NAME + "_ore", MOD_ID))));
		Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey("gravel_" + BASE_NAME + "_ore", MOD_ID), new BlockItem(GRAVEL_ORE_BLOCK, DEFAULT_PROPERTIES.get().setId(TEGMatLibUtil.createItemResourceKey("gravel_" + BASE_NAME + "_ore", MOD_ID)).useBlockDescriptionPrefix()));
		fillBaseBlock();
	}

	@Override
	public List<OreConfiguration.TargetBlockState> getOreStates() {
		return List.of(OreConfiguration.target(new BlockMatchTest(Blocks.SAND), SAND_ORE_BLOCK.defaultBlockState()), OreConfiguration.target(new BlockMatchTest(Blocks.GRAVEL), GRAVEL_ORE_BLOCK.defaultBlockState()));
	}

	@Override
	public List<Block> getBlocks() {
		return List.of(BLOCK, SAND_ORE_BLOCK, GRAVEL_ORE_BLOCK);
	}

	public Block getSandOre() {
		return SAND_ORE_BLOCK;
	}

	public Block getGravelOre() {
		return GRAVEL_ORE_BLOCK;
	}

}
