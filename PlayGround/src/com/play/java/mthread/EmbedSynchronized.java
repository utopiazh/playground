package com.play.java.mthread;

import com.play.java.debug.FunctionCall;

/**
 * "synchronized" keyword is implemented as recursive lock, 
 * so embeded function call works.
 * @author zhouhang
 */
public class EmbedSynchronized {

	public synchronized void syncL1() {
//		FunctionCall.printStackTrace();
		System.out.println(FunctionCall.getMethodName(0));
		syncL2();
		
	}
	
	public synchronized void syncL2() {
//		FunctionCall.printStackTrace();
		System.out.println(FunctionCall.getMethodName(0));

	}
	
	public static void main(String[] args) {
		EmbedSynchronized sync = new EmbedSynchronized();
		sync.syncL1();
	}
}
 