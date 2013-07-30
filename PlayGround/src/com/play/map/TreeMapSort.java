package com.play.map;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapSort {
	
	public static void main(String[] args) {
		
		
		Map map = new HashMap<String, Integer>();
		map.put("abc", 1);
		map.put("123", 2);
		map.put("efg", 2);
		
		System.out.println(map);
		
		System.out.println(new TreeMap(map));
	}
}
