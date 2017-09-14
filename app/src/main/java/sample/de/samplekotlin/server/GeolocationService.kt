package sample.de.samplekotlin.server

import android.util.Log
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by dkalinowski on 13.09.17.
 */
interface GeolocationApi {
    @GET("maps/api/geocode/json")
    fun getGeolocation(@Query("address") address: String, @Query("sensor") sensor: String = "false") : Observable<Geolocation>
}

class GeolocationService{

    val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    val geolocationApi = retrofitBuilder.create(GeolocationApi::class.java)

    fun getGeolocation(location: String): Observable<Geolocation> {
        Log.v("GeolocationService", "called getGeolocation")
        return geolocationApi.getGeolocation(location)
    }
}
