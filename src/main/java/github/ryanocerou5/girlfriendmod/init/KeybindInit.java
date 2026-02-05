package github.ryanocerou5.girlfriendmod.init;

import github.ryanocerou5.girlfriendmod.GirlfriendMod;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeybindInit {
    public static KeyBinding ACTIVATE_GIRLFRIEND;

    public static void registerKeybinds() {
        GirlfriendMod.LOGGER.info("Registering Keybinds for " + GirlfriendMod.MOD_ID);
        ACTIVATE_GIRLFRIEND = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key." + GirlfriendMod.MOD_ID + ".activate_girlfriend",
                        GLFW.GLFW_KEY_COMMA,
                        "category." + GirlfriendMod.MOD_ID
                )
        );
    }
}