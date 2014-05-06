package enhancedportals.inventory;

import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.tileentity.portal.TileDiallingDevice;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerDiallingDeviceManual extends BaseContainer
{
    TileDiallingDevice dial;

    public ContainerDiallingDeviceManual(TileDiallingDevice d, InventoryPlayer p)
    {
        super(null, p);
        dial = d;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("dial"))
        {
            dial.getPortalController().connectionDial(new GlyphIdentifier(tag.getString("dial")), null, player);
        }
    }
}
