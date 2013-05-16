package com.play.datetime;

import java.util.Date;

import com.play.mthread.ThreadDumper;
import com.play.mthread.TimeAnalyzer;

public class DatePerf {

	public void testPerf() {
		final int LEN = 10000000;
		long[] times = new long[LEN];
		for(int i = 0; i < LEN; ++ i) {
			times[i] = System.currentTimeMillis();
		}
		long start = System.currentTimeMillis();
		for(int i = 0; i < LEN; ++ i) {
			String s = (new Date(times[i])).toString();
		}
		long end = System.currentTimeMillis();
		System.out.println("Cost " + (end - start));
		// Cost 5050
        // avg cost = 5050 / 1000 0000 = 0.5 micro seconds
		TimeAnalyzer.timeStats();
	}
	
	public void testPerfMThread() {
		final int LEN = 10000000;
		final long[] times = new long[LEN];
		for(int i = 0; i < LEN; ++ i) {
			times[i] = System.currentTimeMillis();
		}
		Thread t1 = new Thread() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				for(int i = 0; i < LEN; ++ i) {
					String s = (new Date(times[i])).toString();
				}
				long end = System.currentTimeMillis();
				System.out.println("Cost " + (end - start));
				TimeAnalyzer.timeStats();
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				for(int i = 0; i < LEN; ++ i) {
					String s = (new Date(times[i])).toString();
				}
				long end = System.currentTimeMillis();
				System.out.println("Cost " + (end - start));
				TimeAnalyzer.timeStats();
			}
		};
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) {
//		StackTaceDumper dumper = new StackTaceDumper(1000, 20);
		ThreadDumper dumper = new ThreadDumper(1000, 20);
		dumper.start();
		DatePerf perf = new DatePerf();
		perf.testPerf();		
		perf.testPerfMThread();		
		//Cost 6725
		//Cost 6726
		// Cost 6233
		// Cost 7649
		// Cost 7863

		try {
			dumper.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
