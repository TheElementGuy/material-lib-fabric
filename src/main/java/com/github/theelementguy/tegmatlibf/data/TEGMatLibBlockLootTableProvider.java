package com.github.theelementguy.tegmatlibf.data;

import com.github.theelementguy.tegmatlibf.core.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibBlockLootTableProvider extends FabricBlockLootTableProvider {

	private Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibBlockLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output, provider);
		MATERIALS = materials::getMaterials;
	}

	@Override
	public void generate() {

		for (MaterialConfiguration config : MATERIALS.get()) {
			add(config);
		}

	}

	protected void add(MaterialConfiguration config) {

		switch (config.getType()) {
			case IRON -> {
				IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) config;
				dropSelf(mat.getBaseBlock());
				dropSelf(mat.getRawBlock());
				if (config.isSingleOre()) {
					add(mat.getOre(), b -> createOreDrop(mat.getOre(), mat.getRawItem()));
					add(mat.getDeepslateOre(), b -> createOreDrop(mat.getDeepslateOre(), mat.getRawItem()));
				} else {
					add(mat.getOre(), b -> createMultipleOreDrops(mat.getOre(), mat.getRawItem(), mat));
					add(mat.getDeepslateOre(), b -> createMultipleOreDrops(mat.getDeepslateOre(), mat.getRawItem(), mat));
				}
			}
			case DIAMOND -> {
				DiamondTypeMaterialConfiguration mat = (DiamondTypeMaterialConfiguration) config;
				dropSelf(mat.getBaseBlock());
				if (config.isSingleOre()) {
					add(mat.getOre(), b -> createOreDrop(mat.getOre(), mat.getBaseItem()));
					add(mat.getDeepslateOre(), b -> createOreDrop(mat.getDeepslateOre(), mat.getBaseItem()));
				} else {
					add(mat.getOre(), b -> createMultipleOreDrops(mat.getOre(), mat.getBaseItem(), mat));
					add(mat.getDeepslateOre(), b -> createMultipleOreDrops(mat.getDeepslateOre(), mat.getBaseItem(), mat));
				}
			}
			case CUBIC_ZIRCONIA -> {
				CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) config;
				dropSelf(mat.getBaseBlock());
				dropSelf(mat.getRawBlock());
				if (config.isSingleOre()) {
					add(mat.getOre(), b -> createOreDrop(mat.getOre(), mat.getRawItem()));
					add(mat.getDeepslateOre(), b -> createOreDrop(mat.getDeepslateOre(), mat.getRawItem()));
				} else {
					add(mat.getOre(), b -> createMultipleOreDrops(mat.getOre(), mat.getRawItem(), mat));
					add(mat.getDeepslateOre(), b -> createMultipleOreDrops(mat.getDeepslateOre(), mat.getRawItem(), mat));
				}
			}
			case NETHER_DIAMOND -> {
				NetherDiamondTypeMaterialConfiguration mat = (NetherDiamondTypeMaterialConfiguration) config;
				dropSelf(mat.getBaseBlock());
				if (config.isSingleOre()) {
					add(mat.getNetherOre(), b -> createOreDrop(mat.getNetherOre(), mat.getBaseItem()));
				} else {
					add(mat.getNetherOre(), b -> createMultipleOreDrops(mat.getNetherOre(), mat.getBaseItem(), mat));
				}
			}
			case END_DIAMOND -> {
				EndDiamondTypeMaterialConfiguration mat = (EndDiamondTypeMaterialConfiguration) config;
				dropSelf(mat.getBaseBlock());
				if (config.isSingleOre()) {
					add(mat.getEndOre(), b -> createOreDrop(mat.getEndOre(), mat.getBaseItem()));
				} else {
					add(mat.getEndOre(), b -> createMultipleOreDrops(mat.getEndOre(), mat.getBaseItem(), mat));
				}
			}
			case END_IRON -> {
				EndIronTypeMaterialConfiguration mat = (EndIronTypeMaterialConfiguration) config;
				dropSelf(mat.getBaseBlock());
				dropSelf(mat.getRawBlock());
				if (config.isSingleOre()) {
					add(mat.getEndOre(), b -> createOreDrop(mat.getEndOre(), mat.getBaseItem()));
				} else {
					add(mat.getEndOre(), b -> createMultipleOreDrops(mat.getEndOre(), mat.getRawItem(), mat));
				}
			}
			case SAND_DIAMOND -> {
				SandDiamondTypeMaterialConfiguration mat = (SandDiamondTypeMaterialConfiguration) config;
				dropSelf(mat.getBaseBlock());
				if (config.isSingleOre()) {
					add(mat.getSandOre(), b -> createOreDrop(mat.getSandOre(), mat.getBaseItem()));
					add(mat.getGravelOre(), b -> createOreDrop(mat.getGravelOre(), mat.getBaseItem()));
				} else {
					add(mat.getSandOre(), b -> createMultipleOreDrops(mat.getSandOre(), mat.getBaseItem(), mat));
					add(mat.getGravelOre(), b -> createMultipleOreDrops(mat.getGravelOre(), mat.getBaseItem(), mat));
				}
			}
		}

	}

	protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, MaterialConfiguration materialConfiguration) {
		return createMultipleOreDrops(pBlock, item, materialConfiguration.getBaseDrops(), materialConfiguration.getMaxDrops());
	}

	protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		return this.createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops))).apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
	}
}
