package com.github.theelementguy.tegmatlibf.trim;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibTrimMaterialProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibTrimMaterialProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void addEntries(FabricDynamicRegistryProvider.Entries entries) {
		for (MaterialConfiguration config : MATERIALS.get()) {
			config.addTrimMaterialEntry(entries);
		}
	}

	public void bootstrap(BootstrapContext<TrimMaterial> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.bootstrapTrimMaterial(context);
		}

	}

}
