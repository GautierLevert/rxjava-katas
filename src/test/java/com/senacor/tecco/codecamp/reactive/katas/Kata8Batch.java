package com.senacor.tecco.codecamp.reactive.katas;

import com.senacor.tecco.codecamp.reactive.ReactiveUtil;
import com.senacor.tecco.codecamp.reactive.WaitMonitor;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.senacor.tecco.codecamp.reactive.services.WikiService.WIKI_SERVICE;

/**
 * @author Andreas Keefer
 */
public class Kata8Batch {

    @Test
    public void withoutBatch() throws Exception {

        final WaitMonitor waitMonitor = new WaitMonitor();
        // 1. Benutze den WikiService#wikiArticleBeingReadObservableBurst, der einen Stream von WikiArtikel Namen liefert, die gerade gelesen werden.
        // 2. beobachte den Stream 2 sekunden lang
        // 3. speichere die Artikel ab (WikiService.save(String)). Der service gibt die laufzeit zurück
        // 4. summiere die laufzeit der save calls und gib dies aus

        WIKI_SERVICE.wikiArticleBeingReadObservableBurst()
                .take(2, TimeUnit.SECONDS)
                .doOnNext(ReactiveUtil::print)
                .map(WIKI_SERVICE::save)
                .reduce(0L,(l,r) -> l + r)
                .subscribe(runtime -> System.out.println("RT: " + runtime), Throwable::printStackTrace, waitMonitor::complete);

        waitMonitor.waitFor(10, TimeUnit.SECONDS);

    }


    @Test
    public void batch() throws Exception {
        // 1. mache das gleiche wie oben, nur verwende diesmal die #save(Iterable) Methode um einene Batch von
        //    Artikeln zu speichern. Beachte dabei, das du hier potentiel einen Stream hast, du kannst also nicht warten
        //    bis der Stream alle artikel geliefert hat und dann alles in einem grossen batch abspeichern

        WIKI_SERVICE.wikiArticleBeingReadObservableBurst();
    }
}
