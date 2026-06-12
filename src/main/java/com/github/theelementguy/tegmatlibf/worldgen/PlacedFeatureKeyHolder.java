package com.github.theelementguy.tegmatlibf.worldgen;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PlacedFeatureKeyHolder {

	private ResourceKey<PlacedFeature> SMALL_KEY;
	private ResourceKey<PlacedFeature> MEDIUM_KEY;
	private ResourceKey<PlacedFeature> LARGE_KEY;
	private ResourceKey<PlacedFeature> EXTRA_KEY;

	public PlacedFeatureKeyHolder(@Nullable ResourceKey<PlacedFeature> smallKey, @Nullable ResourceKey<PlacedFeature> mediumKey, @Nullable ResourceKey<PlacedFeature> largeKey, @Nullable ResourceKey<PlacedFeature> extraKey) {
		SMALL_KEY = smallKey;
		LARGE_KEY = largeKey;
		MEDIUM_KEY = mediumKey;
		EXTRA_KEY = extraKey;
	}

	public Optional<ResourceKey<PlacedFeature>> getSmallKey() {
		return Optional.ofNullable(SMALL_KEY);
	}

	public Optional<ResourceKey<PlacedFeature>> getMediumKey() {
		return Optional.ofNullable(MEDIUM_KEY);
	}

	public Optional<ResourceKey<PlacedFeature>> getLargeKey() {
		return Optional.ofNullable(LARGE_KEY);
	}

	public Optional<ResourceKey<PlacedFeature>> getExtraKey() {
		return Optional.ofNullable(EXTRA_KEY);
	}

}
