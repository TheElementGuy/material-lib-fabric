package com.github.theelementguy.tegmatlibf.trim;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibTrimMaterialProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibTrimMaterialProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void addEntries(FabricDynamicRegistryProvider.Entries entries) {

		float id = 1.0f;

		for (MaterialConfiguration config : MATERIALS.get()) {
			id += 0.1f;
			config.addTrimMaterialEntry(entries, id);
		}
	}

	public void bootstrap(BootstrapContext<TrimMaterial> context) {

		float id = 1.0f;

		for (MaterialConfiguration config : MATERIALS.get()) {
			id += 0.1f;
			config.bootstrapTrimMaterial(context, id);
		}

	}

}
