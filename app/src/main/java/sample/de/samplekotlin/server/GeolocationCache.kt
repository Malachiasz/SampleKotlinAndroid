package sample.de.samplekotlin.server

import android.util.Log
import io.reactivex.Observable

/**
 * Created by dkalinowski on 13.09.17.
 */
class GeolocationCache {
    val memoryCache: MutableMap<String, Geolocation> = HashMap()

    fun getGeolocation(location: String): Observable<Geolocation> {
        Log.v("GeolocationCache", "called getGeolocation")
        if (memoryCache.containsKey(location)) {
            return Observable.fromCallable({ memoryCache[location] })
        } else {
            return Observable.never()
        }
    }
}