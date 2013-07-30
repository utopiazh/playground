package com.play.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * First test on Lucene
 * 
 * @author zhouh1
 * 
 */
public class HelloLucene {

	public static void main(String[] args) {
		
		
		try {
			String path = "./test/lucene/hello";
			
//			IndexWriter writer = getWriter(path);
//			
//			System.out.println("# Add doc 1");
//			addDoc1(writer);					
//			writer.commit();
			//writer.close();
			
//			// query
//			Query q = getQuery(null);
//			System.out.println("Query: " + q);
			
			// get reader
			
			IndexReader reader = DirectoryReader.open();
			//IndexReader reader = DirectoryReader.open(index);

			//IndexReader reader = IndexReader.open(directory);
			//Terms t =  MultiFields.getTerms(reader, keyField.getName());
			Terms t =  MultiFields.getTerms(reader, 'guid');
			if(t == null) {
				log.error("Failed to get terms, reader: " + reader + ", keyField: " + keyField);
				return;
			}
			TermsEnum te = t.iterator(null);

			DocsEnum de = null;
			BytesRef term;
			while (true) {
				term = te.next();
				if (term == null) {
					break;
				}
				String key = term.utf8ToString();

				de = te.docs(null, de);
				List<Integer> docs = new ArrayList<Integer>();
				int doc = 0;
				while ((doc = de.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
					
					Document dd = reader.document(docID);
					String xx = dd.get('price_1');
					
					docs.add(doc);
				}
				if (!docs.isEmpty()) {
					newData.put(key, docs);
				}
			}
			
//			search(reader, q, true);
//			
//			System.out.println("# Add doc 2");
//			addDoc2(writer);
//			// get new reader
//			reader = DirectoryReader.openIfChanged((DirectoryReader) reader, writer, true);
//			search(reader, q, true);
//			
//			reader.close();	
//			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static IndexWriter getWriter(String path) throws IOException {
		// prepare dir

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		} else {
			File[] files = dir.listFiles();
			for (File file : files) {
				file.delete();
			}
		}

		// open index
		Directory index = FSDirectory.open(dir);
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,
				analyzer);

		return new IndexWriter(index, config);
	}

	public static void addDoc1(IndexWriter writer) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", "software engineer", Field.Store.YES));
		doc.add(new TextField("name", "jeef.dean", Field.Store.YES));
		writer.addDocument(doc);
	}

	public static void addDoc2(IndexWriter writer) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("status", "rich", Field.Store.NO));
		doc.add(new TextField("title", "senior software engineer",
				Field.Store.YES));
		doc.add(new TextField("name", "larry.page", Field.Store.YES));
		writer.addDocument(doc);
	}

	public static Query getQuery(Analyzer analyzer) throws ParseException {
		if (analyzer == null) {
			analyzer = new StandardAnalyzer(Version.LUCENE_43);
		}
		// query
		BooleanQuery bq = new BooleanQuery();
		// query 1
		Query q1 = new QueryParser(Version.LUCENE_43, "title", analyzer)
				.parse("engineer");
		q1.setBoost(3.0f);
		bq.add(q1, Occur.SHOULD);

		Query q2 = new QueryParser(Version.LUCENE_43, "status", analyzer)
				.parse("rich");
		q2.setBoost(0.1f);
		bq.add(q2, Occur.SHOULD);

		return bq;
	}

	public static void search(IndexReader reader, Query q, boolean explain)
			throws IOException {
		// search
		int hitsPerPage = 10;
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		// display results
		displayResults(q, hits, searcher, explain);
	}

	public static void displayResults(Query q, ScoreDoc[] hits,
			IndexSearcher searcher, boolean explain) throws IOException {
		System.out.println("Hits: " + hits.length);
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d);
			if (explain) {
				Explanation explanation = searcher.explain(q, docId);
				System.out.println(explanation);
			}
		}
	}

}
