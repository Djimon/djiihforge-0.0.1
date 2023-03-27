package net.djih.tutorialmod.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.block.ModBlocks;
import net.djih.tutorialmod.recipe.DustyStationRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class DustyStationRecipeCategory implements IRecipeCategory<DustyStationRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(TutorialMod.MOD_ID,"dustyfying");
    public static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/gui/dusty_station_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public DustyStationRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(TEXTURE,0,0,176,85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DUSTY_STATION.get()));
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder,@Nonnull DustyStationRecipe recipe,@Nonnull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 34,40).addItemStack(recipe.getMainInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 57,18).addItemStack(recipe.getExtraInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 103,18).addItemStack(recipe.getCatalystInput());

        builder.addSlot(RecipeIngredientRole.OUTPUT,80,60).addItemStack(recipe.getResultItem());
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Dusty Station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public RecipeType<DustyStationRecipe> getRecipeType() {
        //RecipeType has the methods getUid() and getRecipeClass()
        return new RecipeType<>(UID, DustyStationRecipe.class);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends DustyStationRecipe> getRecipeClass() {
        return DustyStationRecipe.class;
    }
}
