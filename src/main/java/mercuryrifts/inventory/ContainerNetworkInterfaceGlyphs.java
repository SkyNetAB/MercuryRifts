package mercuryrifts.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import mercuryrifts.mercuryrifts;
import mercuryrifts.client.gui.BaseGui;
import mercuryrifts.client.gui.GuiNetworkInterfaceGlyphs;
import mercuryrifts.network.GuiHandler;
import mercuryrifts.portal.GlyphIdentifier;
import mercuryrifts.tileentity.TileController;

public class ContainerNetworkInterfaceGlyphs extends BaseContainer
{
    TileController controller;

    public ContainerNetworkInterfaceGlyphs(TileController c, InventoryPlayer p)
    {
        super(null, p, GuiNetworkInterfaceGlyphs.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize);
        controller = c;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("nid"))
        {
            controller.setIdentifierNetwork(new GlyphIdentifier(tag.getString("nid")));
            player.openGui(mercuryrifts.instance, GuiHandler.NETWORK_INTERFACE_A, controller.getWorldObj(), controller.xCoord, controller.yCoord, controller.zCoord);
        }
    }
}
