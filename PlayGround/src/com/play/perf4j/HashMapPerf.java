package com.play.perf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

public class HashMapPerf {
	private static class GoodHash {
		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			return super.equals(o);
		}
	}

	private static class BadHash {
		@Override
		public int hashCode() {
			return super.hashCode() % 1;
		}

		@Override
		public boolean equals(Object o) {
			return super.equals(o);
		}
	}

	public void testCRUD(List<Object> objs) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		for (Object o : objs) {
			map.put(o, o);
		}
		for (Object o : objs) {
			map.get(o);
		}
		for (Object o : objs) {
			map.put(o, o);
		}
		for (Object o : objs) {
			map.remove(o);
		}
	}

	public static void main(String[] args) {
		List<Object> good = new ArrayList<Object>();
		for (int i = 0; i < 100000; ++i) {
			good.add(new GoodHash());
		}
		List<Object> bad = new ArrayList<Object>();
		for (int i = 0; i < 100000; ++i) {
			bad.add(new BadHash());
		}

		for (int i = 0; i < 10; ++i) {
			HashMapPerf perf = new HashMapPerf();
			StopWatch sw = new LoggingStopWatch();
			perf.testCRUD(good);
			sw.lap("testCRUD-good");
			perf.testCRUD(bad);
			sw.stop("testCRUD-bad");
		}
	}

}
