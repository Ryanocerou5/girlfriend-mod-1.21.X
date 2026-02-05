package github.ryanocerou5.girlfriendmod.text;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import github.ryanocerou5.girlfriendmod.GirlfriendMod;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TextLibrary {
    private static final Identifier GIRLFRIEND_DIALOGUE_JSON = Identifier.of(GirlfriendMod.MOD_ID, "dialogue/dialogue.json");
    private static final Gson GSON = new Gson();
    public static List<Message> MESSAGES = new ArrayList<>();

    public static void load(ResourceManager resourceManager) {
        try {
            // getResource now returns Optional<Resource>
            resourceManager.getResource(GIRLFRIEND_DIALOGUE_JSON).ifPresent(resource -> {
                try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
                    MESSAGES = GSON.fromJson(reader, new TypeToken<List<Message>>(){}.getType());

                    for (Message m : MESSAGES) {
                        if (m.choices == null) {
                            m.choices = new ArrayList<>();
                        }
                    }

                } catch (IOException e) {
                    GirlfriendMod.LOGGER.error("Failed to read girlfriend dialogue JSON", e);
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            GirlfriendMod.LOGGER.error("Failed to read girlfriend dialogue resource", e);
            e.printStackTrace();
        }
    }
}
