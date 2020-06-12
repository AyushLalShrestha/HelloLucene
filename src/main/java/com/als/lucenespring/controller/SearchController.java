package com.als.lucenespring.controller;

import com.als.lucenespring.indexwriter.WriterProcess;
import com.als.lucenespring.searcher.LuceneSearcher;
import com.als.lucenespring.searcher.SearcherProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private SearcherProcess searcherProcess;

    @Autowired
    private WriterProcess writerProcess;

    @RequestMapping(value="/start", method= RequestMethod.GET)
    @ResponseStatus(value= HttpStatus.OK)
    public String startIndexing() {
        this.writerProcess.startWriterThreads();
        return "Indexing Started";
    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseStatus(value= HttpStatus.OK)
    public String searchIndexes() {
        List<String> results = this.searcherProcess.search("Lucene");
        return results.toString();
    }
}
