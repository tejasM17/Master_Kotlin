package My_Practice.day_5_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val start = System.currentTimeMillis()

    launch { task(1, 1000) }
    launch { task(2, 1500) }
    launch { task(3, 2000) }
    val elapsed = System.currentTimeMillis() - start
    println("Total time: ${elapsed}ms")

}

suspend fun task(id: Int, delayMs: Long) {
    delay(delayMs)
    println("Task $id done")
}