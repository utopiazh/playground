package com.play.lucene.terms;

import java.io.IOException;

import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

import com.play.lucene.HelloLucene;

public class TestTermsEnum {

	public static void main(String[] args) {
		try {
			String path = "./test/lucene/terms";
			
			IndexWriter writer = HelloLucene.getWriter(path);
			
			System.out.println("# Add doc 1");
			HelloLucene.addDoc1(writer);					
			writer.commit();
			//writer.close();
						
			// get reader
			IndexReader reader = DirectoryReader.open(writer, true);
			//IndexReader reader = DirectoryReader.open(index);

			listFields(reader);
			
			System.out.println("# Add doc 2");
			HelloLucene.addDoc2(writer);
			// get new reader
			reader = DirectoryReader.openIfChanged((DirectoryReader) reader, writer, true);
			
			listFields(reader);
			
			reader.close();	
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void listFields(IndexReader reader) throws IOException{
		IndexReaderContext context = reader.getContext();
		Bits acceptDoc = new Bits.MatchAllBits(2);
		for(AtomicReaderContext arc : context.leaves()){
			AtomicReader ar = arc.reader();
			System.out.println("Reader: " + ar);
			System.out.println("\tFiledInfo:");
			FieldInfos infos = ar.getFieldInfos();
			Fields fields = ar.fields();
			for(int i = 0; i < infos.size(); ++i) {
				FieldInfo info = infos.fieldInfo(i);
				System.out.println("\t\tFiled:" + info.name);				
				Terms terms = fields.terms(info.name);
				if(terms != null) {
					TermsEnum te = terms.iterator(null);					
					te.next();
					do{	
						BytesRef br = te.term();						
						System.out.println("\t\tTerm: " + new String(br.bytes));
						
						DocsEnum de = te.docs(acceptDoc, null);	
						
						de.nextDoc();
						do {							
							System.out.println("\t\t\tDocsEnum, " + "docId: "+  de.docID() + ", freq: " + de.freq() + ", cost: " + de.cost());
						} while(de.nextDoc() != de.NO_MORE_DOCS);
						
						DocsAndPositionsEnum postings = te.docsAndPositions(acceptDoc, null);
						postings.nextDoc();
						do{							
							System.out.println("\t\t\tDocsAndPositionsEnum, " + "docId: "+  postings.docID() + ", freq: " + postings.freq() + ", cost: " + postings.cost());
							for(int j = 0; j < postings.freq(); ++j) {
								int pos = postings.nextPosition();
								System.out.println("\t\t\t\tpos:" + pos);
							}
						} while(postings.nextDoc() != postings.NO_MORE_DOCS);
						
					} while(te.next() != null);
				}
			}
			
			
			
			
		}
	}
}
