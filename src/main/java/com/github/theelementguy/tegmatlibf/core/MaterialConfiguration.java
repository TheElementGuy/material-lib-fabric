package com.github.theelementguy.tegmatlibf.core;

import com.github.theelementguy.tegmatlibf.core.component.OptionalComponent;
import com.github.theelementguy.tegmatlibf.core.tier.MineabilityTier;
import com.github.theelementguy.tegmatlibf.core.tier.MiningTier;
import com.github.theelementguy.tegmatlibf.data.ModelException;
import com.github.theelementguy.tegmatlibf.data.ModelExceptionValues;
import com.github.theelementguy.tegmatlibf.item.SpearMaterial;
import com.github.theelementguy.tegmatlibf.loot.LootModifierInfo;
import com.github.theelementguy.tegmatlibf.loot.PreLootModifierInfo;
import com.github.theelementguy.tegmatlibf.util.TEGMatLibUtil;
import com.github.theelementguy.tegmatlibf.worldgen.OreGenHolder;
import com.github.theelementguy.tegmatlibf.worldgen.config.OreGenConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * The base class for material configurations. Do not construct this class; instead, use one of the child classes.
 */
public abstract class MaterialConfiguration {

	protected final String BASE_NAME;
	protected final String MOD_ID;
	protected final String HUMAN_READABLE_NAME;

	protected final Supplier<Item.Properties> DEFAULT_PROPERTIES;

	protected final MaterialType MATERIAL_TYPE;

	protected Item BASE_MATERIAL;

	protected Block BLOCK;

	protected Item SWORD;
	protected Item AXE;
	protected Item PICKAXE;
	protected Item SHOVEL;
	protected Item HOE;
	protected Item SPEAR;
	protected Item HELMET;
	protected Item CHESTPLATE;
	protected Item LEGGINGS;
	protected Item BOOTS;

	protected OptionalComponent<Item> HORSE_ARMOR;
	protected OptionalComponent<Item> NAUTILUS_ARMOR;

	protected final float SMELTING_EXPERIENCE;

	protected Supplier<ResourceKey<@NotNull TrimMaterial>> TRIM_MATERIAL;
	protected Supplier<MaterialAssetGroup> MATERIAL_ASSET_GROUP;

	protected final String TRIM_MATERIAL_DESCRIPTION_COLOR;

	protected Supplier<ToolMaterial> TOOL_MATERIAL;
	protected Supplier<SpearMaterial> SPEAR_MATERIAL;
	protected Supplier<ArmorMaterial> ARMOR_MATERIAL;

	protected Supplier<TagKey<@NotNull Block>> INCORRECT_FOR_MATERIAL;
	protected Supplier<TagKey<@NotNull Block>> NEEDS_MATERIAL;
	protected Supplier<TagKey<@NotNull Item>> REPAIRABLES;

	protected Supplier<ResourceKey<@NotNull EquipmentAsset>> EQUIPMENT_ASSET;

	protected final Supplier<MapColor> MAP_COLOR;
	protected final Supplier<SoundType> SOUND_TYPE;

	protected OreGenHolder<ResourceKey<@NotNull ConfiguredFeature<?, ?>>> CONFIGURED_FEATURE_KEYS;
	protected OreGenHolder<ResourceKey<@NotNull PlacedFeature>> PLACED_FEATURE_KEYS;
	protected final OreGenHolder<OreGenConfig> ORE_GEN_CONFIGS;

	protected final int DROPS_PER_ORE;
	protected final int EXTRA_DROPS;

	protected final MiningTier TIER;
	protected final MineabilityTier MINEABILITY_TIER;

	protected final String TOOLS_BEFORE;
	protected final String ARMOR_BEFORE;
	protected final Supplier<Item> ITEM_BEFORE;
	protected final Supplier<Block> BLOCK_BEFORE;
	protected final String ORE_BEFORE;

	protected final String ANIMAL_ARMOR_BEFORE;

	protected final List<PreLootModifierInfo> LOOT_MODIFIERS;

	protected final List<ModelException> MODEL_EXCEPTIONS;

