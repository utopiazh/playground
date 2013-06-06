package com.play.lucene.dynamic;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;

public class DynamicFieldComparatorSource extends FieldComparatorSource {

	@Override
	public FieldComparator<?> newComparator(String fieldname, int numHits,
			int sortPos, boolean reversed) throws IOException {
		return new DynamicFieldComparator(numHits);
	}
	
	 public static final class DynamicFieldComparator extends FieldComparator {
		    private final int[] docIDs;
		    private int docBase;
		    private int bottom;

		    DynamicFieldComparator(int numHits) {
		      docIDs = new int[numHits];
		    }

		    @Override
		    public int compare(int slot1, int slot2) {
		      return getValue(docIDs[slot1]) - getValue(docIDs[slot2]);
		    }

		    @Override
		    public int compareBottom(int doc) {
		      return getValue(bottom) - getValue(docBase + doc);
		    }

		    @Override
		    public void copy(int slot, int doc) {
		      docIDs[slot] = docBase + doc;
		    }

  
		    @Override
		    public void setBottom(final int bottom) {
		      this.bottom = docIDs[bottom];
		    }

		    @Override
		    public Comparable<?> value(int slot) {
		      return getValue(docIDs[slot]);
		    }
		    
		    private Integer getValue(int docId) {
		      return UpdateListener.getValue(docId);
		    }

			@Override
			public FieldComparator setNextReader(AtomicReaderContext context)
					throws IOException {
				this.docBase = context.docBase;
				return this;
			}

			@Override
			public int compareDocToValue(int doc, Object value)
					throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
		  }

}
