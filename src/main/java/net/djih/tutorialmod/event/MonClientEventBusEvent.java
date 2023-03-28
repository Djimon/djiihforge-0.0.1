package net.djih.tutorialmod.event;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.client.model.OreChickenModel;
import net.djih.tutorialmod.client.renderer.OreChickenRenderer;
import net.djih.tutorialmod.entity.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MonClientEventBusEvent {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntityTypes.ORE_CHICKEN.get(), OreChickenRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(OreChickenModel.LAYER_LOCATION, OreChickenModel::createBodyLayer);
    }
}
