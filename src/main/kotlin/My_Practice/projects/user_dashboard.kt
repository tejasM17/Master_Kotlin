package My_Practice.projects

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

data class User(val id: Int, val name: String, val bio: String)
data class Post(val id: Int, val text: String)
data class Stats(val followers: Int, val following: Int, val posts: Int)

suspend fun fetchUser(): User {
    delay(500)
    return User(1, "Tejj", "Kotlin Developer")
}

suspend fun fetchPosts(): List<Post> {
    delay(1000)
    return listOf(Post(1, "First post"), Post(2, "Second post"))
}

suspend fun fetchFollowers(): List<User> {
    delay(800)
    return listOf(User(2, "Ram", "Wow is Wooww!!"), User(3, "Raju", "Peace.."))
}

suspend fun fetchStats(): Stats {
    delay(600)
    return Stats(followers = 150, following = 50, posts = 25)
}

suspend fun loadUserDashboard(): Pair<User, Triple<List<Post>, List<User>, Stats>> = coroutineScope {
    val userDeferred = async { fetchUser() }
    val postsDeferred = async { fetchPosts() }
    val followersDeferred = async { fetchFollowers() }
    val statsDeferred = async { fetchStats() }

    val user = userDeferred.await()
    val posts = postsDeferred.await()
    val followers = followersDeferred.await()
    val stats = statsDeferred.await()

    Pair(user, Triple(posts, followers, stats))
}

fun main() = runBlocking{
    val start = System.currentTimeMillis()

    val (user, data) = loadUserDashboard()
    val (posts, followers, stats) = data

    val elapsed = System.currentTimeMillis() - start

    println("╔════════════════════════════════╗")
    println("║   USER DASHBOARD              ║")
    println("╠════════════════════════════════╣")
    println("║ Name: ${user.name}                 ║")
    println("║ Bio: ${user.bio}                 ║")
    println("║ Posts: ${stats.posts}                    ║")
    println("║ Followers: ${stats.followers}                ║")
    println("║ Following: ${stats.following}                 ║")
    println("║                                ║")
    println("║ Recent Posts: ${posts.size}                    ║")
    println("║ Followers List: ${followers.size}                    ║")
    println("╚════════════════════════════════╝")

    println("\nLoaded in ${elapsed}ms (sequential would be 2900ms)")
}