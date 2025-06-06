package be.noah.ritual_magic.mana;

public enum ManaType {
    DRACONIC,
    ATLANTIAN,
    DWARVEN,
    HELLISH,
    NEXUS;

    public String getName() {
        return this.name().toLowerCase();
    }

    public int getColor() {
        return switch (this) {
            case HELLISH -> 0xFFFF5555;    // Red
            case ATLANTIAN -> 0xFF55AAFF;   // Blue
            case DWARVEN -> 0xFF55FF55;   // Green
            case NEXUS -> 0xFFFFFFAA;     // Yellow
            case DRACONIC -> 0xFFAA55FF;  // Purple
        };
    }
}
