package com.joepritzel.les.event;

import java.util.concurrent.TimeUnit;

/**
 * Abstract Event.
 * 
 * @author Joe Pritzel
 * 
 */
public abstract class Event {

	/**
	 * This will be called when the event is intended to be processed.
	 */
	public abstract void fire();

	/**
	 * Reschedules the event to be submitted to the queue after the specified
	 * number of milliseconds.
	 * 
	 * @param delay
	 *            - The number of milliseconds before this event should be
	 *            rescheduled.
	 */
	protected void reschedule(long delay) {
		final Event e = this;
		EventManager.scheduler.schedule(new Runnable() {

			@Override
			public void run() {
				EventManager.queue.add(e);
			}

		}, delay, TimeUnit.MILLISECONDS);
	}
}
