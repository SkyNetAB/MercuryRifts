package uk.co.shadeddimensions.enhancedportals.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import uk.co.shadeddimensions.enhancedportals.client.particle.PortalFX;
import uk.co.shadeddimensions.enhancedportals.tileentity.TilePortal;
import uk.co.shadeddimensions.enhancedportals.tileentity.TilePortalFrameController;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPortal extends BlockEP
{
    public static Icon[] colouredPortalTextures;
    
    public BlockPortal(int id, String name)
    {
        super(id, Material.portal, false);
        setBlockUnbreakable();
        setResistance(2000);
        setUnlocalizedName(name);
        setStepSound(soundGlassFootstep);
        colouredPortalTextures = new Icon[16];
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) < 6)
        {
            return 14;
        }
        
        return 0;
    }

    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
        return colouredPortalTextures[meta];
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        for (int i = 0; i < colouredPortalTextures.length; i++)
        {
            colouredPortalTextures[i] = iconRegister.registerIcon("enhancedportals:colouredPortal_" + i);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata >= 6)
        {
            return;
        }

        if (random.nextInt(100) == 0)
        {
            world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, "portal.portal", 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int l = 0; l < 4; ++l)
        {
            double d0 = x + random.nextFloat();
            double d1 = y + random.nextFloat();
            double d2 = z + random.nextFloat();
            double d3 = 0.0D;
            double d4 = 0.0D;
            double d5 = 0.0D;
            int i1 = random.nextInt(2) * 2 - 1;
            d3 = (random.nextFloat() - 0.5D) * 0.5D;
            d4 = (random.nextFloat() - 0.5D) * 0.5D;
            d5 = (random.nextFloat() - 0.5D) * 0.5D;

            if (metadata == 1)
            {
                d2 = z + 0.5D + 0.25D * i1;
                d5 = random.nextFloat() * 2.0F * i1;
            }
            else if (metadata == 2)
            {
                d0 = x + 0.5D + 0.25D * i1;
                d3 = random.nextFloat() * 2.0F * i1;
            }
            else if (metadata == 3)
            {
                d1 = y + 0.5D + 0.25D * i1;
                d4 = random.nextFloat() * 2.0F * i1;
            }

            int type = 0, colour = 0xB336A1;
            TilePortalFrameController controller = ((TilePortal) world.getBlockTileEntity(x, y, z)).getControllerValidated();

            if (controller != null)
            {
                colour = controller.ParticleColour;
                type = controller.ParticleType;
            }

            FMLClientHandler.instance().getClient().effectRenderer.addEffect(new PortalFX(world, type, colour, d0, d1, d2, d3, d4, d5));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return 0;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
        if (!world.isRemote)
        {
            ((TilePortal) world.getBlockTileEntity(x, y, z)).selfBroken();
        }

        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        // TODO: teleport
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
    {
        int meta = blockAccess.getBlockMetadata(x, y, z);

        if (meta == 1) // X
        {
            setBlockBounds(0f, 0f, 0.375f, 1f, 1f, 0.625f);
        }
        else if (meta == 2) // Z
        {
            setBlockBounds(0.375f, 0f, 0f, 0.625f, 1f, 1f);
        }
        else if (meta == 3) // XZ
        {
            setBlockBounds(0, 0.375f, 0f, 1f, 0.625f, 1f);
        }
        else if (meta >= 6)
        {
            setBlockBounds(0f, 0f, 0f, 0f, 0f, 0f);
        }
        else
        {
            setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TilePortal();
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(World world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return ((TilePortal) par1World.getBlockTileEntity(par2, par3, par4)).activate(par5EntityPlayer);
    }
}
