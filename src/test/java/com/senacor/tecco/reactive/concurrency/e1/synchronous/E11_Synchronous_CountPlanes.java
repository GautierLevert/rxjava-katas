package com.senacor.tecco.reactive.concurrency.e1.synchronous;

import com.senacor.tecco.reactive.concurrency.PlaneArticleBaseTest;
import com.senacor.tecco.reactive.concurrency.Summary;
import org.junit.Test;

public class E11_Synchronous_CountPlanes extends PlaneArticleBaseTest {

    @Test
    public void thatPlaneBuildCountIsFetchedSynchronously() throws Exception {

        //get article on 777
        String article777 = fetchArticle("Boeing 777");

        //extract number of built planes
        String buildCount777 = parseBuildCount(article777);

        //get article on 747
        String article747 = fetchArticle("Boeing 747");

        //extract number of built planes
        String buildCount747 = parseBuildCount(article747);

        Summary.printCounter("777", buildCount777);
        Summary.printCounter("747", buildCount747);
    }

    // fetches an article from Wikipedia
    private String fetchArticle(String articleName) {
        return wikiService.fetchArticle(articleName);
    }
}
