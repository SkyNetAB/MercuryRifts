package mercuryrifts.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import mercuryrifts.mercuryrifts;
import mercuryrifts.network.GuiHandler;
import mercuryrifts.portal.GlyphElement;
import mercuryrifts.portal.GlyphIdentifier;
import mercuryrifts.portal.PortalTextureManager;
import mercuryrifts.tileentity.TileDiallingDevice;

public class ContainerDiallingDeviceSave extends BaseContainer
{
    TileDiallingDevice dial;

    public ContainerDiallingDeviceSave(TileDiallingDevice d, InventoryPlayer p)
    {
        super(null, p);
        dial = d;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("uid") && tag.hasKey("texture") && tag.hasKey("name"))
        {
            PortalTextureManager ptm = new PortalTextureManager();
            ptm.readFromNBT(tag, "texture");
            dial.glyphList.add(new GlyphElement(tag.getString("name"), new GlyphIdentifier(tag.getString("uid")), ptm));
            player.openGui(mercuryrifts.instance, GuiHandler.DIALLING_DEVICE_A, dial.getWorldObj(), dial.xCoord, dial.yCoord, dial.zCoord);
        }
    }
}
