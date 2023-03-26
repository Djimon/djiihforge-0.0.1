package net.djih.tutorialmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.djih.tutorialmod.TutorialMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;


public class DustyStationRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    //private final NonNullList<Ingredient> recipeItems;
    private final ItemStack mainInput;
    private final ItemStack extraInput;
    private final ItemStack catalysator;

    public DustyStationRecipe(ResourceLocation id, ItemStack main, ItemStack extra, ItemStack cat, ItemStack output) {
        this.id = id;
        //this.recipeItems = recipeItems;
        this.mainInput = main;
        this.extraInput = extra;
        this.catalysator  = cat;
        this.output = output;
        System.out.println("[Tutorialmod]: Added recipe " +this.id.toString());
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        //match the Recipe input with the Slots of the container(=blockEntity)
        boolean slotZero = Ingredient.of(mainInput).test(pContainer.getItem(0));
        boolean slotOne = Ingredient.of(extraInput).test(pContainer.getItem(1));
        boolean slotTwo = Ingredient.of(catalysator).test(pContainer.getItem(2));

        return slotZero && slotOne && slotTwo;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public ItemStack getMainInput(){
        return this.mainInput;
    }

    public ItemStack getExtraInput(){
        return this.extraInput;
    }

    public ItemStack getCatalystInput(){
        return this.catalysator;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public  static  class Type implements RecipeType<DustyStationRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "dustyfying";
    }

    public static class Serializer implements RecipeSerializer<DustyStationRecipe>{

        public static final Serializer INSTANCE = new Serializer();
        public static final  ResourceLocation ID = new ResourceLocation(TutorialMod.MOD_ID,"dustyfying");

        @Override
        public DustyStationRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"output"));

            //JsonArray ingredients = GsonHelper.getAsJsonArray(json,"ingredients");
            //pSize is the number of needed input ingredients
            //NonNullList<Ingredient> inputs = NonNullList.withSize(3,Ingredient.EMPTY);

            //ItemStack mainInput = Ingredient.fromJson(GsonHelper.getAsJsonObject(json,"mainInput")).getItems()[0];
            ItemStack mainInput = Ingredient.fromJson(GsonHelper
                    .getAsJsonObject(json,"mainInput")).getItems()[0];
            ItemStack extraInput = Ingredient.fromJson(GsonHelper
                    .getAsJsonObject(json,"extraInput")).getItems()[0];
            ItemStack catInput = Ingredient.fromJson(GsonHelper
                    .getAsJsonObject(json,"catalysator")).getItems()[0];


//            for(int i=0; i< inputs.size();i++){
//                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
//                System.out.println(ingredients.get(i).getAsString());
//            }

            return new DustyStationRecipe(id,mainInput,extraInput,catInput, output);
        }

        @Nullable
        @Override
        public DustyStationRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf pBuffer) {
            //readInt() is the ingredients Size written in the toNetwork writeInt
//            NonNullList<Ingredient> input = NonNullList.withSize(pBuffer.readInt(),Ingredient.EMPTY);
//
//            for(int i=0; i< input.size(); i++){
//                input.set(i, Ingredient.fromNetwork(pBuffer));
//            }

            ItemStack mainInput = pBuffer.readItem();
            ItemStack extraInput = pBuffer.readItem();
            ItemStack catInput = pBuffer.readItem();
            ItemStack output = pBuffer.readItem();
            return  new DustyStationRecipe(id,mainInput,extraInput,catInput, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, DustyStationRecipe pRecipe) {
//            pBuffer.writeInt(pRecipe.getIngredients().size());
//            for(Ingredient ing: pRecipe.getIngredients()){
//                ing.toNetwork(pBuffer);
//            }
            pBuffer.writeItemStack(pRecipe.getMainInput(),false );
            pBuffer.writeItemStack(pRecipe.getExtraInput(),false );
            pBuffer.writeItemStack(pRecipe.getCatalystInput(),false );
            pBuffer.writeItemStack(pRecipe.getResultItem(),false );
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }


        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        //unchecked, needed because of generics
        private static <G> Class<G> castClass(Class<?> cls){
            return (Class<G>)cls;
        }
    }

}
