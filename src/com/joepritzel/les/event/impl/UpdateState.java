package com.joepritzel.les.event.impl;

import com.joepritzel.les.event.Event;
import com.joepritzel.les.model.State;

/**
 * This event updates State.
 * 
 * @author Joe Pritzel
 * 
 */
public class UpdateState extends Event {

	@Override
	public void fire() {
		State.update();
		reschedule(100);
	}

}
