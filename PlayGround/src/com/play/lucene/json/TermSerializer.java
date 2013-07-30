package com.play.lucene.json;

import java.lang.reflect.Type;

import org.apache.lucene.index.Term;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TermSerializer  implements JsonSerializer<Term> {
	private static Gson gson = new GsonBuilder().create();

	@Override
	public JsonElement serialize(Term src, Type typeOfSrc,
			JsonSerializationContext context) {
		TermData td = new TermData(src);
		return gson.toJsonTree(td);		
	}

}
