package com.github.theelementguy.tegmatlibf.worldgen.config;

public enum OreGenSize {

	SMALL("small"),
	LARGE("large"),
	MEDIUM("medium"),
	EXTRA("extra");

	private final String sizeString;

	private OreGenSize(String sizeString) {
		this.sizeString = sizeString;
	}

	public String toString() {
		return sizeString;
	}

}
