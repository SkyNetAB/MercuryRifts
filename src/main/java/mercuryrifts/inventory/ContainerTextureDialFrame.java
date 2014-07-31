package mercuryrifts.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import mercuryrifts.tileentity.TileDiallingDevice;

public class ContainerTextureDialFrame extends ContainerTextureFrame
{
    TileDiallingDevice dial;

    public ContainerTextureDialFrame(TileDiallingDevice d, InventoryPlayer p)
    {
        super(d.getPortalController(), p);
        dial = d;
    }
}
