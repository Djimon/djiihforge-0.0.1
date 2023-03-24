package net.djih.tutorialmod.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DataTabletItem extends Item{
    public  DataTabletItem(Properties properties){
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level plevel, Player pPlayer, InteractionHand pHand)
    {
        if(pPlayer.getItemInHand(pHand).hasTag()) {
            pPlayer.getItemInHand(pHand).getTag().remove("tutorialmod.last_ore");
        }
        return super.use(plevel, pPlayer, pHand);

    }

    //add enchantmant glittering
    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.hasTag();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(pStack.hasTag()){
            //Name of the nbt tag is the pKey: "tutorialmod.last_ore"
            String currentOre = pStack.getTag().getString("tutorialmod.last_ore");
            pTooltipComponents.add(new TextComponent(currentOre));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
