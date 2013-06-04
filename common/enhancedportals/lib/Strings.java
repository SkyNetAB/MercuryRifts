package enhancedportals.lib;

import net.minecraft.util.EnumChatFormatting;

public enum Strings
{
    Accept("gui.accept"),
    Cancel("gui.cancel"),
    ChatAdvDimAlreadyInstalled("chat.advDimUpgradeAlreadyInstalled", EnumChatFormatting.RED),
    ChatDimAlreadyInstalled("chat.dimUpgradeAlreadyInstalled", EnumChatFormatting.RED),
    ChatMaxUpgradesInstalled("chat.maxUpgradesInstalled", EnumChatFormatting.RED),
    ChatUpgradeInstalled("chat.upgradeAlreadyInstalled", EnumChatFormatting.RED),
    Clear("gui.clear"),
    ClickToSetIdentifier("gui.setIdentifier"),
    ClickToSetNetwork("gui.setNetwork"),
    Dial("gui.dial"),
    Facade("gui.facade"),
    FullBlock("gui.fullBlock"),
    Glyphs("gui.glyphs"),
    IdentifierSelection("gui.selectIdentifier"),
    Inverted("gui.inverted"),
    Modifications("gui.modifications"),
    ModifierActive("gui.activeModifier"),
    Network("gui.network"),
    NetworkSelection("gui.networkSelection"),
    Normal("gui.normal"),
    PortalModifierUpgrade("upgrade.portalModifier", EnumChatFormatting.GOLD),
    Precise("gui.precise"),
    Random("gui.random"),
    RedstoneControl("gui.redstoneControl"),
    RemoveUpgrade("gui.upgrade.remove", EnumChatFormatting.DARK_GRAY),
    RightClickToReset("gui.rightClickToReset"),
    Thick("gui.thick"),
    Thicker("gui.thicker"),
    Thickness("gui.thickness"),
    UniqueIdentifier("gui.uniqueIdentifier"),
    Upgrades("gui.upgrades");

    private EnumChatFormatting chatFormatting;
    private String             text;

    private Strings(String str)
    {
        text = str;
        chatFormatting = null;
    }

    private Strings(String str, EnumChatFormatting formatting)
    {
        text = str;
        chatFormatting = formatting;
    }

    @Override
    public String toString()
    {
        return (chatFormatting != null ? chatFormatting.toString() : "") + Localization.localizeString(text);
    }
}

/*public class Strings
{
    public static final String UPGRADES = "gui.upgrades"; // Upgrades
    public static final String MODIFICATIONS = "gui.modifications"; // Modifications
    
    public static final String NETWORK = "gui.network"; // Network
    public static final String NETWORK_SELECTION = "gui.network.selection"; // Network Selection
    
    public static final String GLYPHS = "gui.glyphs"; // Glyphs
    public static final String DIAL = "gui.dial"; // Dial
    
    public static final String REDSTONE_CONTROL = "gui.redstoneControl"; // Redstone Control
    public static final String REDSTONE_CONTROL_NORMAL = REDSTONE_CONTROL + ".normal"; // Normal
    public static final String REDSTONE_CONTROL_INVERTED = REDSTONE_CONTROL + ".inverted"; // Inverted
    public static final String REDSTONE_CONTROL_PRECISE = REDSTONE_CONTROL + ".precise"; // Precise
    
    public static final String THICKNESS = "gui.thickness"; // Thickness
    public static final String THICKNESS_NORMAL = THICKNESS + ".normal"; // Normal
    public static final String THICKNESS_THICK = THICKNESS + ".thick"; // Thick
    public static final String THICKNESS_THICKER = THICKNESS + ".thicker"; // Thicker
    public static final String THICKNESS_FULL_BLOCK = THICKNESS + ".fullBlock"; // Full Block
    
    public static final String FACADE = "gui.facade"; // Facade
    
    public static final String CANCEL = "gui.cancel"; // Cancel
    public static final String RANDOM = "gui.random"; // Random
    public static final String CLEAR = "gui.clear"; // Clear
    public static final String ACCEPT = "gui.accept"; // Accept
    public static final String ADD = "gui.add"; // Add
    public static final String REMOVE = "gui.remove"; // Remove
    public static final String SAVE = "gui.save"; // Save
    
    public static final String CHAT_UPGRADE_INSTALLED = "chat.upgradeAlreadyInstalled"; // This upgrade is already installed.
    public static final String CHAT_ADVDIM_ALREADY_INSTALLED = "chat.upgradeAdvDimInstalled"; // You must remove the Advanced Dimensional upgrade first.
    public static final String CHAT_DIM_ALREADY_INSTALLED = "chat.upgradeDimInstalled"; // You must remove the Dimensional upgrade first.
}*/
