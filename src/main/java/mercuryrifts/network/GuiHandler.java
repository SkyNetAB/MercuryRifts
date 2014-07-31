package mercuryrifts.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import mercuryrifts.mercuryrifts;
import mercuryrifts.client.gui.GuiDiallingDevice;
import mercuryrifts.client.gui.GuiDiallingDeviceEdit;
import mercuryrifts.client.gui.GuiDiallingDeviceManual;
import mercuryrifts.client.gui.GuiDiallingDeviceSave;
import mercuryrifts.client.gui.GuiDimensionalBridgeStabilizer;
import mercuryrifts.client.gui.GuiManual;
import mercuryrifts.client.gui.GuiModuleManipulator;
import mercuryrifts.client.gui.GuiNetworkInterface;
import mercuryrifts.client.gui.GuiNetworkInterfaceGlyphs;
import mercuryrifts.client.gui.GuiPortalController;
import mercuryrifts.client.gui.GuiPortalControllerGlyphs;
import mercuryrifts.client.gui.GuiProgrammableInterface;
import mercuryrifts.client.gui.GuiProgrammableInterfaceErrorLog;
import mercuryrifts.client.gui.GuiRedstoneInterface;
import mercuryrifts.client.gui.GuiTextureDialFrame;
import mercuryrifts.client.gui.GuiTextureDialParticle;
import mercuryrifts.client.gui.GuiTextureDialPortal;
import mercuryrifts.client.gui.GuiTextureFrame;
import mercuryrifts.client.gui.GuiTextureParticle;
import mercuryrifts.client.gui.GuiTexturePortal;
import mercuryrifts.client.gui.GuiTransferEnergy;
import mercuryrifts.client.gui.GuiTransferFluid;
import mercuryrifts.client.gui.GuiTransferItem;
import mercuryrifts.inventory.ContainerDiallingDevice;
import mercuryrifts.inventory.ContainerDiallingDeviceEdit;
import mercuryrifts.inventory.ContainerDiallingDeviceManual;
import mercuryrifts.inventory.ContainerDiallingDeviceSave;
import mercuryrifts.inventory.ContainerDimensionalBridgeStabilizer;
import mercuryrifts.inventory.ContainerManual;
import mercuryrifts.inventory.ContainerModuleManipulator;
import mercuryrifts.inventory.ContainerNetworkInterface;
import mercuryrifts.inventory.ContainerNetworkInterfaceGlyphs;
import mercuryrifts.inventory.ContainerPortalController;
import mercuryrifts.inventory.ContainerPortalControllerGlyphs;
import mercuryrifts.inventory.ContainerProgrammableInterface;
import mercuryrifts.inventory.ContainerProgrammableInterfaceErrorLog;
import mercuryrifts.inventory.ContainerRedstoneInterface;
import mercuryrifts.inventory.ContainerTextureDialFrame;
import mercuryrifts.inventory.ContainerTextureDialParticle;
import mercuryrifts.inventory.ContainerTextureDialPortal;
import mercuryrifts.inventory.ContainerTextureFrame;
import mercuryrifts.inventory.ContainerTextureParticle;
import mercuryrifts.inventory.ContainerTexturePortal;
import mercuryrifts.inventory.ContainerTransferEnergy;
import mercuryrifts.inventory.ContainerTransferFluid;
import mercuryrifts.inventory.ContainerTransferItem;
import mercuryrifts.tileentity.TileController;
import mercuryrifts.tileentity.TileDiallingDevice;
import mercuryrifts.tileentity.TileEP;
import mercuryrifts.tileentity.TileModuleManipulator;
import mercuryrifts.tileentity.TileProgrammableInterface;
import mercuryrifts.tileentity.TileRedstoneInterface;
import mercuryrifts.tileentity.TileStabilizerMain;
import mercuryrifts.tileentity.TileTransferEnergy;
import mercuryrifts.tileentity.TileTransferFluid;
import mercuryrifts.tileentity.TileTransferItem;

