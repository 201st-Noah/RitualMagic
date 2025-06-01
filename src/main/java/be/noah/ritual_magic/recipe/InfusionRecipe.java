package be.noah.ritual_magic.recipe;

import be.noah.ritual_magic.item.LeveldMagicItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfusionRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    public final ItemStack input;
    public final List<ItemStack> pedestalItems;
    public final ItemStack output;
    private final int minLevel;
    private final int maxLevel;
    private final int levelGain;
    private final boolean preserveMainItem;

    public InfusionRecipe(ResourceLocation id, ItemStack input, List<ItemStack> pedestalItems, ItemStack output, int minLevel, int maxLevel, int levelGain, boolean preserveMainItem) {
        this.id = id;
        this.input = input;
        this.pedestalItems = pedestalItems;
        this.output = output;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.levelGain = levelGain;
        this.preserveMainItem = preserveMainItem;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;
        ItemStack centerItem = container.getItem(0);

        if (!centerItem.is(input.getItem())) return false;

        if (centerItem.getItem() instanceof LeveldMagicItem magicItem) {
            int itemLevel = magicItem.getItemLevel(centerItem);
            return itemLevel >= minLevel && itemLevel <= maxLevel;
        }

        return minLevel == 0;
    }

    public boolean pedestalItemsMatch(List<ItemStack> actualItems) {
        List<ItemStack> required = new ArrayList<>(pedestalItems);
        List<ItemStack> found = new ArrayList<>(actualItems);

        for (ItemStack req : required) {
            boolean matched = false;
            Iterator<ItemStack> iter = found.iterator();
            while (iter.hasNext()) {
                ItemStack next = iter.next();
                if (ItemStack.isSameItemSameTags(req, next)) {
                    iter.remove();
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }
        return found.isEmpty(); // Must be exact
    }

    @Override public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        ItemStack center = container.getItem(0);

        if (preserveMainItem && center.getItem() instanceof LeveldMagicItem magicItem) {
            ItemStack upgraded = center.copy();
            magicItem.addItemLevel(upgraded, levelGain);
            return upgraded;
        }

        return output.copy();
    }
    @Override public boolean canCraftInDimensions(int w, int h) { return true; }
    @Override public ItemStack getResultItem(RegistryAccess access) { return output; }
    @Override public ResourceLocation getId() { return id; }
    @Override public RecipeSerializer<?> getSerializer() { return ModRecipes.INFUSION_SERIALIZER.get(); }
    @Override public RecipeType<?> getType() { return ModRecipes.INFUSION_TYPE.get(); }
    //pointer
    public static class Serializer implements RecipeSerializer<InfusionRecipe> {
        @Override
        public InfusionRecipe fromJson(ResourceLocation id,@Nullable JsonObject json) {
            if (json == null) {
                throw new JsonParseException("Infusion recipe JSON is null for: " + id);
            }
            ItemStack input = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));

            JsonArray array = json.getAsJsonArray("pedestal_items");
            List<ItemStack> pedestalItems = new ArrayList<>();
            for (JsonElement element : array) {
                pedestalItems.add(ShapedRecipe.itemStackFromJson(element.getAsJsonObject()));
            }
            int minLevel = json.has("min_level") ? json.get("min_level").getAsInt() : 0;
            int maxLevel = json.has("max_level") ? json.get("max_level").getAsInt() : Integer.MAX_VALUE;
            int levelGain = json.has("level_gain") ? json.get("level_gain").getAsInt() : 0;
            boolean preserve = json.has("preserve_main_item") && json.get("preserve_main_item").getAsBoolean();

            return new InfusionRecipe(id, input, pedestalItems, output, minLevel, maxLevel, levelGain, preserve);
        }

        @Override
        public @Nullable InfusionRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ItemStack input = buf.readItem();
            int count = buf.readVarInt();
            List<ItemStack> pedestal = new ArrayList<>();
            for (int i = 0; i < count; i++) pedestal.add(buf.readItem());
            ItemStack output = buf.readItem();
            int minLevel = buf.readVarInt();
            int maxLevel = buf.readVarInt();
            int levelGain = buf.readVarInt();
            boolean preserve = buf.readBoolean();
            return new InfusionRecipe(id, input, pedestal, output, minLevel, maxLevel, levelGain, preserve);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, InfusionRecipe recipe) {
            buf.writeItem(recipe.input);
            buf.writeVarInt(recipe.pedestalItems.size());
            for (ItemStack item : recipe.pedestalItems) {
                buf.writeItem(item);
            }
            buf.writeItem(recipe.output);
            buf.writeVarInt(recipe.minLevel);
            buf.writeVarInt(recipe.maxLevel);
            buf.writeVarInt(recipe.levelGain);
            buf.writeBoolean(recipe.preserveMainItem);
        }
    }
}
