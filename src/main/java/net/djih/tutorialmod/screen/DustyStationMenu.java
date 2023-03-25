package net.djih.tutorialmod.screen;

import net.djih.tutorialmod.block.ModBlocks;
import net.djih.tutorialmod.block.entity.custom.DustyStationBlockEntity;
import net.djih.tutorialmod.screen.slot.ModResultSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

public class DustyStationMenu extends AbstractContainerMenu {

    private final DustyStationBlockEntity blockEntity;
    private final Level level;

    public DustyStationMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public DustyStationMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModeMenuTypes.DUSTY_STATION_MENU.get(), pContainerId);
        checkContainerSize(inv,4);
        blockEntity = ((DustyStationBlockEntity) entity);
        this.level = inv.player.level;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler ->{
            this.addSlot(new SlotItemHandler(handler, 0,34,40));
            this.addSlot(new SlotItemHandler(handler, 1,57,18));
            this.addSlot(new SlotItemHandler(handler, 2,103,18));
            this.addSlot(new ModResultSlot(handler, 3,80,60));
        });
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TILE_ENTITY_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX +VANILLA_SLOT_COUNT;

    private static final int TILE_ENTITY_INVENTORY_SLOT_COUNT = 4;

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if(sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if(pIndex < VANILLA_FIRST_SLOT_INDEX +VANILLA_SLOT_COUNT){
            if(!moveItemStackTo(sourceStack,TILE_ENTITY_INVENTORY_FIRST_SLOT_INDEX,
                    TILE_ENTITY_INVENTORY_FIRST_SLOT_INDEX +TILE_ENTITY_INVENTORY_SLOT_COUNT,false)){
                return ItemStack.EMPTY;
            }
        } else if (pIndex < TILE_ENTITY_INVENTORY_FIRST_SLOT_INDEX +TILE_ENTITY_INVENTORY_SLOT_COUNT){
            if(!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX,
                    VANILLA_FIRST_SLOT_INDEX+VANILLA_SLOT_COUNT,false)){
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex: " +pIndex);
            return ItemStack.EMPTY;
        }

        if(sourceStack.getCount() == 0){
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level,blockEntity.getBlockPos()),pPlayer,
                ModBlocks.DUSTY_STATION.get());
    }

    //helper to position the inventory slots
    private void addPlayerInventory(Inventory playerInv){
        for(int i=0; i<3; ++i){
            for(int j=0; j <9 ; ++j){
                this.addSlot(new Slot(playerInv,j+i*9+9,8+j*18,86+i*18));
            }
        }
    }

    //helper to position the hotbar slots
    private void addPlayerHotbar(Inventory playerInv){
        for(int i=0; i <9; ++i){
            this.addSlot(new Slot(playerInv,i,8+i*18,144));
        }
    }
}
