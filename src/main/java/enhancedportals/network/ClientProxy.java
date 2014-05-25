package enhancedportals.network;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import truetyper.FontLoader;
import truetyper.TrueTypeFont;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import enhancedportals.EnhancedPortals;
import enhancedportals.block.BlockDecorBorderedQuartz;
import enhancedportals.block.BlockDecorStabilizer;
import enhancedportals.block.BlockFrame;
import enhancedportals.block.BlockPortal;
import enhancedportals.block.BlockStabilizer;
import enhancedportals.block.BlockStabilizerEmpty;
import enhancedportals.client.PortalRenderer;
import enhancedportals.item.ItemBlankPortalModule;
import enhancedportals.item.ItemBlankUpgrade;
import enhancedportals.item.ItemGlasses;
import enhancedportals.item.ItemLocationCard;
import enhancedportals.item.ItemNanobrush;
import enhancedportals.item.ItemPortalModule;
import enhancedportals.item.ItemUpgrade;
import enhancedportals.item.ItemWrench;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.portal.PortalTextureManager;

public class ClientProxy extends CommonProxy
{
    public class ParticleSet
    {
        public int[] frames;
        public int type;

        public ParticleSet(int t, int[] s)
        {
            frames = s;
            type = t;
        }
    }

    public static TrueTypeFont bookFont;

    public static int renderPass = 0;

    public static GlyphIdentifier saveGlyph;
    public static PortalTextureManager saveTexture;
    public static String saveName;
    public static int editingID = -1;

    public static String manualEntry = "main";
    public static int manualPage = 0;

    public static int editingDialEntry = -1;
    public static PortalTextureManager dialEntryTexture = new PortalTextureManager();

    public static ArrayList<IIcon> customFrameTextures = new ArrayList<IIcon>();
    public static ArrayList<IIcon> customPortalTextures = new ArrayList<IIcon>();
    public static ArrayList<ParticleSet> particleSets = new ArrayList<ParticleSet>();
    public static Random random = new Random();

    static HashMap<String, ItemStack[]> craftingRecipes = new HashMap<String, ItemStack[]>();

    public static void manualChangeEntry(String entry)
    {
        manualEntry = entry;
        manualPage = 0;
    }

    public static boolean manualEntryHasPage(int page)
    {
        return !EnhancedPortals.localize("manual." + manualEntry + ".page." + page).contains(".page.");
    }

    public static ItemStack[] getCraftingRecipeForManualEntry()
    {
        return craftingRecipes.get(manualEntry);
    }

