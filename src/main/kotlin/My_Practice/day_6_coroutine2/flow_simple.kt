package My_Practice.day_6_coroutine2

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*

fun simpleFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking {
    simpleFlow().collect { value ->
        println("Got: $value")
    }
}