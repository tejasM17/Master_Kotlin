package My_Practice.day_coroutine_5

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    try {
        val result = async {
            delay(1000)
            throw Exception("999+ Error..!")
        }
        result.await()
    } catch (e: Exception) {
        println("Caught: ${e.message}")
    }
}