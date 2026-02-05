package github.ryanocerou5.girlfriendmod.mixin;

import github.ryanocerou5.girlfriendmod.gui.GirlfriendHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseBlockerMixin {

    // --- Block left/right click ---
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void blockMouseButtons(long window, int button, int action, int mods, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (GirlfriendHud.overlayEnabled && client.player != null && client.world != null) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT || button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                ci.cancel(); // cancel clicks
            }
        }
    }

    // --- Block scroll wheel ---
    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void blockScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (GirlfriendHud.overlayEnabled && client.player != null && client.world != null) {
            ci.cancel(); // cancel scrolling
        }
    }
}
