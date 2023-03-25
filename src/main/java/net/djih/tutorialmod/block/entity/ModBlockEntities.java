package net.djih.tutorialmod.block.entity;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.block.ModBlocks;
import net.djih.tutorialmod.block.entity.custom.DustyStationBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TutorialMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<DustyStationBlockEntity>> DUSTY_STATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dusty_station_block_entity", ()-> BlockEntityType.Builder.of(DustyStationBlockEntity::new,
                    ModBlocks.DUSTY_STATION.get()).build(null));

    public static  void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
