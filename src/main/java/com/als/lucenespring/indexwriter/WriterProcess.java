package com.als.lucenespring.indexwriter;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Repository
public class WriterProcess {
    private final ExecutorService writerThreadPool;
    private final String[] inputFiles = {
            "/Users/ayushshrestha/my_projects/lucene-spring/data/input/books.txt"
    };

    public WriterProcess() {
        this.writerThreadPool = new ThreadPoolExecutor(
            1, 5, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(1000, false),
            new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void startWriterThreads() {
        for (String inputFilePath : this.inputFiles) {
            try {
                this.writerThreadPool.execute(new RunnableWriterThread(inputFilePath));
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }

        }
    }
}
