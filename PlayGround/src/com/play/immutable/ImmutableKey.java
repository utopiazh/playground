package com.play.immutable;

import java.util.HashMap;
import java.util.Map;

public class ImmutableKey {
	public static class TEntry {
		private String name;

		@Override
		public int hashCode() {
			return name.hashCode();
		}
	}

	public static void main(String[] args) {
		Map<TEntry, TEntry> map = new HashMap<TEntry, TEntry>();

		TEntry t1 = new TEntry();
		t1.name = "a";

		map.put(t1, t1);

		t1.name = "b";

		System.out.println(map.get(t1));

	}
}