public class GuiHandler implements IGuiHandler
{
    public static final int PORTAL_CONTROLLER_A = 0;
    public static final int PORTAL_CONTROLLER_B = 1;
    public static final int NETWORK_INTERFACE_A = 2;
    public static final int NETWORK_INTERFACE_B = 3;
    public static final int DIALLING_DEVICE_A = 4;
    public static final int DIALLING_DEVICE_B = 5;
    public static final int DIALLING_DEVICE_C = 6;
    public static final int DIALLING_DEVICE_D = 7;
    public static final int TEXTURE_A = 8;
    public static final int TEXTURE_B = 9;
    public static final int TEXTURE_C = 10;
    public static final int TEXTURE_DIALLING_EDIT_A = 11;
    public static final int TEXTURE_DIALLING_EDIT_B = 12;
    public static final int TEXTURE_DIALLING_EDIT_C = 13;
    public static final int TEXTURE_DIALLING_SAVE_A = 14;
    public static final int TEXTURE_DIALLING_SAVE_B = 15;
    public static final int TEXTURE_DIALLING_SAVE_C = 16;
    public static final int REDSTONE_INTERFACE = 17;
    public static final int PROGRAMMABLE_INTERFACE = 18;
    public static final int PROGRAMMABLE_INTERFACE_ERRORS = 19;
    public static final int MODULE_MANIPULATOR = 20;
    public static final int TRANSFER_FLUID = 21;
    public static final int TRANSFER_ENERGY = 22;
    public static final int TRANSFER_ITEM = 23;
    public static final int DIMENSIONAL_BRIDGE_STABILIZER = 24;
    public static final int MANUAL = 25;

    public static void openGui(EntityPlayer player, TileEntity tile, int gui)
    {
        player.openGui(mercuryrifts.instance, gui, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == MANUAL)
        {
            return new GuiManual(player);
        }

        TileEntity t = world.getTileEntity(x, y, z);

        if (!(t instanceof TileEP))
        {
            return null;
        }

        TileEP tile = (TileEP) t;

        if (ID == PORTAL_CONTROLLER_A)
        {
            return new GuiPortalController((TileController) tile, player);
        }
        else if (ID == PORTAL_CONTROLLER_B)
        {
            return new GuiPortalControllerGlyphs((TileController) tile, player);
        }
        else if (ID == REDSTONE_INTERFACE)
        {
            return new GuiRedstoneInterface((TileRedstoneInterface) tile, player);
        }
        else if (ID == NETWORK_INTERFACE_A)
        {
            return new GuiNetworkInterface((TileController) tile, player);
        }
        else if (ID == NETWORK_INTERFACE_B)
        {
            return new GuiNetworkInterfaceGlyphs((TileController) tile, player);
        }
        else if (ID == MODULE_MANIPULATOR)
        {
            return new GuiModuleManipulator((TileModuleManipulator) tile, player);
        }
        else if (ID == DIMENSIONAL_BRIDGE_STABILIZER)
        {
            return new GuiDimensionalBridgeStabilizer((TileStabilizerMain) tile, player);
        }
        else if (ID == DIALLING_DEVICE_A)
        {
            return new GuiDiallingDevice((TileDiallingDevice) tile, player);
        }
        else if (ID == DIALLING_DEVICE_B)
        {
            return new GuiDiallingDeviceManual((TileDiallingDevice) tile, player);
        }
        else if (ID == DIALLING_DEVICE_C)
        {
            return new GuiDiallingDeviceSave((TileDiallingDevice) tile, player);
        }
        else if (ID == DIALLING_DEVICE_D)
        {
            return new GuiDiallingDeviceEdit((TileDiallingDevice) tile, player);
        }
        else if (ID == TEXTURE_A)
        {
            return new GuiTextureFrame((TileController) tile, player);
        }
        else if (ID == TEXTURE_B)
        {
            return new GuiTexturePortal((TileController) tile, player);
        }
        else if (ID == TEXTURE_C)
        {
            return new GuiTextureParticle((TileController) tile, player);
        }
        else if (ID == TEXTURE_DIALLING_SAVE_A)
        {
            return new GuiTextureDialFrame((TileDiallingDevice) tile, player, false);
        }
        else if (ID == TEXTURE_DIALLING_SAVE_B)
        {
            return new GuiTextureDialPortal((TileDiallingDevice) tile, player, false);
        }
        else if (ID == TEXTURE_DIALLING_SAVE_C)
        {
            return new GuiTextureDialParticle((TileDiallingDevice) tile, player, false);
        }
        else if (ID == TEXTURE_DIALLING_EDIT_A)
        {
            return new GuiTextureDialFrame((TileDiallingDevice) tile, player, true);
        }
        else if (ID == TEXTURE_DIALLING_EDIT_B)
        {
            return new GuiTextureDialPortal((TileDiallingDevice) tile, player, true);
        }
        else if (ID == TEXTURE_DIALLING_EDIT_C)
        {
            return new GuiTextureDialParticle((TileDiallingDevice) tile, player, true);
        }
        else if (ID == PROGRAMMABLE_INTERFACE)
        {
            return new GuiProgrammableInterface((TileProgrammableInterface) tile, player);
        }
        else if (ID == PROGRAMMABLE_INTERFACE_ERRORS)
        {
            return new GuiProgrammableInterfaceErrorLog((TileProgrammableInterface) tile, player);
        }
        else if (ID == TRANSFER_FLUID)
        {
            return new GuiTransferFluid((TileTransferFluid) tile, player);
        }
        else if (ID == TRANSFER_ENERGY)
        {
            return new GuiTransferEnergy((TileTransferEnergy) tile, player);
        }
        else if (ID == TRANSFER_ITEM)
        {
            return new GuiTransferItem((TileTransferItem) tile, player);
        }

        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == MANUAL)
        {
            return new ContainerManual(player.inventory);
        }

