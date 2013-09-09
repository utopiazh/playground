package com.play.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {

//	private static ReadWriteLock lock = new ReentrantReadWriteLock(true);
//	private final static Lock readLock = lock.readLock();
//	private final static Lock writeLock = lock.readLock();
//	
	
	private static Lock lock = new ReentrantLock(true);
	private final static Lock readLock = lock;
	private final static Lock writeLock = lock;
	
	private final static AtomicLong readCnt = new AtomicLong();
	private final static AtomicLong writeCnt = new AtomicLong();
	
	
	public static void read() {
		try {
			readLock.lock();			
			readCnt.getAndIncrement();
		} finally {
			readLock.unlock();
		}
	}
	
	public static void write() {
		try {
			writeLock.lock();
			writeCnt.getAndIncrement();
		} finally {
			writeLock.unlock();
		}
	}
	
	public static class Reader extends Thread {
		long limit;
		public Reader(long limit) {
			this.limit = limit;
		}
		
		public void run() {
			while(readCnt.get() < limit && writeCnt.get() < limit) {
				read();
			}
		}
	}
	
	public static class Writer extends Thread {
		long limit;
		public Writer(long limit) {
			this.limit = limit;
		}
		
		public void run() {
			while(readCnt.get() < limit && writeCnt.get() < limit) {			
				write();
			}
		}
	}
	
	public static void test() {
		long limit = 1000000;
		int readers = 3;
		int writers = 3;
		List<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < readers; ++i) {
			Thread t = new Reader(limit);
			threads.add(t);
		}
		for(int i = 0; i < writers; ++i) {
			Thread t = new Writer(limit);
			threads.add(t);
		}
		
		long s = System.currentTimeMillis();
		for(Thread t : threads) {
			t.start();
		}
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		long e = System.currentTimeMillis();
		
		System.out.println("Read " + readCnt.get() + ", Write " + writeCnt.get() + ", time " + (e - s));
		
	}
	
	public static void main(String[] args) { 
		for(int i = 0; i < 10; ++i) {
			readCnt.set(0);
			writeCnt.set(0);
			test();
		}
	}
}