package be.noah.ritual_magic.util;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> DEEP_SEE_FIRE_BLOCK = tag("deep_see_fire_block");
        public static final TagKey<Block> DRAGON_FIRE_BLOCK = tag("dragon_fire_block");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(RitualMagic.MODID, name));
        }
    }
}
