package com.github.theelementguy.tegmatlibf.worldgen;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ConfiguredFeatureKeyHolder {

	private ResourceKey<ConfiguredFeature<?, ?>> SMALL_KEY;
	private ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_KEY;
	private ResourceKey<ConfiguredFeature<?, ?>> LARGE_KEY;
	private ResourceKey<ConfiguredFeature<?, ?>> EXTRA_KEY;

	public ConfiguredFeatureKeyHolder(@Nullable ResourceKey<ConfiguredFeature<?, ?>> smallKey, @Nullable ResourceKey<ConfiguredFeature<?, ?>> mediumKey, @Nullable ResourceKey<ConfiguredFeature<?, ?>> largeKey, @Nullable ResourceKey<ConfiguredFeature<?, ?>> extraKey) {
		SMALL_KEY = smallKey;
		LARGE_KEY = largeKey;
		MEDIUM_KEY = mediumKey;
		EXTRA_KEY = extraKey;
	}

	public Optional<ResourceKey<ConfiguredFeature<?, ?>>> getSmallKey() {
		return Optional.ofNullable(SMALL_KEY);
	}

	public Optional<ResourceKey<ConfiguredFeature<?, ?>>> getMediumKey() {
		return Optional.ofNullable(MEDIUM_KEY);
	}

	public Optional<ResourceKey<ConfiguredFeature<?, ?>>> getLargeKey() {
		return Optional.ofNullable(LARGE_KEY);
	}

	public Optional<ResourceKey<ConfiguredFeature<?, ?>>> getExtraKey() {
		return Optional.ofNullable(EXTRA_KEY);
	}

}
