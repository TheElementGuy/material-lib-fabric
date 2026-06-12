package com.github.theelementguy.tegmatlibf.core;

import com.github.theelementguy.tegmatlibf.core.tier.MineabilityTier;
import com.github.theelementguy.tegmatlibf.core.tier.MiningTier;
import com.github.theelementguy.tegmatlibf.data.ModelException;
import com.github.theelementguy.tegmatlibf.data.ModelExceptionValues;
import com.github.theelementguy.tegmatlibf.loot.LootItemSlot;
import com.github.theelementguy.tegmatlibf.loot.LootModifierType;
import com.github.theelementguy.tegmatlibf.loot.PreLootModifierInfo;
import com.github.theelementguy.tegmatlibf.worldgen.OreGenHolder;
import com.github.theelementguy.tegmatlibf.worldgen.config.OreGenConfig;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class MaterialConfigurationBuilder<T extends MaterialConfigurationBuilder<T>> {

	protected String BASE_NAME;
	protected String MOD_ID;
	protected String HUMAN_READABLE_NAME;

	protected Supplier<Item.Properties> DEFAULT_PROPERTIES = Item.Properties::new;

	protected float SMELTING_EXPERIENCE;

	protected String TRIM_MATERIAL_DESCRIPTION_COLOR;

	protected Supplier<MapColor> MAP_COLOR;
	protected Supplier<SoundType> SOUND_TYPE;
	protected OreGenHolder<OreGenConfig> ORE_GEN_CONFIGS;

	protected int DROPS_PER_ORE = 1;
	protected int EXTRA_DROPS = 0;

	protected MiningTier TIER;
	protected MineabilityTier MINEABILITY_TIER = MineabilityTier.DEFAULT;

	protected int TOOL_DURABILITY;
	protected float SPEED;
	protected float ATTACK_DAMAGE_BONUS;
	protected int TOOL_ENCHANTMENT;

	protected float SWING_DURATION;
	protected float DELAY;
	protected float DAMAGE_MULTIPLIER;
	protected float DISMOUNT_MAX_DURATION;
	protected float DISMOUNT_MIN_SPEED;
	protected float KNOCKBACK_MAX_DURATION;
	protected float KNOCKBACK_MIN_SPEED;
	protected float DAMAGE_MAX_DURATION;
	protected float DAMAGE_MIN_SPEED;

	protected int ARMOR_DURABILITY;
	protected int HEAD_DEFENSE;
	protected int CHESTPLATE_DEFENSE;
	protected int LEGGINGS_DEFENSE;
	protected int BOOTS_DEFENSE;
	protected int HORSE_DEFENSE;
	protected int ARMOR_ENCHANTMENT;
	protected float TOUGHNESS = 0f;
	protected float KNOCKBACK_RESISTANCE = 0f;
	protected Supplier<Holder<SoundEvent>> EQUIP_SOUND;

	protected String TOOLS_BEFORE;
	protected String ARMOR_BEFORE;
	protected Supplier<Item> ITEM_BEFORE;
	protected Supplier<Block> BLOCK_BEFORE;
	protected String ORE_BEFORE;

	protected boolean USING_HORSE_ARMOR = false;
	protected boolean USING_NAUTILUS_ARMOR = false;

	protected String ANIMAL_ARMOR_BEFORE = null;

	protected List<PreLootModifierInfo> LOOT_MODIFIERS = new ArrayList<>();

	protected List<ModelException> MODEL_EXCEPTIONS = new ArrayList<>();

	/**
	 * Sets the mod ID for the material.
	 * @param modId your mod ID
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T modId(String modId) {
		MOD_ID = modId;
		return self();
	}

	/**
	 * Sets the base name for the material. self() should only use lowercase letters and underscores.
	 * @param name the name to be used
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T baseName(String name) {
		BASE_NAME = name;
		return self();
	}

	/**
	 * Sets the in-game name for the material. self() will be capitalized appropriately.
	 * @param name the in-game name to be used
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T inGameName(String name) {
		HUMAN_READABLE_NAME = name;
		return self();
	}

	/**
	 * Sets the default properties of the items that are created (e.g. fire-proof).
	 * @param properties a supplier of the default properties (for example, <code>() -> new Item.Properties().fireResistant()</code>
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T defaultProperties(Supplier<Item.Properties> properties) {
		DEFAULT_PROPERTIES = properties;
		return self();
	}

	/**
	 * Sets the parameters for the tool material used for all tools of self() material.
	 * @param durability the number of uses
	 * @param speed the efficiency, with higher numbers being faster
	 * @param attackDamageBonus extra damage added to all tools
	 * @param enchantmentValue how easily enchantable the tools are, with higher numbers being more enchantable
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T toolMaterial(int durability, float speed, float attackDamageBonus, int enchantmentValue) {
		TOOL_DURABILITY = durability;
		SPEED = speed;
		ATTACK_DAMAGE_BONUS = attackDamageBonus;
		TOOL_ENCHANTMENT = enchantmentValue;
		return self();
	}

	/**
	 * Sets the parameters for the armor material used for all armor pieces of the material.
	 * @param durability the number of hits that can be taken (approximately)
	 * @param helmetDefense armor points from the helmet
	 * @param chestplateDefense armor points from the chestplate
	 * @param leggingsDefense armor points from the leggings
	 * @param bootsDefense armor points from the boots
	 * @param horseDefense armor points from the horse armor (note that horse armor is not created by self() library)
	 * @param enchantmentValue how easily enchantable the armor pieces are, with higher numbers being more enchantable
	 * @param equipSound supplier of a <code>SoundEvent</code> that dictates in-game equip sound
	 * @param toughness an additional source of defense
	 * @param knockbackResistance sets a dampener on how far knockback launches the user, with higher being more protective
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance) {
		ARMOR_DURABILITY = durability;
		HEAD_DEFENSE = helmetDefense;
		CHESTPLATE_DEFENSE = chestplateDefense;
		LEGGINGS_DEFENSE = leggingsDefense;
		BOOTS_DEFENSE = bootsDefense;
		HORSE_DEFENSE = horseDefense;
		ARMOR_ENCHANTMENT = enchantmentValue;
		EQUIP_SOUND = equipSound;
		TOUGHNESS = toughness;
		KNOCKBACK_RESISTANCE = knockbackResistance;
		return self();
	}

	/**
	 * Sets the parameters for the armor material used for all armor pieces of the material. Toughness and knockback resistance are assumed to be zero.
	 * @param durability the number of hits that can be taken (approximately)
	 * @param helmetDefense armor points from the helmet
	 * @param chestplateDefense armor points from the chestplate
	 * @param leggingsDefense armor points from the leggings
	 * @param bootsDefense armor points from the boots
	 * @param horseDefense armor points from the horse armor (note that horse armor is not created by self() library)
	 * @param enchantmentValue how easily enchantable the armor pieces are, with higher numbers being more enchantable
	 * @param equipSound supplier of a <code>SoundEvent</code> that dictates in-game equip sound
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound) {
		ARMOR_DURABILITY = durability;
		HEAD_DEFENSE = helmetDefense;
		CHESTPLATE_DEFENSE = chestplateDefense;
		LEGGINGS_DEFENSE = leggingsDefense;
		BOOTS_DEFENSE = bootsDefense;
		HORSE_DEFENSE = horseDefense;
		ARMOR_ENCHANTMENT = enchantmentValue;
		EQUIP_SOUND = equipSound;
		return self();
	}

	/**
	 * Sets the amount of experience gained by smelting the raw form or ore
	 * @param experience the amount of experience to be gained
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T smeltingExperience(float experience) {
		SMELTING_EXPERIENCE = experience;
		return self();
	}

	/**
	 * Sets the properties used for the block.
	 * @param color a supplier of the <code>MapColor</code> used on a map
	 * @param stepSound a supplier of the <code>SoundType</code> corresponding to the noise made from stepping on the block
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T blockProperties(Supplier<MapColor> color, Supplier<SoundType> stepSound) {
		MAP_COLOR = color;
		SOUND_TYPE = stepSound;
		return self();
	}

	/**
	 * Sets the parameters for the spear. Note that I do not know what most of these do; see the vanilla code for typical values.
	 * @param swingDuration sets the swing duration
	 * @param damageMultiplier sets the damage multiplier
	 * @param delay sets the delay
	 * @param dismountMaxDuration sets the maximum duration for dismount
	 * @param dismountMinSpeed sets the minimum speed for dismount
	 * @param knockbackMaxDuration sets the maximum duration for knockback
	 * @param knockbackMinSpeed sets the minimum speed for knockback
	 * @param damageMaxDuration sets the maximum duration for damage
	 * @param damageMinSpeed sets the minimum speed for damage
	 * @return the updated <code>Builder</code>
	 */
	public T spearMaterial(float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed) {
		this.SWING_DURATION = swingDuration;
		this.DAMAGE_MULTIPLIER = damageMultiplier;
		this.DELAY = delay;
		this.DISMOUNT_MAX_DURATION = dismountMaxDuration;
		this.DISMOUNT_MIN_SPEED = dismountMinSpeed;
		this.KNOCKBACK_MAX_DURATION = knockbackMaxDuration;
		this.KNOCKBACK_MIN_SPEED = knockbackMinSpeed;
		this.DAMAGE_MAX_DURATION = damageMaxDuration;
		this.DAMAGE_MIN_SPEED = damageMinSpeed;
		return self();
	}

	/**
	 * Sets the {@link OreGenConfig}s for the material, with a small, medium, large, and extra.
	 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
	 * @param medium a supplier for the <code>OreGenConfig</code> corresponding to the medium vein
	 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
	 * @param extra a supplier for the <code>OreGenConfig</code> corresponding to the extra vein
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T oreConfigAll(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
		ORE_GEN_CONFIGS = new OreGenHolder<>(small, medium, large, extra);
		return self();
	}

	/**
	 * Sets the {@link OreGenConfig}s for the material, with a small, medium, and large.
	 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
	 * @param medium a supplier for the <code>OreGenConfig</code> corresponding to the medium vein
	 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T oreConfigNoExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large) {
		ORE_GEN_CONFIGS = new OreGenHolder<>(small, medium, large, null);
		return self();
	}

	/**
	 * Sets the {@link OreGenConfig}s for the material, with a small and large.
	 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
	 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T oreConfigSimple(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large) {
		ORE_GEN_CONFIGS = new OreGenHolder<>(small, null, large, null);
		return self();
	}

	/**
	 * Sets the {@link OreGenConfig}s for the material, with a small, large, and extra.
	 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
	 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
	 * @param extra a supplier for the <code>OreGenConfig</code> corresponding to the extra vein
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T oreConfigSimpleWithExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
		ORE_GEN_CONFIGS = new OreGenHolder<OreGenConfig>(small, null, large, extra);
		return self();
	}

	/**
	 * Sets the number of drops per ore, with no variation
	 * @param drops the number of drops per ore
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T dropsPerOre(int drops) {
		DROPS_PER_ORE = drops;
		return self();
	}

	/**
	 * Sets the number of drops per ore, between a minimum and maximum, inclusive.
	 * @param min the minimum number of drops per ore
	 * @param max the maximum number of drops per ore
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T dropsPerOreMinMax(int min, int max) {
		DROPS_PER_ORE = min;
		EXTRA_DROPS = max - min;
		return self();
	}

	/**
	 * Sets the number of drops per ore, with a minimum base number of drops, and maximum extra added on
	 * @param baseDrops the minimum, base number of drops
	 * @param extra a maximum attainable extra number of drops
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T dropsPerOre(int baseDrops, int extra) {
		DROPS_PER_ORE = baseDrops;
		EXTRA_DROPS = extra;
		return self();
	}

	/**
	 * Sets the mining tier of the material. Note that the mineability tier will be set to <code>MineabilityTier.DEFAULT</code>.
	 * @param tier the mining tier of the material. self() is the level that the tools can mine.
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T tier(MiningTier tier) {
		TIER = tier;
		return self();
	}

	/**
	 * Sets the mining tier and mineability tier of the material.
	 * @param miningTier the mining tier of the material. self() is the level that the tools can mine.
	 * @param mineabilityTier the mineability tier of the material. self() is the level needed to mine the ores and other blocks.
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T tier(MiningTier miningTier, MineabilityTier mineabilityTier) {
		TIER = miningTier;
		MINEABILITY_TIER = mineabilityTier;
		return self();
	}

	/**
	 * Sets the color that the trim material shows up in an armor pieces description.
	 * @param colorHex the color, as a hex code string
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T trimMaterialDescriptionColor(String colorHex) {
		TRIM_MATERIAL_DESCRIPTION_COLOR = colorHex;
		return self();
	}

	/**
	 * Flags for the use of horse armor.
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T usingHorseArmor() {
		USING_HORSE_ARMOR = true;
		return self();
	}

	/**
	 * Flags for the use of nautilus armor.
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T usingNautilusArmor() {
		USING_NAUTILUS_ARMOR = true;
		return self();
	}

	/**
	 * Sets the animal armor preceeding the material's animal armor in the inventory.
	 * @param animalArmorBefore the raw string for the animal armor coming before (for example, "iron")
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T animalArmorBefore(String animalArmorBefore) {
		ANIMAL_ARMOR_BEFORE = animalArmorBefore;
		return self();
	}

	/**
	 * Adds a loot modifier.
	 * @param slot the item to add
	 * @param type either <code>LootModifierType.ADD</code> or <code>LootModifierType.EXTRA</code>. Sets whether to give the item (<code>ADD</code>), or add the item to the current loot (<code>EXTRA</code>).
	 * @param table a string representing the table to add to
	 * @param chance the chance that the item will be added
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T addLoot(LootItemSlot slot, LootModifierType type, String table, float chance) {
		LOOT_MODIFIERS.add(new PreLootModifierInfo(slot, type, table, chance));
		return self();
	}

	/**
	 * Adds a model exception. This will override the default model for the given block. This will not apply to ores.
	 * @param name the ID of the block that is being overridden.
	 * @param overrideTemplate the new {@link ModelExceptionValues} to use
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public T addModelException(String name, ModelExceptionValues overrideTemplate) {
		MODEL_EXCEPTIONS.add(new ModelException(name, overrideTemplate));
		return self();
	}

	protected abstract T self();

}
