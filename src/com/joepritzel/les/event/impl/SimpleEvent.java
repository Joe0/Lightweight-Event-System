package com.joepritzel.les.event.impl;

import com.joepritzel.les.event.Event;
import com.joepritzel.les.model.State;

/**
 * Used to simulate an event that changes the state of the application.
 * 
 * @author Joe Pritzel
 * 
 */
public class SimpleEvent extends Event {

	@Override
	public void fire() {
		State.inc();
	}

}
