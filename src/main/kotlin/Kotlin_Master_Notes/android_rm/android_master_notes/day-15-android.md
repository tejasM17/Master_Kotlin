# 📱 Day 15 — Android: Error Handling + UiState (First Real Sealed Interface)

**Date:** September 12, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅                                TODAY 🎯                      NEXT ⏭️
Day 14: Retrofit — happy path only     Day 15: What if the          Week 6: MVVM
(assumes internet always works)        internet is off?              (ViewModel)
```

**Research done for today:** Verified this pattern against Google's own **Now in Android** reference app source (officially referenced by developer.android.com, confirmed back on Day 1) plus cross-checked against multiple current sources, including one confirmed only 33 days old. The `sealed interface` + `data object Loading` pattern shown below matches what Google's own reference architecture actually uses. ✅ CONFIRMED (pattern-level, cross-referenced — not a single codelab URL this time, flagging that honestly).

**What changes today:** Day 14's `PostListScreen` assumed the network always works. Turn off WiFi and rerun it — it silently does nothing, or crashes. Today, it handles reality properly.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `try/catch` around suspend calls | Catching a failed network call instead of crashing |
| 2 | `sealed interface` UiState | One type that can only ever be Loading, Success, or Error — nothing else |
| 3 | Build | Day 14's screen, now resilient |

---

## 1️⃣ try/catch Around Suspend Calls

**The problem:**
```kotlin
// Day 14 — no internet? This throws, and nothing catches it. Crash.
posts = PostApi.service.getPosts()
```

**The fix — same `try/catch` from plain Kotlin, works identically around suspend calls:**
```kotlin
try {
    posts = PostApi.service.getPosts()
} catch (e: Exception) {
    // handle it — don't crash
}
```

**Nothing new here** — this is Kotlin Level 4/5 knowledge (control flow, functions), applied to a suspend function. Confirmed no special coroutine syntax needed — a suspend function throws exceptions exactly like a normal one.

✅ **Concept 1 done when:** you can explain why `try/catch` works the same for suspend functions as regular ones.

---

## 2️⃣ sealed interface — One State, No Impossible Combinations

**The bad way (confirmed common beginner mistake across every source checked):**
```kotlin
// ❌ Allows nonsense states: isLoading=true AND data filled AND error set, all at once
data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val posts: List<Post> = emptyList()
)
```

**The fix — a `sealed interface`:**
```kotlin
sealed interface PostsUiState {
    data object Loading : PostsUiState
    data class Success(val posts: List<Post>) : PostsUiState
    data class Error(val message: String) : PostsUiState
}
```

**Read it like this:**

| Piece | Job |
|---|---|
| `sealed interface` | "There are a FIXED set of possible states, listed right here — nothing else is allowed" |
| `data object Loading` | No extra data needed, just a marker — `data object` (not plain `object`) is the current recommended style |
| `data class Success(val posts: ...)` | Only exists WITH the actual data attached — you can't have "success" without posts |
| `data class Error(val message: ...)` | Only exists WITH an error message attached |

**Why this beats the boolean version:** it's now **impossible** to represent "loading AND has data AND has an error" at the same time — the type system itself blocks it. This connects directly to Kotlin Level 4's `when` — a `sealed interface` forces `when` to check every case:

```kotlin
when (uiState) {
    is PostsUiState.Loading -> CircularProgressIndicator()
    is PostsUiState.Success -> LazyColumn { /* show uiState.posts */ }
    is PostsUiState.Error -> Text("Error: ${uiState.message}")
}
```

No `else` branch needed — the compiler knows those 3 are the *only* possibilities and checks you handled all of them.

✅ **Concept 2 done when:** you can explain why the boolean version allows "impossible" states but the sealed interface doesn't.

---

## 3️⃣ Build: The Resilient Screen

```kotlin
sealed interface PostsUiState {
    data object Loading : PostsUiState
    data class Success(val posts: List<Post>) : PostsUiState
    data class Error(val message: String) : PostsUiState
}

@Composable
fun ResilientPostListScreen() {
    var uiState by remember { mutableStateOf<PostsUiState>(PostsUiState.Loading) }

    suspend fun loadPosts() {
        uiState = PostsUiState.Loading
        uiState = try {
            PostsUiState.Success(PostApi.service.getPosts())
        } catch (e: Exception) {
            PostsUiState.Error(e.message ?: "Something went wrong")
        }
    }

    LaunchedEffect(Unit) { loadPosts() }
    val scope = rememberCoroutineScope()

    when (val state = uiState) {
        is PostsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is PostsUiState.Success -> {
            LazyColumn {
                items(state.posts, key = { it.id }) { post ->
                    Text(text = post.title, modifier = Modifier.padding(16.dp))
                }
            }
        }
        is PostsUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "⚠️ ${state.message}")
                Button(onClick = { scope.launch { loadPosts() } }) {
                    Text("Retry")
                }
            }
        }
    }
}
```

**Call it:**
```kotlin
// In setContent { }
ResilientPostListScreen()
```

### 🧪 The Real Test

1. Run it with internet **on** → spinner briefly, then the real list (same as Day 14)
2. **Turn on Airplane Mode**, tap Retry → spinner, then a real error message + Retry button
3. Turn WiFi back **on**, tap Retry → recovers, shows the list

**That 3-step test is the entire point of today.** Day 14's version had exactly one working path. Today's has three, and none of them crash the app.

---

## 🎯 Checkpoint

- [ ] Can explain why `try/catch` needs no special syntax for suspend functions
- [ ] Can explain why boolean flags allow impossible states but `sealed interface` doesn't
- [ ] Built `PostsUiState` with all 3 cases
- [ ] Ran the 3-step airplane-mode test successfully, including Retry

All 4 checked → **Day 15 done. Week 5 complete.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `try/catch` on suspend calls | Works exactly like normal Kotlin — no special coroutine syntax |
| `sealed interface` | Fixed set of possible states, no illegal combinations |
| `data object` | Modern style for a state with no extra data |
| `when` on a sealed type | Compiler-enforced — you can't forget a case |
| Loading/Success/Error pattern | The real, industry-standard shape for any network screen |

---

## ⏭️ Day 16 Preview — Week 6 Begins

1. `ViewModel` — moving this exact logic out of the composable, properly
2. Why UI state shouldn't live directly inside a composable for real apps
3. Build: refactor today's screen into a real MVVM shape

Move **"Day 16"** when ready.
 
---

**No rush. No pressure. You just wrote code that survives contact with a bad internet connection — which is most of what separates a demo from a real app.** 🎉