package com.als.lucenespring.indexwriter;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class LuceneWriter {
    IndexWriter indexWriter;
    StandardAnalyzer standardAnalyzer;
    IndexWriterConfig config;
    Directory directory;

    public LuceneWriter(String outputDir) throws IOException {
        this.standardAnalyzer = new StandardAnalyzer();
        this.config = new IndexWriterConfig(standardAnalyzer);
        this.directory = FSDirectory.open(Paths.get(outputDir));
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        this.setIndexWriter(directory, config);
    }

    public void closeIndexWriter() {
        try {
            this.indexWriter.close();
        } catch (IOException ioe){
            // Do something for failed writer closing
        }

    }

    private void addDoc(Map<String, String> keyValuePair) throws IOException {
        Document doc = new Document();
        for(String key: keyValuePair.keySet()){
            doc.add(new TextField(key, keyValuePair.get(key), Field.Store.YES));
        }
        System.out.println("Writing Document" + doc.toString());
        this.indexWriter.addDocument(doc);
    }

    public void writeIndexFromSource(String inputFilePath) throws IOException {
        // try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String line, header;
            String[] headers = {};
            if ((header = br.readLine()) != null) {
                 headers = header.split(",");
            }
            while ((line = br.readLine()) != null) {
                // process the line and if valid, add it to the index
                String [] values = line.split(",");
                Map<String, String> keyValuePair = new HashMap<>();
                for(int i=0; i<headers.length; i++){
                    keyValuePair.put(headers[i], values[i]);
                }
                addDoc(keyValuePair);
            }
        }
    }

    public void setIndexWriter(Directory index, IndexWriterConfig config) throws IOException {
        this.indexWriter = new IndexWriter(index, config);

    }

    public IndexWriter getIndexWriter() {
        return indexWriter;
    }

}

