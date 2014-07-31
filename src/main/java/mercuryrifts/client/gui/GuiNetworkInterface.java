package mercuryrifts.client.gui;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import mercuryrifts.mercuryrifts;
import mercuryrifts.client.gui.elements.ElementGlyphDisplay;
import mercuryrifts.inventory.ContainerNetworkInterface;
import mercuryrifts.network.GuiHandler;
import mercuryrifts.network.packet.PacketRequestGui;
import mercuryrifts.tileentity.TileController;

public class GuiNetworkInterface extends BaseGui
{
    public static final int CONTAINER_SIZE = 68;
    TileController controller;
    ElementGlyphDisplay display;

    public GuiNetworkInterface(TileController c, EntityPlayer p)
    {
        super(new ContainerNetworkInterface(c, p.inventory), CONTAINER_SIZE);
        controller = c;
        name = "gui.networkInterface";
        setHidePlayerInventory();
    }
    
    @Override
    protected void mouseClicked(int x, int y, int button)
    {
        super.mouseClicked(x, y, button);

        if (x >= guiLeft + 7 && x <= guiLeft + 169 && y >= guiTop + 29 && y < guiTop + 47)
        {
            mercuryrifts.packetPipeline.sendToServer(new PacketRequestGui(controller, GuiHandler.NETWORK_INTERFACE_B));
        }
    }
    
    @Override
    public void initGui()
    {
        super.initGui();
        display = new ElementGlyphDisplay(this, 7, 29, controller.getIdentifierNetwork());
        addElement(display);
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        display.setIdentifier(controller.getIdentifierNetwork());
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        super.drawGuiContainerForegroundLayer(x, y);
        getFontRenderer().drawString(mercuryrifts.localize("gui.networkIdentifier"), 7, 19, 0x404040);
        getFontRenderer().drawString(mercuryrifts.localize("gui.networkedPortals"), 7, 52, 0x404040);
        String s = controller.connectedPortals == -1 ? mercuryrifts.localize("gui.notSet") : "" + controller.connectedPortals;
        getFontRenderer().drawString(s, xSize - getFontRenderer().getStringWidth(s) - 7, 52, 0x404040);

        if (x >= guiLeft + 7 && x <= guiLeft + 169 && y >= guiTop + 29 && y < guiTop + 47)
        {
            drawHoveringText(Arrays.asList(new String[] { mercuryrifts.localize("gui.clickToModify") }), x - guiLeft, y - guiTop, getFontRenderer());
        }
    }
}
