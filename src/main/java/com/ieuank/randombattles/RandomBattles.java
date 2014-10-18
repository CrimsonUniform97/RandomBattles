package com.ieuank.randombattles;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod(modid = RandomBattles.MODID, version = RandomBattles.VERSION, dependencies="required-after:pixelmon", acceptableRemoteVersions="*")
public class RandomBattles
{
    public static final String MODID = "RandomBattles";
    public static final String VERSION = "1.0";
    public ConcurrentHashMap<EntityPlayerMP, Integer> playerQueue;
    public Logger logger;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {        
    	this.logger = LogManager.getLogger();
    	this.playerQueue = new ConcurrentHashMap<EntityPlayerMP, Integer>();
        RandomBattleTickHandler tickHandler = new RandomBattleTickHandler(this);   
        FMLCommonHandler.instance().bus().register(tickHandler);
         MinecraftForge.EVENT_BUS.register(tickHandler);
    }
    
    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event)
    {
      if ((MinecraftServer.getServer().getCommandManager() instanceof ServerCommandManager)) {}
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new BattleCommand(this));
    }
}
