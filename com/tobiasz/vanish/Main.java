package com.tobiasz.vanish;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
  extends JavaPlugin
  implements Listener
{
  public void onEnable()
  {
	  Bukkit.getConsoleSender().sendMessage("##########");
	  Bukkit.getConsoleSender().sendMessage("MCVanish By Tobiasz");
	  Bukkit.getConsoleSender().sendMessage("Pakiet Standart");
	  Bukkit.getConsoleSender().sendMessage("Wersja 1.0.0");
	  Bukkit.getConsoleSender().sendMessage("Baza Danych : Zalogowano");
	  Bukkit.getConsoleSender().sendMessage("##########");
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
  }
  
  private ArrayList<Player> vanished = new ArrayList();
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage("§c Blad Brak Permisji");
      return true;
    }
    Player p = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("vanish"))
    {
      if (!sender.hasPermission("tobiasz.vanish"))
      {
        sender.sendMessage("§cNie masz praw do tego! §c(tobiasz.vanish)");
        return true;
      }
      if (!this.vanished.contains(p))
      {
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
          pl.hidePlayer(p);
        }
        this.vanished.add(p);
        p.sendMessage("§a Jestes teraz ukryty");
        return true;
      }
      for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
        pl.showPlayer(p);
      }
      this.vanished.remove(p);
      p.sendMessage("§ caJestes teraz widoczny!");
      return true;
    }
    return true;
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e)
  {
    for (Player p : this.vanished) {
      e.getPlayer().hidePlayer(p);
    }
  }
  
  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent e)
  {
    this.vanished.remove(e.getPlayer());
  }
}
