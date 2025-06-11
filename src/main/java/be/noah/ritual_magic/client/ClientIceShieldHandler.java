package be.noah.ritual_magic.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClientIceShieldHandler {
    private static final Set<UUID> playersWithShield = new HashSet<>();

    public static void handleUpdate(UUID playerId, int hitsLeft) {
        if (hitsLeft > 0) {
            playersWithShield.add(playerId);
        } else {
            playersWithShield.remove(playerId);
        }
    }

    public static boolean hasShield(UUID uuid) {
        return playersWithShield.contains(uuid);
    }
}
