package com.gianlucamc.ezbroadcast.objects;

import java.util.List;

import com.gianlucamc.ezbroadcast.EZBroadcast;

public class Broadcast {

	private String id;
	private List<String> messages;

	public Broadcast(String id, List<String> messages) {
		this.id = id;
		this.messages = messages;
	}

	public String getID() {
		return id;
	}

	public List<String> getMessages() {
		return messages;
	}

	public static Broadcast getBroadcast(String id) {
		for (Broadcast broadcast : EZBroadcast.getBroadcasts()) {
			if (broadcast.getID().equalsIgnoreCase(id)) {
				return broadcast;
			}
		}

		return null;
	}
}