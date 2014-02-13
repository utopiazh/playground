package com.play.immutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ImmutableCache {

	public static void simpleUpdate() {

		Map<Integer, Integer> data = new HashMap<Integer, Integer>();
		data.put(1, 1);
		data.put(2, 2);

		ImmutableMap map = ImmutableMap.copyOf(data);

		System.out.println(map);
		map.put(3, 3);
		map.put(2, 4);

		System.out.println(map);

	}

	public static void arrayUpdate() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		Map<Integer, List<Integer>> data = new HashMap<Integer, List<Integer>>();
		data.put(1, ImmutableList.copyOf(list));

		list.add(3);
		data.put(2, ImmutableList.copyOf(list));

		ImmutableMap<Integer, List<Integer>> map = ImmutableMap.copyOf(data);

		System.out.println(map);
		map.get(2).add(5);

		System.out.println(map);

	}

	public static void arrayUpdate2() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		Map<Integer, List<Integer>> data = new HashMap<Integer, List<Integer>>();
		data.put(1, Collections.unmodifiableList(list));

		list.add(3);
		data.put(2, Collections.unmodifiableList(list));

		ImmutableMap<Integer, List<Integer>> map = ImmutableMap.copyOf(data);

		System.out.println(map);
		map.get(2).add(5);

		System.out.println(map);

	}

	public static void main(String[] args) {
		// simpleUpdate();

		// arrayUpdate();

		arrayUpdate2();

	}
}
