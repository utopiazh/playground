package com.play.lucene.json;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Query2Json {
	public static void main(String[] args) {
		try {
			// query
			BooleanQuery bq = new BooleanQuery();
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
			
			Query q1 = new QueryParser(Version.LUCENE_43, "title", analyzer).parse("engineer");
			q1.setBoost(3.0f);
			bq.add(q1, Occur.SHOULD);
			
			Query q2 = new QueryParser(Version.LUCENE_43, "status", analyzer).parse("rich");
			q2.setBoost(0.1f);
			bq.add(q2, Occur.SHOULD);
					
			
			Gson json = new GsonBuilder().setPrettyPrinting()
					.registerTypeAdapter(Term.class, new TermDeserializer())
					.registerTypeAdapter(Term.class, new TermSerializer())
					.registerTypeAdapter(Query.class, new QueryMarshal())
					.create();
			
			String jstr = json.toJson(bq);
			System.out.println(jstr);
			
			BooleanQuery back = json.fromJson(jstr, BooleanQuery.class);
			System.out.println(back.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
