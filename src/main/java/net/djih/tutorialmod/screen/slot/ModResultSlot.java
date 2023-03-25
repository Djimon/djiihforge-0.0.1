package net.djih.tutorialmod.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModResultSlot extends SlotItemHandler {
    public ModResultSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    //player is not allowed to put items in teh RESULT Slot
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }


}
