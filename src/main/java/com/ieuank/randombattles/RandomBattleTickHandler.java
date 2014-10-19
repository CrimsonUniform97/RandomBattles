package com.ieuank.randombattles;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.player.EntityPlayerMP;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class RandomBattleTickHandler
{
  public int ticks = 0;
  public int tickspersecond = 60;
  public int seconds = 0;
  public ConcurrentHashMap<Battle, Boolean> battles = new ConcurrentHashMap<Battle, Boolean>();
  private RandomBattles mod;
	
  RandomBattleTickHandler(RandomBattles rb)
  {
	this.mod = rb;
  }
  
  @SubscribeEvent
  public void playerTickStart(TickEvent.PlayerTickEvent event)
  {
	/* Try to create new battles */
	  if(this.mod.playerQueue.size() > 1) {
			EntityPlayerMP p1 = null;
			EntityPlayerMP p2 = null;
			for(Entry<EntityPlayerMP, Integer> qplayer : this.mod.playerQueue.entrySet()) {
				EntityPlayerMP p = qplayer.getKey();
				Integer joinTime = qplayer.getValue();
				Integer currentTime = (int) System.currentTimeMillis() / 1000;
				if(currentTime - joinTime > 300) {
					// In queue longer than 5m
					this.removeQueue(p);
					RandomBattles.logger.log(Level.INFO, "Removed player " + p.getDisplayName() + " from queue after " + (currentTime - joinTime) + " seconds");
				} else {
					if(p1 == null) {
						p1 = p;
						RandomBattles.logger.log(Level.INFO, "Trying to match player " + p.getDisplayName() + " to someone in the queue");
					} else if (p2 == null) {
						p2 = p;
						RandomBattles.logger.log(Level.INFO, "Matched player " + p1.getDisplayName() + " to " + p.getDisplayName() + " (" + this.battles.size() + 1 + ")");
						Battle b = new Battle(p1, p2);
						this.battles.put(b, true);
						RandomBattles.logger.log(Level.INFO, "Started battle between " + p1.getDisplayName() + " and " + p2.getDisplayName());
						this.removeQueue(p1);
						this.removeQueue(p2);
						p1 = null;
						p2 = null;
					}
				}
			}
	  }
	  
	  
    if(this.battles.size() > 0) {
    	for(Entry<Battle, Boolean> entry : this.battles.entrySet()) {
    		Battle b = entry.getKey();
    		if(entry.getValue()) {
    			// Battle active
    			if(!b.doneBattle) {
    				b.doBattle();
					RandomBattles.logger.log(Level.INFO, "Battling " + b.p1.getDisplayName() + " and " + b.p2.getDisplayName());
		    		Utilities.broadcastServerMessage(b.p1.getDisplayName() + " and " + b.p2.getDisplayName() + " are now battling.");
    			} else {
    				this.battles.put(b, false);
					RandomBattles.logger.log(Level.INFO, "Battle between " + b.p1.getDisplayName() + " and " + b.p2.getDisplayName() + " finished");
					Utilities.getWinner(b.p1, b.ps1, b.p2, b.ps2);
    			}
    		} else {
				RandomBattles.logger.log(Level.INFO, "Battle between " + b.p1.getDisplayName() + " and " + b.p2.getDisplayName() + " removed");
				this.battles.remove(b);
    		}
    	}
    }
  }
  
  public void removeQueue(EntityPlayerMP player) {
	  this.mod.playerQueue.remove(player);
  }
}




/* Location:           D:\Minecraft Modding\RandomBattle.jar

 * Qualified Name:     com.randombattle.RandomBattleTickHandler

 * JD-Core Version:    0.7.1

 */