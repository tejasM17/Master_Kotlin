package My_Practice.projects

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun apiCall1(): String {
    delay(1000)
    return "API 1 result"
}

suspend fun apiCall2(): String {
    delay(1500)
    return "API 2 result"
}

suspend fun apiCall3(): String {
    delay(2000)
    return "API 3 result"
}

suspend fun fetchAllConcurrently(): Triple<String, String, String> = coroutineScope {
    val start = System.currentTimeMillis()

    val result1 = async {
        apiCall1().also { println("API 1 done") }
    }
    val result2 = async {
        apiCall2().also { println("API 2 done") }
    }
    val result3 = async {
        apiCall3().also { println("API 3 done") }
    }

    val r1 = result1.await()
    val r2 = result2.await()
    val r3 = result3.await()

    val elapsed = System.currentTimeMillis() - start
    println("Total concurrent time: ${elapsed}ms")
    println("Sequential would take: 4500ms")
    println("Time saved: ${4500 - elapsed}ms")

    Triple(r1, r2, r3)
}

fun main() = runBlocking {
    val (r1, r2, r3) = fetchAllConcurrently()
    println("\nResults:")
    println("1: $r1")
    println("2: $r2")
    println("3: $r3")
}