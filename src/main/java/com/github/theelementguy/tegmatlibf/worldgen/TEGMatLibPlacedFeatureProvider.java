package com.github.theelementguy.tegmatlibf.worldgen;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibPlacedFeatureProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibPlacedFeatureProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void addEntries(FabricDynamicRegistryProvider.Entries entries) {
		for (MaterialConfiguration m : MATERIALS.get()) {
			m.addPlacedFeatureEntries(entries);
		}
	}

}
