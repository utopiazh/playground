package com.play.google.json;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Serialize Multimap (guava) to Json.
 * 
 * The underlying workflow is:
 * 
 * 1. Change MultiMap<K, V> to Map<K, Collection<V>>. 
 * 2. Serial Map<K, Collection<V>> to Json. 
 * 3. Feed the Json string to Gson.  
 * 
 * @author zhouhang
 *
 */
public class MultimapSerializer implements JsonSerializer<Multimap> {
	private static Gson gson = new GsonBuilder().create();
	
	public MultimapSerializer(){
		
	}
	
	public JsonElement serialize(Multimap src, Type typeOfSrc, JsonSerializationContext context) {
		Map map = src.asMap();
		return gson.toJsonTree(map, Map.class);
		
	}
}
