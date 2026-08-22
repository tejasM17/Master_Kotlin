package My_Practice.day_6_coroutine2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

data class Article(val id: Int, val title: String)

suspend fun fetchArticle(id: Int): Article = withContext(Dispatchers.IO) {
    delay(500)
    Article(id, "The Person Who Fu**** His Future #$id")
}

suspend fun fetchAllArticles(ids: List<Int>): List<Article> = coroutineScope {
    ids.map { id -> async { fetchArticle(id) } }
        .awaitAll()
}

suspend fun fetchWithTimeout(id: Int): Article? =
    withTimeoutOrNull(300) {
        fetchArticle(id)
    }

suspend fun safeFetch(id: Int): Result<Article> = try {
    Result.success(fetchArticle(id))
} catch (e: Exception) {
    Result.failure(e)
}

fun articleFeed(ids: List<Int>): Flow<Article> = flow {
    for (id in ids) {
        emit(fetchArticle(id))
    }
}

fun main() = runBlocking {
    println("📰 NEWS FEED LOADER — Coroutine Kingdom Final Boss\n")

    val start = System.currentTimeMillis()
    val articles = fetchAllArticles(listOf(1, 2, 3))
    val elapsed = System.currentTimeMillis() - start
    println("✅ Fetched ${articles.size} articles concurrently in ${elapsed}ms")
    articles.forEach { println("   - ${it.title}") }

    println("\n⏳ Testing timeout guard...")
    val guarded = fetchWithTimeout(99)
    println(if (guarded == null) "   ⏱️ Timed out as expected!" else "   Got: $guarded")

    println("\n🛡️ Testing safe fetch...")
    val safe = safeFetch(42)
    safe.onSuccess { println("   ✅ Success: ${it.title}") }
        .onFailure { println("   ❌ Failed: ${it.message}") }

    println("\n🌊 Streaming article feed (Flow)...")
    articleFeed(listOf(10, 11, 12))
        .collect { article -> println("   📨 Live: ${article.title}") }

    println("\n🏆 Kingdom conquered! You just used every coroutine concept together.")
}