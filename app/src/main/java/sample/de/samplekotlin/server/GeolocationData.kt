package sample.de.samplekotlin.server

/**
 * Created by dkalinowski on 13.09.17.
 */
data class Geolocation(
        val results: List<Result>,
        val status: String// OK
)

data class Result(
        val address_components: List<Address_component>,
        val formatted_address: String,// Oksford OX1 2JD, Wielka Brytania
        val geometry: Geometry,
        val place_id: String,// ChIJW0iM76nGdkgR7a8BoIMY_9I
        val types: List<String>
)

data class Address_component(
        val long_name: String,// Oksford
        val short_name: String,// Oksford
        val types: List<String>
)

data class Geometry(
        val location: Location,
        val location_type: String,// GEOMETRIC_CENTER
        val viewport: Viewport
)

data class Viewport(
        val northeast: Northeast,
        val southwest: Southwest
)

data class Southwest(
        val lat: Double,// 51.75346741970849
        val lng: Double// -1.255715780291502
)

data class Northeast(
        val lat: Double,// 51.75616538029149
        val lng: Double// -1.253017819708498
)

data class Location(
        val lat: Double,// 51.7548164
        val lng: Double// -1.2543668
)