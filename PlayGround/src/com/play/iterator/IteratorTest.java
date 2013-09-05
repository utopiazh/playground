package com.play.iterator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class IteratorTest {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < 10; ++i) {
			String s = Integer.toString(i);
			map.put(s, s);
		}
		
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> e = it.next();
			Integer v = Integer.parseInt(e.getValue());
			if( v % 2 == 0) {
				continue;
			}
			e.setValue(null);
			it.remove();
		}
		
		System.out.println(map);
		
	}
	
}
