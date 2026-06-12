package com.github.theelementguy.tegmatlibf.worldgen.config;

import com.github.theelementguy.tegmatlibf.core.SingleOrMultiple;
import com.github.theelementguy.tegmatlibf.util.OrePlacement;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OreGenConfig {

	protected final OreGenSize SIZE;

	protected final HeightRangePlacement PLACEMENT;

	protected final int SIZE_INT;
	protected final float DISCARD_ON_AIR_CHANCE;

	protected final OreRarity RARITY;
	protected final int PLACEMENT_INT;

	protected final TagKey<Biome> BIOMES;
	protected final ResourceKey<Biome> BIOME;

	protected final SingleOrMultiple BIOME_HOLDING_TYPE;

	public OreGenConfig(OreGenSize size, HeightRangePlacement placement, int sizeInt, float discardOnAirChance, OreRarity rarity, int placementInt, TagKey<Biome> biomes) {
		SIZE = size;
		PLACEMENT = placement;
		SIZE_INT = sizeInt;
		DISCARD_ON_AIR_CHANCE = discardOnAirChance;
		RARITY = rarity;
		PLACEMENT_INT = placementInt;
		BIOMES = biomes;
		BIOME_HOLDING_TYPE = SingleOrMultiple.MULTIPLE;
		BIOME = null;
	}

	public OreGenConfig(OreGenSize size, HeightRangePlacement placement, int sizeInt, float discardOnAirChance, OreRarity rarity, int placementInt, ResourceKey<Biome> biomes) {
		SIZE = size;
		PLACEMENT = placement;
		SIZE_INT = sizeInt;
		DISCARD_ON_AIR_CHANCE = discardOnAirChance;
		RARITY = rarity;
		PLACEMENT_INT = placementInt;
		BIOME = biomes;
		BIOME_HOLDING_TYPE = SingleOrMultiple.SINGLE;
		BIOMES = null;
	}

	public static Supplier<OreGenConfig> smallAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> rareSmallAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> smallAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> rareSmallAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, Dimension dimension, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> mediumAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> rareMediumAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> mediumAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> rareMediumAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, Dimension dimension, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> largeAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> rareLargeAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> largeAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> rareLargeAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, Dimension dimension, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
		}
		return () -> new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, dimension.getBiomesContained());
	}

	public static Supplier<OreGenConfig> extraWithTag(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, TagKey<Biome> biomes) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, biomes);
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, biomes);
		}
		return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, biomes);
	}

	public static Supplier<OreGenConfig> rareExtraWithTag(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, TagKey<Biome> biomes) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, biomes);
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, biomes);
		}
		return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, biomes);
	}

	public static Supplier<OreGenConfig> extraWithBiome(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, ResourceKey<Biome> biome) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, biome);
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, biome);
		}
		return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, biome);
	}

	public static Supplier<OreGenConfig> rareExtraWithBiome(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, ResourceKey<Biome> biome) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, biome);
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, biome);
		}
		return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.RARE, chunksPerVein, biome);
	}

	public static Supplier<OreGenConfig> extraWithTag(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, TagKey<Biome> biomes, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, biomes);
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, biomes);
		}
		return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, biomes);
	}

	public static Supplier<OreGenConfig> rareExtraWithTag(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, TagKey<Biome> biomes, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, biomes);
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, biomes);
		}
		return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, biomes);
	}

	public static Supplier<OreGenConfig> extraWithBiome(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, ResourceKey<Biome> biome, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, biome);
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, biome);
		}
		return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.COMMON, numberPerChunk, biome);
	}

	public static Supplier<OreGenConfig> rareExtraWithBiome(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int chunksPerVein, ResourceKey<Biome> biome, float discardOnAirChance) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, biome);
		} else if (distributionType == OreDistribution.UNIFORM) {
			return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, biome);
		}
		return () -> new OreGenConfig(OreGenSize.EXTRA, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, discardOnAirChance, OreRarity.RARE, chunksPerVein, biome);
	}
	
	public void registerConfiguredFeature(BootstrapContext<ConfiguredFeature<?, ?>> context, List<OreConfiguration.TargetBlockState> ores, ResourceKey<ConfiguredFeature<?, ?>> key) {
		context.register(key, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ores, this.SIZE_INT, this.DISCARD_ON_AIR_CHANCE)));
	}

	public void registerPlacedFeature(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> featureKey, ResourceKey<ConfiguredFeature<?, ?>> configKey) {
		context.register(featureKey, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configKey), (RARITY == OreRarity.COMMON) ? OrePlacement.commonOrePlacement(PLACEMENT_INT, PLACEMENT) : OrePlacement.rareOrePlacement(PLACEMENT_INT, PLACEMENT)));
	}

	public Predicate<BiomeSelectionContext> getPredicate() {

		return (biomeSelectionContext -> {
			if (BIOME_HOLDING_TYPE == SingleOrMultiple.SINGLE) {
				assert BIOME != null;
				return BIOME.equals(biomeSelectionContext.getBiomeKey());
			} else if (BIOME_HOLDING_TYPE == SingleOrMultiple.MULTIPLE) {
				assert BIOMES != null;
				return biomeSelectionContext.hasTag(BIOMES);
			} else {
				return false;
			}
		});

	}

}
