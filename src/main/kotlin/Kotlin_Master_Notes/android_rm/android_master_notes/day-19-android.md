# 📱 Day 19 — Android: Capstone — Detail Screen + Navigation

**Date:** October 2, 2026

> 💬 *"You don't have to be great to start, but you have to start to be great."* — Zig Ziglar

---

## 🗺️ THE BIG PICTURE

```
DONE ✅                                  TODAY 🎯                        NEXT ⏭️
Day 18: Capstone scoped + reorganized    Day 19: Reconnect Day 8's      Day 20: Favorite
into data/di/ui/worker folders           navigation — real detail       toggle (Room write)
                                          screen, real data              beyond insert-only
```

**Research done for today:** Verified against the official "Navigate between screens with Compose" codelab + official "Jetpack Compose Navigation" (Rally) codelab, confirmed genuinely current (checked at 179 days old). Both confirm the exact `route/{arg}` + `navArgument` + `backStackEntry` pattern from Day 8 is still the correct, current approach. ✅ CONFIRMED

**What changes today:** the list screen just lists. Today, tapping a quote opens a real detail screen — reusing Day 8's exact navigation shape, but now wired through Hilt (Day 17) and a real Room query (Day 13).

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | Route with an ID, not a whole object | Pass just the `quoteId`, re-fetch the real data on the other side |
| 2 | `SavedStateHandle` | How a Hilt ViewModel reads a navigation argument |
| 3 | Build | Real `QuoteDetailScreen`, wired into `NavHost` |

---

## 1️⃣ Pass the ID, Not the Object

**Day 8 passed a whole `String` task name through the route.** For real apps with a database, the better pattern is passing just the **ID**, then letting the destination screen look up the real, current data itself.

```kotlin
composable(
    route = "detail/{quoteId}",
    arguments = listOf(navArgument("quoteId") { type = NavType.IntType })
) {
    QuoteDetailScreen()
}
```

**Why ID, not the whole `Quote` object?** Two reasons, both real:
- Routes are just strings — passing a whole object gets awkward fast
- The detail screen re-queries Room by ID, so if the quote is ever edited elsewhere, the detail screen always shows the current version — not a stale copy carried through navigation

✅ **Concept 1 done when:** you can say why passing an ID is better than passing the whole `Quote`.

---

## 2️⃣ SavedStateHandle — Reading the Argument, the Hilt Way

**Definition:** a Hilt `ViewModel` can have Navigation's arguments injected automatically via `SavedStateHandle` — no manual passing needed.

```kotlin
@HiltViewModel
class QuoteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: QuoteRepository
) : ViewModel() {

    private val quoteId: Int = checkNotNull(savedStateHandle["quoteId"])

    val quote: StateFlow<Quote?> = repository.getQuoteById(quoteId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
```

**Read it like this:**

| Piece | Job |
|---|---|
| `savedStateHandle: SavedStateHandle` | Hilt auto-injects the current route's arguments here |
| `savedStateHandle["quoteId"]` | Reads the `{quoteId}` from the route — matched by name |
| `checkNotNull(...)` | Fails loudly if it's somehow missing, instead of silently continuing with bad data |

**Direct callback:** everything after that line is Day 16-17's exact `StateFlow` pattern, unchanged. Only *how the ViewModel learns which quote to load* is new.

✅ **Concept 2 done when:** you can explain how `"quoteId"` in the route string connects to `savedStateHandle["quoteId"]`.

---

## 3️⃣ Build: The Real Detail Screen

### Step 1 — New Room query (`QuoteDao.kt`)

```kotlin
@Query("SELECT * FROM quotes WHERE id = :quoteId")
fun getQuoteById(quoteId: Int): Flow<Quote?>
```

### Step 2 — Repository, one new line

```kotlin
fun getQuoteById(id: Int): Flow<Quote?> = dao.getQuoteById(id)
```

### Step 3 — QuoteDetailScreen (`ui/detail/QuoteDetailScreen.kt`)

```kotlin
@Composable
fun QuoteDetailScreen(viewModel: QuoteDetailViewModel = hiltViewModel()) {
    val quote by viewModel.quote.collectAsState()

    Column(modifier = Modifier.padding(24.dp)) {
        quote?.let {
            Text(text = "\"${it.quote}\"", style = MaterialTheme.typography.headlineSmall)
            Text(text = "— ${it.author}", modifier = Modifier.padding(top = 8.dp))
        } ?: Text("Loading...")
    }
}
```

### Step 4 — Make list rows clickable, reusing Day 8's exact pattern

```kotlin
@Composable
fun QuoteListScreen(
    viewModel: QuoteListViewModel = hiltViewModel(),
    onQuoteClick: (Int) -> Unit
) {
    val quotes by viewModel.quotes.collectAsState()

    LazyColumn {
        items(quotes, key = { it.id }) { quote ->
            Text(
                text = quote.quote,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onQuoteClick(quote.id) }
            )
        }
    }
}
```

### Step 5 — Wire NavHost (`MainActivity.kt`)

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "list") {
                composable("list") {
                    QuoteListScreen(
                        onQuoteClick = { id -> navController.navigate("detail/$id") }
                    )
                }
                composable(
                    route = "detail/{quoteId}",
                    arguments = listOf(navArgument("quoteId") { type = NavType.IntType })
                ) {
                    QuoteDetailScreen()
                }
            }
        }
    }
}
```

**Notice:** exactly matching Day 8's rule — `QuoteListScreen` receives a plain `onQuoteClick` lambda, never `navController` itself. `QuoteDetailScreen()` doesn't even need any navigation parameters — `hiltViewModel()` + `SavedStateHandle` handle the ID automatically.

---

## 🧪 The Real Test

1. Run the app → list of quotes appears (Day 16-18 work)
2. Tap any quote → detail screen opens, shows that **exact** quote + author, full-size
3. Press system back → returns to the list, scroll position preserved
4. Tap a *different* quote → detail screen updates to the new one (proving the ID-based re-query, not a stale cached object)

---

## 🎯 Checkpoint

- [ ] Can explain why passing `quoteId` beats passing the whole `Quote` object
- [ ] Can trace how `"quoteId"` in the route connects to `savedStateHandle["quoteId"]`
- [ ] Built `QuoteDetailScreen` + `QuoteDetailViewModel`, wired into `NavHost`
- [ ] Tapped 2 different quotes, confirmed the detail screen shows the correct one each time

All 4 checked → **Day 19 done.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| ID through the route, not the object | Detail screen always shows current data, not a stale copy |
| `SavedStateHandle` | Hilt ViewModel's way of reading nav arguments automatically |
| `getQuoteById(id): Flow<Quote?>` | One new Room query, same patterns as Day 13 |
| `onQuoteClick: (Int) -> Unit` | Same hoisting rule from Day 8, still holding at capstone scale |

---

## ⏭️ Day 20 Preview

1. A Room `@Update` query — writing changes to an existing row, not just insert/delete
2. A `isFavorite: Boolean` field added to `Quote`
3. Build: a ❤️ button on the detail screen that toggles and persists

Say **"Day 20"** when ready.

---

**No rush. No pressure. Two screens, real data, real navigation — the capstone just became an actual app, not a list of features on paper.** 🎉