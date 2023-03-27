package net.djih.tutorialmod.entity;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.entity.custom.OreChicken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, TutorialMod.MOD_ID);

    public static final RegistryObject<EntityType<OreChicken>> ORE_CHICKEN =
            ENTITY_TYPES.register("ore_chicken",
                    () -> EntityType.Builder.of(OreChicken::new, MobCategory.CREATURE)
                            .build(new ResourceLocation(TutorialMod.MOD_ID,"ore_chicken").toString()));


    public static void register(IEventBus bus){
        ENTITY_TYPES.register(bus);
    }
}
