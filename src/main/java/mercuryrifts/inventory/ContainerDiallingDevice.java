package mercuryrifts.inventory;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import mercuryrifts.mercuryrifts;
import mercuryrifts.network.GuiHandler;
import mercuryrifts.network.packet.PacketGui;
import mercuryrifts.network.packet.PacketTextureData;
import mercuryrifts.portal.GlyphElement;
import mercuryrifts.tileentity.TileDiallingDevice;

public class ContainerDiallingDevice extends BaseContainer
{
    TileDiallingDevice dial;
    ArrayList<String> list = new ArrayList<String>();

    public ContainerDiallingDevice(TileDiallingDevice d, InventoryPlayer p)
    {
        super(null, p);
        dial = d;
        hideInventorySlots();
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        if (list.size() != dial.glyphList.size())
        {
            updateAll();
        }
        else
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (!list.get(i).equals(dial.glyphList.get(i).name))
                {
                    updateAll();
                    break;
                }
            }
        }

        list.clear();

        for (GlyphElement e : dial.glyphList)
        {
            list.add(e.name);
        }
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("terminate"))
        {
            dial.getPortalController().connectionTerminate();
        }
        else if (tag.hasKey("dial"))
        {
            int id = tag.getInteger("dial");

            if (dial.glyphList.size() > id)
            {
                GlyphElement e = dial.glyphList.get(id);
                dial.getPortalController().connectionDial(e.identifier, e.texture, player);
            }
        }
        else if (tag.hasKey("edit"))
        {
            int id = tag.getInteger("edit");

            if (dial.glyphList.size() > id)
            {
                GlyphElement e = dial.glyphList.get(id);
                player.openGui(mercuryrifts.instance, GuiHandler.DIALLING_DEVICE_D, dial.getWorldObj(), dial.xCoord, dial.yCoord, dial.zCoord);
                mercuryrifts.packetPipeline.sendTo(new PacketTextureData(e.name, e.identifier.getGlyphString(), e.texture), (EntityPlayerMP) player);
            }
        }
        else if (tag.hasKey("delete"))
        {
            int id = tag.getInteger("delete");

            if (dial.glyphList.size() > id)
            {
                dial.glyphList.remove(id);
            }

            mercuryrifts.packetPipeline.sendTo(new PacketGui(dial), (EntityPlayerMP) player);
        }
    }

    void updateAll()
    {
        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting) crafters.get(i);
            mercuryrifts.packetPipeline.sendTo(new PacketGui(dial), (EntityPlayerMP) icrafting);
        }
    }
}
