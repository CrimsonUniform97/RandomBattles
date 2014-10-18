package com.ieuank.randombattles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;

public class Battle {
	  public String gameStage = "pregame";
	  public EntityPlayer p1 = null;
	  public EntityPlayer p2 = null;
	  public PlayerStorage ps1 = null;
	  public PlayerStorage ps2 = null;
	  public boolean doneBattle = false;
	  public String poke;
	  
	  public Battle(EntityPlayer pl1, EntityPlayer pl2) {
		  this.p1 = pl1;
		  this.p2 = pl2;
	  }
	  
	  public void run() {

	        if (!doneBattle)
	        {
	            if (Utilities.giveRandomPokemon(p1, p2))
	            {
	            	this.doBattle();
	            }
	        }
	        else if ((BattleRegistry.getBattle(p1) == null) || (BattleRegistry.getBattle(p2) == null))
	        {
	          ((EntityPlayerMP)p1).addChatComponentMessage((new ChatComponentTranslation("The Game is over. Thanks for playing!", new Object[0])));
	          ((EntityPlayerMP)p2).addChatComponentMessage((new ChatComponentTranslation("The Game is over. Thanks for playing!", new Object[0])));
	          this.doneBattle = true;
	        }
	  }
	  
	 public void doBattle() {
         Utilities.doBattle(p1, p2);
         doneBattle = true;
	 }

}
