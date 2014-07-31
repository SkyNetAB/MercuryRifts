package mercuryrifts.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import mercuryrifts.mercuryrifts;

public class ItemNanobrush extends Item
{
    public static ItemNanobrush instance;

    public static IIcon texture;

    public ItemNanobrush(String n)
    {
        super();
        instance = this;
        setCreativeTab(mercuryrifts.creativeTab);
        setUnlocalizedName(n);
        setMaxStackSize(1);
    }

    @Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
    {
        return true;
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return texture;
    }

    @Override
    public void registerIcons(IIconRegister register)
    {
        texture = register.registerIcon("mercuryrifts:paintbrush");
    }
}
