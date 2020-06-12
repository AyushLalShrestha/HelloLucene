package com.als.lucenespring.searcher;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Responsible for retrieving information from Lucene
 * @author ALS
 */
public class LuceneSearcher {
    IndexSearcher indexSearcher;
    StandardAnalyzer analyzer;

    public LuceneSearcher(String indexDirectoryPath) throws IOException {
        Directory directory = FSDirectory.open(Paths.get(indexDirectoryPath));
        IndexReader reader = DirectoryReader.open(directory);
        this.analyzer = new StandardAnalyzer();
        this.setIndexSearcher(reader);
    }

    public Query parseStringQuery(String stringQuery) throws ParseException {
        QueryParser parser = new QueryParser("Title", analyzer);
        return parser.parse(stringQuery);
    }

    public List<String> getResults(String stringQuery) throws IOException, ParseException {
        Query query = this.parseStringQuery(stringQuery);
        TopDocs results = this.indexSearcher.search(query, 5);
        System.out.println("Hits for " + stringQuery + " == " + results.totalHits);
        ScoreDoc[] hits = results.scoreDocs;
        return prepareStringArrayResults(hits);

    }

    private List<String> prepareStringArrayResults(ScoreDoc[] hits) throws IOException {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = this.indexSearcher.doc(docId);
            String stringResult = d.toString();
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


