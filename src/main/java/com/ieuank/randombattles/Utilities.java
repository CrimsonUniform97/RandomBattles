package com.ieuank.randombattles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

import org.apache.logging.log4j.Level;

import com.pixelmonmod.pixelmon.battles.controller.BattleController2Participant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.config.PixelmonEntityList;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumPokeballs;
import com.pixelmonmod.pixelmon.enums.EnumPokemon;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerNotLoadedException;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;

import cpw.mods.fml.common.Mod.Instance;

public class Utilities
{
  static String poke;
  private static Integer fullParty = 6;
  @Instance public static RandomBattles mod;
  
  public static void broadcastServerMessage(String string)
  {
    String[] players = MinecraftServer.getServer().getAllUsernames();
    for (int i = 0; i < players.length; i++) {
      MinecraftServer.getServer().getConfigurationManager().func_152612_a(players[i]).addChatComponentMessage((new ChatComponentTranslation(string, new Object[0])));
    }
  }
  
  public static void doWin(EntityPlayer p) {
	  
  }
  
  public static void getWinner(EntityPlayer p1, PlayerStorage ps1, EntityPlayer p2, PlayerStorage ps2)
  {
    int fainted1 = 0;
    int fainted2 = 0;
    try
    {
      ps1 = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p1);
    }
    catch (PlayerNotLoadedException e1)
    {
      e1.printStackTrace();
    }
    try
    {
      ps2 = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p2);
    }
    catch (PlayerNotLoadedException e)
    {
      e.printStackTrace();
    }
    for (int i = 0; i < (fullParty - 1); i++) {
      if (ps2.partyPokemon[i] != null) {
        if (ps2.partyPokemon[i].getBoolean("IsFainted") == true) {
          fainted2++;
        }
      }
    }
    for (int i = 0; i < (fullParty - 1); i++) {
      if (ps1.partyPokemon[i] != null) {
        if (ps1.partyPokemon[i].getBoolean("IsFainted") == true) {
          fainted1++;
        }
      }
    }
    if ((fainted1 == ps1.partyPokemon.length) && (fainted2 == ps2.partyPokemon.length))
    {
      doTie();
    }
    else if (fainted1 == ps1.partyPokemon.length)
    {
      broadcastServerMessage(p1.getDisplayName() + " has beaten " + p2.getDisplayName());
      doWin(p1);
    }
    else if (fainted2 == ps2.partyPokemon.length)
    {
        broadcastServerMessage(p2.getDisplayName() + " has beaten " + p1.getDisplayName());
      doWin(p2);
    }
    else
    {
    	RandomBattles.logger.log(Level.INFO, "Player " + p2.getDisplayName() + " has " + ps2.partyPokemon.length + " Pokémon of which " + fainted2 + " fainted");
    	RandomBattles.logger.log(Level.INFO, "Player " + p1.getDisplayName() + " has " + ps1.partyPokemon.length + " Pokémon of which " + fainted1 + " fainted");
      System.out.println("ERROR CALCULATING WINNER! THEY BOTH HAVE ACTIVE POKEMON AFTER BATTLE DOING TIE!");
      doTie();
    }
  }
  
  public static void doTie() {}
  
  /*public static void resetGame(Battle b)
  {
    b.gameStage = "pregame";
    b.doneBattle = false;
  }*/
  
  /*public void cleanParty(EntityPlayerMP player) {
	  try {
		PlayerStorage storage = PixelmonStorage.PokeballManager.getPlayerStorage(player);
		for(NBTTagCompound poke : storage.partyPokemon) {
			poke.hea
		}
	} catch (PlayerNotLoadedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }*/
  
  public static boolean giveRandomPokemon(EntityPlayer p1, EntityPlayer p2)
  {
      for (int i = 0; i < fullParty; i++) {
        try
        {
          PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p1).changePokemonAndAssignID(i, null);
        }
        catch (PlayerNotLoadedException e)
        {
          e.printStackTrace();
        }
      }
      for (int i = 0; i < fullParty; i++) {
        try
        {
          poke = EnumPokemon.randomPoke().toString();
          EntityPixelmon p = (EntityPixelmon)PixelmonEntityList.createEntityByName(poke, p1.getEntityWorld());
          p.getLvl().setLevel(50);
          if (p.getMoveset().size() == 0) {
            p.loadMoveset();
          }
          p.caughtBall = EnumPokeballs.PokeBall;
          
          PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p1).addToParty(p);
        }
        catch (Exception e)
        {
          System.out.println("ERROR CALCULATING POKEMON FOR " + p1.getDisplayName() + "!");
          return false;
        }
      }
      for (int i = 0; i < fullParty; i++) {
        try
        {
          PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p2).changePokemonAndAssignID(i, null);
        }
        catch (PlayerNotLoadedException e)
        {
          e.printStackTrace();
        }
      }
      for (int i = 0; i < fullParty; i++) {
        try
        {
          poke = EnumPokemon.randomPoke().toString();
          EntityPixelmon p = (EntityPixelmon)PixelmonEntityList.createEntityByName(poke, p2.getEntityWorld());
          p.getLvl().setLevel(50);
          p.caughtBall = EnumPokeballs.PokeBall;
          if (p.getMoveset().size() == 0) {
            p.loadMoveset();
          }
          PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p2).addToParty(p);
        }
        catch (Exception e)
        {
          System.out.println("ERROR CALCULATING POKEMON FOR " + p2.getDisplayName() + "!");
          return false;
        }
      }
      return true;
  }
  

  public static boolean giveRandomPokemon(EntityPlayer pl)
  {
      for (int i = 0; i < fullParty; i++) {
        try
        {
          PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)pl).changePokemonAndAssignID(i, null);
        }
        catch (PlayerNotLoadedException e)
        {
          e.printStackTrace();
        }
      }
      for (int i = 0; i < fullParty; i++) {
        try
        {
          poke = EnumPokemon.randomPoke().toString();
          EntityPixelmon p = (EntityPixelmon)PixelmonEntityList.createEntityByName(poke, pl.getEntityWorld());
          p.getLvl().setLevel(50);
          if (p.getMoveset().size() == 0) {
            p.loadMoveset();
          }
          p.caughtBall = EnumPokeballs.PokeBall;
          
          PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)pl).addToParty(p);
        }
        catch (Exception e)
        {
          System.out.println("ERROR CALCULATING POKEMON FOR " + pl.getDisplayName() + "!");
          return false;
        }
      }
      return true;
  }
  
  /*public static boolean getPlayers()
  {
    if (MinecraftServer.getServer().getConfigurationManager().playerEntityList.size() == 2)
    {
      RandomBattleTickHandler.p1 = MinecraftServer.getServer().getConfigurationManager().func_152612_a(MinecraftServer.getServer().getConfigurationManager().getAllUsernames()[0]);
      RandomBattleTickHandler.p2 = MinecraftServer.getServer().getConfigurationManager().func_152612_a(MinecraftServer.getServer().getConfigurationManager().getAllUsernames()[1]);
      try
      {
        RandomBattleTickHandler.ps1 = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)RandomBattleTickHandler.p1);
        broadcastServerMessage(RandomBattleTickHandler.p1.getDisplayName() + "'s party is loaded in ps1!");
        RandomBattleTickHandler.ps2 = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)RandomBattleTickHandler.p2);
        broadcastServerMessage(RandomBattleTickHandler.p2.getDisplayName() + "'s party is loaded in ps2!");
      }
      catch (PlayerNotLoadedException e)
      {
        System.out.println("ERROR GETTING ONE OF THE PLAYERS PARTYS! THIS IS FATAL!");
        broadcastServerMessage("ERROR: Couldn't load one of the players party!");
      }
      if (RandomBattleTickHandler.ps1 == null)
      {
        System.out.println("ERROR WITH " + RandomBattleTickHandler.p1.getDisplayName() + "'s PARTY! STARTING PREGAME PHASE OVER!");
        resetGame();
        return false;
      }
      if (RandomBattleTickHandler.ps2 == null)
      {
        System.out.println("ERROR WITH " + RandomBattleTickHandler.p2.getDisplayName() + "'s PARTY! STARTING PREGAME PHASE OVER!");
        resetGame();
        return false;
      }
    }
    return true;
  }*/
  
  public static void doBattle(EntityPlayer p1, EntityPlayer p2)
  {
    try
    {
      EntityPixelmon player1firstPokemon = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p1).getFirstAblePokemon(p1.worldObj);
      PlayerParticipant player1 = new PlayerParticipant((EntityPlayerMP)p1, new EntityPixelmon[] { player1firstPokemon });
      
      EntityPixelmon player2firstPokemon = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p2).getFirstAblePokemon(((EntityPlayerMP)p2).worldObj);
      PlayerParticipant player2 = new PlayerParticipant((EntityPlayerMP)p2, new EntityPixelmon[] { player2firstPokemon });
      

      BattleController2Participant bc1 = new BattleController2Participant(player1, player2);
      return;
    }
    catch (Exception e)
    {
      broadcastServerMessage("There was an error starting battle! Starting game over!");
    }
  }
}




/* Location:           D:\Minecraft Modding\RandomBattle.jar

 * Qualified Name:     com.randombattle.Utilities

 * JD-Core Version:    0.7.1

 */