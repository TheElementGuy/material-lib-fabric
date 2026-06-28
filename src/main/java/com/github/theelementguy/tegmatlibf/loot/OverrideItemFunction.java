package com.github.theelementguy.tegmatlibf.loot;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.functions.SetItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OverrideItemFunction extends LootItemConditionalFunction {

	private final Holder<Item> item;

	private OverrideItemFunction(List<LootItemCondition> predicates, Item item) {
		super(predicates);
		this.item = Holder.direct(item);
	}

	@Override
	public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
		return null;
	}

	@Override
	public @NotNull ItemStack run(@NotNull ItemStack itemStack, LootContext lootContext) {

		return itemStack.transmuteCopy(item.value());

	}

	public static LootItemConditionalFunction.Builder<?> builder(Item itemEntry) {
		return simpleBuilder((predicates) -> new OverrideItemFunction(predicates, itemEntry));
	}
}
