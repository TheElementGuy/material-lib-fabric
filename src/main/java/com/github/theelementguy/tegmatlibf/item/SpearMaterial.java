package com.github.theelementguy.tegmatlibf.item;

public record SpearMaterial(float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed) {

	@Override
	public float swingDuration() {
		return swingDuration;
	}

	@Override
	public float damageMultiplier() {
		return damageMultiplier;
	}

	@Override
	public float delay() {
		return delay;
	}

	@Override
	public float dismountMaxDuration() {
		return dismountMaxDuration;
	}

	@Override
	public float dismountMinSpeed() {
		return dismountMinSpeed;
	}

	@Override
	public float knockbackMaxDuration() {
		return knockbackMaxDuration;
	}

	@Override
	public float knockbackMinSpeed() {
		return knockbackMinSpeed;
	}

	@Override
	public float damageMaxDuration() {
		return damageMaxDuration;
	}

	@Override
	public float damageMinSpeed() {
		return damageMinSpeed;
	}
}
