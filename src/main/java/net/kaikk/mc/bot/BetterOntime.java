/*
    BetterOntime plugin for Minecraft Bukkit server
    Copyright (C) 2015 Antonino Kai Pocorobba

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.kaikk.mc.bot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterOntime extends JavaPlugin {
	private static BetterOntime instance;
	Config config;
	DataStore ds;
	
	public void onEnable() {
		instance=this;
		
		this.config=new Config(instance);
		
		try {
			this.ds=new DataStore(instance);
			this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
			this.getCommand("betterontime").setExecutor(new CommandExec(this));
			this.getCommand("msgraw").setExecutor(new CommandExec(this));
			
			new UpdateTask(this).runTaskTimerAsynchronously(instance, 20L, this.config.saveDataInterval*20L);
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(instance);
		}
	}
	
	public void onDisable() {
		instance=null;
	}
	
	public static BetterOntime instance() {
		return instance;
	}
}
