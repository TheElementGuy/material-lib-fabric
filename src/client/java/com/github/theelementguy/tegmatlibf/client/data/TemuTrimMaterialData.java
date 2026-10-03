package com.github.theelementguy.tegmatlibf.client.data;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import org.jetbrains.annotations.NotNull;

public record TemuTrimMaterialData(String materialName, ResourceKey<@NotNull TrimMaterial> material) {
}
