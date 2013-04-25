package com.play.mthread;

/**
 * How to demostrate the side effect of starting a thread in constructor?
 * 
 * *Not successful yet.*
 * 
 * Reference:
 * http://www.ibm.com/developerworks/java/library/j-jtp0618/index.html
 * 
 * @author zhouhang
 *
 */
public class ConstructorLeak {
	private Thread thread;
	private Thread thread2;
	private String data;
	
	private static class TestThread extends Thread {
		private ConstructorLeak leak;
		public TestThread(ConstructorLeak leak) {
			this.leak = leak;
		}
		@Override
		public void run() {
			leak.leak();
			leak.setData(null);
		}
	}
	public ConstructorLeak() {
		data = "Hello leak!";
		thread = new TestThread(this);
		thread.start();
		thread = new TestThread(this);
		thread.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void leak() {
		synchronized(this) {
			System.out.println(System.currentTimeMillis()  + data + ":" +  Thread.currentThread() + "=== IN");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(System.currentTimeMillis()  + data + ":" +  Thread.currentThread() + "=== OUT");
		}
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public static void main(String[] args) {
		ConstructorLeak leak = new ConstructorLeak();
		leak.leak();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
