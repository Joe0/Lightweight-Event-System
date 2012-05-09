package com.joepritzel.les.event.impl;

import com.joepritzel.les.event.Event;
import com.joepritzel.les.model.State;

/**
 * Used to simulate events that have delays in them.
 * 
 * @author Joe Pritzel
 * 
 */
public class ReplayingEvent extends Event {

	private States state = States.FIRST;

	@Override
	public void fire() {
		switch (state) {
		case FIRST:
			checkpoint(States.SECOND);
			return;
		case SECOND:
			checkpoint(States.THIRD);
			return;
		case THIRD:
			State.inc();
			return;
		}
	}

	private final void checkpoint(States state) {
		this.state = state;
		reschedule(100);
	}

}

enum States {
	FIRST, SECOND, THIRD
}
