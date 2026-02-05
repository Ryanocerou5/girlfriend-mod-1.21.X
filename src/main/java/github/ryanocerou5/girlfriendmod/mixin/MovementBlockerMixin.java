package github.ryanocerou5.girlfriendmod.mixin;

import github.ryanocerou5.girlfriendmod.gui.GirlfriendHud;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MovementBlockerMixin {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void blockKeys(long window, int key, int scancode, int action, int mods, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!GirlfriendHud.overlayEnabled || client.player == null || client.world == null) return;

        if (isBlockableKey(client, key) && action != GLFW.GLFW_RELEASE) {
            ci.cancel(); // cancel the input before it updates player.input
        }
    }

    @Unique
    private static boolean isBlockableKey(MinecraftClient client, int key) {
        KeyBinding[] blockableKeys = {
                client.options.forwardKey,
                client.options.backKey,
                client.options.leftKey,
                client.options.rightKey,
                client.options.jumpKey,
                client.options.sprintKey,
                client.options.inventoryKey,
                client.options.sneakKey,
                client.options.pickItemKey,
                client.options.advancementsKey,
                client.options.swapHandsKey,
        };

        for (KeyBinding kb : blockableKeys) {
            InputUtil.Key boundKey = InputUtil.fromTranslationKey(kb.getBoundKeyTranslationKey());
            if (boundKey.getCode() == key || key == GLFW.GLFW_KEY_ESCAPE) return true;
        }
        return false;
    }
}

