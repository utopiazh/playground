package com.play.java.mem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * From "java concurrency in practice", but doesn't work yet.
 *
 * @author zhouh1
 *
 */
public class UnsafePublish {
	public Holder holder;

	public void initialize() {
		holder = new Holder((int)(100*Math.random()));
	}

	public static class Holder {
		private int n;

		public Holder(int n) {
			this.n = n;
		}

		public void assertSanity() {
			if (n != n) {
				throw new AssertionError("This statement is false.");
			}
		}
	}

	public static class InitThread extends Thread {
		final UnsafePublish up;

		public InitThread(UnsafePublish up) {
			this.up = up;
		}

		@Override
		public void run() {
			while (true) {
				up.initialize();
			}
		}
	}

	public static class TestThread extends Thread {
		final UnsafePublish up;

		public TestThread(UnsafePublish up) {
			this.up = up;
		}

		@Override
		public void run() {
			while (true) {
				if(up.holder != null) {
					up.holder.assertSanity();
				}
			}
		}
	}

	public static void main(String[] args) {

		final UnsafePublish up = new UnsafePublish();

		List<Thread> ts = new ArrayList<Thread>();

		for (int i = 0; i < 10; ++i) {
			Thread t = new InitThread(up);
			ts.add(t);

			t = new TestThread(up);
			ts.add(t);
		}

		for (Thread t : ts) {
			t.start();
		}

		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
