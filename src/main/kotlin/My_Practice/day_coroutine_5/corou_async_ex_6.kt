package My_Practice.day_coroutine_5

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// Basic async/await
fun main() = runBlocking {
    val data: Deferred<String> = async {
        delay(2000)
        "Downloaded data"
    }

    println("Doing other work...")
    delay(500)

    val result = data.await()  // Wait for result
    println(result)
}

// Output:
// Doing other work...
// Downloaded data (after 2 seconds total)