package com.play.lucene.numeric;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class NumericRangeQueryTest {

	public static void main(String[] args){
		String path = "./test/lucene/numeric";
		
		File dir = new File(path);
		if(dir.exists()) {
			try {
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		dir.mkdirs();
		
		Directory d = null;
		try {
			d = FSDirectory.open(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);			
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);
		
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(d, config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		FieldType stupid = new FieldType();
		stupid.setNumericPrecisionStep(1);
		stupid.setStored(true);
		stupid.setNumericType(NumericType.LONG);
		stupid.setIndexed(true); // Must be indexed.
		
		Document doc1 = new Document();
		LongField lf =new LongField("long", Long.MAX_VALUE, stupid);		
//		LongField lf =new LongField("long", Long.MAX_VALUE, Store.YES);
		System.out.println(lf);
		doc1.add(lf);
		doc1.add(new IntField("int", Integer.MAX_VALUE, Store.YES));
		doc1.add(new DoubleField("double", Double.MIN_VALUE, Store.YES));
		
		try {
			writer.addDocument(doc1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document doc2 = new Document();
		LongField lf2 =new LongField("long", 12L, stupid);		
//		LongField lf2 =new LongField("long", 12L, Store.YES);
		doc2.add(lf2);		
		doc2.add(new IntField("int", Integer.MIN_VALUE, Store.YES));
		doc2.add(new DoubleField("double", Double.MAX_VALUE, Store.YES));
		
		try {
			writer.addDocument(doc2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		IndexReader reader = null;
		try {
			
			reader = DirectoryReader.open(d);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		IndexSearcher searcher = new IndexSearcher(reader);
		NumericRangeQuery<Long> query = NumericRangeQuery.newLongRange("long", 4, Long.MIN_VALUE, Long.MAX_VALUE, true, true);
		TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);		
		try {
			searcher.search(query, collector);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		
		boolean explain = false;
		System.out.println("Hits: " + hits.length);
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document doc = null;
			try {
				doc = searcher.doc(docId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			System.out.println((i + 1) + ". " + "id: " + docId + ", long: " + doc.get("long") +", score: " + hits[i].score);
			if (explain) {
				Explanation explanation;
				try {
					explanation = searcher.explain(query, docId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				System.out.println(explanation);
			}
		}
		
	}
}
