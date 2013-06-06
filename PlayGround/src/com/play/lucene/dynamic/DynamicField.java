package com.play.lucene.dynamic;

import java.io.IOException;

import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.SortField;
import org.apache.solr.response.TextResponseWriter;
import org.apache.solr.schema.SchemaField;
import org.apache.solr.schema.FieldType;


public class DynamicField extends FieldType {
	
	 @Override
	  public SortField getSortField(SchemaField field, boolean top) {
	    return new SortField(field.getName(), 
	      new DynamicFieldComparatorSource(), top);
	  }


	@Override
	public void write(TextResponseWriter writer, String name, IndexableField f)
			throws IOException {
	    writer.writeStr(name, f.stringValue(), false);		
	}

}
