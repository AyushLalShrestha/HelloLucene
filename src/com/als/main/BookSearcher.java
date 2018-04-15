package com.als.main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import com.als.idxsearcher.Searcher;
import com.als.idxwriter.Writer;

public class BookSearcher {

	public static void main(String[] args) {
		try {
			StandardAnalyzer analyzer = new StandardAnalyzer();
			Directory index = new RAMDirectory();
			Writer writerClass = new Writer(analyzer, index);
			writerClass.indexDummyDocuments();
			writerClass.closeIndexWriter();

			Searcher searcherClass = new Searcher(index);

			String querystr;
			Scanner scanner = new Scanner(System.in);
			while (true) {
				querystr = scanner.nextLine();
				try {
					QueryParser parser = new QueryParser("description", analyzer);
					Query query = parser.parse(querystr);
					List<String> results = searcherClass.getResults(query);
					for (String result : results) {
						System.out.println(result);
					}
				} catch (ParseException pe) {
					System.out.println(pe.getMessage());
				}
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
