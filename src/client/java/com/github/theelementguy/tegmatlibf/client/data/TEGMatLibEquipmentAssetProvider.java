package com.github.theelementguy.tegmatlibf.client.data;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TEGMatLibEquipmentAssetProvider extends EquipmentAssetProvider {

	protected final PackOutput.PathProvider pathProvider;

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibEquipmentAssetProvider(FabricPackOutput output, FullyConfiguredMaterialHolder materials) {
		super(output);
		pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
		MATERIALS = materials::getMaterials;
	}

	protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
		for (MaterialConfiguration m : MATERIALS.get()) {
			bootstrapEquipmentAsset(output, m);
		}
	}

	@Override
	public @NotNull CompletableFuture<?> run(final @NotNull CachedOutput cache) {
		Map<ResourceKey<@NotNull EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();
		registerModels((id, asset) -> {
			if (equipmentAssets.putIfAbsent(id, asset) != null) {
				throw new IllegalStateException("Tried to register equipment asset twice for id: " + id);
			}
		});
		Objects.requireNonNull(pathProvider);
		return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, pathProvider::json, equipmentAssets);
	}

	public void bootstrapEquipmentAsset(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer, MaterialConfiguration materialConfiguration) {
		EquipmentClientInfo.Builder builder = EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(materialConfiguration.getModID(), materialConfiguration.getEquipmentAsset().identifier().getPath()));
		if (materialConfiguration.getHorseArmor().isUsing()) {
			builder.addLayers(EquipmentClientInfo.LayerType.HORSE_BODY, new EquipmentClientInfo.Layer(materialConfiguration.getEquipmentAsset().identifier(), Optional.empty(), true));
		}
		if (materialConfiguration.getNautilusArmor().isUsing()) {
			builder.addLayers(EquipmentClientInfo.LayerType.NAUTILUS_BODY, new EquipmentClientInfo.Layer(materialConfiguration.getEquipmentAsset().identifier()));
		}
		consumer.accept(materialConfiguration.getEquipmentAsset(), builder.build());
	}
}
