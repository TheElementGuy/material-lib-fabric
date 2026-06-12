package com.github.theelementguy.tegmatlibf.loot;

import com.github.theelementguy.tegmatlibf.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlibf.core.MaterialConfiguration;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class TEGMatLibLootModifier {

	public final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibLootModifier(FullyConfiguredMaterialHolder materialHolder) {
		MATERIALS = materialHolder;
	}

	public void applyModifier() {

		LootTableEvents.MODIFY.register((key, tableBuilder, source, holder) -> {
			for (MaterialConfiguration m : MATERIALS.getMaterials()) {
				for (LootModifierInfo l : m.getLootModifiers()) {
					Identifier wantedID = Identifier.withDefaultNamespace(l.table());
					if (source.isBuiltin() && key.identifier().equals(wantedID)) {
						if (l.type() == LootModifierType.EXTRA) {
							LootPool.Builder pool = LootPool.lootPool().add(LootItem.lootTableItem(l.item())).when(LootItemRandomChanceCondition.randomChance(l.chance()));
							tableBuilder.pool(pool.build());
						} else if (l.type() == LootModifierType.ADD) {
							tableBuilder.modifyPools(pool -> {
								pool.apply(OverrideItemFunction.builder(l.item()).when(LootItemRandomChanceCondition.randomChance(l.chance())));
							});
						}
					}
				}
			}
		});
	}

}
