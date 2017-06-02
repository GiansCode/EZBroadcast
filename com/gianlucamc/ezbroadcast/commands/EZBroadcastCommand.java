package com.gianlucamc.ezbroadcast.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gianlucamc.ezbroadcast.EZBroadcast;
import com.gianlucamc.ezbroadcast.objects.Broadcast;

import me.clip.placeholderapi.PlaceholderAPI;

public class EZBroadcastCommand implements CommandExecutor {

	private EZBroadcast plugin;

	public EZBroadcastCommand(EZBroadcast plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			if (!(sender.hasPermission("ezbroadcast.commands.help"))) {
				sender.sendMessage(plugin.getMessageManager().getNoPermission());
				return true;
			}

			for (String helpMessage : plugin.getMessageManager().getHelp()) {
				helpMessage = ChatColor.translateAlternateColorCodes('&', helpMessage);
				helpMessage = helpMessage.replace("%version%", plugin.getDescription().getVersion());

				sender.sendMessage(helpMessage);
			}
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				if (!(sender.hasPermission("ezbroadcast.commands.list"))) {
					sender.sendMessage(plugin.getMessageManager().getNoPermission());
					return true;
				}

				sender.sendMessage("Created Broadcasts:");

				for (Broadcast broadcast : EZBroadcast.getBroadcasts()) {
					String id = broadcast.getID();

					sender.sendMessage("- " + id);
				}

				sender.sendMessage(" ");
				return true;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				if (!(sender.hasPermission("ezbroadcast.commands.reload"))) {
					sender.sendMessage(plugin.getMessageManager().getNoPermission());
					return true;
				}

				plugin.saveDefaultConfig();
				plugin.reloadConfig();
				plugin.loadConfig();

				sender.sendMessage(plugin.getMessageManager().getReloadSuccess());
				return true;
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("bc")) {
				if (!(sender.hasPermission("ezbroadcast.commands.bc"))) {
					sender.sendMessage(plugin.getMessageManager().getNoPermission());
					return true;
				}

				Broadcast broadcast = Broadcast.getBroadcast(args[1]);

				if (broadcast == null) {
					sender.sendMessage(plugin.getMessageManager().getNoBroadcastFound().replace("%id%", args[1]));
					return true;
				}

				List<String> messages = broadcast.getMessages();

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

		if (args.length > 2) {
			sender.sendMessage(plugin.getMessageManager().getIncorrectArguments());
			return true;
		}
		return true;
	}
}