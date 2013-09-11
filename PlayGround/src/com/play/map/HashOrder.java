package com.play.map;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HashOrder {

	public static class Hash {
		int i;
		public Hash(int i) {
			this.i = i;
		}
		@Override
		public int hashCode() {
			return ~i;
		}
		@Override
		public String toString() {
			return "Hash [i=" + i + "]";
		}		
		
		
	}
	
	public static void main(String[] args) {
		Set<Hash> set = new HashSet<Hash>();
		for(int i = 0; i < 10; ++i) {
			set.add(new Hash(i));
		}
		
		for(Hash h : set) {
			System.out.println(h);
		}
		
		Map<Hash, Hash> map = new ConcurrentHashMap<Hash, Hash>();
		for(int i = 0; i < 10; ++i) {
			Hash h = new Hash(i);
			map.put(h, h);
		}
		
		for(Hash h : map.keySet()) {
			System.out.println(h);
		}
	}
}
