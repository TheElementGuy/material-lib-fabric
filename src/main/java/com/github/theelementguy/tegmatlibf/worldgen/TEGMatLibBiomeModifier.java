package com.github.theelementguy.tegmatlibf.worldgen;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibBiomeModifier {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibBiomeModifier(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void run() {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.registerBiomeModifiers();
		}

	}

}
