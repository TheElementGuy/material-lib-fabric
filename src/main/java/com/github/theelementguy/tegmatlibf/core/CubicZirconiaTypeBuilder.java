package com.github.theelementguy.tegmatlibf.core;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * Builder used to construct {@link CubicZirconiaTypeMaterialConfiguration}s. Create a new instance and call methods until ready to call <code>build()</code>.
 */
public class CubicZirconiaTypeBuilder extends MaterialConfigurationBuilder<CubicZirconiaTypeBuilder> {

	protected String RAW_BEFORE;

	@Override
	protected CubicZirconiaTypeBuilder self() {
		return this;
	}

	/**
	 * Sets the position of the material's items in the inventory, in relation to other items.
	 * @param toolsBefore the tool set that this material will be placed after, as a string (for example, "stone")
	 * @param armorBefore the armor set that this material will be placed after, as a string (for example, "chainmail")
	 * @param itemBefore a supplier of the item that the base material will be placed after
	 * @param blockBefore a supplier of the block that the base block will be placed after
	 * @param rawBefore the raw item/block that the raw material/block will be placed after, as a string (for example, "gold")
	 * @param oreBefore the ore that the stone and deepslate ores will be placed after, as a string (for example, "lapis")
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public CubicZirconiaTypeBuilder setBefore(String toolsBefore, String armorBefore, Supplier<Item> itemBefore, Supplier<Block> blockBefore, String rawBefore, String oreBefore) {
		this.TOOLS_BEFORE = toolsBefore;
		this.ARMOR_BEFORE = armorBefore;
		this.ITEM_BEFORE = itemBefore;
		this.BLOCK_BEFORE = blockBefore;
		this.ORE_BEFORE = oreBefore;
		this.RAW_BEFORE = rawBefore;
		return this;
	}

	/**
	 * Builds the {@link CubicZirconiaTypeMaterialConfiguration} that has been specified.
	 * @return the built <code>CubicZirconiaTypeMaterialConfiguration</code> object
	 */
	public CubicZirconiaTypeMaterialConfiguration build() {
		return new CubicZirconiaTypeMaterialConfiguration(MOD_ID, BASE_NAME, HUMAN_READABLE_NAME, TRIM_MATERIAL_DESCRIPTION_COLOR, TOOL_DURABILITY, SPEED, ATTACK_DAMAGE_BONUS, TOOL_ENCHANTMENT, DEFAULT_PROPERTIES, ARMOR_DURABILITY, HEAD_DEFENSE, CHESTPLATE_DEFENSE, SMELTING_EXPERIENCE, LEGGINGS_DEFENSE, BOOTS_DEFENSE, HORSE_DEFENSE, EQUIP_SOUND, TOUGHNESS, KNOCKBACK_RESISTANCE, MAP_COLOR, SOUND_TYPE, ORE_GEN_CONFIGS, DROPS_PER_ORE, EXTRA_DROPS, TIER, MINEABILITY_TIER, TOOLS_BEFORE, ARMOR_BEFORE, ITEM_BEFORE, BLOCK_BEFORE, ORE_BEFORE, RAW_BEFORE, SWING_DURATION, DAMAGE_MULTIPLIER, DELAY, DISMOUNT_MAX_DURATION, DISMOUNT_MIN_SPEED, KNOCKBACK_MAX_DURATION, KNOCKBACK_MIN_SPEED, DAMAGE_MAX_DURATION, DAMAGE_MIN_SPEED, USING_HORSE_ARMOR, USING_NAUTILUS_ARMOR, ANIMAL_ARMOR_BEFORE, LOOT_MODIFIERS, MODEL_EXCEPTIONS);
	}

}
