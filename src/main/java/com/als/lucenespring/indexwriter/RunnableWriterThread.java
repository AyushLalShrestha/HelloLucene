package com.als.lucenespring.indexwriter;

import java.io.IOException;

public class RunnableWriterThread implements Runnable {
    LuceneWriter luceneWriter;
    String inputFilePath;
    private final String outputDir = "/Users/ayushshrestha/my_projects/lucene-spring/data/indexes";

    public RunnableWriterThread(String inputFilePath) throws IOException {
        this.inputFilePath = inputFilePath;
        this.luceneWriter = new LuceneWriter(this.outputDir);
    }

    @Override
    public void run() {
        try {
            this.luceneWriter.writeIndexFromSource(this.inputFilePath);
        } catch (IOException ioe) {
            // Do Something with this exception like logging
            ioe.printStackTrace();
        } finally {
            this.luceneWriter.closeIndexWriter();
        }
    }
}
