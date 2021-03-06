package mercuryrifts.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import mercuryrifts.mercuryrifts;
import mercuryrifts.client.gui.elements.ElementRedstoneFlux;
import mercuryrifts.client.gui.tabs.TabRedstoneFlux;
import mercuryrifts.inventory.ContainerDimensionalBridgeStabilizer;
import mercuryrifts.network.packet.PacketGuiData;
import mercuryrifts.tileentity.TileStabilizerMain;
import mercuryrifts.utility.GeneralUtils;

public class GuiDimensionalBridgeStabilizer extends BaseGui
{
    public static final int CONTAINER_SIZE = 90, CONTAINER_SIZE_SMALL = 44;
    TileStabilizerMain stabilizer;

    public GuiDimensionalBridgeStabilizer(TileStabilizerMain s, EntityPlayer p)
    {
        super(new ContainerDimensionalBridgeStabilizer(s, p.inventory), GeneralUtils.hasEnergyCost() ? CONTAINER_SIZE : CONTAINER_SIZE_SMALL);
        stabilizer = s;
        name = "gui.dimensionalBridgeStabilizer";
        setCombinedInventory();
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("button", false);
            mercuryrifts.packetPipeline.sendToServer(new PacketGuiData(tag));
        }
    }
    
    @Override
    public void initGui()
    {
        super.initGui();
        
        if (GeneralUtils.hasEnergyCost())
        {
            buttonList.add(new GuiButton(0, guiLeft + 7, guiTop + containerSize - 27, 140, 20, stabilizer.powerState == 0 ? mercuryrifts.localize("gui.powerModeNormal") : stabilizer.powerState == 1 ? mercuryrifts.localize("gui.powerModeRisky") : stabilizer.powerState == 2 ? mercuryrifts.localize("gui.powerModeUnstable") : mercuryrifts.localize("gui.powerModeUnpredictable")));
            addElement(new ElementRedstoneFlux(this, xSize - 23, 18, stabilizer.getEnergyStorage()));
            addTab(new TabRedstoneFlux(this, stabilizer));
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        super.drawGuiContainerBackgroundLayer(f, i, j);
        
        mc.renderEngine.bindTexture(playerInventoryTexture);
        drawTexturedModalRect(guiLeft + xSize - 25, guiTop + containerSize - 26, 7, 7, 18, 18);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        
        String s1 = "" + stabilizer.intActiveConnections * 2;
        getFontRenderer().drawString(mercuryrifts.localize("gui.information"), 8, 18, 0x404040);
        getFontRenderer().drawString(mercuryrifts.localize("gui.activePortals"), 12, 28, 0x777777);
        getFontRenderer().drawString(s1, xSize - 27 - getFontRenderer().getStringWidth(s1), 28, 0x404040);
        
        if (GeneralUtils.hasEnergyCost())
        {
            int instability = stabilizer.powerState == 0 ? stabilizer.instability : stabilizer.powerState == 1 ? 20 : stabilizer.powerState == 2 ? 50 : 70;
            String s2 = instability + "%";
            
            getFontRenderer().drawString(mercuryrifts.localize("gui.instability"), 12, 38, 0x777777);
            getFontRenderer().drawString(s2, xSize - 27 - getFontRenderer().getStringWidth(s2), 38, instability == 0 ? 0x00BB00 : instability == 20 ? 0xDD6644 : instability == 50 ? 0xDD4422 : 0xFF0000);
        }
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        
        if (GeneralUtils.hasEnergyCost())
        {
            ((GuiButton) buttonList.get(0)).displayString = stabilizer.powerState == 0 ? mercuryrifts.localize("gui.powerModeNormal") : stabilizer.powerState == 1 ? mercuryrifts.localize("gui.powerModeRisky") : stabilizer.powerState == 2 ? mercuryrifts.localize("gui.powerModeUnstable") : mercuryrifts.localize("gui.powerModeUnpredictable");
        }
    }
}
