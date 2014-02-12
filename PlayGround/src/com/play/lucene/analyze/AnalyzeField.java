package com.play.lucene.analyze;

import java.io.File;
import java.io.IOException;

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
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class AnalyzeField {

	public static void main(String[] args) {


		try {
			String path = "./test/lucene/analyze";
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

			IndexWriter writer = new IndexWriter(index, config);

			System.out.println("# Add doc 1");
			Document doc = new Document();
			doc.add(new TextField("title", "software engineer", Field.Store.YES));
			doc.add(new TextField("name", "jeef.dean", Field.Store.YES));
			doc.add(new Field("Country", "1,2", Field.Store.YES, Field.Index.ANALYZED_NO_NORMS));
			writer.addDocument(doc);

			writer.commit();
			//writer.close();

			// query
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

			Query q3 = new QueryParser(Version.LUCENE_43, "Country", analyzer)
			.parse("1");
			q3.setBoost(0.1f);
			bq.add(q3, Occur.MUST_NOT);

			System.out.println("Query: " + bq);

			// get reader

			IndexReader reader = DirectoryReader.open(writer, true);

			// search
			int hitsPerPage = 10;
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(
					hitsPerPage, true);
			searcher.search(bq, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			// display results
			boolean explain = false;

			System.out.println("Hits: " + hits.length);
			for (int i = 0; i < hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				System.out.println((i + 1) + ". " + d);
				if (explain) {
					Explanation explanation = searcher.explain(bq, docId);
					System.out.println(explanation);
				}
			}

			reader.close();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
