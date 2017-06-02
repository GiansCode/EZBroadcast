package com.gianlucamc.ezbroadcast;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import com.gianlucamc.ezbroadcast.commands.EZBroadcastCommand;
import com.gianlucamc.ezbroadcast.config.MessageManager;
import com.gianlucamc.ezbroadcast.config.SettingsManager;
import com.gianlucamc.ezbroadcast.config.SoundManager;
import com.gianlucamc.ezbroadcast.objects.Broadcast;
import com.gianlucamc.ezbroadcast.tasks.BroadcastTask;
import com.google.common.collect.Lists;

public class EZBroadcast extends JavaPlugin {

	private MessageManager messageManager;
	private SettingsManager settingsManager;
	private SoundManager soundManager;

	private static List<Broadcast> broadcasts;

	@Override
	public void onEnable() {
		if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
			Bukkit.getServer().getLogger().severe("[EZBroadcast] PlaceholderAPI not found, disabling the plugin...");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}

		broadcasts = Lists.newArrayList();

		saveDefaultConfig();
		loadConfig();

		getCommand("ezbroadcast").setExecutor(new EZBroadcastCommand(this));

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BroadcastTask(this), 20 * settingsManager.getMessageInterval(), 20 * settingsManager.getMessageInterval());
	}

	@Override
	public void onDisable() {
		broadcasts = null;

		saveDefaultConfig();
	}

	public void loadConfig() {
		getBroadcasts().clear();

		messageManager = new MessageManager(
				ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.noPermission")),
				ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.noBroadcastFound")),
				ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.incorrectArguments")),
				ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.reloadSuccess")),
				getConfig().getStringList("messages.help")
				);

		settingsManager = new SettingsManager(
				getConfig().getBoolean("settings.randomAnnouncements"),
				getConfig().getInt("settings.messageInterval")
				);

		soundManager = new SoundManager(
				getConfig().getBoolean("sound.enabled"),
				Sound.valueOf(getConfig().getString("sound.sound")),
				(float) getConfig().getInt("sound.volume"),
				(float) getConfig().getInt("sound.pitch")
				);

		ConfigurationSection section = getConfig().getConfigurationSection("broadcasts");

		if (section != null) {
			Set<String> broadcasts = section.getKeys(false);

			if (broadcasts != null && !broadcasts.isEmpty()) {
				for (String id : broadcasts) {
					List<String> messages = getConfig().getStringList("broadcasts." + id + ".messages");

					getBroadcasts().add(new Broadcast(id, messages));
				}
			}

			Bukkit.getServer().getLogger().info("[EZBroadcast] Created " + getBroadcasts().size() + " Broadcasts!");
		}
	}

	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public static List<Broadcast> getBroadcasts() {
		return broadcasts;
	}
}