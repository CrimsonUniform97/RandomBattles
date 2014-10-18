package com.ieuank.randombattles;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.comm.CommandChatHandler;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class BattleCommand implements ICommand {
	private List<String> aliases;
	private RandomBattles mod;
	  public BattleCommand(RandomBattles rb)
	  {
		this.mod = rb;
	    this.aliases = new ArrayList<String>();
	    this.aliases.add("join");
	    this.aliases.add("battle");
	  }

	  public String getCommandName()
	  {
	    return "join";
	  }

	  @Override
	  public String getCommandUsage(ICommandSender icommandsender)
	  {
	    return "join";
	  }

	  @Override
	  public List<String> getCommandAliases()
	  {
	    return this.aliases;
	  }

	  @Override
	  public void processCommand(ICommandSender icommandsender, String[] astring)
	  {
	    if(astring.length == 0)
	    {
	    	EntityPlayerMP p = MinecraftServer.getServer().getConfigurationManager().func_152612_a(icommandsender.getCommandSenderName());
	    	if(BattleRegistry.getBattle(p) == null) {
		    	this.mod.playerQueue.put(p, (int) (System.currentTimeMillis() / 1000));
		    	if(this.mod.playerQueue.size() > 1) {
		    		CommandChatHandler.sendChat(icommandsender, "You have entered the battle queue. There are now " + this.mod.playerQueue.size() + " people in queue.", new Object[0]);
		    		this.mod.logger.log(Level.INFO, p.getDisplayName() + " joined the battle queue (" + this.mod.playerQueue.size() + ")");
		    		Utilities.broadcastServerMessage(p.getDisplayName() + " is ready for battle! [" + this.mod.playerQueue.size() + "/inf.]");
		    	} else {
		    		CommandChatHandler.sendChat(icommandsender, "You have entered the battle queue. There is now " + this.mod.playerQueue.size() + " person in queue.", new Object[0]);
		    		this.mod.logger.log(Level.INFO, p.getDisplayName() + " joined the battle queue (" + this.mod.playerQueue.size() + ")");
		    		Utilities.broadcastServerMessage(p.getDisplayName() + " is ready for battle! [" + this.mod.playerQueue.size() + "/inf.]");
			    }
	    	} else {
	    		CommandChatHandler.sendChat(icommandsender, "You can not enter the queue while in a battle.", new Object[0]);
	    		this.mod.logger.log(Level.INFO, p.getDisplayName() + " tried joining battle queue while in a battle.");
	    		
	    	}
	    } else {
	      CommandChatHandler.sendChat(icommandsender, "Invalid arguments", new Object[0]);
	      CommandChatHandler.sendChat(icommandsender, this.getCommandUsage(icommandsender), new Object[0]);
	    }
	    
	  }

	  @Override
	  public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	  {
	    return true;
	  }

	  @Override
	  public List<?> addTabCompletionOptions(ICommandSender icommandsender,
	      String[] astring)
	  {
	    return null;
	  }

	  @Override
	  public boolean isUsernameIndex(String[] astring, int i)
	  {
	    return false;
	  }

	  @Override
	  public int compareTo(Object o)
	  {
	    return 0;
	  }
}
