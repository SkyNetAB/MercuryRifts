package uk.co.shadeddimensions.ep3.tileentity.portal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import uk.co.shadeddimensions.ep3.block.BlockFrame;
import uk.co.shadeddimensions.ep3.item.ItemPaintbrush;
import uk.co.shadeddimensions.ep3.network.GuiHandler;
import uk.co.shadeddimensions.ep3.network.PacketHandlerServer;
import uk.co.shadeddimensions.ep3.tileentity.portal.TileDiallingDevice.GlyphElement;
import uk.co.shadeddimensions.ep3.util.WorldUtils;
import uk.co.shadeddimensions.library.util.ItemHelper;

public class TileRedstoneInterface extends TileFrame
{
	public boolean isOutput = false;
	public byte state = 0, previousRedstoneState = 0;
	byte timeUntilOff = 0;
	static int TPS = 20;
	public static byte MAX_INPUT_STATE = 8, MAX_OUTPUT_STATE = 8;
	long lastInteractTime = 0l;

	@Override
	public void packetGui(NBTTagCompound tag, EntityPlayer player)
	{
		
	}

	@Override
	public boolean canUpdate()
	{
		return true;
	}

	@Override
	public boolean activate(EntityPlayer player, ItemStack stack)
	{
		if (player.isSneaking())
		{
			return false;
		}
		
		TileController controller = getPortalController();

		if (stack != null && controller != null && controller.isFinalized())
		{
			if (ItemHelper.isWrench(stack) && !player.isSneaking())
			{
				GuiHandler.openGui(player, this, GuiHandler.REDSTONE_INTERFACE);
				return true;
			}
			else if (stack.itemID == ItemPaintbrush.ID)
			{
				GuiHandler.openGui(player, controller, GuiHandler.TEXTURE_FRAME);
				return true;
			}
		}

		return false;
	}

	public void onEntityTeleport(Entity entity)
	{
		if (isOutput)
		{
			if (state == 4 || state == 5 && entity instanceof EntityPlayer || state == 6 && entity instanceof EntityAnimal || state == 7 && entity instanceof EntityMob)
			{
				timeUntilOff = (byte) TPS;
			}

			notifyNeighbors();
		}
	}

	@Override
	public void packetGuiFill(DataOutputStream stream) throws IOException
	{
		stream.writeBoolean(isOutput);
		stream.writeByte(state);
	}

	public int isProvidingPower(int side)
	{
		if (timeUntilOff != 0)
		{
			return 15;
		}

		return 0;
	}

	private void notifyNeighbors()
	{
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, BlockFrame.ID);

