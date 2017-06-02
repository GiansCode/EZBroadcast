package com.gianlucamc.ezbroadcast.config;

import org.bukkit.Sound;

public class SoundManager {

	private boolean enabled;
	private Sound sound;
	private float volume;
	private float pitch;

	public SoundManager(boolean enabled, Sound sound, float volume, float pitch) {
		this.enabled = enabled;
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Sound getSound() {
		return sound;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}
}