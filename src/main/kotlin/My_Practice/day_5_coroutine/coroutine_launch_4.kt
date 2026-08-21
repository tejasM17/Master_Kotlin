package My_Practice.day_5_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// launch = "start a coroutine but don't wait for it"
// = Fire and forget
// = Build a house but don't wait for completion

fun main() = runBlocking {
    launch {  // Start coroutine
        delay(1000)
        println("Done!")  // This happens after 1 second
    }

    println("Main continues")  // This happens immediately
}

// Output:
// Main continues
// Done!