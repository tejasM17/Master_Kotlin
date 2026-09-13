# 📱 Day 14 — Android: Retrofit — Your First Real Network Call

**Date:** September 13, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 1-4)                    TODAY 🎯 (Week 5 starts)      NEXT ⏭️
Compose, state, nav, lifecycle,       Day 14: Retrofit —            Day 15: Error
permissions, WorkManager, Room        real REST API call            handling + auth
```

**Research done for today:** Verified `developer.android.com/codelabs/basic-android-kotlin-compose-getting-data-internet` (official "Get data from the internet" codelab, part of Android Basics with Compose Unit 5). ✅ CONFIRMED. 
- Notably confirmed the **current official choice** is `kotlinx.serialization` for JSON parsing — not the older Gson approach you'll see in a lot of tutorials online. Using the current official one.

**What changes today:** Days 12-13 gave you a local database. Today's tool talks to a server somewhere else entirely — the other half of almost every real app.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `Retrofit` | Turns a REST API into plain Kotlin function calls |
| 2 | `@Serializable` data class | Maps JSON fields straight into a Kotlin object |
| 3 | Build | Fetch a real list from a public API and show it |

---

## 1️⃣ What Is Retrofit?

**Definition (confirmed official):** Retrofit is *"a good example of a well-supported and maintained library"* used to talk to a RESTful web service — you describe the API as an **interface**, and Retrofit generates the actual networking code for you.

**The exact official pattern:**

```kotlin
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
```

**Read it like this:**

| Piece | Job |
|---|---|
| `interface ApiService` | You never write the implementation — Retrofit generates it |
| `@GET("posts")` | "This function calls the `/posts` endpoint with an HTTP GET" |
| `suspend fun getPosts()` | Confirmed official: *"Use Retrofit's support for coroutines"* — same `suspend` rules from Day 5, 13 |
| `: List<Post>` | The response JSON gets turned straight into a list of your own Kotlin objects |

**Direct callback:** you already know `suspend fun` from coroutines (Day 5) and Room (Day 13). Retrofit doesn't introduce a new async system — it plugs into the one you already know.

✅ **Concept 1 done when:** you can say why `ApiService` has no method body, just a signature.

---

## 2️⃣ @Serializable — Matching JSON to Kotlin

**Definition:** a data class marked `@Serializable` tells `kotlinx.serialization` exactly how to convert incoming JSON text into a real Kotlin object.

**Example — matching a real public API's JSON shape:**
```json
{ "id": 1, "title": "sample post" }
```
becomes:
```kotlin
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val title: String
)
```

**Rule:** property names must match the JSON field names (case-sensitive by default). That's it — no manual parsing code, same as Room's `@Entity` needing no manual SQL for basic operations.

✅ **Concept 2 done when:** you can match a data class's fields to a piece of raw JSON by eye.

---

## 3️⃣ Build: Fetch Real Data

We'll use **JSONPlaceholder** (`jsonplaceholder.typicode.com`), a free, standard public test API used across the industry for exactly this kind of practice.

### Step 1: Dependencies

```kotlin
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0")
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
```

### Step 2: Manifest permission

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Step 3: The pieces (`Post.kt`, `ApiService.kt`)

```kotlin
@Serializable
data class Post(val id: Int, val title: String)

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
```

### Step 4: Build the Retrofit instance

```kotlin
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private val retrofit = Retrofit.Builder()
    .baseUrl("https://jsonplaceholder.typicode.com/")
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

object PostApi {
    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
```

**`by lazy { }`:** creates `service` only the first time it's actually used, then reuses it — standard for something expensive to set up once.

### Step 5: The screen

```kotlin
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

@Composable
fun PostListScreen() {
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        posts = PostApi.service.getPosts()
    }

    LazyColumn {
        items(posts, key = { it.id }) { post ->
            Text(
                text = post.title,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

**`LaunchedEffect(Unit)`:** a new piece — runs the block **once**, when the composable first appears (`Unit` never changes, so it never re-runs). This is the standard Compose way to trigger a one-time suspend call, like a network fetch, when a screen opens.

**Call it:**
```kotlin
// In setContent { }
PostListScreen()
```

### Expected Result

Run it (device needs internet access). After a brief pause, a real scrollable list of ~100 post titles appears — actual data from a real server on the internet, not something you typed in.

---

## 🎯 Checkpoint

- [ ] Can explain why `ApiService` is just an interface with no body
- [ ] Can match a `@Serializable` data class's fields to raw JSON
- [ ] Built the full Retrofit setup (dependencies, permission, service, instance)
- [ ] `PostListScreen` runs and shows real data fetched from the internet

All 4 checked → **Day 14 done.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `Retrofit` | Interface → real HTTP calls, auto-generated |
| `@GET("posts")` | One annotation per endpoint |
| `suspend fun` in the interface | Reuses Day 5/13's coroutine rules, nothing new |
| `@Serializable` data class | JSON → Kotlin object, matched by field name |
| `LaunchedEffect(Unit)` | Run a suspend call once, when the screen appears |

---

## ⏭️ Day 15 Preview

1. Handling network failures (no internet, server errors) — `try/catch` around suspend calls
2. Loading / Success / Error UI states (a `sealed class` used for real, first time)
3. Build: the same screen, now resilient to a bad connection

Move **"Day 15"** when ready.

---

**No rush. No pressure. You just pulled real data from a real server on the internet into your app — the other half of nearly every professional app, now unlocked.** 🎉