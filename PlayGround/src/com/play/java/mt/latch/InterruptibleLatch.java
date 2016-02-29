package com.play.java.mt.latch;

import java.util.concurrent.CountDownLatch;

public class InterruptibleLatch {

	public static void main(String[] args) {
		
		final CountDownLatch latch  = new CountDownLatch(1);
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized(latch) {
					latch.notify();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				latch.countDown();
			}
		};
		thread.start();
		try {
			while(true) {
				latch.await();
				System.out.println("Latch notified.");
				if(latch.getCount() == 0) {
					System.out.println("Latch released");
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
