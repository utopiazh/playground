package com.play.google.json;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GsonTest {


	public static void main(String[] args) {
		TestClass clazz = new TestClass();
		clazz.setD(1.0000001d);
		clazz.setI(100);
		String[] list = {"testStr", "test str1", "test \" string 2"};
		clazz.setList(Arrays.asList(list));
		
		Map<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < list.length; ++ i) {
			map.put(Integer.toString(i), list[i]);
		}
		clazz.setMap(map);
		
		Multimap<String, String> mmap = ArrayListMultimap.create();
		for(int i = 0; i < list.length; ++ i) {
			for(int j = 0; j <= i; ++ j) {
				mmap.put(Integer.toString(i), list[j]);
			}
		}
		clazz.setMmap(mmap);
		
		// To json
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Multimap.class, new MultimapSerializer());
		gsonBuilder.registerTypeAdapter(Multimap.class, new MultimapDeserializer());
		Gson gson = gsonBuilder.create();
		String content = gson.toJson(clazz);
		System.out.println(content);
		
		// From json
		
		TestClass object = gson.fromJson(content, TestClass.class);
		System.out.println(clazz.equals(object));
		
	}
}
