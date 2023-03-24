package net.djih.tutorialmod.item.custom;

import net.djih.tutorialmod.item.ModItems;
import net.djih.tutorialmod.util.InventoryUtil;
import net.djih.tutorialmod.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicBallItem extends Item {
    public MagicBallItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()){
            BlockPos positionCLicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            for(int i = 0; i <= positionCLicked.getY() + 64; i++){
                Block blockBelow = pContext.getLevel().getBlockState(positionCLicked.below(i)).getBlock();

                if(isValueableBlock(blockBelow)){
                    //commented out since the new item data_tablet shows the desired information
                    //outputValuableCoords(positionCLicked.below(i),player,blockBelow);
                    foundBlock = true;

                    //Check if player has DATA_TABLET, then save the info into it
                    if(InventoryUtil.hasPlayerStackInInventory(player,ModItems.DATA_TABLET.get())){
                        addNbtToDataTablet(player,positionCLicked.below(i),blockBelow);
                    }
                    break;
                }
            }

            if(!foundBlock){
                player.sendMessage(new TranslatableComponent("item.tutorialmod.magic_ball.no_valuable"),
                        player.getUUID());
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                (player)-> player.broadcastBreakEvent(player.getUsedItemHand()));

        return super.useOn(pContext);
    }

    //Function: add the information from the last scanned ore into the first available dataTable in player Inventory
    private void addNbtToDataTablet(Player player, BlockPos pos, Block blockBelow){
        ItemStack dataTablet = player.getInventory().getItem(InventoryUtil.getFirstInventoryIndex(player, ModItems.DATA_TABLET.get()));

        //CompoundTag nbtData = new CompoundTag();
        if(!dataTablet.hasTag()) {
            CompoundTag nbtData = new CompoundTag();
            //pkey = nbt tag name, pValue = inserted value
            nbtData.putString("tutorialmod.last_ore", "Found " + blockBelow.asItem().getRegistryName().toString() + " at (" +
                    pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")");
            dataTablet.setTag(nbtData);
        } else {
            CompoundTag nbtData = dataTablet.getTag();
            nbtData.putString("tutorialmod.last_ore", "Found " + blockBelow.asItem().getRegistryName().toString() + " at (" +
                    pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")");
            dataTablet.setTag(nbtData);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(new TranslatableComponent("tooltip.tutorialmod.magic_ball.tooltip.shift"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.tutorialmod.magic_ball.tooltip"));
        }
    }

    private void outputValuableCoords(BlockPos blockPos, Player player, Block blockBelow){
        player.sendMessage(new TextComponent("Found: " + blockBelow.asItem().getRegistryName().toString() + " at" +
                "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() +")"),player.getUUID());
    }

    private boolean isValueableBlock(Block block){
        // return ModTags.Blocks.MAGIC_BALL_VALUABLE.contains(block); // 1.18.1
        return Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(block).get()).is(ModTags.Blocks.MAGIC_BALL_VALUABLE);
    }
}
