package com.play.solr.externalfilefield;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrCore;
import org.apache.solr.request.SolrRequestHandler;
import org.apache.solr.schema.IndexSchema;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Joiner;
import com.play.lucene.HelloLucene;

public class EffTest {
	public static void main(String[] args) {

		Joiner joiner = Joiner.on('/');
		String config = "./test/solr/solrconfig.xml";
		String schema = "./test/solr/schema-eff.xml";
		String indexDir = "./test/solr/index";
		String indexName = "test";
		String path = joiner.join(indexDir, indexName);
		try {
			IndexWriter writer = HelloLucene.getWriter(path);
			HelloLucene.addDoc1(writer);
			HelloLucene.addDoc2(writer);

			writer.close();
			// IndexReader reader = DirectoryReader.open(writer, true);

			InputSource configSource = new InputSource(new FileInputStream(
					config));
			SolrConfig solrConfig = new SolrConfig(indexDir, null, configSource);
			InputSource schemaSource = new InputSource(new FileInputStream(
					schema));
			IndexSchema solrSchema = new IndexSchema(solrConfig, null,
					schemaSource);

			// SchemaField sf = solrSchema.getField("popularity");
			// System.out.println(sf);
			// FileFloatSource ffs = ((ExternalFileField)
			// sf.getType()).getFileFloatSource(sf, path);
			// System.out.println(ffs);

			// Map context = ffs.newContext(new IndexSearcher(reader));
			// FunctionValues fvs = ffs.getValues(context, (AtomicReaderContext)
			// reader.getContext());
			// Float f = fvs.floatVal(1);
			// System.out.println(f);

			SolrCore solr = new SolrCore(indexName, indexDir, solrConfig,
					solrSchema, null);
			
			Map<String, SolrRequestHandler> handlers = solr.getRequestHandlers();
			
			
			
			//solr.execute(handler, req, rsp);
			// Directory dir = solr.getDirectoryFactory().get(path, null, null);
			//
			// Query q = HelloLucene.getQuery(null);
			//
			//
			// // IndexReader reader = DirectoryReader.open(dir);
			// // HelloLucene.search(reader, q, true);
			// // reader.close();
			//
			// RefCounted<SolrIndexSearcher> solrSearcher = solr.getSearcher();
			// IndexSearcher searcher = solrSearcher.get();
			// solrSearcher.incref();
			//
			// Sort sort = new Sort();
			// // SchemaField sf = solrSchema.getField("popularity");
			// SortField sortField = sf.getSortField(true);
			// sortField.rewrite(searcher);
			// sort.setSort(sortField);
			//
			// search(searcher, q, true, sort);
			//
			// solrSearcher.decref();
			// solr.closeSearcher();
			solr.close();

		} catch (ParserConfigurationException | IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public static void search(IndexSearcher searcher, Query q, boolean explain,
			Sort sort) throws IOException {
		// search
		int hitsPerPage = 10;

		// TopScoreDocCollector collector = TopScoreDocCollector.create(
		// hitsPerPage, true);
		// searcher.search(q, collector);
		// ScoreDoc[] hits = collector.topDocs().scoreDocs;
		sort.rewrite(searcher);
		TopFieldDocs tfds = searcher.search(q, hitsPerPage, sort);
		HelloLucene.displayResults(q, tfds.scoreDocs, searcher, explain);

	}
}
