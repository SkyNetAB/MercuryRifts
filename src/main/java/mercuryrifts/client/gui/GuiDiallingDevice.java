package mercuryrifts.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import mercuryrifts.mercuryrifts;
import mercuryrifts.client.gui.elements.ElementScrollDiallingDevice;
import mercuryrifts.client.gui.tabs.TabTip;
import mercuryrifts.inventory.ContainerDiallingDevice;
import mercuryrifts.network.ClientProxy;
import mercuryrifts.network.GuiHandler;
import mercuryrifts.network.packet.PacketGuiData;
import mercuryrifts.network.packet.PacketRequestGui;
import mercuryrifts.tileentity.TileController;
import mercuryrifts.tileentity.TileDiallingDevice;

public class GuiDiallingDevice extends BaseGui
{
    public static final int CONTAINER_SIZE = 175, CONTAINER_WIDTH = 256;
    TileDiallingDevice dial;
    TileController controller;
    GuiButton buttonDial;

    public GuiDiallingDevice(TileDiallingDevice d, EntityPlayer p)
    {
        super(new ContainerDiallingDevice(d, p.inventory), CONTAINER_SIZE);
        texture = new ResourceLocation("mercuryrifts", "textures/gui/dialling_device.png");
        xSize = CONTAINER_WIDTH;
        dial = d;
        controller = dial.getPortalController();
        name = "gui.dialDevice";
        setHidePlayerInventory();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        
        buttonDial = new GuiButton(1, guiLeft + xSize - 147, guiTop + ySize - 27, 140, 20, mercuryrifts.localize("gui.terminate"));
        buttonDial.enabled = controller.isPortalActive();
        buttonList.add(new GuiButton(0, guiLeft + 7, guiTop + ySize - 27, 100, 20, mercuryrifts.localize("gui.manualEntry")));
        buttonList.add(buttonDial);
        
        addElement(new ElementScrollDiallingDevice(this, dial, 7, 28));
        addTab(new TabTip(this, "dialling"));
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        buttonDial.enabled = controller.isPortalActive();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        getFontRenderer().drawString(mercuryrifts.localize("gui.storedIdentifiers"), 7, 18, 0x404040);
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            mercuryrifts.packetPipeline.sendToServer(new PacketRequestGui(dial, GuiHandler.DIALLING_DEVICE_B));
        }
        else if (button.id == 1)
        {
            if (controller.isPortalActive())
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setBoolean("terminate", true);
                mercuryrifts.packetPipeline.sendToServer(new PacketGuiData(tag));
            }
        }
    }

    public void onEntrySelected(int entry)
    {
        if (!controller.isPortalActive())
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("dial", entry);
            mercuryrifts.packetPipeline.sendToServer(new PacketGuiData(tag));
            Minecraft.getMinecraft().thePlayer.closeScreen();
        }
    }
    
    public void onEntryEdited(int entry)
    {
        ClientProxy.editingID = entry;
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("edit", entry);
        mercuryrifts.packetPipeline.sendToServer(new PacketGuiData(tag));
    }
    
    public void onEntryDeleted(int entry)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("delete", entry);
        mercuryrifts.packetPipeline.sendToServer(new PacketGuiData(tag));
    }
}
