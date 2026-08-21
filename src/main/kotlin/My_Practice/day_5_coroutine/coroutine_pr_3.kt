package My_Practice.day_5_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun simulateNetworkCall(delayMs: Long ): String {
    delay(delayMs)
    return "Network Response!!"
}

suspend fun processData() {
    delay(1000)
    println("Step 1 Done..")
    delay(500)
    println("Setp 2 done..")
    delay(500)
    println("Step 3  Done..")
    delay(500)
    println("Step 3  Done..")
    delay(500)
    println("Step 3  Done..")
    delay(500)
    println("Step 3  Done..")
    delay(500)
    println("Step 3  Done..")

}

fun main() = runBlocking{
    val result = simulateNetworkCall(2000)
    println(result)
    processData()
}