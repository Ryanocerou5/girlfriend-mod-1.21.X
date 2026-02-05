package github.ryanocerou5.girlfriendmod;

import github.ryanocerou5.girlfriendmod.client.ClientKeyInputHandler;
import github.ryanocerou5.girlfriendmod.gui.GirlfriendHud;
import github.ryanocerou5.girlfriendmod.text.TextLibrary;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class GirlfriendModClient implements ClientModInitializer {
    private static final Identifier DIALOGUE_RELOAD_ID = Identifier.of(GirlfriendMod.MOD_ID, "girlfriend_dialogue");

    @Override
    public void onInitializeClient() {
        GirlfriendHud.register();
        ClientKeyInputHandler.register();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
                .registerReloadListener(new SimpleSynchronousResourceReloadListener() {

                    @Override
                    public Identifier getFabricId() {
                        return DIALOGUE_RELOAD_ID;
                    }

                    @Override
                    public void reload(ResourceManager manager) {
                        TextLibrary.load(manager);
                    }
                });
    }
}
