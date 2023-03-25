package net.djih.tutorialmod.screen;

import net.djih.tutorialmod.TutorialMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModeMenuTypes {

    public  static  final DeferredRegister<MenuType<?>> MENUES = DeferredRegister.create(ForgeRegistries.CONTAINERS, TutorialMod.MOD_ID);

    public static final RegistryObject<MenuType<DustyStationMenu>> DUSTY_STATION_MENU = registerMenuType(DustyStationMenu::new, "dusty_station_menu");
    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENUES.register(name, ()-> IForgeMenuType.create(factory));
    }

    public  static void register(IEventBus eventbus){
        MENUES.register(eventbus);
    }
}