        TileEntity t = world.getTileEntity(x, y, z);

        if (!(t instanceof TileEP))
        {
            return null;
        }

        TileEP tile = (TileEP) t;

        if (ID == PORTAL_CONTROLLER_A)
        {
            return new ContainerPortalController((TileController) tile, player.inventory);
        }
        else if (ID == PORTAL_CONTROLLER_B)
        {
            return new ContainerPortalControllerGlyphs((TileController) tile, player.inventory);
        }
        else if (ID == REDSTONE_INTERFACE)
        {
            return new ContainerRedstoneInterface((TileRedstoneInterface) tile, player.inventory);
        }
        else if (ID == NETWORK_INTERFACE_A)
        {
            return new ContainerNetworkInterface((TileController) tile, player.inventory);
        }
        else if (ID == NETWORK_INTERFACE_B)
        {
            return new ContainerNetworkInterfaceGlyphs((TileController) tile, player.inventory);
        }
        else if (ID == MODULE_MANIPULATOR)
        {
            return new ContainerModuleManipulator((TileModuleManipulator) tile, player.inventory);
        }
        else if (ID == DIMENSIONAL_BRIDGE_STABILIZER)
        {
            return new ContainerDimensionalBridgeStabilizer((TileStabilizerMain) tile, player.inventory);
        }
        else if (ID == DIALLING_DEVICE_A)
        {
            return new ContainerDiallingDevice((TileDiallingDevice) tile, player.inventory);
        }
        else if (ID == DIALLING_DEVICE_B)
        {
            return new ContainerDiallingDeviceManual((TileDiallingDevice) tile, player.inventory);
        }
        else if (ID == DIALLING_DEVICE_C)
        {
            return new ContainerDiallingDeviceSave((TileDiallingDevice) tile, player.inventory);
        }
        else if (ID == DIALLING_DEVICE_D)
        {
            return new ContainerDiallingDeviceEdit((TileDiallingDevice) tile, player.inventory);
        }
        else if (ID == TEXTURE_A)
        {
            return new ContainerTextureFrame((TileController) tile, player.inventory);
        }
        else if (ID == TEXTURE_B)
        {
            return new ContainerTexturePortal((TileController) tile, player.inventory);
        }
        else if (ID == TEXTURE_C)
        {
            return new ContainerTextureParticle((TileController) tile, player.inventory);
        }
        else if (ID == TEXTURE_DIALLING_EDIT_A || ID == TEXTURE_DIALLING_SAVE_A)
        {
            return new ContainerTextureDialFrame((TileDiallingDevice) tile, player.inventory);
        }
        else if (ID == TEXTURE_DIALLING_EDIT_B || ID == TEXTURE_DIALLING_SAVE_B)
        {
            return new ContainerTextureDialPortal((TileDiallingDevice) tile, player.inventory);
        }
        else if (ID == TEXTURE_DIALLING_EDIT_C || ID == TEXTURE_DIALLING_SAVE_C)
        {
            return new ContainerTextureDialParticle((TileDiallingDevice) tile, player.inventory);
        }
        else if (ID == PROGRAMMABLE_INTERFACE)
        {
            return new ContainerProgrammableInterface((TileProgrammableInterface) tile, player.inventory);
        }
        else if (ID == PROGRAMMABLE_INTERFACE_ERRORS)
        {
            return new ContainerProgrammableInterfaceErrorLog((TileProgrammableInterface) tile, player.inventory);
        }
        else if (ID == TRANSFER_FLUID)
        {
            return new ContainerTransferFluid((TileTransferFluid) tile, player.inventory);
        }
        else if (ID == TRANSFER_ENERGY)
        {
            return new ContainerTransferEnergy((TileTransferEnergy) tile, player.inventory);
        }
        else if (ID == TRANSFER_ITEM)
        {
            return new ContainerTransferItem((TileTransferItem) tile, player.inventory);
        }

        return null;
    }
}
