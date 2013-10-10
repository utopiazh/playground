package com.play.lucene.boost;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.Similarity.ExactSimScorer;
import org.apache.lucene.search.similarities.Similarity.SimWeight;
import org.apache.lucene.search.similarities.Similarity.SloppySimScorer;
import org.apache.lucene.search.similarities.SimilarityBase;

public class DummySimiliarity extends SimilarityBase {
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


/*	@Override
	public float lengthNorm(String fieldName, int numTerms) {
		return 1.0f;
	}*/

	@Override
	public float queryNorm(float sumOfSquaredWeights) {
		return 1.0f;
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
		return (long) state.getBoost();
	}

	@Override
	protected float score(BasicStats stats, float freq, float docLen) {
		return stats.getTotalBoost();
	}

	@Override
	public String toString() {
		return null;
	}



}
