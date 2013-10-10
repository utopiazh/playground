package com.play.lucene.boost;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
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
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.play.lucene.HelloLucene;
/**
 * 在使用sortField的时候，Boost不再其作用。
 *
 * 当SortField的值相等的时候，结果按照doc id排序。
 *
 * @author zhouh1
 *
 */
public class BoostVsSort extends HelloLucene {

	public static void main(String[] args) {
		String path = "./test/lucene/bvs";

		try {
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

			// Add document
			{
				Document doc = new Document();
				doc.add(new TextField("title", "CEO & engineer",
						Field.Store.YES));
				doc.add(new TextField("name", "marisa meyers", Field.Store.YES));
				doc.add(new IntField("salary", 25, Field.Store.YES));
				doc.add(new TextField("gender", "female", Field.Store.YES));
				writer.addDocument(doc);
				writer.commit();
			}

			// Add document
			{
				Document doc = new Document();
				doc.add(new TextField("title", "software engineer",
						Field.Store.YES));
				doc.add(new TextField("name", "jeef.dean", Field.Store.YES));
				doc.add(new IntField("salary", 25, Field.Store.YES));
				doc.add(new TextField("gender", "male", Field.Store.YES));
				writer.addDocument(doc);
				writer.commit();
			}


			// Get query
			BooleanQuery bq = new BooleanQuery();

			Query q1 = new QueryParser(Version.LUCENE_43, "title", analyzer)
					.parse("engineer");
			q1.setBoost(3.0f);
			bq.add(q1, Occur.MUST);


			Query q2 = new QueryParser(Version.LUCENE_43, "gender", analyzer)
					.parse("female");
			q2.setBoost(10000000);
			bq.add(q2, Occur.SHOULD);

			System.out.println("Query: " + bq);

			// Get sort
			SortField sf = new SortField("salary", SortField.Type.INT, true);
			Sort sort = new Sort(sf);


			// get reader
			IndexReader reader = DirectoryReader.open(writer, true);
			// search
			int hitsPerPage = 10;
			IndexSearcher searcher = new IndexSearcher(reader);
			searcher.setSimilarity(new DummySimiliarity());
			TopScoreDocCollector collector = TopScoreDocCollector.create(
					hitsPerPage, true);
			TopFieldDocs tfd = searcher.search(bq, 10, sort);

			ScoreDoc[] hits = tfd.scoreDocs;

			// display result
			boolean explain = true;
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
