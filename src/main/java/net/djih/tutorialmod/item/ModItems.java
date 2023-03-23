package net.djih.tutorialmod.item;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.item.custom.CitrineCoal;
import net.djih.tutorialmod.item.custom.MagicBallItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    //create empty "List" of Items that need to be registered with our MOD_ID
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);

    //For each new Item register in to the ITEMS-List
    // each Item needs 1 models/item .json, 1 textures/item .png and a translation in lang .json
    public static final RegistryObject<Item> CITRINE = ITEMS.register( "citrine",
            () -> new Item(new Item.Properties()
                    .tab(ModCreativeModeTab.MY_TUTORIAL_TAB)));

    public static final RegistryObject<Item> CITRINE_COAL = ITEMS.register( "citrine_coal",
            () -> new CitrineCoal(new Item.Properties()
                    .tab(ModCreativeModeTab.MY_TUTORIAL_TAB)));

    //Custom item-> use custom item Constructor!
    public static final RegistryObject<Item> MAGIC_BALL = ITEMS.register( "magic_ball",
            () -> new MagicBallItem(new Item.Properties()
                    .tab(ModCreativeModeTab.MY_TUTORIAL_TAB)));

    public static final RegistryObject<Item> MAGIC_DUST = ITEMS.register( "magic_dust",
            () -> new Item(new Item.Properties()
                    .tab(ModCreativeModeTab.MY_TUTORIAL_TAB)));

    public static  void  register(IEventBus eventBUs)
    {
        ITEMS.register(eventBUs);
    }
}
