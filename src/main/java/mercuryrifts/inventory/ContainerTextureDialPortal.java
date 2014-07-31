package mercuryrifts.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import mercuryrifts.tileentity.TileDiallingDevice;

public class ContainerTextureDialPortal extends ContainerTexturePortal
{
    TileDiallingDevice dial;

    public ContainerTextureDialPortal(TileDiallingDevice d, InventoryPlayer p)
    {
        super(d.getPortalController(), p);
        dial = d;
    }
}
