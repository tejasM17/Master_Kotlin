package My_Practice.day_coroutine_5

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
//    task 1: Download User data
    launch {
        val user = fetchUser()
        println("Downloaded: $user")
    }

    launch {
        val posts= fetchPosts()
        println("Downloaded Posts: ${posts.size}")
    }
    println("Both downloads started!")
    delay(3000)
    println("Both downloads finished..!")
}

suspend fun fetchUser(): String {
    delay(1000)
    return "Tej M"
}

suspend fun fetchPosts(): List<String> {
    delay(2000)
    return listOf("Post 1..", "Post 2.", "Post 3")
}