		for (int i = 0; i < 6; i++)
		{
			ForgeDirection d = ForgeDirection.getOrientation(i);
			worldObj.notifyBlocksOfNeighborChange(xCoord + d.offsetX, yCoord + d.offsetY, zCoord + d.offsetZ, BlockFrame.ID);
		}
	}

	public void onPortalCreated()
	{
		if (isOutput)
		{
			if (state == 0)
			{
				timeUntilOff = (byte) TPS;
			}
			else if (state == 2)
			{
				timeUntilOff = -1;
			}
			else if (state == 3)
			{
				timeUntilOff = 0;
			}

			notifyNeighbors();
		}
	}

	public void onPortalRemoved()
	{
		if (isOutput)
		{
			if (state == 1)
			{
				timeUntilOff = (byte) TPS;
			}
			else if (state == 2)
			{
				timeUntilOff = 0;
			}
			else if (state == 3)
			{
				timeUntilOff = -1;
			}

			notifyNeighbors();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		isOutput = tagCompound.getBoolean("output");
		state = tagCompound.getByte("state");
		previousRedstoneState = tagCompound.getByte("previousRedstoneState");
		timeUntilOff = tagCompound.getByte("timeUntilOff");
	}

	public void setState(byte newState)
	{
		state = newState;

		if (timeUntilOff != 0)
		{
			timeUntilOff = 0;
			notifyNeighbors();
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (isOutput)
		{
			if (timeUntilOff > 1)
			{
				timeUntilOff--;
			}
			else if (timeUntilOff == 1)
			{
				timeUntilOff--;
				notifyNeighbors(); // Make sure we update our neighbors
			}
		}
	}

	@Override
	public void packetGuiUse(DataInputStream stream) throws IOException
	{
		isOutput = stream.readBoolean();
		setState(stream.readByte());
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setBoolean("output", isOutput);
		tagCompound.setByte("state", state);
		tagCompound.setByte("previousRedstoneState", previousRedstoneState);
		tagCompound.setByte("timeUntilOff", timeUntilOff);
	}

	public void onNeighborBlockChange(int id)
	{
		if (!isOutput && System.currentTimeMillis() - lastInteractTime > 250 && !worldObj.isRemote)
        {
            lastInteractTime = System.currentTimeMillis();            
            TileController controller = getPortalController();

            if (controller == null)
            {
                return;
            }

            boolean hasDialler = controller.getDiallingDeviceCount() > 0;
            int redstoneInputState = WorldUtils.getHighestPowerState(this);

            if (state == 1) // Remove portal on signal
            {
                if (redstoneInputState > 0 && controller.isPortalActive())
                {
                    controller.connectionTerminate();
                }
                else if (redstoneInputState == 0 && !controller.isPortalActive())
                {
                    controller.connectionDial();
                }
            }
            else if (state == 3 && redstoneInputState > 0 && previousRedstoneState == 0 && controller.isPortalActive()) // Remove portal on pulse
            {
                controller.connectionTerminate();
            }
            else if (!hasDialler) // These require no dialler
            {
                if (state == 0) // Create portal on signal
                {
                    if (redstoneInputState > 0 && !controller.isPortalActive())
                    {
                        controller.connectionDial();
                    }
                    else if (redstoneInputState == 0 && controller.isPortalActive())
                    {
                        controller.connectionTerminate();
                    }
                }
                else if (state == 2 && redstoneInputState > 0 && previousRedstoneState == 0 && !controller.isPortalActive()) // Create portal on pulse
                {
                    controller.connectionDial();
                }
            }
            else
            {
                TileDiallingDevice dialler = controller.getDialDeviceRandom();

                if (dialler == null)
                {
                    return;
                }

                int glyphCount = dialler.glyphList.size();

                if (glyphCount == 0)
                {
                    return;
                }

                if (state == 4 && redstoneInputState > 0 && !controller.isPortalActive()) // Dial specific identifier
                {
                    if (redstoneInputState - 1 < glyphCount)
                    {
                        GlyphElement e = dialler.glyphList.get(redstoneInputState - 1);
                        controller.connectionDial(e.identifier, e.texture, null);
                    }
                }
                else if (state == 5) // Dial specific identifier II
                {
                    if (redstoneInputState > 0 && !controller.isPortalActive())
                    {
                        if (redstoneInputState - 1 < glyphCount)
                        {
                            GlyphElement e = dialler.glyphList.get(redstoneInputState - 1);
                            controller.connectionDial(e.identifier, e.texture, null);
                        }
                    }
                    else if (redstoneInputState == 0 && controller.isPortalActive())
                    {
                        controller.connectionTerminate();
                    }
                }
                else if (state == 6 && redstoneInputState > 0 && !controller.isPortalActive()) // Dial random identifier
                {
                    GlyphElement e = dialler.glyphList.get(new Random().nextInt(glyphCount));
                    controller.connectionDial(e.identifier, e.texture, null);
                }
                else if (state == 7) // Dial random identifier II
                {
                    if (redstoneInputState > 0 && !controller.isPortalActive())
                    {
                        GlyphElement e = dialler.glyphList.get(new Random().nextInt(glyphCount));
                        controller.connectionDial(e.identifier, e.texture, null);
                    }
                    else if (redstoneInputState == 0 && controller.isPortalActive())
                    {
                        controller.connectionTerminate();
                    }
                }
            }
        }
	}

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {
        
    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {
        
    }
}
