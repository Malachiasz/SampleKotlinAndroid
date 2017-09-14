package sample.de.samplekotlin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dkalinowski on 28.08.17.
 */

public class CacheServer {

    private static final Logger log = LoggerFactory.getLogger(CacheServer.class.getName());

    public String findBy(long key) throws InterruptedException {
        log.info("Loading from Memcached: {}", key);
        Thread.sleep(100);
        return "<data>" + key + "</data>";
    }

    public Observable<String> rxFindBy(long key) {
        return Observable
                .fromCallable(() -> findBy(key))
                .subscribeOn(Schedulers.io());
    }
}
