package net.djih.tutorialmod;

import net.djih.tutorialmod.block.ModBlocks;
import net.djih.tutorialmod.block.entity.ModBlockEntities;
import net.djih.tutorialmod.entity.ModEntityTypes;
import net.djih.tutorialmod.item.ModItems;
import com.mojang.logging.LogUtils;
import net.djih.tutorialmod.recipe.ModRecipes;
import net.djih.tutorialmod.screen.DustyStationScreen;
import net.djih.tutorialmod.screen.ModeMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TutorialMod.MOD_ID)
public class TutorialMod
{
    public static final String MOD_ID = "tutorialmod";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public TutorialMod()
    {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Call registration
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModeMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);

        ModEntityTypes.register(eventBus);


        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        // Register the enqueueIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event){

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DUSTY_GLASS.get(), RenderType.translucent());

        MenuScreens.register(ModeMenuTypes.DUSTY_STATION_MENU.get(), DustyStationScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        event.enqueueWork(()->{

        });
    }

}
