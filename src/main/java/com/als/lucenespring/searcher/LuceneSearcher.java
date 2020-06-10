package com.als.lucenespring.searcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Responsible for retrieving information from Lucene
 * @author ALS
 */
@Repository("LuceneSearcher")
public class LuceneSearcher {
    public String name;

    /**
     * Method that starts the Lucene Service and sanity checks the index
     */
    public LuceneSearcher() {
        name = "Ayush Shr ";
    }

    /**
     * Search Lucene Index for records matching querystring
     * @param querystring - valid Lucene query string
     * @param numRecords - number of requested records
     * @param showAvailable - check for number of matching available records
     * @return Top Lucene query results as a Result object
     * @throws LuceneSearcherException
     * @throws InvalidLuceneQueryException
     */
    public String searchIndex(String querystring, int numRecords) {
       return name;
    }

}

