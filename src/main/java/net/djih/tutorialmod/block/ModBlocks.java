package net.djih.tutorialmod.block;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.item.ModCreativeModeTab;
import net.djih.tutorialmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

    //Simple method for adding new Blocks
    public static final RegistryObject<Block> CITRINE_BLOCK = registerBlock("citrine_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f)
                    .requiresCorrectToolForDrops()),
            ModCreativeModeTab.MY_TUTORIAL_TAB);

    public static final RegistryObject<Block> SPEED_UP_BLOCK = registerBlock("speed_up_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f)
                    .requiresCorrectToolForDrops()),
            ModCreativeModeTab.MY_TUTORIAL_TAB);


    //2 helpers for easier Block creation (inlcuding the item)
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn,tab);
        return toReturn;
    }
    private static  <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                             CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static  void  register(IEventBus eventBUs)
    {
        BLOCKS.register(eventBUs);
    }
}
