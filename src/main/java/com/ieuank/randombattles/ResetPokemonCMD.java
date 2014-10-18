package com.ieuank.randombattles;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.pixelmonmod.pixelmon.comm.CommandChatHandler;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class ResetPokemonCMD implements ICommand {
	private List<String> aliases;
	private RandomBattles mod;
	  public ResetPokemonCMD(RandomBattles rb)
	  {
		this.mod = rb;
	    this.aliases = new ArrayList<String>();
	    this.aliases.add("resetpokemon");
	  }

	  public String getCommandName()
	  {
	    return "resetpokemon";
	  }

	  @Override
	  public String getCommandUsage(ICommandSender icommandsender)
	  {
	    return "resetpokemon";
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
	    	Utilities.giveRandomPokemon(p);
	    	p.addChatComponentMessage((new ChatComponentTranslation("You got random pokémon", new Object[0])));
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
