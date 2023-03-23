package net.djih.tutorialmod.block;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.item.ModCreativeModeTab;
import net.djih.tutorialmod.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.rmi.registry.Registry;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

    //Simple method for adding new Blocks
    // each Block needs 1 block_states .json, 1 models/block .json, 1 models/item .json, 1 textures/block .png and a translation in lang .json
    public static final RegistryObject<Block> CITRINE_BLOCK = registerBlock("citrine_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f)
                    .requiresCorrectToolForDrops()),
            ModCreativeModeTab.MY_TUTORIAL_TAB);

    public static final RegistryObject<Block> LOADING_BLOCK = registerBlock("loading_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(9f)
                    .requiresCorrectToolForDrops()),
            ModCreativeModeTab.MY_TUTORIAL_TAB);

    public static final RegistryObject<Block> SPEED_UP_BLOCK = registerBlock("speed_up_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f)
                    .requiresCorrectToolForDrops()),
            ModCreativeModeTab.MY_TUTORIAL_TAB,"tooltip.tutorialmod.speed_up_block");


    //2 helpers for easier Block creation (inlcuding the item)
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab, String tooltipKey) {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn,tab,tooltipKey);
        return toReturn;
    }

    private static  <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                             CreativeModeTab tab, String tooltipKey){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                pTooltip.add(new TranslatableComponent(tooltipKey));
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
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
