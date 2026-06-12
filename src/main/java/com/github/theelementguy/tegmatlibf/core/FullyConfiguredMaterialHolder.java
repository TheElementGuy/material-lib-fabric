package com.github.theelementguy.tegmatlibf.core;

import java.util.List;
import java.util.function.Supplier;

/**
 * The interface describing the requirements for a {@link MaterialConfiguration} holder. Used for the mod materials class.
 */
public interface FullyConfiguredMaterialHolder {

	void setMaterialConfiguration(List<Supplier<MaterialConfiguration>> material);

	List<MaterialConfiguration> getMaterials();

	String getModID();

}
