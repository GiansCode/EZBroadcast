package com.gianlucamc.ezbroadcast.config;

public class SettingsManager {

	private boolean random;
	private int messageInterval;

	public SettingsManager(boolean random, int messageInterval) {
		this.random = random;
		this.messageInterval = messageInterval;
	}

	public boolean isRandom() {
		return random;
	}

	public int getMessageInterval() {
		return messageInterval;
	}
}