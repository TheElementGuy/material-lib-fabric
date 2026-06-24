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
			builder(ItemTags.TRIM_MATERIALS).add(config.getBaseItem().builtInRegistryHolder().key());
			builder(ItemTags.SWORDS).add(config.getSword().builtInRegistryHolder().key());
			builder(ItemTags.AXES).add(config.getAxe().builtInRegistryHolder().key());
			builder(ItemTags.PICKAXES).add(config.getPickaxe().builtInRegistryHolder().key());
			builder(ItemTags.SHOVELS).add(config.getShovel().builtInRegistryHolder().key());
			builder(ItemTags.HOES).add(config.getHoe().builtInRegistryHolder().key());
			builder(ItemTags.SPEARS).add(config.getSpear().builtInRegistryHolder().key());
			builder(config.getRepairables()).add(config.getBaseItem().builtInRegistryHolder().key());
			builder(ItemTags.HEAD_ARMOR).add(config.getHelmet().builtInRegistryHolder().key());
			builder(ItemTags.CHEST_ARMOR).add(config.getChestplate().builtInRegistryHolder().key());
			builder(ItemTags.LEG_ARMOR).add(config.getLeggings().builtInRegistryHolder().key());
			builder(ItemTags.FOOT_ARMOR).add(config.getBoots().builtInRegistryHolder().key());
			builder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(config.getSword().builtInRegistryHolder().key(), config.getAxe().builtInRegistryHolder().key(), config.getSpear().builtInRegistryHolder().key());
			builder(ConventionalItemTags.MINING_TOOL_TOOLS).add(config.getPickaxe().builtInRegistryHolder().key());
			if (config.getHorseArmor().isUsing()) {
				builder(ConventionalItemTags.HORSE_ARMORS).add(config.getHorseArmor().get().get().asItem().builtInRegistryHolder().key());
			}
			if (config.getNautilusArmor().isUsing()) {
				builder(ConventionalItemTags.NAUTILUS_ARMORS).add(config.getNautilusArmor().get().get().asItem().builtInRegistryHolder().key());
			}
			switch (config.getType()) {
				case DIAMOND, NETHER_DIAMOND, END_DIAMOND, SAND_DIAMOND -> {
					builder(ConventionalItemTags.GEMS).add(config.getBaseItem().builtInRegistryHolder().key());
				}
				case IRON -> {
					IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) config;
					builder(ConventionalItemTags.INGOTS).add(mat.getBaseItem().builtInRegistryHolder().key());
					builder(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem().builtInRegistryHolder().key());
					builder(ConventionalItemTags.NUGGETS).add(mat.getNugget().builtInRegistryHolder().key());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) config;
					builder(ConventionalItemTags.GEMS).add(mat.getBaseItem().builtInRegistryHolder().key());
					builder(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem().builtInRegistryHolder().key());
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration mat = (EndIronTypeMaterialConfiguration) config;
					builder(ConventionalItemTags.INGOTS).add(mat.getBaseItem().builtInRegistryHolder().key());
					builder(ConventionalItemTags.RAW_MATERIALS).add(mat.getRawItem().builtInRegistryHolder().key());
					builder(ConventionalItemTags.NUGGETS).add(mat.getNugget().builtInRegistryHolder().key());
				}
			}
		}

	}
}
