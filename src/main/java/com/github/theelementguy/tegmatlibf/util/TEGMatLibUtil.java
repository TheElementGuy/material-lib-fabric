package com.github.theelementguy.tegmatlibf.util;

import com.github.theelementguy.tegmatlibf.core.tier.MineabilityTier;
import com.github.theelementguy.tegmatlibf.core.tier.MiningTier;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TEGMatLibUtil {

	private static HashMap<Character, Character> upsideDown = new HashMap<>(mapOf('a', 'ɐ', 'b', 'q', 'c', 'ɔ', 'd', 'p', 'e', 'ǝ', 'f', 'ɟ', 'g', 'ᵷ', 'h', 'ɥ', 'i', 'ᴉ', 'k', 'ʞ', 'C', 'Ɔ', 'u', 'n', 'n', 'u', ' ', ' ', 'z', 'z', 'Z', 'Z', 'q', 'b', 'p', 'd', 'r', 'ɹ', 'o', 'o', 'l', 'l', 's', 's', 'R', 'ᴚ', 'S', 'S', 'B', 'ᗺ', 'A', 'Ɐ', 'w', 'ʍ', 'W', 'M', 'x', 'x', 'P', 'Ԁ', 'T', '⟘', 'H', 'H', 'v', 'ʌ', 'D', 'ᗡ', 'N', 'N', 'E', 'Ǝ', 'O', 'O', 'm', 'ɯ', 't', 'ʇ', 'L', 'Ꞁ', 'I', 'I'));

	public static ResourceKey<Item> createItemResourceKey(String name, String modId) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<Block> createBlockResourceKey(String name, String modId) {
		return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<Recipe<?>> createRecipeResourceKey(String name, String modId) {
		return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<EquipmentAsset> createEquipmentAssetResourceKey(String name, String modId) {
		return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(modId, name));
	}

	public static Item getItemFromKey(String key, String modId) {

		if (BuiltInRegistries.ITEM.get(TEGMatLibUtil.createItemResourceKey(key, modId)).isEmpty()) {
			return getItemFromKeyMinecraft(key);
		}

		return BuiltInRegistries.ITEM.get(TEGMatLibUtil.createItemResourceKey(key, modId)).get().value();

	}

	public static Item getItemFromKeyMinecraft(String key) {
		return BuiltInRegistries.ITEM.get(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace(key))).get().value();
	}

	public static Block getBlockFromKey(String key, String modId) {
		if (BuiltInRegistries.BLOCK.get(TEGMatLibUtil.createBlockResourceKey(key, modId)).isEmpty()) {
			return getBlockFromKey(key, "minecraft");
		}
		return BuiltInRegistries.BLOCK.get(TEGMatLibUtil.createBlockResourceKey(key, modId)).get().value();
	}

	public static EquipmentAsset getMaterialAssetGroupFromKey(String key, String modId) {
		Registry<EquipmentAsset> registry = (Registry<EquipmentAsset>) BuiltInRegistries.REGISTRY.get(EquipmentAssets.ROOT_ID.registry()).get().value();
		return registry.get(TEGMatLibUtil.createEquipmentAssetResourceKey(key, modId)).get().value();
	}

	public static String toUpsideDown(String given) {

		ArrayList<Character> charList = new ArrayList<>(given.length());

		for (char c : given.toCharArray()) {
			charList.add(c);
		}

		int i = 0;

		for (Character c : charList) {
			if (upsideDown.containsKey(c)) {
				charList.set(i, upsideDown.get(c));
			} else {
				throw new NoSuchElementException("No upside down equivalent for: " + c);
			}
			i++;
		}

		StringBuilder b = new StringBuilder();

		Collections.reverse(charList);

		charList.forEach(b::append);

		return b.toString();
	}

	public static <K, V> Map<K, V> mapOf(Object... things) {

		ArrayList<Object> temp = new ArrayList<>(2);
		HashMap<K, V> toReturn = new HashMap<>(things.length / 2);

		for (int i = 0; i < things.length; i++) {
			if (((i % 2) == 1)) {
				temp.add(things[i]);
				toReturn.put((K) temp.get(0), (V) temp.get(1));
				temp.clear();
			} else {
				temp.add(things[i]);
			}
		}

		return toReturn;

	}

	public static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeatureResourceKey(String modId, String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<PlacedFeature> createPlacedFeatureResourceKey(String modId, String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(modId, name));
	}

	public static void inventoryAddAfter(Item item, Item referenceItem, ResourceKey<CreativeModeTab> tab) {
		CreativeModeTabEvents.modifyOutputEvent(tab).register(c -> c.insertAfter(new ItemStack(referenceItem, 1), new ItemStack(item, 1)));
	}

	public static void inventoryAddAfter(Block item, Block referenceItem, ResourceKey<CreativeModeTab> tab) {
		CreativeModeTabEvents.modifyOutputEvent(tab).register(c -> c.insertAfter(new ItemStack(referenceItem, 1), new ItemStack(item, 1)));
	}

	public static void inventoryAddBefore(Item item, Item referenceItem, ResourceKey<CreativeModeTab> tab) {
		CreativeModeTabEvents.modifyOutputEvent(tab).register(c -> c.insertBefore(new ItemStack(referenceItem, 1), new ItemStack(item, 1)));
	}

	public static ResourceKey<TrimMaterial> createTrimMaterialResourceKey(String name, String modId) {
		return ResourceKey.create(Registries.TRIM_MATERIAL, Identifier.fromNamespaceAndPath(modId, name));
	}

	public static void setAddAfter(String set, String tools, String armor, ResourceKey<@NotNull CreativeModeTab> tab, String modId) {
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_sword", modId), TEGMatLibUtil.getItemFromKey(tools + "_sword", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_axe", modId), TEGMatLibUtil.getItemFromKey(tools + "_axe", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_spear", modId), TEGMatLibUtil.getItemFromKey(tools + "_spear", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_helmet", modId), TEGMatLibUtil.getItemFromKey(armor + "_boots", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_chestplate", modId), TEGMatLibUtil.getItemFromKey(set + "_helmet", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_leggings", modId), TEGMatLibUtil.getItemFromKey(set + "_chestplate", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_boots", modId), TEGMatLibUtil.getItemFromKey(set + "_leggings", modId), tab);
	}

	public static void toolsAddAfter(String set, String begin, ResourceKey<@NotNull CreativeModeTab> tab, String modId) {
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_axe", modId), TEGMatLibUtil.getItemFromKey(begin + "_hoe", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_pickaxe", modId), TEGMatLibUtil.getItemFromKey(set + "_axe", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_shovel", modId), TEGMatLibUtil.getItemFromKey(set + "_pickaxe", modId), tab);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_hoe", modId), TEGMatLibUtil.getItemFromKey(set + "_shovel", modId), tab);
	}

}
