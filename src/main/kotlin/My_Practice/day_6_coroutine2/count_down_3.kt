package My_Practice.day_6_coroutine2

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

suspend fun fetchWithGuard(): String {
    return withTimeoutOrNull(2000) {
        delay(3000)
        "Data arrived"
    } ?: "Gave up — took too long"
}

fun main() = runBlocking {
    println(fetchWithGuard())
}
