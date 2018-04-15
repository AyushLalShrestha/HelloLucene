package com.als.main;

import org.apache.lucene.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class HelloLuceneSecondary {

	public static void main(String[] args) {
		/*To use Lucene, an application should:
	    Create Documents by adding Fields;
	    Create an IndexWriter and add documents to it with addDocument();
	    Call QueryParser.parse() to build a query from a string; and
	    Create an IndexSearcher and pass the query to its search() method.
	    Some simple examples of code which does this are:
	     IndexFiles.java creates an index for all the files contained in a directory.
	     SearchFiles.java prompts for queries and searches an index.  */

	//Code Starts from here
	Analyzer analyzer = new StandardAnalyzer();

	// Store the index in memory:
	Directory directory = new RAMDirectory();
	// To store an index on disk, use this instead:
	//Directory directory = FSDirectory.open("/tmp/testindex");
	IndexWriterConfig config = new IndexWriterConfig(analyzer);
	IndexWriter iwriter = new IndexWriter(directory, config);
	Document doc = new Document();
	String text = "This is the text to be indexed.";
	doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
	iwriter.addDocument(doc);
	iwriter.close();

	// Now search the index:
	DirectoryReader ireader = DirectoryReader.open(directory);
	IndexSearcher isearcher = new IndexSearcher(ireader);
	// Parse a simple query that searches for "text":
	QueryParser parser = new QueryParser("fieldname", analyzer);
	Query query = parser.parse("text");
	ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
	assertEquals(1, hits.length);
	// Iterate through the results:
	for (int i = 0; i < hits.length; i++) {
	  Document hitDoc = isearcher.doc(hits[i].doc);
	  assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
	}
	ireader.close();
	directory.close();

	}

}
