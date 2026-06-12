package com.github.theelementguy.tegmatlibf.worldgen;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibConfiguredFeatureProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibConfiguredFeatureProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.registerConfiguredFeatures(context);
		}

	}

}
