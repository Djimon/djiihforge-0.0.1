package net.djih.tutorialmod.block.entity.custom;

import net.djih.tutorialmod.block.entity.ModBlockEntities;
import net.djih.tutorialmod.item.ModItems;
import net.djih.tutorialmod.recipe.DustyStationRecipe;
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
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.Optional;
import java.util.Random;

public class DustyStationBlockEntity extends BlockEntity implements MenuProvider {
    private  final ItemStackHandler itemHanlder = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgess = 72;

    public DustyStationBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.DUSTY_STATION_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex){
                    case 0: return DustyStationBlockEntity.this.progress ;
                    case 1: return DustyStationBlockEntity.this.maxProgess;
                    default: return 0;
                }
            }
            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0: DustyStationBlockEntity.this.progress = pValue; break;
                    case 1: DustyStationBlockEntity.this.maxProgess = pValue; break;
                }
            }
            //number of data (case 0 +case 1 = 2 data)
            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Dusty Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new DustyStationMenu(pContainerId, pInventory,this, this.data);
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
        pTag.putInt("dusty_station.progress",progress);
        super.saveAdditional(pTag);
    }

    //save the custom inventory of the machine
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHanlder.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("dusty_station.progress");
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
        if(hasRecipe(pBlockEntity)){
            pBlockEntity.progress++;
            setChanged(level,blockPos,blockState);
            if(pBlockEntity.progress >= pBlockEntity.maxProgess)
            {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(level,blockPos,blockState);
        }
    }

    private static Boolean hasRecipe(DustyStationBlockEntity entity){
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHanlder.getSlots());
        for(int i=0; i< entity.itemHanlder.getSlots(); i++){
            inventory.setItem(i,entity.itemHanlder.getStackInSlot(i));
        }

        Optional<DustyStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(DustyStationRecipe.Type.INSTANCE,inventory,level);

        return match.isPresent()
                && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory,match.get().getResultItem())
                //&& hasToolsInToolSlot(entity) //only of you use a tool that looses durability
                ;
    }

    private static boolean hasToolsInToolSlot(DustyStationBlockEntity entity) {
        return entity.itemHanlder.getStackInSlot(2).getItem() == ModItems.MAGIC_BALL.get();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack resultItem) {
        return inventory.getItem(3).getItem() == resultItem.getItem() || inventory.getItem(3).isEmpty();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(DustyStationBlockEntity entity){
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHanlder.getSlots());

        for(int i=0; i< entity.itemHanlder.getSlots(); i++){
            inventory.setItem(i,entity.itemHanlder.getStackInSlot(i));
        }

        Optional<DustyStationRecipe> match = level.getRecipeManager().getRecipeFor(DustyStationRecipe.Type.INSTANCE,inventory,level);

        if(match.isPresent()){
            entity.itemHanlder.extractItem(0,1,false);
            entity.itemHanlder.extractItem(1,1,false);
            entity.itemHanlder.extractItem(2,1,false);
            //only of you use a tool that looses durability
            //entity.itemHanlder.getStackInSlot(2).hurt(1, new Random(),null);

            entity.itemHanlder.setStackInSlot(3, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHanlder.getStackInSlot(3).getCount()+1));

            entity.resetProgress();
        }
    }
}
