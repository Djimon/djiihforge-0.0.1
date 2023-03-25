package net.djih.tutorialmod.block.entity.custom;

import net.djih.tutorialmod.block.entity.ModBlockEntities;
import net.djih.tutorialmod.item.ModItems;
import net.djih.tutorialmod.screen.DustyStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.Random;

public class DustyStationBlockEntity extends BlockEntity implements MenuProvider {
    private  final ItemStackHandler itemHanlder = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public DustyStationBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.DUSTY_STATION_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Dusty Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new DustyStationMenu(pContainerId, pInventory,this);
    }

    //necessary to make it compatible with other mods (insert, output items, etc.)
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return  lazyItemHandler.cast();
        }

        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()-> itemHanlder);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    //save the custom inventory of the machine
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHanlder.serializeNBT());
        super.saveAdditional(pTag);
    }

    //save the custom inventory of the machine
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHanlder.deserializeNBT(pTag.getCompound("inventory"));
    }

    //drops invenotry of machine, when mined
    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHanlder.getSlots());
        for(int i=0; i < itemHanlder.getSlots(); i++){
            inventory.setItem(i,itemHanlder.getStackInSlot(i));
        }

        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    public static <E extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, DustyStationBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity) && hasNotReachedStackLimit(pBlockEntity)){
            craftItem(pBlockEntity);
        }
    }

    private static void craftItem(DustyStationBlockEntity entity){
        entity.itemHanlder.extractItem(0,1,false);
        entity.itemHanlder.extractItem(1,1,false);
        entity.itemHanlder.extractItem(2,1,false);
        //entity.itemHanlder.getStackInSlot(2).hurt(1, new Random(),null);

        entity.itemHanlder.setStackInSlot(3, new ItemStack(ModItems.CITRINE.get(), entity.itemHanlder.getStackInSlot(3).getCount()+1));
    }

    private static Boolean hasRecipe(DustyStationBlockEntity entity){
        boolean hasItemWaterSlot = PotionUtils.getPotion(entity.itemHanlder.getStackInSlot(0)) == Potions.WATER;
        boolean hasIteminFirstSlot = entity.itemHanlder.getStackInSlot(1).getItem() == ModItems.MAGIC_DUST.get();
        boolean hasItemInSecondSlot = entity.itemHanlder.getStackInSlot(2).getItem() == ModItems.MAGIC_BALL.get();

        return hasItemWaterSlot && hasIteminFirstSlot && hasItemInSecondSlot;
    }

    private static  boolean hasNotReachedStackLimit(DustyStationBlockEntity entity){
        return  entity.itemHanlder.getStackInSlot(3).getCount() < entity.itemHanlder.getStackInSlot(3).getMaxStackSize();
    }
}
