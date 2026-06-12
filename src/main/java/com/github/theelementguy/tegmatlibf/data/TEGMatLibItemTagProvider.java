package com.github.theelementguy.tegmatlibf.data;

import com.github.theelementguy.tegmatlibf.core.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output, provider);
		MATERIALS = materials::getMaterials;
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider provider) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			valueLookupBuilder(ItemTags.TRIM_MATERIALS).add(config.getBaseItem());
			valueLookupBuilder(ItemTags.SWORDS).add(config.getSword());
			valueLookupBuilder(ItemTags.AXES).add(config.getAxe());
			valueLookupBuilder(ItemTags.PICKAXES).add(config.getPickaxe());
			valueLookupBuilder(ItemTags.SHOVELS).add(config.getShovel());
			valueLookupBuilder(ItemTags.HOES).add(config.getHoe());
			valueLookupBuilder(ItemTags.SPEARS).add(config.getSpear());
			valueLookupBuilder(config.getRepairables()).add(config.getBaseItem());
			valueLookupBuilder(ItemTags.HEAD_ARMOR).add(config.getHelmet());
			valueLookupBuilder(ItemTags.CHEST_ARMOR).add(config.getChestplate());
			valueLookupBuilder(ItemTags.LEG_ARMOR).add(config.getLeggings());
			valueLookupBuilder(ItemTags.FOOT_ARMOR).add(config.getBoots());
			valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(config.getSword(), config.getAxe(), config.getSpear());
			valueLookupBuilder(ConventionalItemTags.MINING_TOOL_TOOLS).add(config.getPickaxe());
			if (config.getHorseArmor().isUsing()) {
				valueLookupBuilder(ConventionalItemTags.HORSE_ARMORS).add(config.getHorseArmor().get().get().asItem());
			}
			if (config.getNautilusArmor().isUsing()) {
				valueLookupBuilder(ConventionalItemTags.NAUTILUS_ARMORS).add(config.getNautilusArmor().get().get().asItem());
			}
			switch (config.getType()) {
				case DIAMOND, NETHER_DIAMOND, END_DIAMOND, SAND_DIAMOND -> {
					valueLookupBuilder(ConventionalItemTags.GEMS).add(config.getBaseItem());
				}
				case IRON -> {
					IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) config;
					valueLookupBuilder(ConventionalItemTags.INGOTS).add(mat.getBaseItem());
					valueLookupBuilder(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem());
					valueLookupBuilder(ConventionalItemTags.NUGGETS).add(mat.getNugget());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) config;
					valueLookupBuilder(ConventionalItemTags.GEMS).add(mat.getBaseItem());
					valueLookupBuilder(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem());
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration mat = (EndIronTypeMaterialConfiguration) config;
					valueLookupBuilder(ConventionalItemTags.INGOTS).add(mat.getBaseItem());
					valueLookupBuilder(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem());
					valueLookupBuilder(ConventionalItemTags.NUGGETS).add(mat.getNugget());
				}
			}
		}

	}
}
