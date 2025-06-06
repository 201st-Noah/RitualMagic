package be.noah.ritual_magic.Mana;

import net.minecraft.nbt.CompoundTag;

import java.util.EnumMap;
import java.util.Locale;

public class ManaPool {

    private final EnumMap<ManaType, Integer> mana = new EnumMap<>(ManaType.class);
    private final EnumMap<ManaType, Integer> maxMana = new EnumMap<>(ManaType.class);

    public ManaPool() {
        for (ManaType type : ManaType.values()) {
            mana.put(type, 0);
            maxMana.put(type, 100);
        }
    }

    //Mana...
    public int get(ManaType manaType) {
        return mana.getOrDefault(manaType, 0);
    }
    public void set(ManaType type, int amount) {
        mana.put(type, Math.min(amount, getMax(type)));
    }
    public void add(ManaType type, int amount) {
        set(type, get(type) + amount);
    }
    public boolean consume(ManaType type, int amount) {
        int current = get(type);
        if (current >= amount) {
            mana.put(type, current - amount);
            return true;
        }
        return false;
    }

    //MaxMana
    public int getMax(ManaType type) {
        return maxMana.getOrDefault(type, 100);
    }
    public void setMax(ManaType type, int max) {
        maxMana.put(type, Math.max(0, max));

        if (get(type) > max) {
            set(type, max);
        }
    }
    public void increaseMax(ManaType type, int amount) {
        setMax(type, getMax(type) + amount);
    }

    //data
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();

        for (ManaType type : ManaType.values()) {
            String key = type.name().toLowerCase(Locale.ROOT);
            tag.putInt("mana_" + key, get(type));
            tag.putInt("max_" + key, getMax(type));
        }

        return tag;
    }
    public void deserialize(CompoundTag tag) {
        for (ManaType type : ManaType.values()) {
            String key = type.name().toLowerCase(Locale.ROOT);
            if (tag.contains("mana_" + key)) {
                mana.put(type, tag.getInt("mana_" + key));
            }
            if (tag.contains("max_" + key)) {
                maxMana.put(type, tag.getInt("max_" + key));
            }
        }
    }
}
