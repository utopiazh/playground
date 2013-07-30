package com.play.lucene.json;

import org.apache.lucene.index.Term;

public class TermData {
	private String field;
	private String text;
	
	public TermData(String field, String value) {
		this.field = field;
		this.text = value;
	}
	
	public TermData(Term term) {
		this.field = term.field();
		this.text = term.text();
	}
	
	public Term toTerm() {
		return new Term(field, text);
	}
}
