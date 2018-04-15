package com.als.idxwriter;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

public class Writer {
	IndexWriter indexWriter;

	public Writer(StandardAnalyzer analyzer, Directory index) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		this.setIndexWriter(index, config);

	}

	public void closeIndexWriter() throws IOException {
		this.indexWriter.close();
	}

	private void addDoc(String title, String isbn, String description) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		// use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		doc.add(new TextField("description", description, Field.Store.YES));
		this.indexWriter.addDocument(doc);
	}

	public void indexDummyDocuments() throws IOException {
		addDoc("Lucene in Action", "193398817", "Learn lucene in less than 10 hours");
		addDoc("Lucene for Dummies", "55320055Z", "Don't be a Dummy in Lucene. Learn it.");
		addDoc("Managing Gigabytes", "55063554A", "Manage gigabytes of data, easily");
		addDoc("The Art of Computer Science", "9900333X", "Learn the Beautiful art of CSC and lucene too, in 50 hours");
		addDoc("Java in Action", "19322398817", "Learn Java in less than 10 days");
		addDoc("Python cookbook", "19322398817", "Python programming in quick time");
		addDoc("Ruby in Action", "203398817", "Get on the train of ruby");
		addDoc("Clean Code", "593398817", "Learn to write clean code");
		addDoc("NBA basketball basics", "563398817", "Learn basics of basketball");
		addDoc("Hadoop in Action", "123398817", "Setup a hadoop cluster with hdfs, a distributed approach");
		addDoc("HBase Basics", "2244556622", "Distributed hbase database on top of hadoop and hdfs");

	}

	public void setIndexWriter(Directory index, IndexWriterConfig config) throws IOException {
		this.indexWriter = new IndexWriter(index, config);

	}

	public IndexWriter getIndexWriter() {
		return indexWriter;
	}

}
