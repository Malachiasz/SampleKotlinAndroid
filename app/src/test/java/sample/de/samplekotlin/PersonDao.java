package sample.de.samplekotlin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import io.reactivex.Observable;

/**
 * Created by dkalinowski on 28.08.17.
 */

public class PersonDao {

    private static final Logger log = LoggerFactory.getLogger(PersonDao.class.getName());

    public Person findById(int id) throws InterruptedException {
        //SQL, SQL, SQL
        log.info("Loading {}", id);
        Thread.sleep(Duration.ofMillis(1000).toMillis());
        return new Person();
    }

    public Observable<Person> rxFindById(int id) {
//		return Observable.just(findById(id));
        return Observable.fromCallable(() ->
                findById(id)
        );
    }
}
