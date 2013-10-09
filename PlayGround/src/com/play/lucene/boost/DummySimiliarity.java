package com.play.lucene.boost;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.similarities.Similarity;

public class DummySimiliarity extends Similarity {
	/**
	 *
	 */
	private static final long serialVersionUID = -9058870746124829217L;

	public float computeNorm(String field, FieldInvertState state) {
	/*未使用的局部变量
		 final int numTerms;
		if (discountOverlaps)
		 numTerms = state.getLength() - state.getNumOverlap();
		else
		numTerms = state.getLength();
		return (state.getBoost() * lengthNorm(field, numTerms));
     */
		return (state.getBoost() * 1.0f);
 }
	 protected boolean discountOverlaps;

	 public void setDiscountOverlaps(boolean v) {
		 discountOverlaps = v;
	}

	 public boolean getDiscountOverlaps() {
	  return discountOverlaps;
	 }
	@Override
	public float coord(int overlap, int maxOverlap) {
		return 1.0f;
	}

	@Override
	public float idf(int docFreq, int numDocs) {
		return 1.0f;
	}

/*	@Override
	public float lengthNorm(String fieldName, int numTerms) {
		return 1.0f;
	}*/

	@Override
	public float queryNorm(float sumOfSquaredWeights) {
		return 1.0f;
	}

	@Override
	public float sloppyFreq(int distance) {
		return 1.0f;
	}

	@Override
	public float tf(float frq) {
		if(frq>0)
			return 1.0f;
		else
			return 0;
	}
	@Deprecated
	  public static float decodeNorm(byte b) {
	    return 1.0f;  // & 0xFF maps negative bytes to positive above 127
	  }

	public float decodeNormValue(byte b) {
	    return 1.0f;  // & 0xFF maps negative bytes to positive above 127
	  }

	@Override
	public long computeNorm(FieldInvertState state) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SimWeight computeWeight(float queryBoost,
			CollectionStatistics collectionStats, TermStatistics... termStats) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExactSimScorer exactSimScorer(SimWeight weight,
			AtomicReaderContext context) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SloppySimScorer sloppySimScorer(SimWeight weight,
			AtomicReaderContext context) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
