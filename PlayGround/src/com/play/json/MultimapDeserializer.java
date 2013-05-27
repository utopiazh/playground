package com.play.json;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class MultimapDeserializer implements JsonDeserializer<Multimap> {
	private static Gson gson = new GsonBuilder().create();

	public Multimap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Multimap mmap = ArrayListMultimap.create();	  	 
		Map map = gson.fromJson(json, Map.class);		 	
		Map<Object, Collection<Object>> src = (Map<Object, Collection<Object>>)map;
		for(Entry<Object, Collection<Object>> entry : src.entrySet()) {
			mmap.putAll(entry.getKey(), entry.getValue());
		}	 	
		return mmap;
	}
}
