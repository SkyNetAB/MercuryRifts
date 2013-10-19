/**
 * Derived from BuildCraft released under the MMPL https://github.com/BuildCraft/BuildCraft http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package uk.co.shadeddimensions.ep3.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import uk.co.shadeddimensions.ep3.gui.tooltips.ToolTip;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBetterButton extends GuiButton
{
    public static final String BUTTON_TEXTURES = "textures/gui/buttons.png";
    protected final IButtonTextureSet texture;
    private ToolTip toolTip;

    public GuiBetterButton(int id, int x, int y, int width, IButtonTextureSet texture, String label)
    {
        super(id, x, y, width, texture.getHeight(), label);
        this.texture = texture;
    }

    public GuiBetterButton(int id, int x, int y, int width, String label)
    {
        this(id, x, y, width, StandardButtonTextureSets.LARGE_BUTTON, label);
    }

    public GuiBetterButton(int id, int x, int y, String label)
    {
        this(id, x, y, 200, StandardButtonTextureSets.LARGE_BUTTON, label);
    }

    protected void bindButtonTextures(Minecraft minecraft)
    {
        minecraft.renderEngine.bindTexture(new ResourceLocation("enhancedportals", BUTTON_TEXTURES));
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
    {
        if (!drawButton)
        {
            return;
        }
        FontRenderer fontrenderer = minecraft.fontRenderer;
        bindButtonTextures(minecraft);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int xOffset = texture.getX();
        int yOffset = texture.getY();
        int h = texture.getHeight();
        int w = texture.getWidth();
        boolean mouseOver = isMouseOverButton(mouseX, mouseY);
        int hoverState = getHoverState(mouseOver);
        drawTexturedModalRect(xPosition, yPosition, xOffset, yOffset + hoverState * h, width / 2, h);
        drawTexturedModalRect(xPosition + width / 2, yPosition, xOffset + w - width / 2, yOffset + hoverState * h, width / 2, h);
        mouseDragged(minecraft, mouseX, mouseY);
        drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (h - 8) / 2, getTextColor(mouseOver));
    }

    public int getHeight()
    {
        return texture.getHeight();
    }

    public int getTextColor(boolean mouseOver)
    {
        if (!enabled)
        {
            return 0xffa0a0a0;
        }
        else if (mouseOver)
        {
            return 0xffffa0;
        }
        else
        {
            return 0xe0e0e0;
        }
    }

    public ToolTip getToolTip()
    {
        return toolTip;
    }

    public int getWidth()
    {
        return width;
    }

    public boolean isMouseOverButton(int mouseX, int mouseY)
    {
        return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + getHeight();
    }

    public void setToolTip(ToolTip tips)
    {
        toolTip = tips;
    }
}
