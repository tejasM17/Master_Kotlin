package My_Practice.day_5_coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


/**
 * # Coroutines Concepts
 *
 * - **`suspend`**: This function can pause and resume without blocking.
 * - **`coroutineScope`**: This is where child coroutines live.
 * - **`async`**: Start work in background and return a future result.
 * - **`await()`**: Retrieve that result when it is ready.
 */


// SEQUENTIAL (Do one after another)
suspend fun sequentialFetch() = coroutineScope {
    val user = async { ftcUser() }.await()    // 2s
    val posts = async { ftcPost() }.await()  // 3s
    // Total time: 5 seconds ❌
}

// CONCURRENT (Do at the same time)
suspend fun concurrentFetch() = coroutineScope {
    val user = async { ftcUser() }    // Start both
    val posts = async { ftcPost() }  // At the same time

    val userData = user.await()         // Wait for both
    val postData = posts.await()
    // Total time: 3 seconds (max of two) ✅
}

suspend fun ftcUser(): String {
    delay(2000)
    return "Alice"
}

suspend fun ftcPost(): List<String> {
    delay(3000)
    return listOf("Post 1", "Post 2")
}

// Test it
fun main() = runBlocking {
    val start = System.currentTimeMillis()

    concurrentFetch()

    val elapsed = System.currentTimeMillis() - start
    println("Time: ${elapsed}ms")  // ~3000ms, not 5000ms!
}