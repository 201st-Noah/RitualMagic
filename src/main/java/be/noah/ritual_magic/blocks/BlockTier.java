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

    //for the Anti TP Field
    public int getRange(){
        switch (this) {
            case BASIC -> {return 20;}
            case INTERMEDIATE -> {return 50;}
            case ADVANCED -> {return 100;}
            case ULTIMATE -> {return 200;}
            default -> {return 0;}
        }
    }

    //for the Anti TP Field
    public float getDamagePercent(){
        switch (this) {
            case BASIC -> {return 0.1f;}
            case INTERMEDIATE -> {return 0.2f;}
            case ADVANCED -> {return 0.3f;}
            case ULTIMATE -> {return 0.5f;}
            default -> {return 0;}
        }
    }
}
