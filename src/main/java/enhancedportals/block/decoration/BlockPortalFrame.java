package enhancedportals.block.decoration;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import enhancedportals.block.BlockFrame;
import enhancedportals.utility.ConnectedTextures;

public class BlockPortalFrame extends BlockDecoration
{
    public static BlockPortalFrame instance;
    static ConnectedTextures connectedTextures;

    public BlockPortalFrame(String n)
    {
        super(n);
        instance = this;
        connectedTextures = BlockFrame.connectedTextures.copy(this, -1);
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int f)
    {
        return connectedTextures.getIconForSide(blockAccess, x, y, z, f);
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return connectedTextures.getBaseIcon();
    }

    @Override
    public void registerBlockIcons(IIconRegister iir)
    {

    }
}
