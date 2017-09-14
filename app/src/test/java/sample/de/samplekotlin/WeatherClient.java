package sample.de.samplekotlin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import io.reactivex.Observable;

/**
 * Created by dkalinowski on 28.08.17.
 */

public class WeatherClient {

    private static final Logger log = LoggerFactory.getLogger(WeatherClient.class.getName());

    public Weather fetch(String city) throws InterruptedException {
        log.info("Loading for {}", city);
        Thread.sleep(Duration.ofMillis(900).toMillis());
        //HTTP, HTTP, HTTP
        return new Weather();
    }

    public Observable<Weather> rxFetch(String city) {
        return Observable.fromCallable(() -> fetch(city));
    }

}
