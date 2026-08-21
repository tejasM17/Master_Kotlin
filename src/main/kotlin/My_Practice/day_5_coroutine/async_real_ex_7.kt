package My_Practice.day_5_coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

data class User(val id: Int, val name: String)
data class Post(val id: Int, val post: String)
data class Comment(val id: Int, val comment: String)

fun main()= runBlocking {
    val userId = 1
    // Start all requests at the same time
    val userDeferred = async { fetchUser(userId) }
    val postsDeferred = async { fetchPosts(userId) }
    val commentsDeferred = async { fetchComments(userId) }

    // Wait for all to complete
    val user = userDeferred.await()
    val posts = postsDeferred.await()
    val comments = commentsDeferred.await()

    println("User: $user")
    println("Posts: ${posts.size}")
    println("Comments: ${comments.size}")
}

suspend fun fetchUser(id: Int): User {
    delay(1000)
    return User(id, "Alice")
}

suspend fun fetchPosts(userId: Int): List<Post> {
    delay(2000)
    return listOf(Post(1, "My Day"), Post(2, "Kotlin"))
}

suspend fun fetchComments(userId: Int): List<Comment> {
    delay(1500)
    return listOf(Comment(1, "Nice!"), Comment(2, "Great!"))
}
// Total time: 2000ms (max of all three), not 4500ms!
