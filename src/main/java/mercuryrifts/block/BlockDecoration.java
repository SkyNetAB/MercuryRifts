package mercuryrifts.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import mercuryrifts.mercuryrifts;

public class BlockDecoration extends Block
{
    protected BlockDecoration(String n)
    {
        super(Material.rock);
        setBlockName(n);
        setHardness(3);
        setStepSound(soundTypeStone);
        setCreativeTab(mercuryrifts.creativeTab);
    }
}
