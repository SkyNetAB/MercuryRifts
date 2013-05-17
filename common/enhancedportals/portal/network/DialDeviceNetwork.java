package enhancedportals.portal.network;

import net.minecraft.server.MinecraftServer;
import enhancedportals.lib.Reference;

public class DialDeviceNetwork extends NetworkManager
{
    public DialDeviceNetwork(MinecraftServer server)
    {
        super(server);
    }

    @Override
    public String getSaveFileName()
    {
        return Reference.MOD_ID + "_DialDevices.dat";
    }
}
