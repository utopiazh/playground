package com.play.mthread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Date;

public class ThreadDumper extends Thread {
	long interval = 1000;
	long count = 0;
	
	public ThreadDumper(long interval, long count) {
		this.interval = interval;
		this.count = count;
	}
	
	public void dumpAllThreads() {
		final ThreadMXBean thbean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] threads = thbean.dumpAllThreads(true, true);
		
		System.out.println("ThreadDumper : " + (new Date()));
		for(ThreadInfo t: threads) {
			System.out.println(t);
		}
	}
	
	@Override
	public void run() {
		while(count-- > 0) {
			try {
				Thread.sleep(interval);
				dumpAllThreads();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ThreadDumper dumper = new ThreadDumper(1000, 0);
		dumper.dumpAllThreads();
	}
}
