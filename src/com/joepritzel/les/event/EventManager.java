package com.joepritzel.les.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Used to manage events, including their scheduling, execution, etc.
 * 
 * @author Joe Pritzel
 * 
 */
public class EventManager {

	/**
	 * A thread-safe queue of events to be executed sequentially.
	 */
	public static final BlockingQueue<Event> queue = new LinkedBlockingQueue<Event>();

	/**
	 * A generic scheduler.
	 */
	public static final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(3);

	/**
	 * The total number of events fired.
	 */
	public static volatile int eventsFired = 0;

	/**
	 * Starts processing events.
	 */
	public static void processEvents() {
		EventProcessor.instance.start();
	}
}

/**
 * Processes events.
 * 
 * @author Joe Pritzel
 * 
 */
class EventProcessor {

	/**
	 * The instance...
	 */
	public static final EventProcessor instance = new EventProcessor();

	/**
	 * The actual thread that will do the processing.
	 */
	private final Thread thread = new Thread(new Runnable() {

		@Override
		public void run() {
			do {
				try {
					EventManager.queue.take().fire();
				} catch (Exception e) {
					e.printStackTrace();
				}

				EventManager.eventsFired++;
			} while (true);
		}

	});

	/**
	 * Keep it private, as we're trying to make this a singleton.
	 */
	private EventProcessor() {
	}

	/**
	 * Starts the EventProcessor.
	 */
	public final void start() {
		thread.start();
	}
}
