package mercuryrifts.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import mercuryrifts.mercuryrifts;
import mercuryrifts.inventory.ContainerRedstoneInterface;
import mercuryrifts.network.packet.PacketGuiData;
import mercuryrifts.tileentity.TileRedstoneInterface;

public class GuiRedstoneInterface extends BaseGui
{
    public static final int CONTAINER_SIZE = 68;
    TileRedstoneInterface redstone;

    public GuiRedstoneInterface(TileRedstoneInterface ri, EntityPlayer p)
    {
        super(new ContainerRedstoneInterface(ri, p.inventory), CONTAINER_SIZE);
        name = "gui.redstoneInterface";
        redstone = ri;
        setHidePlayerInventory();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(new GuiButton(0, guiLeft + 8, guiTop + 18, xSize - 16, 20, ""));
        buttonList.add(new GuiButton(1, guiLeft + 8, guiTop + 40, xSize - 16, 20, ""));
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("id", button.id);
        mercuryrifts.packetPipeline.sendToServer(new PacketGuiData(tag));
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();

        String stateText = "";
        boolean flag = redstone.isOutput;

        switch (redstone.state)
        {
            case 0:
                stateText = flag ? mercuryrifts.localize("gui.portalCreated") : mercuryrifts.localize("gui.createPortalOnSignal");
                break;

            case 1:
                stateText = flag ? mercuryrifts.localize("gui.portalRemoved") : mercuryrifts.localize("gui.removePortalOnSignal");
                break;

            case 2:
                stateText = flag ? mercuryrifts.localize("gui.portalActive") : mercuryrifts.localize("gui.createPortalOnPulse");
                break;

            case 3:
                stateText = flag ? mercuryrifts.localize("gui.portalInactive") : mercuryrifts.localize("gui.removePortalOnPulse");
                break;

            case 4:
                stateText = flag ? mercuryrifts.localize("gui.entityTeleport") : mercuryrifts.localize("gui.dialStoredIdentifier");
                break;

            case 5:
                stateText = flag ? mercuryrifts.localize("gui.playerTeleport") : mercuryrifts.localize("gui.dialStoredIdentifier2");
                break;

            case 6:
                stateText = flag ? mercuryrifts.localize("gui.animalTeleport") : mercuryrifts.localize("gui.dialRandomIdentifier");
                break;

            case 7:
                stateText = flag ? mercuryrifts.localize("gui.monsterTeleport") : mercuryrifts.localize("gui.dialRandomIdentifier2");
                break;
        }

        ((GuiButton) buttonList.get(0)).displayString = redstone.isOutput ? mercuryrifts.localize("gui.output") : mercuryrifts.localize("gui.input");
        ((GuiButton) buttonList.get(1)).displayString = stateText;
    }
}
