package com.github.theelementguy.tegmatlibf.block;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * The provided class from TEG Material Library that automatically registers blocks. This class is meant to be created in the mod constructor.
 */
public class TEGMatLibBlockProvider {

	private final String MOD_ID;
	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	/**
	 * Constructor for the {@link TEGMatLibBlockProvider} class. Meant to be called in mod constructor.
	 * @param materials a {@link FullyConfiguredMaterialHolder} holding the {@link MaterialConfiguration}s
	 */
	public TEGMatLibBlockProvider(FullyConfiguredMaterialHolder materials) {
		MOD_ID = materials.getModID();
		MATERIALS = materials;
	}

	/**
	 * Registers the blocks specified by each {@link MaterialConfiguration} to the game.
	 */
	public void registerBlocks() {

		LOG.info("HELLO from tegmatlib block registration: {}", MOD_ID);

		ArrayList<Supplier<MaterialConfiguration>> configs = new ArrayList<>(MATERIALS.getMaterials().size());

		for (MaterialConfiguration config : MATERIALS.getMaterials()) {
			config.fillBlocks();
			configs.add(() -> config);
		}

		MATERIALS.setMaterialConfiguration(configs);

	}

}
