package com.als.lucenespring.controller;

import com.als.lucenespring.searcher.LuceneSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private LuceneSearcher indexSearcher;

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseStatus(value= HttpStatus.OK)
    public String checkService() {
        return indexSearcher.searchIndex("ayush", 12) + "Lucene services are up and running.";
    }
}
