package com.play.mthread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class TimeAnalyzer {
	public static void timeStats() {
		long tid = Thread.currentThread().getId();
		
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    if(!bean.isThreadCpuTimeSupported()) {
	    	System.out.println("Cpu time is not supported.");
	    	return;
	    }
	    
	    long tc = bean.getThreadCpuTime(tid);
	    long tu = bean.getThreadUserTime(tid);
	    long ts = tc - tu;
	    
	    System.out.println("Thead " + Thread.currentThread());
	    System.out.println("\tCpu time: \t" + tc);
	    System.out.println("\tUser time: \t" + tu);
	    System.out.println("\tSystem time: \t" + ts);
	    
	}
}
