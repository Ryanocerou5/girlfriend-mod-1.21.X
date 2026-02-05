package github.ryanocerou5.girlfriendmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class AbstractScreen<T extends ScreenHandler> extends HandledScreen<T> {
    protected abstract Identifier getTexture();
    protected abstract int getBackgroundWidth();
    protected abstract int getBackgroundHeight();
    public AbstractScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = getBackgroundHeight();
        this.backgroundWidth = getBackgroundWidth();
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, getTexture());
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(getTexture(), x, y, 0, 0, backgroundWidth, backgroundHeight, getBackgroundWidth(), getBackgroundHeight());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
