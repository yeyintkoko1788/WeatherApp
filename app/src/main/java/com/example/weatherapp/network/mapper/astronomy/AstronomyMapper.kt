package com.example.weatherapp.network.mapper.astronomy

import com.example.weatherapp.domain.model.astronomy.AstronomyVO
import com.example.weatherapp.network.component.MyPreference
import com.example.weatherapp.network.mapper.UnidirectionalMap
import com.example.weatherapp.network.response.AstronomyResponse
import org.threeten.bp.Duration
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class AstronomyMapper @Inject constructor(
    private val sharedPreference : MyPreference
) : UnidirectionalMap<AstronomyResponse, AstronomyVO> {
    var df: DecimalFormat = DecimalFormat("#.###")
    override fun map(data: AstronomyResponse): AstronomyVO {
        return AstronomyVO(
            region = data.location.region,
            country = data.location.country,
            localTime = getDate(data.location.localtime),
            distance = df.format(haversine(sharedPreference.getLat().toDouble(),sharedPreference.getLon().toDouble(),data.location.lat,data.location.lon)) + " meters",
            sunrise = data.astronomy.astro.sunrise,
            sunset = data.astronomy.astro.sunset,
            moonrise = data.astronomy.astro.moonrise,
            moonset = data.astronomy.astro.moonset,
            timeDffSunsetAndMoonset = getTimeDifferent(data.astronomy.astro.sunset, data.astronomy.astro.moonset),
            timeDffSunriseAndMoonrise = getTimeDifferent(data.astronomy.astro.sunrise, data.astronomy.astro.moonrise)
        )
    }
}

fun getDate(date : String) : String {
    val convertFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" , Locale.ENGLISH)
    val sourceFormat = SimpleDateFormat("yyyy-MM-dd H:mm" , Locale.ENGLISH)

    return try {
        convertFormat.format(sourceFormat.parse(date)!!)
    } catch (e : Exception) {
        ""
    }
}

fun getTimeDifferent(time1 : String, time2 : String) : String {
    // Define the time format
    val timeFormat = DateTimeFormatter.ofPattern("hh:mm a")

    // Parse the time strings
    val time1 = LocalTime.parse(time1, timeFormat)
    val time2 = LocalTime.parse(time2, timeFormat)

    // Calculate the duration between the two times
    var duration = Duration.between(time1, time2)

    // Handle the case where time2 is on the next day
    if (duration.isNegative) {
        duration = duration.plusHours(24)
    }

    // Print the duration in a human-readable format
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    return "${hours} hours and ${minutes} minutes"
}

fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371e3 // Earth radius in meters

    val phi1 = Math.toRadians(lat1)
    val phi2 = Math.toRadians(lat2)
    val deltaPhi = Math.toRadians(lat2 - lat1)
    val deltaLambda = Math.toRadians(lon2 - lon1)

    val a = sin(deltaPhi / 2).pow(2) +
            cos(phi1) * cos(phi2) *
            sin(deltaLambda / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c // Distance in meters
}