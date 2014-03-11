package com.luckyhu.game.framework.game.util;

import java.util.LinkedList;

public class LHActionQueue {
	LinkedList<Runnable> queue;

	public LHActionQueue() {
		queue = new LinkedList<Runnable>();
	}

	public void enqueue(Runnable runable) {
		queue.offer(runable);
	}

	public void runAll() {
		Runnable run = queue.pollLast();
		while (run != null) {
			run.run();
			run = queue.pollLast();
		}
	}
}
