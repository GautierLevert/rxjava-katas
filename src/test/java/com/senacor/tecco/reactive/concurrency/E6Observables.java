package com.senacor.tecco.reactive.concurrency;

import com.senacor.tecco.reactive.ReactiveUtil;
import com.senacor.tecco.reactive.Watch;
import com.senacor.tecco.reactive.services.CountService;
import com.senacor.tecco.reactive.services.RatingService;
import com.senacor.tecco.reactive.services.WikiService;
import de.tudarmstadt.ukp.wikipedia.parser.ParsedPage;
import org.junit.Rule;
import org.junit.Test;

import rx.Observable;

public class E6Observables {

    private final WikiService wikiService = new WikiService("en");
    private final CountService countService = new CountService();
    private final RatingService ratingService = new RatingService();

    @Rule
    public final Watch watch = new Watch();

    @Test
    public void thatPlaneInfoIsCombinedWithObservables_notPerfectYet() throws Exception {


        fetchArticle("Boeing 777")
                .flatMap(article -> {
                    Observable<ParsedPage> parsedPageObservable = parsePage(article);
                    return parsedPageObservable.flatMap(page -> {
                        Observable<Integer> countObservable = countWords(page);
                        Observable<Integer> rateObservable = rateArticles(page);
                        String numberBuild = parseNumberBuilt(article);
                        return Observable.zip(countObservable, rateObservable, (words, rating) -> new PageMetrix(words, rating, numberBuild));
                    });
                })
                .subscribe(pageMetrix -> Summary.print("777", pageMetrix.words, pageMetrix.rating, pageMetrix.numberBuild));

        fetchArticle("Boeing 747")
                .flatMap(article -> {
                    Observable<ParsedPage> parsedPageObservable = parsePage(article);
                    return parsedPageObservable.flatMap(page -> {
                        Observable<Integer> countObservable = countWords(page);
                        Observable<Integer> rateObservable = rateArticles(page);
                        String numberBuild = parseNumberBuilt(article);
                        return Observable.zip(countObservable, rateObservable, (words, rating) -> new PageMetrix(words, rating, numberBuild));
                    });
                })
                .subscribe(pageMetrix -> Summary.print("747", pageMetrix.words, pageMetrix.rating, pageMetrix.numberBuild));
    }


    private Observable<String> fetchArticle(String articleName) {
        return wikiService.fetchArticle(articleName);
    }

    private Observable<ParsedPage> parsePage(String article777) {
        return wikiService.parseMediaWikiText(article777);
    }

    private Observable<Integer> countWords(ParsedPage parsedPage) {
        return countService.countWords(parsedPage);
    }

    private Observable<Integer> rateArticles(ParsedPage parsedPage) {
        return ratingService.rate(parsedPage);
    }

    private String parseNumberBuilt(String article) {
        return ReactiveUtil.findValue(article, "number built");
    }

    private final static class PageMetrix {
        private final int words;
        private final int rating;
        private final String numberBuild;

        private PageMetrix(int words, int rating, String numberBuild) {
            this.words = words;
            this.rating = rating;
            this.numberBuild = numberBuild;
        }

    }

}