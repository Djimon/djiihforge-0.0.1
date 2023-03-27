package net.djih.tutorialmod.event.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MagicDustFromCreeperAdditionModifier extends LootModifier {
    private final Item addition;

    protected MagicDustFromCreeperAdditionModifier(LootItemCondition[] conditionsIn, Item add) {
        super(conditionsIn);
        this.addition = add;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(addition,1));
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<MagicDustFromCreeperAdditionModifier> {

        @Override
        public MagicDustFromCreeperAdditionModifier read(ResourceLocation location, JsonObject object,
                                                         LootItemCondition[] conditions) {
            Item add = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object,"addition")));
            return new MagicDustFromCreeperAdditionModifier(conditions,add);
        }

        @Override
        public JsonObject write(MagicDustFromCreeperAdditionModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("additions", ForgeRegistries.ITEMS.getKey(instance.addition).toString());
            return json;
        }
    }
}
