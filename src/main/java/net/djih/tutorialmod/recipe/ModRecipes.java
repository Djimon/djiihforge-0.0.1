package net.djih.tutorialmod.recipe;

import net.djih.tutorialmod.TutorialMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TutorialMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DustyStationRecipe>> DUSTYFYING_SERIALIZER =
            SERIALIZERS.register("dustyfying", ()-> DustyStationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
