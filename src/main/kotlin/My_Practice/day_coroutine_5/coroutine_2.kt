package My_Practice.day_coroutine_5

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun delayGreet() {
    println("Starting...")
    delay(2000)
    println("Hello, Broo!! after 2 seconds")
}

suspend fun fetchUserData(userId: Int): String {
    delay(1000)

    return "User #$userId"
}

suspend fun processUser(userId: Int) {
    println("Starting..!")
    val user = fetchUserData(userId)
    delay(500)
    println("User : $user")
}

fun main() = runBlocking {
    val  greeting = delayGreet()
    println(greeting)
    processUser(3)
}