package My_Practice.day_coroutine_5

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun regular() {
    println("it is reggular")
}

suspend fun suspendFun(){
    println("can suspned")
    delay(3000)
    println("Resumed after 3 second")
}

fun main() = runBlocking {
    regular()
    suspendFun()
}