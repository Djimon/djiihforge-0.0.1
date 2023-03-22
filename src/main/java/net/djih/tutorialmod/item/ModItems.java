package net.djih.tutorialmod.item;

import net.djih.tutorialmod.TutorialMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    //List of Items that need to be registered with our MOD_ID
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);

    //For each new Item
    public static final RegistryObject<Item> CITRINE = ITEMS.register( "citrine",
            () -> new Item(new Item.Properties()
                    .tab(ModCreativeModeTab.MY_TUTORIAL_TAB)));



    public static  void  register(IEventBus eventBUs)
    {
        ITEMS.register(eventBUs);
    }
}
