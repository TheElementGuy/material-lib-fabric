package com.github.theelementguy.tegmatlibf.core.component;

import net.minecraft.world.item.Item;

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalComponent<T> {

	private T COMPONENT;

	private final boolean USING;
	private final ComponentType TYPE;

	private OptionalComponent(boolean using, ComponentType type) {
		USING = using;
		TYPE = type;
	}

	public static OptionalComponent<Item> horseArmor(boolean using) {
		return new OptionalComponent<>(using, ComponentType.HORSE_ARMOR);
	}

	public boolean isUsing() {
		return USING;
	}

	public ComponentType getType() {
		return TYPE;
	}

	public void fillIfUsing(Supplier<T> filler) {
		if (USING) {
			COMPONENT = filler.get();
		} else {
			COMPONENT = null;
		}
	}

	public Optional<T> get() {
		return Optional.ofNullable(COMPONENT);
	}

}
