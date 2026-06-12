package com.github.theelementguy.tegmatlibf.worldgen.config;

import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public enum Dimension {

	OVERWORLD(BiomeTags.IS_OVERWORLD),
	NETHER(BiomeTags.IS_NETHER),
	END(BiomeTags.IS_END);

	private final TagKey<Biome> biomesContained;
	private Dimension(TagKey<Biome> biomesContained) {
		this.biomesContained = biomesContained;
	}

	public TagKey<Biome> getBiomesContained() {
		return biomesContained;
	}

}
