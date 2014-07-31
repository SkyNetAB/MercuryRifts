package mercuryrifts.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import mercuryrifts.mercuryrifts;
import mercuryrifts.block.BlockPortal;

public class CreativeTabmr extends CreativeTabs
{
    public CreativeTabmr()
    {
        super(mercuryrifts.ID);
    }

    @Override
    public Item getTabIconItem()
    {
        return new ItemStack(BlockPortal.instance).getItem();
    }
}
