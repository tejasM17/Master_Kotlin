package My_Practice.day_5_coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// coroutineScope = Create a new scope that waits for children
// = Does NOT block the thread (suspends instead)
// = Safe to use in suspend functions

// launch: Fire and forget (child independent)
fun main() = runBlocking {
    launch {
        delay(1000)
        println("Launch done")
    }
    println("Main continues immediately")  // Doesn't wait
    delay(2000)  // Have to manually wait
}

// coroutineScope: Wait for children
suspend fun withScope() = coroutineScope {
    launch {
        delay(1000)
        println("Scope done")
    }
    println("Scope waiting...")  // Waits for children
}  // Function waits here for all children


//fun main() = runBlocking {
//    withScope()
//    println("Function returned (children completed)")  // Waits
//}