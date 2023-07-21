package be.noah.ritual_magic.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab RITUAL_MAGIC_TAB = new CreativeModeTab("ritual_magic_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ATLANTIAN_STEEL_INGOT.get());
        }
    };
}
