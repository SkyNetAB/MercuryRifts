package mercuryrifts.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.FMLCommonHandler;
import mercuryrifts.mercuryrifts;
import mercuryrifts.client.gui.BaseGui;
import mercuryrifts.client.gui.GuiPortalControllerGlyphs;
import mercuryrifts.network.GuiHandler;
import mercuryrifts.network.packet.PacketGuiData;
import mercuryrifts.portal.GlyphIdentifier;
import mercuryrifts.portal.PortalException;
import mercuryrifts.tileentity.TileController;

public class ContainerPortalControllerGlyphs extends BaseContainer
{
    TileController controller;

    public ContainerPortalControllerGlyphs(TileController c, InventoryPlayer p)
    {
        super(null, p, GuiPortalControllerGlyphs.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize);
        controller = c;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("uid"))
        {
            try
            {
                controller.setIdentifierUnique(new GlyphIdentifier(tag.getString("uid")));
                player.openGui(mercuryrifts.instance, GuiHandler.PORTAL_CONTROLLER_A, controller.getWorldObj(), controller.xCoord, controller.yCoord, controller.zCoord);
            }
            catch (PortalException e)
            {
                NBTTagCompound errorTag = new NBTTagCompound();
                errorTag.setInteger("error", 0);
                mercuryrifts.packetPipeline.sendTo(new PacketGuiData(errorTag), (EntityPlayerMP) player);
            }
        }
        else if (tag.hasKey("error") && FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            ((GuiPortalControllerGlyphs) Minecraft.getMinecraft().currentScreen).setWarningMessage();
        }
    }
}
