package com.github.theelementguy.tegmatlibf.data;

import com.github.theelementguy.tegmatlibf.core.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibItemTagProvider extends FabricTagProvider.ItemTagProvider {

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output, provider);
		MATERIALS = materials::getMaterials;
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider provider) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			tag(ItemTags.TRIM_MATERIALS).add(config.getBaseItem().builtInRegistryHolder().key());
			tag(ItemTags.SWORDS).add(config.getSword().builtInRegistryHolder().key());
			tag(ItemTags.AXES).add(config.getAxe().builtInRegistryHolder().key());
			tag(ItemTags.PICKAXES).add(config.getPickaxe().builtInRegistryHolder().key());
			tag(ItemTags.SHOVELS).add(config.getShovel().builtInRegistryHolder().key());
			tag(ItemTags.HOES).add(config.getHoe().builtInRegistryHolder().key());
			tag(config.getRepairables()).add(config.getBaseItem().builtInRegistryHolder().key());
			tag(ItemTags.HEAD_ARMOR).add(config.getHelmet().builtInRegistryHolder().key());
			tag(ItemTags.CHEST_ARMOR).add(config.getChestplate().builtInRegistryHolder().key());
			tag(ItemTags.LEG_ARMOR).add(config.getLeggings().builtInRegistryHolder().key());
			tag(ItemTags.FOOT_ARMOR).add(config.getBoots().builtInRegistryHolder().key());
			tag(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(config.getSword().builtInRegistryHolder().key(), config.getAxe().builtInRegistryHolder().key());
			tag(ConventionalItemTags.MINING_TOOL_TOOLS).add(config.getPickaxe().builtInRegistryHolder().key());
			switch (config.getType()) {
				case DIAMOND, NETHER_DIAMOND, END_DIAMOND, SAND_DIAMOND -> {
					tag(ConventionalItemTags.GEMS).add(config.getBaseItem().builtInRegistryHolder().key());
				}
				case IRON -> {
					IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) config;
					tag(ConventionalItemTags.INGOTS).add(mat.getBaseItem().builtInRegistryHolder().key());
					tag(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem().builtInRegistryHolder().key());
					tag(ConventionalItemTags.NUGGETS).add(mat.getNugget().builtInRegistryHolder().key());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) config;
					tag(ConventionalItemTags.GEMS).add(mat.getBaseItem().builtInRegistryHolder().key());
					tag(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem().builtInRegistryHolder().key());
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration mat = (EndIronTypeMaterialConfiguration) config;
					tag(ConventionalItemTags.INGOTS).add(mat.getBaseItem().builtInRegistryHolder().key());
					tag(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem().builtInRegistryHolder().key());
					tag(ConventionalItemTags.NUGGETS).add(mat.getNugget().builtInRegistryHolder().key());
				}
			}
		}

	}
}
