package github.ryanocerou5.girlfriendmod.gui;

import github.ryanocerou5.girlfriendmod.GirlfriendMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GirlfriendHud {
    // --- VARIABLES --- //
    private static final Identifier GIRLFRIEND_TEXTURE = Identifier.of(GirlfriendMod.MOD_ID, "textures/gui/girlfriend.png");

    private static final int TEXTURE_WIDTH = 181;
    private static final int TEXTURE_HEIGHT = 274;
    public static boolean overlayEnabled = false;

    public static void setOverlayEnabled(boolean enabled)
    {
        if (overlayEnabled == enabled) return;

        overlayEnabled = enabled;
        MinecraftClient client = MinecraftClient.getInstance();

        if (overlayEnabled && client.player != null)
        {
            KeyBinding[] movementKeys = {
                    client.options.forwardKey,
                    client.options.backKey,
                    client.options.leftKey,
                    client.options.rightKey,
                    client.options.jumpKey,
                    client.options.sprintKey,
                    client.options.sneakKey
            };

            for (KeyBinding kb : movementKeys) {
                kb.setPressed(false);
            }
            client.mouse.unlockCursor();
        }
        else
        {
            client.mouse.lockCursor();
        }
    }


    public static void register() {
        HudRenderCallback.EVENT.register(GirlfriendHud::onHudRender);
    }

    private static void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        // Extract float tickDelta from tickCounter
        float tickDelta = tickCounter.getTickDelta(false);
        // Fetch the client
        MinecraftClient client = MinecraftClient.getInstance();

        // Don't draw if there's no player or world
        if (client.player == null || client.world == null) return;

        // Screen Dimensions
        int screenWidth = drawContext.getScaledWindowWidth();
        int screenHeight = drawContext.getScaledWindowHeight();

        if (!overlayEnabled)
        {
            // HUD transform
            float scale = 0.4f;
            int width = (int)(TEXTURE_WIDTH * scale);
            int height = (int)(TEXTURE_HEIGHT * scale);

            int x = screenWidth - width - 10;
            int y = screenHeight - height;

            // Draw the texture
            drawContext.drawTexture(
                    GIRLFRIEND_TEXTURE,
                    x, y,
                    0, 0,
                    width,
                    height,
                    width,
                    height
            );
        }

        if (overlayEnabled)
        {
            client.mouse.unlockCursor();
            // Overlay transform
            float scale = 0.8f;
            int width = (int)(TEXTURE_WIDTH * scale);
            int height = (int)(TEXTURE_HEIGHT * scale);

            int x = (screenWidth - width) / 2;
            int y = screenHeight - height;

            // Set Z index above inventory
            drawContext.getMatrices().push();
            drawContext.getMatrices().translate(0, 0, 600);

            // Draw full screen tint
            drawContext.fill(
                    0, 0,
                    drawContext.getScaledWindowWidth(),
                    drawContext.getScaledWindowHeight(),
                    0x88000000 // ARGB: alpha + black
            );
            // Draw the texture
            drawContext.drawTexture(
                    GIRLFRIEND_TEXTURE,
                    x, y,
                    0, 0,
                    width,
                    height,
                    width,
                    height
            );

            drawContext.getMatrices().pop();
        }
    }
}
