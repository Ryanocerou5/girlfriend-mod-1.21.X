package github.ryanocerou5.girlfriendmod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import github.ryanocerou5.girlfriendmod.GirlfriendMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GirlfriendHud {
    // --- VARIABLES --- //
    private static final Identifier GIRLFRIEND_TEXTURE = Identifier.of(GirlfriendMod.MOD_ID, "textures/gui/girlfriend.png");
    private static final Identifier GIRLFRIEND_TEXTBOX_TEXTURE = Identifier.of(GirlfriendMod.MOD_ID, "textures/gui/girlfriend_textbox.png");

    private static final int GIRLFRIEND_TEXTURE_WIDTH = 181;
    private static final int GIRLFRIEND_TEXTURE_HEIGHT = 274;
    private static final int GIRLFRIEND_TEXTBOX_TEXTURE_WIDTH = 256;
    private static final int GIRLFRIEND_TEXTBOX_TEXTURE_HEIGHT = 256;
    public static boolean overlayEnabled = false;

    // Animation
    private static long overlayActivatedTime = 0;
    private static final long ANIMATION_DURATION_MS = 200;

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

            overlayActivatedTime = System.currentTimeMillis(); // Start animation

            client.mouse.unlockCursor();
            GirlfriendTextManager.startMessage(0);
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
        TextRenderer textRenderer = client.textRenderer;

        // Don't draw if there's no player or world
        if (client.player == null || client.world == null) return;

        // Screen Dimensions
        int screenWidth = drawContext.getScaledWindowWidth();
        int screenHeight = drawContext.getScaledWindowHeight();

        if (!overlayEnabled)
        {
            // HUD transform
            float scale = 0.4f;
            int width = (int)(GIRLFRIEND_TEXTURE_WIDTH * scale);
            int height = (int)(GIRLFRIEND_TEXTURE_HEIGHT * scale);

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
            // Overlay transform
            float girlfriendScale = 0.8f;
            float girlfriendScaleX = girlfriendScale;
            float girlfriendScaleY = girlfriendScale;

            // Time since overlay activated
            long now = System.currentTimeMillis();
            long elapsed = now - overlayActivatedTime;

            if (elapsed < ANIMATION_DURATION_MS) {
                float t = elapsed / (float) ANIMATION_DURATION_MS; // 0 -> 1

                // Squash/stretch curve: squish in, then back out
                // Example: scaleY goes down then back up, scaleX does the opposite
                girlfriendScaleX = girlfriendScale - 0.05f * (float)Math.sin(t * Math.PI); // widens slightly
                girlfriendScaleY = girlfriendScale + 0.05f * (float)Math.sin(t * Math.PI); // squashes slightly
            }

            int girlfriendWidth = (int)(GIRLFRIEND_TEXTURE_WIDTH * girlfriendScaleX);
            int girlfriendHeight = (int)(GIRLFRIEND_TEXTURE_HEIGHT * girlfriendScaleY);

            int textboxWidth = GIRLFRIEND_TEXTBOX_TEXTURE_WIDTH;
            int textboxHeight = GIRLFRIEND_TEXTBOX_TEXTURE_HEIGHT;

            int girlfriendX = (screenWidth - girlfriendWidth) / 2;
            int girlfriendY = screenHeight - girlfriendHeight;

            int textboxX = (screenWidth - 256) / 2;
            int textboxY = screenHeight - 56;

            client.mouse.unlockCursor();

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
                    girlfriendX, girlfriendY,
                    0, 0,
                    girlfriendWidth,
                    girlfriendHeight,
                    girlfriendWidth,
                    girlfriendHeight
            );

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            drawContext.drawTexture(
                    GIRLFRIEND_TEXTBOX_TEXTURE,
                    textboxX, textboxY,
                    0, 0,
                    256,
                    56,
                    textboxWidth,
                    textboxHeight
            );
            RenderSystem.disableBlend();

            String message = GirlfriendTextManager.getVisibleText();
            int textWidth = textRenderer.getWidth(message);
            int textX = (screenWidth - textWidth) / 2;
            int textY = textboxY + 51 / 2;

            drawContext.drawText(
                    textRenderer,
                    Text.literal(message),
                    textX, textY,
                    0xFFFFFF,
                    true);


            drawContext.getMatrices().pop();
        }
    }
}
