package be.noah.ritual_magic.Mana;

import be.noah.ritual_magic.networking.ModMessages;
import be.noah.ritual_magic.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class ManaNetworkData extends SavedData {

    private static final String DATA_NAME = "mana_network_data";
    private final Set<UUID> dirtyPlayers = new HashSet<>();
    private final Map<UUID, ManaPool> playerMana = new HashMap<>();

    public static ManaNetworkData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                ManaNetworkData::new,
                ManaNetworkData::new,
                DATA_NAME
        );
    }

    public ManaPool getOrCreate(UUID playerId) {
        return playerMana.computeIfAbsent(playerId, id -> new ManaPool());
    }

    public boolean consume(UUID playerId, ManaType type, int amount) {
        ManaPool pool = getOrCreate(playerId);
        boolean result = pool.consume(type, amount);
        if (result) {
            dirtyPlayers.add(playerId);
            setDirty();
        }
        return result;
    }

    public void add(UUID playerId, ManaType type, int amount) {
        getOrCreate(playerId).add(type, amount);
        dirtyPlayers.add(playerId);
        setDirty();
    }

    public void setMax(UUID playerId, ManaType type, int amount) {
        getOrCreate(playerId).setMax(type, amount);
        dirtyPlayers.add(playerId);
        setDirty();
    }

    public void increaseMax(UUID playerId, ManaType type, int amount) {
        getOrCreate(playerId).increaseMax(type, amount);
        dirtyPlayers.add(playerId);
        setDirty();
    }

    public void syncDirty(ServerLevel level) {
        Iterator<UUID> iterator = dirtyPlayers.iterator();
        while (iterator.hasNext()) {
            UUID uuid = iterator.next();
            ServerPlayer player = level.getServer().getPlayerList().getPlayer(uuid);
            if (player != null) {
                ManaPool pool = getOrCreate(uuid);
                CompoundTag tag = pool.serialize();
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(uuid, tag), player);
            }
            iterator.remove();
        }
    }

    // === SAVE ===
    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag playerList = new ListTag();

        for (var entry : playerMana.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.putUUID("uuid", entry.getKey());
            playerTag.put("mana", entry.getValue().serialize());
            playerList.add(playerTag);
        }

        tag.put("players", playerList);
        return tag;
    }

    // === LOAD ===
    public ManaNetworkData() {}

    public ManaNetworkData(CompoundTag tag) {
        ListTag list = tag.getList("players", Tag.TAG_COMPOUND);
        for (Tag entry : list) {
            CompoundTag playerTag = (CompoundTag) entry;
            UUID uuid = playerTag.getUUID("uuid");

            ManaPool pool = new ManaPool();
            pool.deserialize(playerTag.getCompound("mana"));

            playerMana.put(uuid, pool);
        }
    }
}
