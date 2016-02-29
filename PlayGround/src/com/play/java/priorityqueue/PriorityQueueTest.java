package com.play.java.priorityqueue;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityQueueTest {
	
	private static class Element implements Comparable<Element> {

		int priority;
		int data;
		
		public Element(int p, int data) {
			this.priority = p;
			this.data = data;
		}
		
		@Override
		public int compareTo(Element o) {			
			return this.priority - o.priority;
		}

		@Override
		public String toString() {
			return "Element [priority=" + priority + ", data=" + data + "]";
		}

		
		
		
	}
	
	public static void main(String[] args) {
		PriorityBlockingQueue<Element> queue = new PriorityBlockingQueue<Element>();
		queue.offer(new Element(1, 9));
		queue.offer(new Element(7, 7));
		queue.offer(new Element(3, 3));
		queue.offer(new Element(1, 0));
		
		System.out.println(new ArrayList<Element>(queue));
		while(!queue.isEmpty()) {
			try {
				System.out.println(queue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
