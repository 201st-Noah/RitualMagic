package be.noah.ritual_magic.mana;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class MetalToManaValueRegistry {
    private static final Map<Item, Integer> MANA_VALUES = new HashMap<>();
    private static final int DEFAULT_MANA = 0;

    public static void register(Item item, int mana) {
        MANA_VALUES.put(item, mana);
    }

    public static int getManaValue(Item stack) {
        return MANA_VALUES.getOrDefault(stack, DEFAULT_MANA);
    }

    public static void clear() {
        MANA_VALUES.clear();
    }

    public static void loadFromJsonObject(Map<String, Integer> itemManaMap) {
        for (Map.Entry<String, Integer> entry : itemManaMap.entrySet()) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getKey()));
            if (item != null) {
                register(item, entry.getValue());
            }
        }
    }
}
