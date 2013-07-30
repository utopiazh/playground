package com.play.lucene.json;

import java.lang.reflect.Type;

import org.apache.lucene.index.Term;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TermDeserializer  implements JsonDeserializer<Term> {

	private static Gson gson = new GsonBuilder().create();
	
	@Override
	public Term deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		TermData td = gson.fromJson(json, TermData.class);
		return td.toTerm();
	}

}
