package com.play.datetime;

import java.util.Date;

public class DatePerf {

	
	public static void main(String[] args) {
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
	}
}
