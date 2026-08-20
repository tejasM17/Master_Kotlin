package My_Practice.projects

import kotlinx.coroutines.*

data class WeatherData(
    val temp: Int,
    val humidity: Int,
    val condition: String
)

data class Forecast(
    val today: WeatherData,
    val tomorrow: WeatherData,
    val alert: String?
)

suspend fun fetchCurrentWeather(city: String): WeatherData {
    delay(1000)
    return WeatherData(
        temp = 20,
        humidity = 50,
        condition = "little Sunny"
    )
}

suspend fun fetchForecast(city: String): WeatherData {
    delay(1500)
    return WeatherData(
        temp = 22,
        humidity = 65,
        condition = "Cloudy"
    )
}

suspend fun fetchWeatherAlerts(city: String): String? {
    delay(500)
    return null
}

// async for concurrent requests
suspend fun getWeatherReport(city: String): Forecast = coroutineScope {
    val currentDeferred = async { fetchCurrentWeather(city) }
    val forecastDeferred = async {fetchForecast(city)}
    val alertDeferred = async {fetchWeatherAlerts(city)}

    val current = currentDeferred.await()
    val forecast = forecastDeferred.await()
    val alert = alertDeferred.await()

    Forecast(current, forecast, alert)
}


fun main() = runBlocking{
    val start = System.currentTimeMillis()
    println("Fetching weather for New York...")
    val forecast = getWeatherReport("New York")

    val elapsed = System.currentTimeMillis() - start
    println("\n╔═════════════════════════════╗")
    println("║   WEATHER REPORT - NYC      ║")
    println("╠═════════════════════════════╣")
    println("║ TODAY:                      ║")
    println("║   Temp: ${forecast.today.temp}°C              ║")
    println("║   Humidity: ${forecast.today.humidity}%          ║")
    println("║   Condition: ${forecast.today.condition}      ║")
    println("║                             ║")
    println("║ TOMORROW:                   ║")
    println("║   Temp: ${forecast.tomorrow.temp}°C              ║")
    println("║   Humidity: ${forecast.tomorrow.humidity}%          ║")
    println("║   Condition: ${forecast.tomorrow.condition}     ║")
    if (forecast.alert != null) {
        println("║                             ║")
        println("║ ⚠️  ALERT: ${forecast.alert}         ║")
    }
    println("╚═════════════════════════════╝")

    println("\nFetch time: ${elapsed}ms")
    println("(Without coroutines: ~3000ms)")
    println("(With coroutines: ~${elapsed}ms - FASTER!)")
}
