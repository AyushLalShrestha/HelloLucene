package com.als.lucenespring.searcher;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("SearcherProcess")
public class SearcherProcess {
    private LuceneSearcher luceneSearcher;
    private final String indexDirectory = "/Users/ayushshrestha/my_projects/lucene-spring/data/indexes";

    public SearcherProcess() {
        try {
            this.luceneSearcher = new LuceneSearcher(this.indexDirectory);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }

    public List<String> search(String stringQuery) {
        try {
            return this.luceneSearcher.getResults(stringQuery);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<String>();
        }

    }
}
