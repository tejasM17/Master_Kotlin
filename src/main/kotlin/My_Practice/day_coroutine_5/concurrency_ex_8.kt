package My_Practice.day_coroutine_5

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    try {
        launch {
            repeat(3) {
                println("Child: $it")
                delay(100)
            }
        }

        delay(250)
        throw Exception("Parent error!")
    } catch (e: Exception) {
        println("Caught: ${e.message}")
    }
}

// Output:
// Child: 0
// Child: 1
// Caught: Parent error!
// Child 2 never runs (canceled automatically)