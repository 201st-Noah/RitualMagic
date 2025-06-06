package be.noah.ritual_magic.screens;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RitualMagic.MODID);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }    public static final RegistryObject<MenuType<AncientAnvilMenu>> ANCIENT_ANVIL_MENU =
            registerMenuType("ancient_anvil_menu", AncientAnvilMenu::new);

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }


}
