package com.gianlucamc.ezbroadcast.tasks;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gianlucamc.ezbroadcast.EZBroadcast;

import me.clip.placeholderapi.PlaceholderAPI;

public class BroadcastTask implements Runnable {

	private EZBroadcast plugin;
	private ThreadLocalRandom random = ThreadLocalRandom.current();
	private int currentMessage = 0;

	public BroadcastTask(EZBroadcast plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		if (plugin.getSettingsManager().isRandom()) {
			currentMessage = Math.abs(random.nextInt() % EZBroadcast.getBroadcasts().size());
		} else if ((++currentMessage) >= EZBroadcast.getBroadcasts().size()) {
			currentMessage = 0;
		}

		if (currentMessage < plugin.getBroadcasts().size()) {
			List<String> messages = EZBroadcast.getBroadcasts().get(currentMessage).getMessages();

			for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
				for (String message : messages) {
					message = ChatColor.translateAlternateColorCodes('&', message);
					message = PlaceholderAPI.setPlaceholders(onlinePlayer, message);

					onlinePlayer.sendMessage(message);

					if (plugin.getSoundManager().isEnabled()) {
						onlinePlayer.playSound(onlinePlayer.getLocation(), plugin.getSoundManager().getSound(), plugin.getSoundManager().getVolume(), plugin.getSoundManager().getPitch());
					}
				}
			}
		}
	}
}