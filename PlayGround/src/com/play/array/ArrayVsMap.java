package com.play.array;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ArrayVsMap {

	public static class Element implements Comparable{
		int i;
		public Element(int i) {
			this.i = i;
		}

		@Override
		public int hashCode(){
			return i;
		}

		@Override
		public boolean equals(Object o) {
			return ((Element)o).i == i;
		}

		@Override
		public int compareTo(Object o) {
			return i - ((Element)o).i;
		}


	}
	public static class Entry {
		int[] array = new int[2];

		public Entry() {

		}

		public Entry set(int i) {
			array[i % 2] = i;
			return this;
		}

		public int get(int i) {
			return array[i % 2];
		}
	}

	public static void testArray(int size) {

		long s = System.currentTimeMillis();
		Element[] array = new Element[size];
		for (int i = 0; i < size; ++i) {
			array[i] = new Element(i + size);
		}
		for (int j = 0; j < 10; ++j) {
			for (int i = 0; i < size; ++i) {
				Element x = array[i];
			}
		}
		long e = System.currentTimeMillis();
		System.out.println("Array: " + (e - s));

	}

	public static void testArrayList(int size) {

		long s = System.currentTimeMillis();
		List<Element> array = new ArrayList<Element>(size);
		for (int i = 0; i < size; ++i) {
			array.add(new Element(i + size));
		}
		for (int j = 0; j < 10; ++j) {
			for (int i = 0; i < size; ++i) {
				Element x = array.get(i);
			}
		}
		long e = System.currentTimeMillis();
		System.out.println("Array List: " + (e - s));

	}

	public static void testMap(int size) {
//		Map<Element, Object> map = new ConcurrentHashMap<Element, Object>(size);

		Map<Element, Object> map = new TreeMap<Element, Object>();
		Object o = new Object();

		long s = System.currentTimeMillis();

		for (int i = 0; i < size; ++i) {
			map.put(new Element(i), o);
		}
		for (int j = 0; j < 10; ++j) {
			for (int i = 0; i < size; ++i) {
				Object x = map.get(new Element(i));
			}
		}
		long e = System.currentTimeMillis();
		System.out.println("Map: " + (e - s));

	}


	public static void testLinkArray(int size) {
		long s = System.currentTimeMillis();
		Entry[] array = new Entry[size >> 1];
		for (int i = 0; i < size; ++i) {
			array[i >> 1] = new Entry().set(i);
		}
		for (int j = 0; j < 10; ++j) {
			for (int i = 0; i < size; ++i) {
				int x = array[i >> 1].get(i);
			}
		}
		long e = System.currentTimeMillis();
		System.out.println("Link Array: " + (e - s));
	}

	public static void main(String[] args) {
		int size = 10000000;
		testArray(size);

		testArrayList(size);

		testLinkArray(size);
	}

}
