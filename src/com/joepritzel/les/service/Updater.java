package com.joepritzel.les.service;

import com.joepritzel.les.Application;
import com.joepritzel.les.event.EventManager;
import com.joepritzel.les.model.State;

/**
 * Intended to represent reading the state, so it can update players.
 * 
 * @author Joe Pritzel
 * 
 */
public class Updater implements Runnable {

	private static final Updater instance = new Updater();
	private static final Thread thread = new Thread(instance);

	private Updater() {
	}

	private long lastRun;

	@Override
	public void run() {
		// Handle initial time offset
		Application.start = System.nanoTime();
		lastRun = Application.start;
		try {
			Thread.sleep(600);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		while (true) {
			long now = System.nanoTime();
			long timeBetween = (now - lastRun) / 1000000;
			lastRun = now;
			int i;

			State.update();
			i = State.getI();
			if (i == Application.AMOUNT * 2) {
				Application.end = System.nanoTime();

				double elapsedMs = (Application.end - Application.start) / 1000000.0;
				System.out.println("Time elapsed = " + elapsedMs);
				System.out
						.println("Events fired = " + EventManager.eventsFired);
				double epms = EventManager.eventsFired / elapsedMs;
				System.out.println("Events / ms = " + epms);
				System.out.println("Events / tick = " + epms * 600);
				System.out
						.println("Events / tick / player assuming 2k players = "
								+ epms * 0.3);
				System.exit(0);
			}
			try {
				// Account for the event taking a non-negligible amount
				// of time
				long time = (System.nanoTime() - lastRun) / 1000000;
				System.out.println("Time taken for update = " + time
						+ " - Time between updates = " + timeBetween);
				Thread.sleep(600 - time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Starts the updater.
	 */
	public static void start() {
		thread.start();
	}
}
