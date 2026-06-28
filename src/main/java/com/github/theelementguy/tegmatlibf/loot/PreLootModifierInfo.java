package com.github.theelementguy.tegmatlibf.loot;

import com.github.theelementguy.tegmatlibf.core.CubicZirconiaTypeMaterialConfiguration;
import com.github.theelementguy.tegmatlibf.core.IronTypeMaterialConfiguration;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import com.github.theelementguy.tegmatlibf.core.MaterialType;

public record PreLootModifierInfo(LootItemSlot slot, LootModifierType type, String table, float chance) {
	public LootModifierInfo convert(MaterialConfiguration config) {
		switch (slot) {
			case AXE -> {
				return new LootModifierInfo(type, table, config.getAxe(), chance);
			}
			case BOOTS -> {
				return new LootModifierInfo(type, table, config.getBoots(), chance);
			}
			case RAW -> {
				if (config.getType() == MaterialType.CUBIC_ZIRCONIA) {
					return new LootModifierInfo(type, table, ((CubicZirconiaTypeMaterialConfiguration) config).getRawItem(), chance);
				} else if (config.getType() == MaterialType.IRON) {
					return new LootModifierInfo(type, table, ((IronTypeMaterialConfiguration) config).getRawItem(), chance);
				}
			}
			case CHESTPLATE -> {
				return new LootModifierInfo(type, table, config.getChestplate(), chance);
			}
			case PICKAXE -> {
				return new LootModifierInfo(type, table, config.getPickaxe(), chance);
			}
			case SWORD -> {
				return new LootModifierInfo(type, table, config.getSword(), chance);
			}
			case SHOVEL -> {
				return new LootModifierInfo(type, table, config.getShovel(), chance);
			}
			case HOE -> {
				return new LootModifierInfo(type, table, config.getHoe(), chance);
			}
			case LEGGINGS -> {
				return new LootModifierInfo(type, table, config.getLeggings(), chance);
			}
			case HELMET -> {
				return new LootModifierInfo(type, table, config.getHelmet(), chance);
			}
			case BLOCK -> {
				return new LootModifierInfo(type, table, config.getBaseBlock().asItem(), chance);
			}
			case HORSE_ARMOR -> {
				return new LootModifierInfo(type, table, config.getHorseArmor().get().get(), chance);
			}
			default -> {
				return new LootModifierInfo(type, table, config.getBaseItem(), chance);
			}
		}
		return new LootModifierInfo(type, table, config.getBaseItem(), chance);
	}

}
