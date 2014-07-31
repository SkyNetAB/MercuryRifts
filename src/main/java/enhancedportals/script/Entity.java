package enhancedportals.script;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import org.luaj.vm3.LuaBoolean;
import org.luaj.vm3.LuaInteger;
import org.luaj.vm3.LuaString;
import org.luaj.vm3.LuaValue;

public class Entity
{
    net.minecraft.entity.Entity entity;

    public Entity(net.minecraft.entity.Entity e)
    {
        entity = e;
    }

    public void attack(int amount)
    {
        if (entity instanceof EntityLivingBase)
        {
            ((EntityLivingBase) entity).attackEntityFrom(Common.damageSource, amount);
        }
    }

    public LuaInteger getHealth()
    {
        if (entity instanceof EntityLivingBase)
        {
            return LuaValue.valueOf((int) ((EntityLivingBase) entity).getHealth());
        }

        return null;
    }

    public LuaInteger getMaxHealth()
    {
        if (entity instanceof EntityLivingBase)
        {
            return LuaValue.valueOf((int) ((EntityLivingBase) entity).getMaxHealth());
        }

        return null;
    }

    public LuaString getName()
    {
        return LuaValue.valueOf(entity.getCommandSenderName());
    }

    public LuaString getType()
    {
        return LuaValue.valueOf(entity.getClass().getSimpleName());
    }

    public LuaBoolean isAnimal()
    {
        return LuaValue.valueOf(entity instanceof EntityLiving);
    }

    public LuaBoolean isBeingRidden()
    {
        return LuaValue.valueOf(entity.riddenByEntity != null);
    }

    public LuaBoolean isMonster()
    {
        return LuaValue.valueOf(entity instanceof EntityMob);
    }

    public LuaBoolean isPlayer()
    {
        return LuaValue.valueOf(entity instanceof EntityPlayer);
    }

    public LuaBoolean isRiding()
    {
        return LuaValue.valueOf(entity.isRiding());
    }

    public void sendMessage(String message)
    {
        if (entity instanceof EntityPlayer)
        {
            ((EntityPlayer) entity).addChatComponentMessage(new ChatComponentText(message));
        }
    }
}
