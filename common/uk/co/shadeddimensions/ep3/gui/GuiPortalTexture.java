package uk.co.shadeddimensions.ep3.gui;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import uk.co.shadeddimensions.ep3.client.particle.PortalFX;
import uk.co.shadeddimensions.ep3.container.ContainerPortalTexture;
import uk.co.shadeddimensions.ep3.gui.slider.GuiBetterSlider;
import uk.co.shadeddimensions.ep3.gui.slider.GuiRGBSlider;
import uk.co.shadeddimensions.ep3.lib.Reference;
import uk.co.shadeddimensions.ep3.network.ClientProxy;
import uk.co.shadeddimensions.ep3.network.CommonProxy;
import uk.co.shadeddimensions.ep3.portal.StackHelper;
import uk.co.shadeddimensions.ep3.tileentity.frame.TilePortalController;
import uk.co.shadeddimensions.ep3.util.GuiPayload;

public class GuiPortalTexture extends GuiResizable
{
    TilePortalController controller;
    int pType;

    public GuiPortalTexture(EntityPlayer player, TilePortalController control)
    {
        super(new ContainerPortalTexture(player, control), control);
        controller = control;
        pType = control.particleType;
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 10 || button.id == 11)
        {
            boolean reset = false;
            
            if (button.id == 11)
            {
                ((GuiRGBSlider) buttonList.get(0)).sliderValue = 1f;
                ((GuiRGBSlider) buttonList.get(1)).sliderValue = 1f;
                ((GuiRGBSlider) buttonList.get(2)).sliderValue = 1f;

                Color c = new Color(0xB336A1);
                ((GuiRGBSlider) buttonList.get(3)).sliderValue = c.getRed() / 255f;
                ((GuiRGBSlider) buttonList.get(4)).sliderValue = c.getGreen() / 255f;
                ((GuiRGBSlider) buttonList.get(5)).sliderValue = c.getBlue() / 255f;
                
                reset = true;
            }

            String portalColourHex = String.format("%02x%02x%02x", ((GuiRGBSlider) buttonList.get(0)).getValue(), ((GuiRGBSlider) buttonList.get(1)).getValue(), ((GuiRGBSlider) buttonList.get(2)).getValue());
            String particleColourHex = String.format("%02x%02x%02x", ((GuiRGBSlider) buttonList.get(3)).getValue(), ((GuiRGBSlider) buttonList.get(4)).getValue(), ((GuiRGBSlider) buttonList.get(5)).getValue());

            GuiPayload payload = new GuiPayload();
            payload.data.setInteger("portalColour", Integer.parseInt(portalColourHex, 16));
            payload.data.setInteger("particleColour", Integer.parseInt(particleColourHex, 16));
            
            if (reset)
            {
                payload.data.setInteger("particleType", 0);
                payload.data.setInteger("resetSlot", 1);
            }
            
            ClientProxy.sendGuiPacket(payload);
        }
        else if (button.id == 12 || button.id == 13)
        {
            boolean changed = false;

            if (controller.particleType > 0 && button.id == 12)
            {
                controller.particleType--;
                changed = true;
            }
            else if (controller.particleType < 13 && button.id == 13)
            {
                controller.particleType++;
                changed = true;
            }
            else if (controller.particleType == 0 && button.id == 12)
            {
                controller.particleType = 13;
                changed = true;
            }
            else if (controller.particleType == 13 && button.id == 13)
            {
                controller.particleType = 0;
                changed = true;
            }

            if (changed)
            {
                GuiPayload payload = new GuiPayload();
                payload.data.setInteger("particleType", controller.particleType);                
                ClientProxy.sendGuiPacket(payload);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        super.drawGuiContainerBackgroundLayer(f, i, j);

        drawTab(guiLeft + xSize, guiTop + 10, 87, 97, 0.4f, 0.4f, 1f);
        drawTabFlipped(guiLeft - 87, guiTop + 10, 87, 97, 0.4f, 0.4f, 1f);

        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.renderEngine.bindTexture(new ResourceLocation("enhancedportals", "textures/gui/inventorySlots.png"));
        drawTexturedModalRect(guiLeft + 7, guiTop + 83, 0, 0, 162, 54);
        drawTexturedModalRect(guiLeft + 7, guiTop + 141, 0, 0, 162, 18);
        drawTexturedModalRect(guiLeft + 37, guiTop + 22, 0, 0, 18, 18);
        drawTexturedModalRect(guiLeft + xSize - 48, guiTop + 22, 0, 0, 18, 18);
        drawTexturedModalRect(guiLeft + xSize - 28, guiTop + 22, 0, 0, 18, 18);

        drawParticle(38, 23, ((GuiRGBSlider) buttonList.get(3)).getValue(), ((GuiRGBSlider) buttonList.get(4)).getValue(), ((GuiRGBSlider) buttonList.get(5)).getValue(), 255, PortalFX.getStaticParticleIndex(controller.particleType), true);

        ItemStack stack = controller.getStackInSlot(1) == null ? new ItemStack(Block.portal, 1) : controller.getStackInSlot(1);

        if (StackHelper.isStackDye(stack))
        {
            ItemStack s = new ItemStack(CommonProxy.blockPortal.blockID, 1, StackHelper.getDyeColour(stack));
            stack = s;
        }

        GL11.glColor3f(((GuiRGBSlider) buttonList.get(0)).sliderValue, ((GuiRGBSlider) buttonList.get(1)).sliderValue, ((GuiRGBSlider) buttonList.get(2)).sliderValue);
        itemRenderer.renderWithColor = false;
        itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, stack, guiLeft + xSize - 27, guiTop + 23);
        itemRenderer.renderWithColor = true;
        GL11.glColor3f(1f, 1f, 1f);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);

        fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".portalTexture"), xSize / 2 - fontRenderer.getStringWidth(StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".portalTexture")) / 2, -13, 0xFFFFFF);

        fontRenderer.drawString(StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".texture"), 8, 8, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, 70, 0x404040);

        fontRenderer.drawString(StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".portalColour"), xSize + 6, 18, 0xe1c92f);
        fontRenderer.drawString(StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".particleColour"), -80, 18, 0xe1c92f);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        super.initGui();

        Color c = new Color(controller.portalColour);
        buttonList.add(new GuiRGBSlider(0, guiLeft + xSize + 5, guiTop + 32, StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".colour.red"), c.getRed() / 255f));
        buttonList.add(new GuiRGBSlider(1, guiLeft + xSize + 5, guiTop + 56, StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".colour.green"), c.getGreen() / 255f));
        buttonList.add(new GuiRGBSlider(2, guiLeft + xSize + 5, guiTop + 80, StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".colour.blue"), c.getBlue() / 255f));

        c = new Color(controller.particleColour);
        buttonList.add(new GuiRGBSlider(3, guiLeft - 79, guiTop + 32, StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".colour.red"), c.getRed() / 255f));
        buttonList.add(new GuiRGBSlider(4, guiLeft - 79, guiTop + 56, StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".colour.green"), c.getGreen() / 255f));
        buttonList.add(new GuiRGBSlider(5, guiLeft - 79, guiTop + 80, StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".colour.blue"), c.getBlue() / 255f));

        buttonList.add(new GuiButton(10, guiLeft + xSize - 75 - 7, guiTop + 45, 75, 20, StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".button.save")));
        buttonList.add(new GuiButton(11, guiLeft + 8, guiTop + 45, 75, 20, StatCollector.translateToLocal("gui." + Reference.SHORT_ID + ".button.reset")));

        buttonList.add(new GuiButton(12, guiLeft + 8, guiTop + 21, 16, 20, "<"));
        buttonList.add(new GuiButton(13, guiLeft + 66, guiTop + 21, 16, 20, ">"));
    }

    @Override
    protected void mouseMovedOrUp(int par1, int par2, int par3)
    {
        super.mouseMovedOrUp(par1, par2, par3);

        if (par3 == 0)
        {
            for (Object o : buttonList)
            {
                if (o instanceof GuiBetterSlider)
                {
                    GuiBetterSlider slider = (GuiBetterSlider) o;
                    slider.mouseReleased(par1, par2);
                }
            }
        }
    }
}
