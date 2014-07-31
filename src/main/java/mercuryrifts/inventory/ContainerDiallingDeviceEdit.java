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

public class ContainerDiallingDeviceEdit extends ContainerDiallingDeviceSave
{
    public ContainerDiallingDeviceEdit(TileDiallingDevice d, InventoryPlayer p)
    {
        super(d, p);
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("id") && tag.hasKey("uid") && tag.hasKey("texture") && tag.hasKey("name"))
        {
            PortalTextureManager ptm = new PortalTextureManager();
            ptm.readFromNBT(tag, "texture");
            dial.glyphList.set(tag.getInteger("id"), new GlyphElement(tag.getString("name"), new GlyphIdentifier(tag.getString("uid")), ptm));
            player.openGui(mercuryrifts.instance, GuiHandler.DIALLING_DEVICE_A, dial.getWorldObj(), dial.xCoord, dial.yCoord, dial.zCoord);
        }
    }
}
