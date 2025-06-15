package be.noah.ritual_magic.blocks;

public enum BlockTier {
    BASIC,          //Item lv 00 - 30
    INTERMEDIATE,   //Item lv 30 - 60
    ADVANCED,       //Item lv 60 -90
    ULTIMATE;       //Item lv 90 -100

    public int getInt() {
        switch (this) {
            case BASIC -> {return 0;}
            case INTERMEDIATE -> {return 1;}
            case ADVANCED -> {return 2;}
            case ULTIMATE -> {return 3;}
            default -> {return 0;}
        }
    }
}
