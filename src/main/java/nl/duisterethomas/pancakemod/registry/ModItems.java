package nl.duisterethomas.pancakemod.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import nl.duisterethomas.pancakemod.PancakeMod;
import nl.duisterethomas.pancakemod.items.BakedPancakeItem;

import java.util.function.Function;

public class ModItems {
    public static void initialize() {
        // Add all the items to the right item groups
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(RAW_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_SUGAR));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_SUGAR));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_HONEY));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_HONEY));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_APPLE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_APPLE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_BACON));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_BACON));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(GOLDEN_BAKED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(GOLDEN_ROLLED_PANCAKE));
    }

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PancakeMod.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static final Item RAW_PANCAKE = register(
            "raw_pancake",
            Item::new,
            new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationModifier(0.25f)
                            .build(),
                            Consumables.defaultFood()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 400, 1), 0.5f))
                                    .build())
    );

    public static final Item ROLLED_PANCAKE = register(
            "rolled_pancake",
            Item::new,
            new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(1.0f)
                            .build())
    );

    public static final BakedPancakeItem BAKED_PANCAKE = (BakedPancakeItem) register(
            "baked_pancake",
            (properties) -> new BakedPancakeItem(properties, ROLLED_PANCAKE),
            new Item.Properties()
    );

    public static final Item ROLLED_PANCAKE_SUGAR = register(
            "rolled_pancake_sugar",
            Item::new,
            new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationModifier(1.0f)
                            .build())
    );

    public static final BakedPancakeItem BAKED_PANCAKE_SUGAR = (BakedPancakeItem) register(
            "baked_pancake_sugar",
            (properties) -> new BakedPancakeItem(properties, ROLLED_PANCAKE_SUGAR),
            new Item.Properties()
    );

    public static final Item ROLLED_PANCAKE_HONEY = register(
            "rolled_pancake_honey",
            Item::new,
            new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(10)
                            .saturationModifier(1.0f)
                            .build())
    );

    public static final BakedPancakeItem BAKED_PANCAKE_HONEY = (BakedPancakeItem) register(
            "baked_pancake_honey",
            (properties) -> new BakedPancakeItem(properties, ROLLED_PANCAKE_HONEY),
            new Item.Properties()
    );

    public static final Item ROLLED_PANCAKE_CHOCOLATE = register(
            "rolled_pancake_chocolate",
            Item::new,
            new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(1.0f)
                            .build())
    );

    public static final BakedPancakeItem BAKED_PANCAKE_CHOCOLATE = (BakedPancakeItem) register(
            "baked_pancake_chocolate",
            (properties) -> new BakedPancakeItem(properties, ROLLED_PANCAKE_CHOCOLATE),
            new Item.Properties()
    );

    public static final Item ROLLED_PANCAKE_APPLE = register(
            "rolled_pancake_apple",
            Item::new,
            new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(1.0f)
                            .build())
    );

    public static final BakedPancakeItem BAKED_PANCAKE_APPLE = (BakedPancakeItem) register(
            "baked_pancake_apple",
            (properties) -> new BakedPancakeItem(properties, ROLLED_PANCAKE_APPLE),
            new Item.Properties()
    );

    public static final Item ROLLED_PANCAKE_BACON = register(
            "rolled_pancake_bacon",
            Item::new,
            new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(14)
                            .saturationModifier(1.0f)
                            .build())
    );

    public static final BakedPancakeItem BAKED_PANCAKE_BACON = (BakedPancakeItem) register(
            "baked_pancake_bacon",
            (properties) -> new BakedPancakeItem(properties, ROLLED_PANCAKE_BACON),
            new Item.Properties()
    );

    public static final Item GOLDEN_ROLLED_PANCAKE = register(
            "golden_rolled_pancake",
            Item::new,
            new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(1.0f)
                            .build())
    );

    public static final BakedPancakeItem GOLDEN_BAKED_PANCAKE = (BakedPancakeItem) register(
            "golden_baked_pancake",
            (properties) -> new BakedPancakeItem(properties, GOLDEN_ROLLED_PANCAKE),
            new Item.Properties()
    );
}
