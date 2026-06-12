package com.github.theelementguy.tegmatlibf.loot;

import net.minecraft.world.item.Item;

public record LootModifierInfo(LootModifierType type, String table, Item item, float chance) {
}
