package com.github.theelementguy.tegmatlibf.data;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import com.github.theelementguy.tegmatlibf.trim.TEGMatLibTrimMaterialProvider;
import com.github.theelementguy.tegmatlibf.worldgen.TEGMatLibConfiguredFeatureProvider;
import com.github.theelementguy.tegmatlibf.worldgen.TEGMatLibPlacedFeatureProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class TEGMatLibDynamicRegistryProvider extends FabricDynamicRegistryProvider {

	private final FullyConfiguredMaterialHolder MATERIALS;

	private final TEGMatLibTrimMaterialProvider trims;
	private final TEGMatLibConfiguredFeatureProvider configuredFeatures;
	private final TEGMatLibPlacedFeatureProvider placedFeatures;

	public TEGMatLibDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, FullyConfiguredMaterialHolder materials, TEGMatLibTrimMaterialProvider trims, TEGMatLibConfiguredFeatureProvider configuredFeatures, TEGMatLibPlacedFeatureProvider placedFeatures) {
		super(output, registriesFuture);
		MATERIALS = materials;
		this.trims = trims;
		this.configuredFeatures = configuredFeatures;
		this.placedFeatures = placedFeatures;
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
		entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
		entries.addAll(registries.lookupOrThrow(Registries.TRIM_MATERIAL));
	}

	@Override
	public String getName() {
		return MATERIALS.getModID() + " dynamic registries";
	}
}
