package com.joepritzel.les;

import com.joepritzel.les.event.Event;
import com.joepritzel.les.event.EventManager;
import com.joepritzel.les.event.impl.*;
import com.joepritzel.les.service.Updater;

/**
 * This is our 'test'.
 * 
 * @author Joe Pritzel
 * 
 */
public class Application {

	public volatile static long start;
	public volatile static long end;
	public static final int AMOUNT = 15000000;

	public static void main(String[] args) throws InterruptedException {
		EventManager.processEvents();

		// Starts the updater
		Updater.start();

		// Have the state periodically updated, so the updater doesn't get
		// bogged down
		EventManager.queue.add(new UpdateState());

		// SimpleEvent has no state, so we can reuse it
		Event e2 = new SimpleEvent();
		
		// Add events that are a bit more complicated, that need delays in them
		// and change the state
		for (int i = 0; i < AMOUNT; i += 250) {
			for (int j = 0; j < 250; j++) {
				EventManager.queue.add(new ReplayingEvent());
				EventManager.queue.add(e2);
			}
			Thread.sleep(1);
		}
	}
}
