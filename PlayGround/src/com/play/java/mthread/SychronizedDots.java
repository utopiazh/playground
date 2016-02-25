package com.play.java.mthread;

import java.util.ArrayList;
import java.util.List;

public class SychronizedDots {
	
	static SychronizedDots instance = null;
	public void internal() {
		int jm = (int) (500 + 1000 * Math.random());
		for(int j = 0; j < jm  ; ++ j) {
			long l = 0L;
			for(int i = 0; i < Integer.MAX_VALUE; ++ i) {
				l = l + i;
			}
		}
	}
	
	public void internal2() {
		// do nothing
	}
	
	public synchronized static SychronizedDots getInstance() {
		if(instance == null) {
			instance = new SychronizedDots();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		List<Thread> ts = new ArrayList<Thread>();
		
		for(int i = 0 ; i < 400; ++ i) {
			Thread t = new Thread() {
				public void run() {
					while(true) {
						SychronizedDots.getInstance().internal();
					}
				}
			};
			ts.add(t);
			t.start();

		}
		
		for(Thread t : ts) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
