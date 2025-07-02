package be.noah.ritual_magic.mana;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MetalToManaJsonReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    private static final String DIRECTORY = "mana";
    private static final Logger LOGGER = LoggerFactory.getLogger("ManaReload");

    public MetalToManaJsonReloadListener() {
        super(GSON, DIRECTORY);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        MetalToManaValueRegistry.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
            try {
                JsonObject jsonObject = entry.getValue().getAsJsonObject();
                Map<String, Integer> itemManaMap = new HashMap<>();

                for (Map.Entry<String, JsonElement> manaEntry : jsonObject.entrySet()) {
                    String itemId = manaEntry.getKey();
                    int manaValue = manaEntry.getValue().getAsInt();
                    itemManaMap.put(itemId, manaValue);
                }

                MetalToManaValueRegistry.loadFromJsonObject(itemManaMap);
                LOGGER.info("Loaded mana values from {}", entry.getKey());
            } catch (Exception e) {
                LOGGER.error("Failed to load mana values from {}", entry.getKey(), e);
            }
        }
    }
}