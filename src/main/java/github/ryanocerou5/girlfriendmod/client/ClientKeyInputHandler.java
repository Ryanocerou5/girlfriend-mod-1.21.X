package github.ryanocerou5.girlfriendmod.client;

import github.ryanocerou5.girlfriendmod.gui.GirlfriendHud;
import github.ryanocerou5.girlfriendmod.text.GirlfriendTextManager;
import github.ryanocerou5.girlfriendmod.init.KeybindInit;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ClientKeyInputHandler {
    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.getNetworkHandler() == null) return;

            while (KeybindInit.ACTIVATE_GIRLFRIEND.wasPressed()) {
                GirlfriendHud.setOverlayEnabled(!GirlfriendHud.overlayEnabled);
            }
            while (KeybindInit.REQUEST_ADVANCE.wasPressed()) {
                GirlfriendTextManager.requestAdvance();
            }
        });
    }
}