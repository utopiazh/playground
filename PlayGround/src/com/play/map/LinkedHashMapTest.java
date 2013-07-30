package com.play.map;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Map that keeps insertion-order.
 * @author zhouhang
 *
 */
public class LinkedHashMapTest {

	public static void main(String[] args) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("k1", "v1");
		map.put("k0", "v0");
		map.put("k2", "v2");
		
		for(Entry<String, String> e : map.entrySet()) {
			System.out.println(e);
		}
		
	}
}
