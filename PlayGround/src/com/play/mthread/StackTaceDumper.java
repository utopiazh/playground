package com.play.mthread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class StackTaceDumper extends Thread {
	long interval = 1000;
	long count = 30;
	
	public StackTaceDumper(long dumpInterval, long dumpCount) {
		this.interval = dumpInterval;		
		this.count = dumpCount;
	}
	
	private boolean self() {
		final ThreadMXBean thbean = ManagementFactory.getThreadMXBean();
		int nAlloc = thbean.getThreadCount();
		return nAlloc == 1;		
	}
    
	@Override
	public void run() {
		while(count-- > 0) {
			try {
				Thread.sleep(interval);
				System.out.println("========= Thread Dump " + count +  " : " + (new Date()) );
				Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
				for(Entry<Thread, StackTraceElement[]> entry:  traces.entrySet()) {
					System.out.println(entry.getKey());
					for(StackTraceElement elem: entry.getValue()) {
						System.out.println("\t" + elem);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		StackTaceDumper dumper = new StackTaceDumper(1000, 2);
		dumper.start();
		try {
			dumper.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
