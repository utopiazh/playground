package com.play.lucene.json;

import java.lang.reflect.Type;

import org.apache.lucene.search.Query;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QueryMarshal implements JsonDeserializer<Query>, JsonSerializer<Query> {

	private static final String QUERY_TYPE_KEY = "query_type";
	
	private static Gson gson = new GsonBuilder().create();
	@Override
	public Query deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		String type = json.getAsJsonObject().get(QUERY_TYPE_KEY).getAsString();
		try {
			Class<?> clz = Class.forName(type);
			return context.deserialize(json, clz);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public JsonElement serialize(Query src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonElement json = context.serialize(src);
		json.getAsJsonObject().addProperty(QUERY_TYPE_KEY, src.getClass().getCanonicalName());
		return json;
	}

}
