package com.play.runtime;

import java.util.HashMap;
import java.util.Map;

public class RuntimeClass {
	
	private Map<String, String> map;
	
	public RuntimeClass() {
		map = new HashMap<String, String>();
	}
	
	public RuntimeClass(Map<String, String> map) {
		this.map = map;
	}
	
	public String getMapClass() {
		return map.getClass().toString();
	}
	
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public static void main(String[] args) {
		RuntimeClass c1 = new RuntimeClass();
		System.out.println(c1.getMapClass());
		
		Map<String, String> map = new HashMap<String, String>();
		RuntimeClass c2 = new RuntimeClass(map);
		System.out.println(c2.getMapClass());
		
		c1.setMap(map);
		System.out.println(c1.getMapClass());
	}
}
