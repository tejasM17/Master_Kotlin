package My_Practice.day_coroutine_5

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    println("Start : ${System.currentTimeMillis()}")
    delay(2000)
    println("End: ${System.currentTimeMillis()}")
}