    public static boolean resourceExists(String file)
    {
        IReloadableResourceManager resourceManager = (IReloadableResourceManager) FMLClientHandler.instance().getClient().getResourceManager();

        try
        {
            resourceManager.getResource(new ResourceLocation("enhancedportals", file));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public static boolean setManualPageFromItem(ItemStack s)
    {
        Item i = s.getItem();
        
        if (i instanceof ItemBlock)
        {
            return setManualPageFromBlock(Block.getBlockFromItem(i), s.getItemDamage());
        }
        else
        {
            if (i == ItemBlankPortalModule.instance)
            {
                manualChangeEntry("blank_module");
                return true;
            }
            else if (i == ItemBlankUpgrade.instance)
            {
                manualChangeEntry("blank_upgrade");
                return true;
            }
            else if (i == ItemGlasses.instance)
            {
                manualChangeEntry("glasses");
                return true;
            }
            else if (i == ItemLocationCard.instance)
            {
                manualChangeEntry("location_card");
                return true;
            }
            else if (i == ItemNanobrush.instance)
            {
                manualChangeEntry("nanobrush");
                return true;
            }
            else if (i == ItemWrench.instance)
            {
                manualChangeEntry("wrench");
                return true;
            }
            else if (i == ItemPortalModule.instance)
            {
                manualChangeEntry("module" + s.getItemDamage());
                return true;
            }
            else if (i == ItemUpgrade.instance)
            {
                manualChangeEntry("upgrade" + s.getItemDamage());
                return true;
            }
        }
        
        return false;
    }

    public static boolean setManualPageFromBlock(Block b, int meta)
    {
        if (b == BlockFrame.instance)
        {
            manualChangeEntry("frame" + meta);
            return true;
        }
        else if (b == BlockPortal.instance)
        {
            manualChangeEntry("portal");
            return true;
        }
        else if (b == BlockDecorStabilizer.instance)
        {
            manualChangeEntry("decorStabilizer");
            return true;
        }
        else if (b == BlockStabilizer.instance)
        {
            manualChangeEntry("dbs");
            return true;
        }
        else if (b == BlockStabilizerEmpty.instance)
        {
            manualChangeEntry("dbsEmpty");
            return true;
        }
        else if (b == BlockDecorBorderedQuartz.instance)
        {
            manualChangeEntry("decorBorderedQuartz");
            return true;
        }

        return false;
    }

    @Override
    public File getWorldDir()
    {
        return new File(getBaseDir(), "saves/" + DimensionManager.getWorld(0).getSaveHandler().getWorldDirectoryName());
    }

    @Override
    public void miscSetup()
    {
        super.miscSetup();

        // Randomly chooses a particle then spawns it, stays static
        particleSets.add(new ParticleSet(0, new int[] { 0, 1, 2, 3, 4, 5, 6, 7 }));
        particleSets.add(new ParticleSet(0, new int[] { 16, 17 }));
        particleSets.add(new ParticleSet(0, new int[] { 19, 20, 21, 22 }));
        particleSets.add(new ParticleSet(0, new int[] { 48, 49 }));
        particleSets.add(new ParticleSet(0, new int[] { 96, 97 }));
        particleSets.add(new ParticleSet(0, new int[] { 112, 113, 114 }));
        particleSets.add(new ParticleSet(0, new int[] { 128, 129, 130, 131, 132, 133, 134, 135 }));
        particleSets.add(new ParticleSet(0, new int[] { 144, 145, 146, 147, 148, 149, 150, 151 }));
        particleSets.add(new ParticleSet(0, new int[] { 160, 161, 162, 163, 164, 165, 166, 167 }));
        particleSets.add(new ParticleSet(0, new int[] { 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250 }));

        // Will play through their animation once, then die
        particleSets.add(new ParticleSet(1, new int[] { 7, 6, 5, 4, 3, 2, 1 }));
        particleSets.add(new ParticleSet(1, new int[] { 135, 134, 133, 132, 131, 130, 129, 128 }));
        particleSets.add(new ParticleSet(1, new int[] { 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250 }));

        // Static
        particleSets.add(new ParticleSet(2, new int[] { 32 }));
        particleSets.add(new ParticleSet(2, new int[] { 33 }));
        particleSets.add(new ParticleSet(2, new int[] { 64 }));
        particleSets.add(new ParticleSet(2, new int[] { 65 }));
        particleSets.add(new ParticleSet(2, new int[] { 66 }));
        particleSets.add(new ParticleSet(2, new int[] { 80 }));
        particleSets.add(new ParticleSet(2, new int[] { 81 }));
        particleSets.add(new ParticleSet(2, new int[] { 82 }));
        particleSets.add(new ParticleSet(2, new int[] { 83 }));

        // Will play through their animation until they die
        // particleSets.add(new ParticleSet(3, new int[] { 164, 165 }));

        // Rendering
        PortalRenderer.ID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(PortalRenderer.ID, new PortalRenderer());

        bookFont = FontLoader.createFont(new ResourceLocation("enhancedportals", "fonts/Minecraftia.ttf"), 16f, false);
    }

    @Override
    public void registerItems()
    {
        gogglesRenderIndex = RenderingRegistry.addNewArmourRendererPrefix("epGoggles");

        super.registerItems();
    }

    @Override
    public void setupCrafting()
    {
        super.setupCrafting();

        craftingRecipes.put("frame1", new ItemStack[] { new ItemStack(BlockFrame.instance), new ItemStack(Items.diamond) });
    }
}
