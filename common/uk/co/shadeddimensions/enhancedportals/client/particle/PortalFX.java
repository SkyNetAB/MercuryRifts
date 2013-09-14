package uk.co.shadeddimensions.enhancedportals.client.particle;

import java.awt.Color;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class PortalFX extends EntityFX
{

    private float portalParticleScale;
    private double portalPosX;
    private double portalPosY;
    private double portalPosZ;

    public PortalFX(World par1World, int ParticleType, int ParticleColour, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        motionX = par8;
        motionY = par10;
        motionZ = par12;
        portalPosX = posX = par2;
        portalPosY = posY = par4;
        portalPosZ = posZ = par6;

        if (ParticleColour == 0xB336A1)
        {
            float f = rand.nextFloat() * 0.6F + 0.4F;
            particleRed = particleGreen = particleBlue = 1.0F * f;
            particleGreen *= 0.3F;
            particleRed *= 0.9F;
        }
        else
        {
            Color c = new Color(ParticleColour);
            particleRed = c.getRed();
            particleGreen = c.getGreen();
            particleBlue = c.getBlue();
        }

        portalParticleScale = particleScale = rand.nextFloat() * 0.2F + 0.5F;
        particleMaxAge = (int) (Math.random() * 10.0D) + 40;
        noClip = true;

        setParticleTextureIndex(getParticleIndex(ParticleType));
    }

    public static int getStaticParticleIndex(int ParticleType)
    {
        switch (ParticleType)
        {
            case 0:
                return 7;

            case 2:
                return 49;

            case 11:
                return 129;

            case 12:
                return 145;

            case 13:
                return 161;

            default:
                return getParticleIndex(ParticleType);
        }
    }

    public static int getParticleIndex(int ParticleType)
    {
        switch (ParticleType)
        {
            default:
            case 0:
                return (int) (Math.random() * 8.0D);

            case 1:
                return 32;

            case 2:
                return 48 + (int) (2 * Math.random());

            case 3:
                return 64;

            case 4:
                return 65;

            case 5:
                return 66;

            case 6:
                return 80;

            case 7:
                return 81;

            case 8:
                return 82;

            case 9:
                return 83;

            case 10:
                return 97;

            case 11:
                return 128 + (int) (8 * Math.random());

            case 12:
                return 144 + (int) (8 * Math.random());

            case 13:
                return 160 + (int) (8 * Math.random());
        }
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness(float par1)
    {
        float var2 = super.getBrightness(par1);
        float var3 = (float) particleAge / (float) particleMaxAge;
        var3 = var3 * var3 * var3 * var3;
        return var2 * (1.0F - var3) + var3;
    }

    @Override
    public int getBrightnessForRender(float par1)
    {
        int var2 = super.getBrightnessForRender(par1);
        float var3 = (float) particleAge / (float) particleMaxAge;
        var3 *= var3;
        var3 *= var3;
        int var4 = var2 & 255;
        int var5 = var2 >> 16 & 255;
        var5 += (int) (var3 * 15.0F * 16.0F);

        if (var5 > 240)
        {
            var5 = 240;
        }

        return var4 | var5 << 16;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        float var1 = (float) particleAge / (float) particleMaxAge;
        float var2 = var1;
        var1 = -var1 + var1 * var1 * 2.0F;
        var1 = 1.0F - var1;
        posX = portalPosX + motionX * var1;
        posY = portalPosY + motionY * var1 + (1.0F - var2);
        posZ = portalPosZ + motionZ * var1;

        if (particleAge++ >= particleMaxAge)
        {
            setDead();
        }
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var8 = (particleAge + par2) / particleMaxAge;
        var8 = 1.0F - var8;
        var8 *= var8;
        var8 = 1.0F - var8;
        particleScale = portalParticleScale * var8;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }
}
