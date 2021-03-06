package mercuryrifts.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import mercuryrifts.mercuryrifts;
import mercuryrifts.client.gui.elements.ElementFluid;
import mercuryrifts.client.gui.elements.ElementRedstoneFlux;
import mercuryrifts.inventory.ContainerTransferFluid;
import mercuryrifts.network.packet.PacketGuiData;
import mercuryrifts.tileentity.TileTransferFluid;

public class GuiTransferFluid extends BaseGui
{
    public static final int CONTAINER_SIZE = 75;
    TileTransferFluid fluid;

    public GuiTransferFluid(TileTransferFluid f, EntityPlayer p)
    {
        super(new ContainerTransferFluid(f, p.inventory), CONTAINER_SIZE);
        name = "gui.transferFluid";
        fluid = f;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(new GuiButton(1, guiLeft + 7, guiTop + 49, 140, 20, mercuryrifts.localize("gui." + (fluid.isSending ? "sending" : "receiving"))));
        addElement(new ElementFluid(this, xSize - 25, 7, fluid.tank));
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1)
        {
            mercuryrifts.packetPipeline.sendToServer(new PacketGuiData(new NBTTagCompound()));
        }
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        ((GuiButton) buttonList.get(0)).displayString = mercuryrifts.localize("gui." + (fluid.isSending ? "sending" : "receiving"));
    }
}
