package mercuryrifts.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import mercuryrifts.mercuryrifts;

public class ItemBlankPortalModule extends Item
{
    public static ItemBlankPortalModule instance;
    IIcon texture;

    public ItemBlankPortalModule(String n)
    {
        super();
        instance = this;
        setCreativeTab(mercuryrifts.creativeTab);
        setUnlocalizedName(n);
    }

    @Override
    public IIcon getIconFromDamage(int meta)
    {
        return texture;
    }

    @Override
    public void registerIcons(IIconRegister ir)
    {
        texture = ir.registerIcon("mercuryrifts:blank_portal_module");
    }
}