	protected MaterialConfiguration(String modId, String baseName, String humanReadableName, MaterialType materialType, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<@NotNull SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String toolsBefore, String armorBefore, Supplier<Item> itemBefore, Supplier<Block> blockBefore, String oreBefore, float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed, boolean usingHorseArmor, boolean usingNautilusArmor, String animalArmorBefore, List<PreLootModifierInfo> lootModifiers, List<ModelException> modelExceptions) {
		BASE_NAME = baseName;
		MOD_ID = modId;
		HUMAN_READABLE_NAME = humanReadableName;
		TRIM_MATERIAL_DESCRIPTION_COLOR = trimMaterialDescriptionColor;
		DEFAULT_PROPERTIES = defaultProperties;
		MATERIAL_TYPE = materialType;
		SMELTING_EXPERIENCE = smeltingExperience;
		MAP_COLOR = mapColor;
		SOUND_TYPE = soundType;
		ORE_GEN_CONFIGS = oreGenConfigs;
		DROPS_PER_ORE = dropsPerOre;
		EXTRA_DROPS = extraDrops;
		TIER = tier;
		MINEABILITY_TIER = mineabilityTier;
		TOOLS_BEFORE = toolsBefore;
		ARMOR_BEFORE = armorBefore;
		ITEM_BEFORE = itemBefore;
		BLOCK_BEFORE = blockBefore;
		ORE_BEFORE = oreBefore;
		ANIMAL_ARMOR_BEFORE = animalArmorBefore;
		LOOT_MODIFIERS = lootModifiers;
		MODEL_EXCEPTIONS = modelExceptions;
		INCORRECT_FOR_MATERIAL = () -> TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, "incorrect_for_" + BASE_NAME + "_tool"));
		NEEDS_MATERIAL = () -> TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, "needs_" + BASE_NAME));
		REPAIRABLES = () -> TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, BASE_NAME + "_repairables"));
		EQUIPMENT_ASSET = () -> TEGMatLibUtil.createEquipmentAssetResourceKey(BASE_NAME, MOD_ID);
		TOOL_MATERIAL = () -> new ToolMaterial(INCORRECT_FOR_MATERIAL.get(), toolDurability, speed, attackDamageBonus, enchantmentValue, REPAIRABLES.get());
		ARMOR_MATERIAL = () -> new ArmorMaterial(armorDurability, Util.make(new EnumMap<ArmorType, Integer>(ArmorType.class), attribute -> {
			attribute.put(ArmorType.HELMET, helmetDefense);
			attribute.put(ArmorType.CHESTPLATE, chestplateDefense);
			attribute.put(ArmorType.LEGGINGS, leggingsDefense);
			attribute.put(ArmorType.BOOTS, bootsDefense);
			attribute.put(ArmorType.BODY, horseDefense);
		}), enchantmentValue, equipSound.get(), toughness, knockbackResistance, REPAIRABLES.get(), EQUIPMENT_ASSET.get());
		SPEAR_MATERIAL = () -> new SpearMaterial(swingDuration, damageMultiplier, delay, dismountMaxDuration, dismountMinSpeed, knockbackMaxDuration, knockbackMinSpeed, damageMaxDuration, damageMinSpeed);
		fillTrimMaterialKeys();
		fillConfiguredFeatureKeys();
		fillPlacedFeatureKeys();
		HORSE_ARMOR = OptionalComponent.horseArmor(usingHorseArmor);
		NAUTILUS_ARMOR = OptionalComponent.nautilusArmor(usingNautilusArmor);
	}

	public String getBaseName() {
		return BASE_NAME;
	}

	public MaterialType getType() {
		return MATERIAL_TYPE;
	}

	public abstract void fillItems();

	public abstract void fillBlocks();

	public void fillConfiguredFeatureKeys() {
		CONFIGURED_FEATURE_KEYS = new OreGenHolder<>((ORE_GEN_CONFIGS.hasSmall()) ? () -> TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "small_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.hasMedium()) ? () -> TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "medium_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.hasLarge()) ? () -> TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "large_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.hasExtra()) ? () -> TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "extra_" + BASE_NAME) : null);
	}

	public void fillPlacedFeatureKeys() {
		PLACED_FEATURE_KEYS = new OreGenHolder<>((ORE_GEN_CONFIGS.hasSmall()) ? () -> TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "small_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.hasMedium()) ? () -> TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "medium_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.hasLarge()) ? () -> TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "large_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.hasExtra()) ? () -> TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "extra_" + BASE_NAME + "_ore_placed") : null);
	}

	public abstract List<OreConfiguration.TargetBlockState> getOreStates();

	public void addConfiguredFeatureEntries(FabricDynamicRegistryProvider.Entries entries) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.addConfiguredFeatureEntry(entries, getOreStates(), CONFIGURED_FEATURE_KEYS.getSmall().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.addConfiguredFeatureEntry(entries, getOreStates(), CONFIGURED_FEATURE_KEYS.getMedium().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.addConfiguredFeatureEntry(entries, getOreStates(), CONFIGURED_FEATURE_KEYS.getLarge().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.addConfiguredFeatureEntry(entries, getOreStates(), CONFIGURED_FEATURE_KEYS.getExtra().get());});
	}

	public void addPlacedFeatureEntries(FabricDynamicRegistryProvider.Entries entries) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.addPlacedFeatureEntry(entries, PLACED_FEATURE_KEYS.getSmall().get(), CONFIGURED_FEATURE_KEYS.getSmall().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.addPlacedFeatureEntry(entries, PLACED_FEATURE_KEYS.getMedium().get(), CONFIGURED_FEATURE_KEYS.getMedium().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.addPlacedFeatureEntry(entries, PLACED_FEATURE_KEYS.getLarge().get(), CONFIGURED_FEATURE_KEYS.getLarge().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.addPlacedFeatureEntry(entries, PLACED_FEATURE_KEYS.getExtra().get(), CONFIGURED_FEATURE_KEYS.getExtra().get());});
	}

	public void applyBiomeModifiers() {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {
			BiomeModifications.addFeature(oreConfig.getPredicate(), GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_FEATURE_KEYS.getSmall().get());
		});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {
			BiomeModifications.addFeature(oreConfig.getPredicate(), GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_FEATURE_KEYS.getMedium().get());
		});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {
			BiomeModifications.addFeature(oreConfig.getPredicate(), GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_FEATURE_KEYS.getLarge().get());
		});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {
			BiomeModifications.addFeature(oreConfig.getPredicate(), GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_FEATURE_KEYS.getExtra().get());
		});
	}

	public void registerConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getSmall().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getMedium().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getLarge().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getExtra().get());});
	}

	public void registerPlacedFeatures(BootstrapContext<PlacedFeature> context) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getSmall().get(), CONFIGURED_FEATURE_KEYS.getSmall().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getMedium().get(), CONFIGURED_FEATURE_KEYS.getMedium().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getLarge().get(), CONFIGURED_FEATURE_KEYS.getLarge().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getExtra().get(), CONFIGURED_FEATURE_KEYS.getExtra().get());});
	}

	protected Item registerSimpleItem(String name) {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(name, MOD_ID), new Item(DEFAULT_PROPERTIES.get().setId(TEGMatLibUtil.createItemResourceKey(name, MOD_ID))));
	}

	protected Item registerSimpleItemWithTrimMaterial(String name) {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(name, MOD_ID), new Item(DEFAULT_PROPERTIES.get().trimMaterial(TRIM_MATERIAL.get()).setId(TEGMatLibUtil.createItemResourceKey(name, MOD_ID))));
	}

	protected Block registerSimpleBlock(String name, float destroyTime, float explosionResistance, MapColor color, SoundType soundType) {
		Block blockToReturn = Registry.register(BuiltInRegistries.BLOCK, TEGMatLibUtil.createBlockResourceKey(name, MOD_ID), new Block(BlockBehaviour.Properties.of().destroyTime(destroyTime).explosionResistance(explosionResistance).mapColor(color).sound(soundType).requiresCorrectToolForDrops().setId(TEGMatLibUtil.createBlockResourceKey(name, MOD_ID))));
		Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(name, MOD_ID), new BlockItem(blockToReturn, DEFAULT_PROPERTIES.get().setId(TEGMatLibUtil.createItemResourceKey(name, MOD_ID)).useBlockDescriptionPrefix()));
		return blockToReturn;
	}

	protected Item registerSword() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_sword", MOD_ID), new Item(DEFAULT_PROPERTIES.get().sword(TOOL_MATERIAL.get(), 3.0f, -2.4f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_sword", MOD_ID))));
	}

	protected Item registerAxe() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_axe", MOD_ID), new Item(DEFAULT_PROPERTIES.get().axe(TOOL_MATERIAL.get(), 6.0f, -3.1f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_axe", MOD_ID))));
	}

	protected Item registerPickaxe() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_pickaxe", MOD_ID), new Item(DEFAULT_PROPERTIES.get().pickaxe(TOOL_MATERIAL.get(), 1.0f, -2.0f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_pickaxe", MOD_ID))));
	}

	protected Item registerShovel() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_shovel", MOD_ID), new Item(DEFAULT_PROPERTIES.get().shovel(TOOL_MATERIAL.get(), 1.5f, -3f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_shovel", MOD_ID))));
	}

	protected Item registerHoe() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_hoe", MOD_ID), new Item(DEFAULT_PROPERTIES.get().hoe(TOOL_MATERIAL.get(), -2f, -1f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_hoe", MOD_ID))));
	}

	protected Item registerSpear() {
		SpearMaterial material = SPEAR_MATERIAL.get();
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_spear", MOD_ID), new Item(DEFAULT_PROPERTIES.get().spear(TOOL_MATERIAL.get(), material.swingDuration(), material.damageMultiplier(), material.delay(), material.dismountMaxDuration(), material.dismountMinSpeed(), material.knockbackMaxDuration(), material.knockbackMinSpeed(), material.damageMaxDuration(), material.damageMinSpeed()).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_spear", MOD_ID))));
	}

	protected Item registerHelmet() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_helmet", MOD_ID), new Item(DEFAULT_PROPERTIES.get().humanoidArmor(ARMOR_MATERIAL.get(), ArmorType.HELMET).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_helmet", MOD_ID))));
	}

	protected Item registerChestplate() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_chestplate", MOD_ID), new Item(DEFAULT_PROPERTIES.get().humanoidArmor(ARMOR_MATERIAL.get(), ArmorType.CHESTPLATE).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_chestplate", MOD_ID))));
	}

	protected Item registerLeggings() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_leggings", MOD_ID), new Item(DEFAULT_PROPERTIES.get().humanoidArmor(ARMOR_MATERIAL.get(), ArmorType.LEGGINGS).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_leggings", MOD_ID))));
	}

	protected Item registerBoots() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_boots", MOD_ID), new Item(DEFAULT_PROPERTIES.get().humanoidArmor(ARMOR_MATERIAL.get(), ArmorType.BOOTS).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_boots", MOD_ID))));
	}

	protected Item registerHorseArmor() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_horse_armor", MOD_ID), new Item(DEFAULT_PROPERTIES.get().horseArmor(ARMOR_MATERIAL.get()).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_horse_armor", MOD_ID))));
	}

	protected Item registerNautilusArmor() {
		return Registry.register(BuiltInRegistries.ITEM, TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_nautilus_armor", MOD_ID), new Item(DEFAULT_PROPERTIES.get().nautilusArmor(ARMOR_MATERIAL.get()).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_nautilus_armor", MOD_ID))));
	}

	protected void fillBaseEquipment() {
		SWORD = registerSword();
		AXE = registerAxe();
		PICKAXE = registerPickaxe();
		SHOVEL = registerShovel();
		HOE = registerHoe();
		SPEAR = registerSpear();

		HELMET = registerHelmet();
		CHESTPLATE = registerChestplate();
		LEGGINGS = registerLeggings();
		BOOTS = registerBoots();

		HORSE_ARMOR.fillIfUsing(this::registerHorseArmor);
		NAUTILUS_ARMOR.fillIfUsing(this::registerNautilusArmor);
	}

	protected void fillBaseBlock() {

		BLOCK = registerSimpleBlock(BASE_NAME + "_block", 5f, 6f, MAP_COLOR.get(), SOUND_TYPE.get());

	}

	public Block getBaseBlock() {
		return BLOCK;
	}

	public MiningTier getMiningLevel() {
		return TIER;
	}

	public TagKey<Block> getIncorrectForMaterial() {
		return INCORRECT_FOR_MATERIAL.get();
	}

	public TagKey<Block> getNeedsMaterial() {
		return NEEDS_MATERIAL.get();
	}

	public boolean isSingleOre() {
		return DROPS_PER_ORE == 1;
	}

	public int getMaxDrops() {
		return DROPS_PER_ORE + EXTRA_DROPS;
	}

	public int getBaseDrops() {
		return DROPS_PER_ORE;
	}

	public abstract List<Block> getBlocks();

	public Item getSword() {
		return SWORD;
	}

	public Item getAxe() {
		return AXE;
	}

	public Item getPickaxe() {
		return PICKAXE;
	}

	public Item getShovel() {
		return SHOVEL;
	}

	public Item getHoe() {
		return HOE;
	}

	public Item getSpear() {
		return SPEAR;
	}

	public TagKey<Item> getRepairables() {
		return REPAIRABLES.get();
	}

	public Item getHelmet() {
		return HELMET;
	}

	public Item getChestplate() {
		return CHESTPLATE;
	}

	public Item getLeggings() {
		return LEGGINGS;
	}

	public Item getBoots() {
		return BOOTS;
	}

	public Item getBaseItem() {
		return BASE_MATERIAL.asItem();
	}

	public void bootstrapTrimMaterial(BootstrapContext<TrimMaterial> context) {
		context.register(TRIM_MATERIAL.get(), new TrimMaterial(MATERIAL_ASSET_GROUP.get(), Component.translatable(Util.makeDescriptionId("trim_material", TRIM_MATERIAL.get().identifier())).withStyle(Style.EMPTY.withColor(TextColor.parseColor(TRIM_MATERIAL_DESCRIPTION_COLOR).getOrThrow()))));
	}

	public void addTrimMaterialEntry(FabricDynamicRegistryProvider.Entries entries) {
		entries.add(TRIM_MATERIAL.get(), new TrimMaterial(MATERIAL_ASSET_GROUP.get(), Component.translatable(Util.makeDescriptionId("trim_material", TRIM_MATERIAL.get().identifier())).withStyle(Style.EMPTY.withColor(TextColor.parseColor(TRIM_MATERIAL_DESCRIPTION_COLOR).getOrThrow()))));
	}

	public void fillTrimMaterialKeys() {
		TRIM_MATERIAL = () -> TEGMatLibUtil.createTrimMaterialResourceKey(BASE_NAME, MOD_ID);
		MATERIAL_ASSET_GROUP = () -> MaterialAssetGroup.create(BASE_NAME);
	}

	public MaterialAssetGroup getMaterialAssetGroup() {
		return MATERIAL_ASSET_GROUP.get();
	}

	public ResourceKey<TrimMaterial> getTrimMaterial() {
		return TRIM_MATERIAL.get();
	}

	public ResourceKey<EquipmentAsset> getEquipmentAsset() {
		return EQUIPMENT_ASSET.get();
	}

	public String getHumanReadableName() {
		return HUMAN_READABLE_NAME;
	}

	public float getSmeltingExperience() {
		return SMELTING_EXPERIENCE;
	}

	public String getToolsBefore() {
		return TOOLS_BEFORE;
	}

	public String getArmorBefore() {
		return ARMOR_BEFORE;
	}

	public Item getItemBefore() {
		return ITEM_BEFORE.get();
	}

	public Block getBlockBefore() {
		return BLOCK_BEFORE.get();
	}

	public String getOreBefore() {
		return ORE_BEFORE;
	}

	public MineabilityTier getMineabilityLevel() {
		return MINEABILITY_TIER;
	}

	public OptionalComponent<Item> getNautilusArmor() {
		return NAUTILUS_ARMOR;
	}

	public OptionalComponent<Item> getHorseArmor() {
		return HORSE_ARMOR;
	}

	public String getAnimalArmorBefore() {
		return ANIMAL_ARMOR_BEFORE;
	}

	public List<LootModifierInfo> getLootModifiers() {
		return LOOT_MODIFIERS.stream().map((modifier) -> {return modifier.convert(this);}).toList();
	}

	public ModelExceptionValues applyException(String name, ModelExceptionValues preferred) {
		for (ModelException m : MODEL_EXCEPTIONS) {
			if (m.name().equals(name)) {
				return m.overrideTemplate();
			}
		}
		return preferred;
	}

	public String getModID() {
		return MOD_ID;
	}
}
