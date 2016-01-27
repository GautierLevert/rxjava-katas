package com.senacor.tecco.reactive.concurrency.e2.callback;

import com.senacor.tecco.reactive.concurrency.PlaneArticleBaseTest;
import com.senacor.tecco.reactive.concurrency.Summary;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Retrieves and combines plane information with callbacks
 *
 * @author Dr. Michael Menzel, Sencaor Technologies AG
 */
public class E22_Callback_SumPlanes extends PlaneArticleBaseTest{

    // error handler function
    Consumer<Exception> exceptionConsumer = (e)->{e.printStackTrace();};

    @Test
    public void thatPlaneBuildCountIsSummedUpWithCallback() throws Exception {
        LinkedBlockingQueue<String[]> planeBuildCountSum = new LinkedBlockingQueue<>();

        fetchArticle("Boeing 777", (article777) -> {
            fetchArticle("Boeing 747", (article747) -> {

                //parse build numbers and add results to planeBuildCounts
                try {
                    planeBuildCountSum.put(new String[]{"777 and 747", Integer.toString(parseBuildCountInt(article777) + parseBuildCountInt(article747))});
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, exceptionConsumer);
        }, exceptionConsumer);

        Summary.printCounter(planeBuildCountSum.poll(5, TimeUnit.SECONDS));
    }

    // fetches an article from Wikipedia
    void fetchArticle(String articleName, Consumer<String> articleConsumer, Consumer<Exception> exceptionConsumer) {
        wikiService.fetchArticleCallback(articleName, articleConsumer, exceptionConsumer);
    }


}
