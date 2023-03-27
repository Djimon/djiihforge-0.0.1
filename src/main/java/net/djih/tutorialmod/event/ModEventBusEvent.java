package net.djih.tutorialmod.event;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.event.loot.CitrineFromDeepslateAdditionModifier;
import net.djih.tutorialmod.event.loot.MagicDustFromCreeperAdditionModifier;
import net.djih.tutorialmod.recipe.DustyStationRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;


@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvent {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent<RecipeSerializer<?>> event){
        Registry.register(Registry.RECIPE_TYPE, DustyStationRecipe.Type.ID, DustyStationRecipe.Type.INSTANCE);
    }

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){

        event.getRegistry().registerAll(
                new CitrineFromDeepslateAdditionModifier.Serializer()
                        .setRegistryName(new ResourceLocation(TutorialMod.MOD_ID,"citrine_from_deepslate")),
                new MagicDustFromCreeperAdditionModifier.Serializer()
                        .setRegistryName(new ResourceLocation(TutorialMod.MOD_ID,"magic_dust_from_creeper"))
        );
    }

}
