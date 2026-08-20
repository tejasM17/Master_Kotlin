package My_Practice.day_coroutine_5

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// runBlocking = "block the current thread until coroutine completes"
// = Bridge from regular code to suspend code
// = ONLY for main() and tests

fun main() {
    // This is a regular function, can't call suspend functions
    // So we use runBlocking

    runBlocking {  // Now we're in coroutine scope
        val result = fetchData()
        println(result)
    }

    println("Program ended")
}

suspend fun fetchData(): String {
    delay(1000)
    return "Data"
}