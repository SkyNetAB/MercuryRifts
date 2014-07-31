package mercuryrifts.client.gui;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import mercuryrifts.mercuryrifts;
import mercuryrifts.network.ClientProxy;
import mercuryrifts.network.GuiHandler;
import mercuryrifts.network.packet.PacketRequestGui;
import mercuryrifts.portal.PortalTextureManager;
import mercuryrifts.tileentity.TileDiallingDevice;

public class GuiTextureDialFrame extends GuiTextureFrame
{
    TileDiallingDevice dial;
    boolean didSave, returnToEdit;

    public GuiTextureDialFrame(TileDiallingDevice d, EntityPlayer p)
    {
        this(d, p, false);
    }
    
    public GuiTextureDialFrame(TileDiallingDevice d, EntityPlayer p, boolean r)
    {
        super(d.getPortalController(), p);
        dial = d;
        returnToEdit = r;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        buttonList.add(new GuiButton(1000, guiLeft + 7, guiTop + ySize + 3, xSize - 14, 20, "Save"));

        Color c = new Color(getPTM().getFrameColour());
        sliderR.sliderValue = c.getRed() / 255f;
        sliderG.sliderValue = c.getGreen() / 255f;
        sliderB.sliderValue = c.getBlue() / 255f;
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == buttonSave.id)
        {
            getPTM().setFrameColour(Integer.parseInt(String.format("%02x%02x%02x", sliderR.getValue(), sliderG.getValue(), sliderB.getValue()), 16));
        }
        else if (button.id == buttonReset.id)
        {
            int colour = 0xffffff;
            getPTM().setFrameColour(colour);

            Color c = new Color(colour);
            sliderR.sliderValue = c.getRed() / 255f;
            sliderG.sliderValue = c.getGreen() / 255f;
            sliderB.sliderValue = c.getBlue() / 255f;
        }
        else if (button.id == 1000)
        {
            didSave = true;
            mercuryrifts.packetPipeline.sendToServer(new PacketRequestGui(dial, returnToEdit ? GuiHandler.DIALLING_DEVICE_D : GuiHandler.DIALLING_DEVICE_C));
        }
        else if (button.id == 500)
        {
            didSave = true;
            mercuryrifts.packetPipeline.sendToServer(new PacketRequestGui(dial, returnToEdit ? GuiHandler.TEXTURE_DIALLING_EDIT_B : GuiHandler.TEXTURE_DIALLING_SAVE_B));
        }
        else if (button.id == 501)
        {
            didSave = true;
            mercuryrifts.packetPipeline.sendToServer(new PacketRequestGui(dial, returnToEdit ? GuiHandler.TEXTURE_DIALLING_EDIT_C : GuiHandler.TEXTURE_DIALLING_SAVE_C));
        }
    }
    
    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        
        if (!didSave)
        {
            ClientProxy.saveGlyph = null;
            ClientProxy.saveName = null;
            ClientProxy.saveTexture = null;
        }
    }
    
    @Override
    public void iconSelected(int icon)
    {
        getPTM().setCustomFrameTexture(icon);
    }
    
    @Override
    public void onItemChanged(ItemStack newItem)
    {
        getPTM().setFrameItem(newItem);
    }
    
    @Override
    public PortalTextureManager getPTM()
    {
        return ClientProxy.saveTexture;
    }
}
