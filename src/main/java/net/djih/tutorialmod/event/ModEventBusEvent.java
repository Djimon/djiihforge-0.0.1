package net.djih.tutorialmod.event;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.recipe.DustyStationRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvent {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent<RecipeSerializer<?>> event){
        Registry.register(Registry.RECIPE_TYPE, DustyStationRecipe.Type.ID, DustyStationRecipe.Type.INSTANCE);
    }

}
