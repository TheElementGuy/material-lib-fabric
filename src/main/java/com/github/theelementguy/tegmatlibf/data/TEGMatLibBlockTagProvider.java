package com.github.theelementguy.tegmatlibf.data;

import com.github.theelementguy.tegmatlibf.core.*;
import com.github.theelementguy.tegmatlibf.core.tier.MineabilityTier;
import com.github.theelementguy.tegmatlibf.core.tier.MiningTier;
import com.github.theelementguy.tegmatlibf.util.TEGMatLibUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

	private Supplier<List<MaterialConfiguration>> MATERIALS;
	
	private final TagKey<Block> NEEDS_WOOD_TOOL;
	private final TagKey<Block> NEEDS_NETHERITE_TOOL;

	public TEGMatLibBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider, FullyConfiguredMaterialHolder materials) {
		super(output, provider);
		MATERIALS = materials::getMaterials;

		NEEDS_WOOD_TOOL = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(materials.getModID(), "needs_tool_level_4"));

		 NEEDS_NETHERITE_TOOL = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(materials.getModID(), "needs_tool_level_0"));
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			addMaterial(config);
		}

	}

	protected void addMaterial(MaterialConfiguration material) {
		
		builder(ConventionalBlockTags.STORAGE_BLOCKS).add(material.getBaseBlock().properties().blockId());
		MineabilityTier tier = (material.getMineabilityLevel() == MineabilityTier.DEFAULT) ? getMineability(material.getMiningLevel()) : material.getMineabilityLevel();
		switch (material.getType()) {
			case IRON -> {
				IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_NETHERITE_TOOL);
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).removeTag(mat.getNeedsMaterial());
				} else {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(getNeedsTagForMineability(tier)).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(getTagForTierIncorrect(mat.getMiningLevel()));
					builder(mat.getNeedsMaterial()).addOptionalTag(getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					builder(ConventionalBlockTags.ORE_RATES_SINGULAR).add(mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
				} else {
					builder(ConventionalBlockTags.ORE_RATES_DENSE).add(mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
				}
				builder(ConventionalBlockTags.ORES_IN_GROUND_STONE).add(mat.getOre().properties().blockId());
				builder(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE).add(mat.getDeepslateOre().properties().blockId());
			}
			case DIAMOND -> {
				DiamondTypeMaterialConfiguration mat = (DiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_NETHERITE_TOOL);
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).removeTag(mat.getNeedsMaterial());
				} else {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(getNeedsTagForMineability(tier)).add(mat.getBaseBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(getTagForTierIncorrect(mat.getMiningLevel()));
					builder(mat.getNeedsMaterial()).addOptionalTag(getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					builder(ConventionalBlockTags.ORE_RATES_SINGULAR).add(mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
				} else {
					builder(ConventionalBlockTags.ORE_RATES_DENSE).add(mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
				}
				builder(ConventionalBlockTags.ORES_IN_GROUND_STONE).add(mat.getOre().properties().blockId());
				builder(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE).add(mat.getDeepslateOre().properties().blockId());
			}
			case CUBIC_ZIRCONIA -> {
				CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_NETHERITE_TOOL);
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).removeTag(mat.getNeedsMaterial());
				} else {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(getNeedsTagForMineability(tier)).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(getTagForTierIncorrect(mat.getMiningLevel()));
					builder(mat.getNeedsMaterial()).addOptionalTag(getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					builder(ConventionalBlockTags.ORE_RATES_SINGULAR).add(mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
				} else {
					builder(ConventionalBlockTags.ORE_RATES_DENSE).add(mat.getOre().properties().blockId(), mat.getDeepslateOre().properties().blockId());
				}
				builder(ConventionalBlockTags.ORES_IN_GROUND_STONE).add(mat.getOre().properties().blockId());
				builder(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE).add(mat.getDeepslateOre().properties().blockId());
			}
			case NETHER_DIAMOND -> {
				NetherDiamondTypeMaterialConfiguration mat = (NetherDiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getNetherOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getNetherOre().properties().blockId());
					builder(NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock().properties().blockId(), mat.getNetherOre().properties().blockId());
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_NETHERITE_TOOL);
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).removeTag(mat.getNeedsMaterial());
				} else {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getNetherOre().properties().blockId());
					builder(getNeedsTagForMineability(tier)).add(mat.getBaseBlock().properties().blockId(), mat.getNetherOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(getTagForTierIncorrect(mat.getMiningLevel()));
					builder(mat.getNeedsMaterial()).addOptionalTag(getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					builder(ConventionalBlockTags.ORE_RATES_SINGULAR).add(mat.getNetherOre().properties().blockId());
				} else {
					builder(ConventionalBlockTags.ORE_RATES_DENSE).add(mat.getNetherOre().properties().blockId());
				}
				builder(ConventionalBlockTags.ORES_IN_GROUND_NETHERRACK).add(mat.getNetherOre().properties().blockId());
			}
			case END_DIAMOND -> {
				EndDiamondTypeMaterialConfiguration mat = (EndDiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_NETHERITE_TOOL);
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).removeTag(mat.getNeedsMaterial());
				} else {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(getNeedsTagForMineability(tier)).add(mat.getBaseBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(getTagForTierIncorrect(mat.getMiningLevel()));
					builder(mat.getNeedsMaterial()).addOptionalTag(getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					builder(ConventionalBlockTags.ORE_RATES_SINGULAR).add(mat.getEndOre().properties().blockId());
				} else {
					builder(ConventionalBlockTags.ORE_RATES_DENSE).add(mat.getEndOre().properties().blockId());
				}
			}
			case END_IRON -> {
				EndIronTypeMaterialConfiguration mat = (EndIronTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_NETHERITE_TOOL);
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).removeTag(mat.getNeedsMaterial());
				} else {
					builder(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(getNeedsTagForMineability(tier)).add(mat.getBaseBlock().properties().blockId(), mat.getRawBlock().properties().blockId(), mat.getEndOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(getTagForTierIncorrect(mat.getMiningLevel()));
					builder(mat.getNeedsMaterial()).addOptionalTag(getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					builder(ConventionalBlockTags.ORE_RATES_SINGULAR).add(mat.getEndOre().properties().blockId());
				} else {
					builder(ConventionalBlockTags.ORE_RATES_DENSE).add(mat.getEndOre().properties().blockId());
				}
			}
			case SAND_DIAMOND -> {
				SandDiamondTypeMaterialConfiguration mat = (SandDiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					builder(BlockTags.MINEABLE_WITH_SHOVEL).add(mat.getBaseBlock().properties().blockId(), mat.getSandOre().properties().blockId(), mat.getGravelOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					builder(BlockTags.MINEABLE_WITH_SHOVEL).add(mat.getBaseBlock().properties().blockId(), mat.getSandOre().properties().blockId(), mat.getGravelOre().properties().blockId());
					builder(NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock().properties().blockId(), mat.getSandOre().properties().blockId(), mat.getGravelOre().properties().blockId());
					builder(mat.getNeedsMaterial()).addOptionalTag(NEEDS_NETHERITE_TOOL);
					builder(mat.getIncorrectForMaterial()).addOptionalTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).removeTag(mat.getNeedsMaterial());
				} else {
					builder(BlockTags.MINEABLE_WITH_SHOVEL).add(mat.getBaseBlock().properties().blockId(), mat.getSandOre().properties().blockId(), mat.getGravelOre().properties().blockId());
					builder(getNeedsTagForMineability(tier)).add(mat.getBaseBlock().properties().blockId(), mat.getSandOre().properties().blockId(), mat.getGravelOre().properties().blockId());
					builder(mat.getIncorrectForMaterial()).addOptionalTag(getTagForTierIncorrect(mat.getMiningLevel()));
					builder(mat.getNeedsMaterial()).addOptionalTag(getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					builder(ConventionalBlockTags.ORE_RATES_SINGULAR).add(mat.getSandOre().properties().blockId(), mat.getGravelOre().properties().blockId());
				} else {
					builder(ConventionalBlockTags.ORE_RATES_DENSE).add(mat.getSandOre().properties().blockId(), mat.getGravelOre().properties().blockId());
				}
			}
		}

	}

	public TagKey<Block> getTagForTierUnder(MiningTier tier) {
		return switch (tier) {
			case WOOD -> {throw new IllegalArgumentException("Mining level of wood not permitted. Such a material does not need to be mined with a pickaxe.");}
			case STONE -> NEEDS_WOOD_TOOL;
			case IRON -> BlockTags.NEEDS_STONE_TOOL;
			case DIAMOND -> BlockTags.NEEDS_IRON_TOOL;
			case NETHERITE -> BlockTags.NEEDS_DIAMOND_TOOL;
			case BEYOND_NETHERITE -> NEEDS_NETHERITE_TOOL;
		};
	}

	public TagKey<Block> getTagForTierIncorrect(MiningTier tier) {
		return switch (tier) {
			case WOOD -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
			case STONE -> BlockTags.INCORRECT_FOR_STONE_TOOL;
			case IRON -> BlockTags.INCORRECT_FOR_IRON_TOOL;
			case DIAMOND -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
			case NETHERITE -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
			case BEYOND_NETHERITE -> {throw new IllegalArgumentException("Mining level of beyond netherite not permitted. Such a material should be incorrect for netherite and remove needs for that level.");}
		};
	}

	public TagKey<Block> getTagForTierNeeds(MiningTier tier) {
		return switch (tier) {
			case WOOD -> NEEDS_WOOD_TOOL;
			case STONE -> BlockTags.NEEDS_STONE_TOOL;
			case IRON -> BlockTags.NEEDS_IRON_TOOL;
			case DIAMOND -> BlockTags.NEEDS_DIAMOND_TOOL;
			case NETHERITE -> NEEDS_NETHERITE_TOOL;
			case BEYOND_NETHERITE -> {throw new IllegalArgumentException("Mining level of beyond netherite not permitted. Such a material should be incorrect for netherite and remove needs for that level.");}
		};
	}

	public MineabilityTier getMineability(MiningTier tier) {
		return switch (tier) {
			case WOOD -> MineabilityTier.ALL;
			case STONE -> MineabilityTier.WOOD;
			case IRON -> MineabilityTier.STONE;
			case DIAMOND -> MineabilityTier.IRON;
			case NETHERITE -> MineabilityTier.DIAMOND;
			case BEYOND_NETHERITE -> MineabilityTier.NETHERITE;
		};
	}

	public TagKey<Block> getNeedsTagForMineability(MineabilityTier tier) {
		return switch (tier) {
			case ALL -> {throw new IllegalArgumentException("All not permitted here. There is no minimum.");}
			case DEFAULT -> {throw new IllegalArgumentException("Default not permitted here. Input should be resolved.");}
			case WOOD -> NEEDS_WOOD_TOOL;
			case STONE -> BlockTags.NEEDS_STONE_TOOL;
			case IRON -> BlockTags.NEEDS_IRON_TOOL;
			case DIAMOND -> BlockTags.NEEDS_DIAMOND_TOOL;
			case NETHERITE -> NEEDS_NETHERITE_TOOL;
		};
	}

}
