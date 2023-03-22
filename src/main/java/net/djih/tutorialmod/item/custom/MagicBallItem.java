package net.djih.tutorialmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

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
                    outputValuableCoords(positionCLicked.below(i),player,blockBelow);
                    foundBlock = true;
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
//+ blockBelow.asItem().getRegistryName().toString()
    private void outputValuableCoords(BlockPos blockPos, Player player, Block blockBelow){
        player.sendMessage(new TextComponent("Found: " + blockBelow.asItem().getRegistryName().toString() + " at" +
                "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() +")"),player.getUUID());
    }
    private boolean isValueableBlock(Block block){
        return block == Blocks.COAL_ORE || block == Blocks.COPPER_ORE || block == Blocks.DIAMOND_ORE || block == Blocks.IRON_ORE;
    }
}
