package enhancedportals.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import enhancedportals.creativetab.CreativeTabEP;

public class Reference
{
    public static final String MOD_ID = "EP2";
    public static final String MOD_NAME = "EnhancedPortals 2";
    public static final String MOD_VERSION = "@VERSION@";

    public static final String MOD_ID_CC = MOD_ID + "_cc";
    public static final String MOD_NAME_CC = "EP2 ComputerCraft";
    public static final String MOD_DEP_CC = "";

    public static final String MOD_ID_WORLD = MOD_ID + "_world";
    public static final String MOD_NAME_WORLD = "EP2 World";
    public static final String MOD_DEP_WORLD = "";

    public static final String PROXY_CLIENT = "enhancedportals.network.ClientProxy";
    public static final String PROXY_COMMON = "enhancedportals.network.CommonProxy";

    public static CreativeTabs CREATIVE_TAB = new CreativeTabEP();

    public static final String LOCALE_LOCATION = "/mods/" + MOD_ID + "/lang/";
    public static final String GUI_LOCATION = "/mods/" + MOD_ID + "/textures/gui/";

    public static final List<ItemStack> glyphItems = new ArrayList<ItemStack>();
    public static final List<String> glyphValues = new ArrayList<String>();
    public static final String glyphSeperator = " ";
    
    public static Logger log = Logger.getLogger("EnhancedPortals 2");
}
