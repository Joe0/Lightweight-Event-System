package com.joepritzel.les.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is intended to represent something that has state, that will be accessed
 * by multiple events.
 * 
 * @author Joe Pritzel
 * 
 */
public class State {
	/**
	 * Our variable that will be changed/accessed by events.
	 */
	private volatile static int i;

	/**
	 * A queue of changes to our state.
	 */
	private static Queue<Runnable> queue = new ConcurrentLinkedQueue<Runnable>();

	/**
	 * A runnable that changes our state.
	 */
	private static Runnable increment = new Runnable() {

		@Override
		public void run() {
			i++;
		}

	};

	/**
	 * Increments our state variable.
	 */
	public static void inc() {
		queue.add(increment);
	}

	/**
	 * @return The value of our state variable.
	 */
	public static int getI() {
		return i;
	}

	/**
	 * Causes State to update.
	 */
	public static void update() {
		synchronized (queue) {
			Runnable r = queue.poll();
			while (r != null) {
				r.run();
				r = queue.poll();
			}
		}
	}
}
