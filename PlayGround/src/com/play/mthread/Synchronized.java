package com.play.mthread;

public class Synchronized {

	public synchronized void syncMethod() {
		System.out.println("Enter: " + Thread.currentThread());
		long x = 0L;
//		int first = 1000;
//		int second = Integer.MAX_VALUE;
		int first = 1;
		int second = 1;
		for(int j = 0; j < Math.random() * first; ++ j) {
			for(int i = 0; i < second; ++i) {
				x += i;
			}
		}
		System.out.println("Out:  " + Thread.currentThread());
	}
	
	public static void main(String[] args) {
		final Synchronized so = new Synchronized();
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while(true) {
					so.syncMethod();
				}
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				while(true) {
					so.syncMethod();
				}
			}
		};
		t1.start();
		t2.start();
	}
	
}
