package com.github.theelementguy.tegmatlibf.worldgen;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class OreGenHolder<T> {

	private Supplier<T> SMALL_KEY;
	private Supplier<T> MEDIUM_KEY;
	private Supplier<T> LARGE_KEY;
	private Supplier<T> EXTRA_KEY;

	public OreGenHolder(@Nullable Supplier<T> smallKey, @Nullable Supplier<T> mediumKey, @Nullable Supplier<T> largeKey, @Nullable Supplier<T> extraKey) {
		SMALL_KEY = smallKey;
		LARGE_KEY = largeKey;
		MEDIUM_KEY = mediumKey;
		EXTRA_KEY = extraKey;
	}

	public Optional<T> getSmall() {
		return Optional.ofNullable(SMALL_KEY).map(Supplier::get);
	}

	public boolean hasSmall() {
		return Optional.ofNullable(SMALL_KEY).isPresent();
	}

	public Optional<T> getMedium() {
		return Optional.ofNullable(MEDIUM_KEY).map(Supplier::get);
	}

	public boolean hasMedium() {
		return Optional.ofNullable(MEDIUM_KEY).isPresent();
	}

	public Optional<T> getLarge() {
		return Optional.ofNullable(LARGE_KEY).map(Supplier::get);
	}

	public boolean hasLarge() {
		return Optional.ofNullable(LARGE_KEY).isPresent();
	}

	public Optional<T> getExtra() {
		return Optional.ofNullable(EXTRA_KEY).map(Supplier::get);
	}

	public boolean hasExtra() {
		return Optional.ofNullable(EXTRA_KEY).isPresent();
	}
	
}
