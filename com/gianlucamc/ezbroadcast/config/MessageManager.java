package com.gianlucamc.ezbroadcast.config;

import java.util.List;

public class MessageManager {

	private String noPermission;
	private String noBroadcastFound;
	private String incorrectArguments;
	private String reloadSuccess;
	private List<String> help;

	public MessageManager(String noPermission, String noBroadcastFound, String incorrectArguments, String reloadSuccess, List<String> help) {
		this.noPermission = noPermission;
		this.noBroadcastFound = noBroadcastFound;
		this.incorrectArguments = incorrectArguments;
		this.reloadSuccess = reloadSuccess;
		this.help = help;
	}

	public String getNoPermission() {
		return noPermission;
	}

	public String getNoBroadcastFound() {
		return noBroadcastFound;
	}

	public String getIncorrectArguments() {
		return incorrectArguments;
	}

	public String getReloadSuccess() {
		return reloadSuccess;
	}

	public List<String> getHelp() {
		return help;
	}
}