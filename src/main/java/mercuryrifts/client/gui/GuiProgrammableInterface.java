package mercuryrifts.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import mercuryrifts.mercuryrifts;
import mercuryrifts.inventory.ContainerProgrammableInterface;
import mercuryrifts.network.GuiHandler;
import mercuryrifts.network.packet.PacketRequestGui;
import mercuryrifts.tileentity.TileProgrammableInterface;

public class GuiProgrammableInterface extends BaseGui
{
    public static int CONTAINER_SIZE = 200, CONTAINER_WIDTH = 256;
    TileProgrammableInterface program;

    public GuiProgrammableInterface(TileProgrammableInterface pr, EntityPlayer p)
    {
        super(new ContainerProgrammableInterface(pr, p.inventory), CONTAINER_SIZE);
        xSize = CONTAINER_WIDTH;
        setHidePlayerInventory();
        name = "gui.programmableInterface";
        texture = new ResourceLocation("mercuryrifts", "textures/gui/program_interface.png");
        program = pr;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(new GuiButton(0, guiLeft + 78, guiTop + CONTAINER_SIZE - 27, xSize - 84, 20, mercuryrifts.localize("gui.compile")));
        buttonList.add(new GuiButton(1, guiLeft + 7, guiTop + CONTAINER_SIZE - 27, 70, 20, mercuryrifts.localize("gui.errorLog")));
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1)
        {
            mercuryrifts.packetPipeline.sendToServer(new PacketRequestGui(program, GuiHandler.PROGRAMMABLE_INTERFACE_ERRORS));
        }
        else if (button.id == 0)
        {

        }
    }
}
