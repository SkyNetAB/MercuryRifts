package mercuryrifts.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import mercuryrifts.mercuryrifts;
import mercuryrifts.client.gui.BaseGui;
import mercuryrifts.client.gui.GuiDimensionalBridgeStabilizer;
import mercuryrifts.inventory.slot.SlotDBS;
import mercuryrifts.network.packet.PacketGuiData;
import mercuryrifts.tileentity.TileStabilizerMain;
import mercuryrifts.utility.GeneralUtils;

public class ContainerDimensionalBridgeStabilizer extends BaseContainer
{
    int lastPower = 0, lastMaxPower = 0, lastPortals = -1, lastInstability = 0, lastPowerState = -1;
    TileStabilizerMain stabilizer;

    public ContainerDimensionalBridgeStabilizer(TileStabilizerMain s, InventoryPlayer p)
    {
        super(s, p, (GeneralUtils.hasEnergyCost() ? GuiDimensionalBridgeStabilizer.CONTAINER_SIZE : GuiDimensionalBridgeStabilizer.CONTAINER_SIZE_SMALL) + BaseGui.bufferSpace + BaseGui.playerInventorySize);
        stabilizer = s;

        int container = GeneralUtils.hasEnergyCost() ? GuiDimensionalBridgeStabilizer.CONTAINER_SIZE : GuiDimensionalBridgeStabilizer.CONTAINER_SIZE_SMALL;
        addSlotToContainer(new SlotDBS(s, 0, 152, container - 25));
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        int currentPower = stabilizer.getEnergyStorage().getEnergyStored(), currentMaxPower = stabilizer.getEnergyStorage().getMaxEnergyStored(), currentPortals = stabilizer.getActiveConnections(), currentInstability = stabilizer.instability, currentPowerState = stabilizer.powerState;

        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting) crafters.get(i);

            if (lastPower != currentPower || lastMaxPower != currentMaxPower)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setInteger("energy", currentPower);
                tag.setInteger("max", currentMaxPower);
                mercuryrifts.packetPipeline.sendTo(new PacketGuiData(tag), (EntityPlayerMP) icrafting);
            }

            if (lastPortals != currentPortals)
            {
                icrafting.sendProgressBarUpdate(this, 2, currentPortals);
            }

            if (lastInstability != currentInstability)
            {
                icrafting.sendProgressBarUpdate(this, 3, currentInstability);
            }

            if (lastPowerState != currentPowerState)
            {
                icrafting.sendProgressBarUpdate(this, 4, currentPowerState);
            }
        }

        lastPower = currentPower;
        lastMaxPower = currentMaxPower;
        lastPortals = currentPortals;
        lastInstability = currentInstability;
        lastPowerState = currentPowerState;
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("button"))
        {
            stabilizer.powerState++;

            if (stabilizer.powerState >= 4)
            {
                stabilizer.powerState = 0;
            }
        }
        else if (tag.hasKey("energy") && tag.hasKey("max"))
        {
            stabilizer.getEnergyStorage().setCapacity(tag.getInteger("max"));
            stabilizer.getEnergyStorage().setEnergyStored(tag.getInteger("energy"));
        }
    }

    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 2)
        {
            stabilizer.intActiveConnections = par2;
        }
        else if (par1 == 3)
        {
            stabilizer.instability = par2;
        }
        else if (par1 == 4)
        {
            stabilizer.powerState = par2;
        }
    }
}
