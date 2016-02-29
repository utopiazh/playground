package com.play.java.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

	public static void mapTest() {
		Map<Double, Integer> map = new HashMap<Double, Integer>();
		for(int i = 0; i < 3600; i ++) {
			double x = -10.0;
			while(map.containsKey(x)) {
				x = x - 0.0000000000001;
			}
			map.put(x, i);
		}
	}
	public static void main(String[] args) {
		long s = System.nanoTime();
		mapTest();
		System.out.println(System.nanoTime() - s);
	}
}
