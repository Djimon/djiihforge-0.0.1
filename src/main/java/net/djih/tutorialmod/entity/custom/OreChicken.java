package net.djih.tutorialmod.entity.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OreChicken extends Chicken {

    public OreChicken(EntityType<? extends Chicken> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean canMate(Animal pOtherAnimal) {
        //only with Box
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        //TODO: rightclick with empty hand to pick up chicken -> despawn chicken, create item in inventory
        return super.mobInteract(pPlayer, pHand);
    }
}
