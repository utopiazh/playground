package com.play.solr;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.util.RefCounted;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Joiner;
import com.play.lucene.HelloLucene;

public class HelloSolr {

	public static void main(String[] args) {
		
		Joiner joiner = Joiner.on('/');
		String config = "./test/solr/solrconfig.xml";
		String schema = "./test/solr/schema.xml";
		String indexDir = "./test/solr/index";
		String indexName = "test";
		
		try {
			prepareData(joiner.join(indexDir, indexName));
			
			InputSource configSource = new InputSource(new FileInputStream(config));
			SolrConfig solrConfig = new SolrConfig(indexDir, null, configSource);
			InputSource schemaSource = new InputSource(new FileInputStream(schema));
			IndexSchema solrSchema = new IndexSchema(solrConfig, null, schemaSource);
			
			SolrCore solr = new SolrCore(indexName, indexDir, solrConfig, solrSchema, null);
			
			RefCounted<SolrIndexSearcher> solrSearcher = solr.getSearcher();			
			SolrIndexSearcher searcher = solrSearcher.get();
			solrSearcher.incref();
			
			search(searcher, HelloLucene.getQuery(null), true);
			
			solrSearcher.decref();
			solr.closeSearcher();
			solr.close();
		} catch (ParserConfigurationException | IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	public static void prepareData(String path) throws IOException {
		IndexWriter writer = HelloLucene.getWriter(path);
		HelloLucene.addDoc1(writer);
		HelloLucene.addDoc2(writer);
	}
	
	public static void search(IndexSearcher searcher, Query q, boolean explain) throws IOException {
		// search
		int hitsPerPage = 10;

		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);		
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		// display results
		System.out.println("Hits: " + hits.length);
		for(int i = 0; i < hits.length; ++ i){
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i+1) + ". " + " title: "+  d.get("title") + ", name: " + d.get("name") + ", score: "+ hits[i].score);
			if(explain) {
				Explanation explanation = searcher.explain(q, docId);
				System.out.println(explanation);
			}
		}
	}
}
