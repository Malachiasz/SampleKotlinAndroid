package sample.de.samplekotlin

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory
import java.io.File
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by dkalinowski on 28.08.17.
 */
class RxJavaTest {

    private val log = LoggerFactory.getLogger(RxJavaTest::class.simpleName)

    @Before
    fun before() {

    }

    @Test
    fun completableFuture() {

        val cFuture = CompletableFuture.completedFuture("42")
        log.info(cFuture.get()!!)

        val futureInteger = cFuture.thenApply { it.length }
        log.info(futureInteger.get()!!.toString())

        val futureDouble = futureInteger.thenApply { it * 2.0 }
        log.info(futureDouble.get()!!.toString())
    }

    @Test
    fun completableFuture2() {
        val never = CompletableFuture<Any>();
    }

    fun print(obj: Any) {
        log.info("Got: {}", obj)
    }


    @Test
    fun observable1() {
        val obs = Observable.just("42")
        obs.subscribe(this::print)
    }

    @Test
    fun observable2() {
        val obs = Observable.just("42", "43", "44")
        obs.subscribe(this::print)
    }

    val client = WeatherClient()

    @Test
    fun clientTest() {
        print(client.fetch("Warsaw"))
    }

    @Test
    fun observable3() {
        val londonObs = Observable.fromCallable { client.fetch("London") }
        londonObs.subscribe(this::print)
    }

    @Test
    fun observable4() {
        val londonObs = client.rxFetch("London")
        londonObs.subscribe({ log.info(it.toString()) })
    }

    @Test
    fun observableWithDelay() {
        val warsawObservable = client.rxFetch("Warszawa")
        val warsawObservableDelayed = warsawObservable.timeout(800, TimeUnit.MICROSECONDS)
        warsawObservableDelayed.subscribe(this::print)
    }

    @Test
    fun observableParalell() {
        val warsawObservable = client.rxFetch("Warszawa")
        val radomObservable = client.rxFetch("Radom")

        warsawObservable.subscribe(this::print)

        val citiesWeahter = warsawObservable.mergeWith(radomObservable);

        citiesWeahter.subscribe(this::print)
    }

    val dao = PersonDao()

    @Test
    fun observableParallel2() {
        val lodz = client
                .rxFetch("Lodz")
                .subscribeOn(Schedulers.io())
        val person = dao
                .rxFindById(42)
                .subscribeOn(Schedulers.io())

        lodz.subscribe({this.print(it)})
        person.subscribe({this.print(it)})

        TimeUnit.SECONDS.sleep(2)
    }

    @Test
    fun observableParallel_ZipWith() {
        val city = client
                .rxFetch("Łódź")
                .subscribeOn(Schedulers.io())  //nie używaj io()
        val person = dao
                .rxFindById(42)
                .subscribeOn(Schedulers.io())

        val result = city.zipWith<Person, String>(person, BiFunction{ weather, aPerson -> weather.toString() + " : " + aPerson.toString() })

        result.subscribe(this::print)

        TimeUnit.SECONDS.sleep(2)
    }

    @Test
    fun observable6_map_repat_zip() {
        val strings = Observable
                .just("A", "B", "C")
                .repeat(2)
        val numbers = Observable
                .range(1, 10)
                .map({x -> x * 10})

        val s2 = Observable.zip(strings, numbers, BiFunction { s: String, n: Int -> s + n })

        s2.subscribe(this::print)
    }

    @Test
    fun observable_threadPoool() {
        val schedulers = Schedulers.from(Executors.newFixedThreadPool(10))
    }

    @Test
    fun observable7(){
        val eu = CacheServer()
        val us = CacheServer()

        val euObservable = eu.rxFindBy(42)
        val rusObservable = us.rxFindBy(43)

        val emptyObservable = Observable.empty<String>()

        Observable.merge(euObservable, rusObservable)
                .first("")
                .subscribe(this::print)

        TimeUnit.SECONDS.sleep(2)
    }

    @Test
    fun observable8(){
        Observable
                .interval(1, TimeUnit.SECONDS)
                .map { it * Math.PI }
                .subscribe(this::print)

        TimeUnit.SECONDS.sleep(5)
    }

    val dir = File("/Users/dkalinowski/Downloads/test")

    @Test
    fun observable9_files() {
        childrenOf(dir)
                .subscribe(this::print)
    }

    private fun childrenOf(dir: File): Observable<String> {
        val listFiles : Array<File> = dir.listFiles()
        val fromArray : Observable<File>? = Observable.fromArray(*listFiles);
        if (fromArray != null) {
            return fromArray
                    .map{it.name}
        }
        return Observable.empty()
    }

    @Test
    fun observable10_files() {
        Observable
                .interval(1, TimeUnit.SECONDS)
                .map { childrenOf2(dir) }
                .distinct()
                .blockingSubscribe(this::print)
    }

    private fun childrenOf2(dir: File): List<String> {
        return childrenOf(dir)
                .toList()
                .blockingGet()
    }

    @Test
    fun observable11() {
        Observable
                .just("1")
               // .empty<String>()
                .single("No Element")
                .subscribe(this::print)
    }

    @Test
    fun observable13_single() {
        Observable
                .interval(100, TimeUnit.MICROSECONDS)
                .map { it.toString() }
                .take(1)
                // .empty<String>()
                .single("No Elements")
                .subscribe(this::print)


        TimeUnit.SECONDS.sleep(100)
    }

    @Test
    fun observable14_files() {
        Observable
                .interval(1, TimeUnit.SECONDS)
                .flatMap { childrenOf(dir) }
                .distinct()
                .blockingSubscribe(this::print)
    }

    sealed class Result {
        data class Success(val value: Object): Result()
        data class Failure(val cause: Throwable): Result()
    }

    @Test
    fun observable15() {

        Observable.fromCallable {
            try {
                val value = doSomething()
                Result.Success(value)
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
    }

    private fun doSomething(): Object {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
