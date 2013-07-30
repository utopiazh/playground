package com.play.array;

import java.util.HashMap;
import java.util.Map;

public class ArrayVsMap {

	public static void main(String[] args) {
		
		int size = 100000000;
		long[] array = new long[size];
		for(int i = 0; i < size ; ++ i) {
			array[i] = i + size;
		}
		
		Map<Integer, Long> map = new HashMap<Integer, Long> ();
		for(int i = 0; i < size; ++ i) {
			map.put(i,(long) (size + i));
		}
		
		long x  = 0L;
		long s = System.currentTimeMillis();
		for(int i = 0; i < size ; ++ i) {
			x = array[i];
		}
		long e = System.currentTimeMillis();
		System.out.println("Array: " + (e - s));
		
		
		s = System.currentTimeMillis();
		for(int i = 0; i < size ; ++ i) {
			x = map.get(i);
		}
		e = System.currentTimeMillis();
		System.out.println("Map: " + (e - s));
		long y = x;
	}
	
}
