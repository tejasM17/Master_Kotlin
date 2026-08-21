package My_Practice.day_5_coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun fetchUsr(userId: Int): Usr {
    delay(1000)
    return Usr(userId, "Tej")
}

suspend fun fetchPsts(userId: Int): List<Pst> {
    delay(1000)
    return listOf(
        Pst(1, "Learning Kotlin"),
        Pst(2, "Learning Coroutines")
    )
}

suspend fun fetchFollowers(userId: Int): List<Usr> {
    delay(1000)
    return listOf(
        Usr(2, "Raju"),
        Usr(3, "Ramm")
    )
}

suspend fun loadUserProfile(userId: Int): UserProfile {
    return coroutineScope {
        val  userDeferred = async { fetchUsr(userId) }
        val postsDeferred = async {fetchPsts(userId)}
        val followersDeferred = async { fetchFollowers(userId) }

        //wait for all data
        val user = userDeferred.await()
        val posts = postsDeferred.await()
        val followers = followersDeferred.await()

        UserProfile(user, posts, followers )
    }
}

data class UserProfile(val user: Usr, val posts: List<Pst>, val followers: List<Usr>)
data class Usr(val id: Int, val name: String)
data class Pst(val id: Int, val title: String)


fun main() = runBlocking {
    val profile = loadUserProfile(1)
    println("Profile loaded: $profile")
}
