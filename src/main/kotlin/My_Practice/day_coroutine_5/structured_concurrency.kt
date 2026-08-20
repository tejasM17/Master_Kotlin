package My_Practice.day_coroutine_5

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Parent waits for all children
// If parent cancels, all children cancel
// If child fails, parent fails

fun main() = runBlocking {  // Parent scope
    launch {  // Child 1
        delay(1000)
        println("Mom-Dad: \uD83C\uDF46 \uD83C\uDF52 \uD83D\uDCA6 \uD83E\uDEC2")
        delay(2000)
        println("Child 1 done")
    }

    launch {  // Child 2
        delay(4000)
        println("Mom-Dad: \uD83C\uDF46 \uD83C\uDF52 \uD83D\uDCA6 \uD83E\uDEC2")
        delay(2000)
        println("Child 2 done")
    }

    println("Parent launched childrens..")
    // Parent waits here (implicit)
    // Won't end until both children complete
}

// Output:
// Parent launched children
// Child 1 done (after 1s)
// Child 2 done (after 2s)
// Program ends