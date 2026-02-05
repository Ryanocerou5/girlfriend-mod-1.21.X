package github.ryanocerou5.girlfriendmod;

import github.ryanocerou5.girlfriendmod.client.ClientKeyInputHandler;
import github.ryanocerou5.girlfriendmod.gui.GirlfriendHud;
import net.fabricmc.api.ClientModInitializer;

public class GirlfriendModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GirlfriendHud.register();
        ClientKeyInputHandler.register();
    }
}
