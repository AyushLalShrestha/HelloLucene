package com.als.main;


import java.io.IOException;
import java.text.ParseException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class HelloLucene {

	public static void main(String[] args) throws IOException, ParseException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory index = new RAMDirectory();

		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter w = new IndexWriter(index, config);
		addDoc(w, "Lucene in Action", "193398817");
		addDoc(w, "Lucene for Dummies", "55320055Z");
		addDoc(w, "Managing Gigabytes", "55063554A");
		addDoc(w, "The Art of Computer Science", "9900333X");
		w.close();
		String querystr = args.length > 0 ? args[0] : "lucene";

		Query qry = null;
		try {
			// QueryParser parser = new QueryParser("field", new StandardAnalyzer());
			QueryParser parser = new QueryParser("title", analyzer);	
			// qry = new TermQuery(new Term("Lucene in Action"));
			qry = parser.parse(querystr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(10);
		searcher.search(qry, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
		}

		// reader can only be closed when there is no need to access the documents any more.
		reader.close();
	}

	private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));

		// use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		w.addDocument(doc);
	}
}
