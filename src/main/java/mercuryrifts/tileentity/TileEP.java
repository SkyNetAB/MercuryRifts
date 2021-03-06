package mercuryrifts.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import mercuryrifts.utility.WorldCoordinates;

public class TileEP extends TileEntity
{
    @Override
    public boolean canUpdate()
    {
        return false;
    }

    public ChunkCoordinates getChunkCoordinates()
    {
        return new ChunkCoordinates(xCoord, yCoord, zCoord);
    }

    public WorldCoordinates getWorldCoordinates()
    {
        return new WorldCoordinates(getChunkCoordinates(), worldObj.provider.dimensionId);
    }

    public void packetGuiFill(ByteBuf buffer)
    {

    }

    public void packetGuiUse(ByteBuf buffer)
    {

    }
}
