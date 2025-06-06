package be.noah.ritual_magic.client;

import be.noah.ritual_magic.mana.ManaPool;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.nbt.CompoundTag;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class ClientManaData {
    private static final Map<ManaType, Integer> currentMana = new EnumMap<>(ManaType.class);
    private static final Map<ManaType, Integer> maxMana = new EnumMap<>(ManaType.class);

    public static void set(UUID playerId, CompoundTag tag) {
        ManaPool pool = new ManaPool();
        pool.deserialize(tag);

        for (ManaType type : ManaType.values()) {
            currentMana.put(type, pool.get(type));
            maxMana.put(type, pool.getMax(type));
        }
    }

    public static int get(ManaType type) {
        return currentMana.getOrDefault(type, 0);
    }

    public static int getMax(ManaType type) {
        return maxMana.getOrDefault(type, 100);
    }
}