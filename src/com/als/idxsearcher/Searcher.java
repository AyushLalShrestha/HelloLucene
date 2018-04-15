package com.als.idxsearcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;

public class Searcher {
	IndexSearcher indexSearcher;

	public Searcher(Directory index) throws IOException {
		IndexReader reader = DirectoryReader.open(index);
		this.setIndexSearcher(reader);
	}

	public List<String> getResults(Query query) throws IOException {
		TopScoreDocCollector collector = TopScoreDocCollector.create(10);
		this.indexSearcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		System.out.println("Found " + hits.length + " hits." + "\n");
		return prepareStringArrayResults(hits);

	}

	private List<String> prepareStringArrayResults(ScoreDoc[] hits) throws IOException {
		List<String> results = new ArrayList<>();
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = this.indexSearcher.doc(docId);
			String stringResult = d.get("isbn") + "\t" + d.get("title") + "\t" + d.get("description");
			results.add(stringResult);
		}
		return results;
	}

	public void setIndexSearcher(IndexReader reader) {
		this.indexSearcher = new IndexSearcher(reader);
	}

	public IndexSearcher getIndexSearcher() {
		return indexSearcher;
	}

}
