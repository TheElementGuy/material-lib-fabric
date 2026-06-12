package com.github.theelementguy.tegmatlibf.item;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.function.Supplier;

public class TEGMatLibItemProvider {

	private final String MOD_ID;
	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibItemProvider(FullyConfiguredMaterialHolder materials) {
		MOD_ID = materials.getModID();
		MATERIALS = materials;
	}

	public void registerItems() {

		LOG.info("HELLO from tegmatlib item registration: {}", MOD_ID);

		ArrayList<Supplier<MaterialConfiguration>> configs = new ArrayList<>(MATERIALS.getMaterials().size());

		for (MaterialConfiguration config : MATERIALS.getMaterials()) {
			config.fillItems();
			configs.add(() -> config);
		}

		MATERIALS.setMaterialConfiguration(configs);

	}

}
