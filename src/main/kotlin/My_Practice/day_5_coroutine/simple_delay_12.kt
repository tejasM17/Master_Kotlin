package My_Practice.day_5_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    println("Start : ${System.currentTimeMillis()}")
    delay(2000)
    println("End: ${System.currentTimeMillis()}")
}
