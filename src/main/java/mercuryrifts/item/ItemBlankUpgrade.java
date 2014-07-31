package mercuryrifts.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import mercuryrifts.mercuryrifts;

public class ItemBlankUpgrade extends Item
{
    public static ItemBlankUpgrade instance;
    IIcon texture;

    public ItemBlankUpgrade(String n)
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
        texture = ir.registerIcon("mercuryrifts:blank_upgrade");
    }
}
