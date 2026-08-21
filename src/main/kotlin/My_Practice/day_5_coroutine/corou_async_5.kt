package My_Practice.day_5_coroutine

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// async = "start a coroutine and get a result"
// = Fire and wait
// = Build a house and wait for completion

fun main() = runBlocking {
    val result: Deferred<String> = async {
        delay(1500)
        "Result from coroutine!"
    }

    println("Waiting for result...")
    val value = result.await()  // Block until done
    println("Got: $value")
}

// Output:
// Waiting for result...
// Got: Result!