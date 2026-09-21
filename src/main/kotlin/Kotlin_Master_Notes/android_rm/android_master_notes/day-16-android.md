# 📱 Day 16 — Android: MVVM, From Scratch (New Project: Quote Keeper)

**Date:** September 20, 2026

> 💬 *"The secret of getting ahead is getting started."* — Mark Twain

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 1-5)                         TODAY 🎯 (Week 6 starts)         NEXT ⏭️
Compose, state, nav, lifecycle,            Day 16: Brand new project —      Day 17+:
permissions, WorkManager, Room, Retrofit   full MVVM, REST + DB, from 0     Dependency Injection (Hilt)
```

**Research done for today:** Verified `developer.android.com/topic/architecture` 
+ `developer.android.com/topic/architecture/recommendations` (both official). Direct confirmed quote: *"Strongly recommended: Expose application data from the data layer using a repository... components in the UI layer such as composables or ViewModels shouldn't interact directly with a data source."* Also confirmed: *"Use coroutines and flows to communicate between layers."* ✅ CONFIRMED — everything below follows this exactly. API response shape also confirmed live against the real `dummyjson.com/docs/quotes` documentation page.

**Today's project:** **Quote Keeper** — fetches motivational quotes from a real public API (`dummyjson.com/quotes`), saves them to Room, shows cached quotes instantly even offline. Fitting, given the quote habit we started on Day 15.

**One brand new project, everything in one place, as requested** — no isolated toy example this time.

---

## 🧠 What MVVM Actually Is (Simple, No Fluff)

| Letter | Name | In Quote Keeper, today |
|---|---|---|
| **M** | Model (data) | `Quote` entity + `QuoteDao` (Room) + `QuoteApi` (Retrofit), tied together by a `QuoteRepository` |
| **V** | View | Your Compose screen — dumb, just displays whatever state it's given |
| **VM** | ViewModel | Holds state, talks to the Repository, survives screen rotation |

**One-sentence rule (confirmed official, direct quote above):** *the View and ViewModel never touch Room or Retrofit directly — only the Repository does.*

```
View (Compose)  →  ViewModel (state + logic)  →  Repository  ┬─ Room (local, cached)
                                                                └─ Retrofit (remote, fresh)
```

---

## 🎯 Today = 3 Concepts, Applied Across One Real Project

| # | Concept | One-line definition |
|---|---|---|
| 1 | `ViewModel` | A state holder that survives configuration changes |
| 2 | `Repository` | The single gatekeeper between ViewModel and (Room + Retrofit) |
| 3 | `StateFlow` | How the ViewModel exposes state to Compose |

---

## 🆕 Step 0: Create the New Project

Same steps as Day 1, different name:

1. Android Studio → **New Project → Empty Activity**
2. Name: `QuoteKeeper`
3. Language: Kotlin, defaults otherwise
4. Finish, wait for Gradle sync

---

## 1️⃣ ViewModel

**The problem it solves:** every screen since Day 4 stored state with `remember { }` — but rotate the phone, and the Activity is recreated, wiping it out. `ViewModel` is built specifically to survive that.

```kotlin
import androidx.lifecycle.ViewModel

class QuoteViewModel : ViewModel() {
    // state will live here, not in the composable
}
```

**Direct callback:** Day 4's optional stretch previewed "state hoisted to a parent." `ViewModel` is simply the *official, framework-provided* parent for that state, instead of hoisting to just another composable.

✅ **Concept 1 done when:** you can say why `ViewModel` survives rotation but `remember { }` doesn't.

---

## 2️⃣ Repository — One Gatekeeper, Two Sources

**Confirmed official rule again:** ViewModel talks to the Repository. Repository talks to Room and Retrofit. Nothing skips a layer.

**Why bother?** If Retrofit's API changes, or you swap it for a different networking library later, only the Repository changes — the ViewModel and UI never know or care. Same reusability principle as Day 5's state hoisting, one layer up.

✅ **Concept 2 done when:** you can say why the ViewModel shouldn't call Retrofit directly.

---

## 3️⃣ StateFlow — ViewModel Talking to Compose

```kotlin
private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
val quotes: StateFlow<List<Quote>> = _quotes.asStateFlow()
```

**Pattern:** private `MutableStateFlow` (only the ViewModel can change it), public read-only `StateFlow` (the UI can only observe it). Prevents the UI from ever mutating state directly, keeping the "single source of truth" rule intact.

**Direct callback:** `StateFlow` is Day 5's `Flow` (Coroutines Province 6) with one extra guarantee — it always holds a current value. You already know 90% of this.

✅ **Concept 3 done when:** you can explain why `_quotes` is private but `quotes` is public.

---

## 🛠️ BUILD: Quote Keeper, Start to Finish

### Step 1 — Dependencies (`build.gradle.kts`)

```kotlin
plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    // Room (Day 12-13)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Retrofit (Day 14)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // ViewModel for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
}
```

Manifest: `<uses-permission android:name="android.permission.INTERNET" />`

---

### Step 2 — Model Layer

**Entity (Day 12 pattern):**
```kotlin
@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey val id: Int,
    val quote: String,
    val author: String
)
```

**DAO (Day 13 pattern):**
```kotlin
@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<Quote>)
}
```

**API response shape (confirmed live from `dummyjson.com/docs/quotes`):**
```kotlin
@Serializable
data class QuoteResponse(val quotes: List<Quote>)

interface QuoteApi {
    @GET("quotes")
    suspend fun getQuotes(): QuoteResponse
}
```

**Database (Day 12 pattern):**
```kotlin
@Database(entities = [Quote::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}
```

---

### Step 3 — The Repository (today's real new piece)

```kotlin
class QuoteRepository(
    private val dao: QuoteDao,
    private val api: QuoteApi
) {
    // Room is the single source of truth — UI always reads from here
    val quotes: Flow<List<Quote>> = dao.getAllQuotes()

    // Refresh pulls fresh data and SAVES it into Room
    suspend fun refreshQuotes() {
        val response = api.getQuotes()
        dao.insertAll(response.quotes)
        // Room's Flow (above) automatically re-emits — Day 13 knowledge, reused
    }
}
```

**This is the whole "offline-first" idea in 6 lines:** the UI never talks to the network directly. It watches Room. The Repository's job is just to keep Room up to date.

---

### Step 4 — The ViewModel

```kotlin
class QuoteViewModel(private val repository: QuoteRepository) : ViewModel() {

    val quotes: StateFlow<List<Quote>> = repository.quotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refreshQuotes()
            } catch (e: Exception) {
                // offline is fine — cached Room data still shows
            }
        }
    }
}
```

**`viewModelScope`:** a coroutine scope confirmed tied to the ViewModel's lifetime — auto-cancelled when the ViewModel is cleared. Same structured concurrency rule from Day 5, applied at the ViewModel layer.

**`.stateIn(...)`:** converts the Repository's plain `Flow` into a `StateFlow` the UI can collect with a starting value — the officially standard way to bridge Repository Flow → ViewModel StateFlow.

---

### Step 5 — Wiring It (minimal, manual — Hilt comes Day 17)

```kotlin
class QuoteViewModelFactory(private val repository: QuoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return QuoteViewModel(repository) as T
    }
}
```

**Honest flag:** this manual factory is exactly the kind of repetitive boilerplate that Day 17's `Hilt` removes. Doing it by hand once, today, is what makes tomorrow's improvement actually click instead of feeling like magic.

---

### Step 6 — The View (dumb, as MVVM demands)

```kotlin
@Composable
fun QuoteScreen(viewModel: QuoteViewModel) {
    val quotes by viewModel.quotes.collectAsState()

    Column {
        Button(onClick = { viewModel.refresh() }) {
            Text("Refresh Quotes")
        }
        LazyColumn {
            items(quotes, key = { it.id }) { quote ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "\"${quote.quote}\"")
                    Text(text = "— ${quote.author}")
                }
            }
        }
    }
}
```

**Call it, from `MainActivity`:**
```kotlin
val db = Room.databaseBuilder(context, AppDatabase::class.java, "quote-db").build()
val retrofit = Retrofit.Builder()
    .baseUrl("https://dummyjson.com/")
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()
val repository = QuoteRepository(db.quoteDao(), retrofit.create(QuoteApi::class.java))
val viewModel: QuoteViewModel = viewModel(factory = QuoteViewModelFactory(repository))

QuoteScreen(viewModel = viewModel)
```

**Notice:** `QuoteScreen` never imports Room or Retrofit. It only knows `viewModel.quotes` and `viewModel.refresh()`. That's the entire MVVM payoff — this screen could be swapped for a totally different UI without touching the Model layer at all.

---

## 🧪 The Real Test

1. Run with internet on → tap Refresh → real quotes appear, saved into Room
2. **Fully close the app**, turn on Airplane Mode, reopen
3. Quotes are **still there** (Room, Day 13's lesson) — even offline
4. **Rotate the emulator** — still there too (ViewModel, today's lesson — unlike Day 4's plain counter)
5. Turn Airplane Mode off, tap Refresh → gets fresh data from the API again

That 5-step test proves all three concepts at once, not in isolation.

---

## 🎯 Checkpoint

- [ ] Can explain why `ViewModel` survives rotation
- [ ] Can explain why the ViewModel never calls Retrofit or Room directly
- [ ] Can explain why `_quotes` is private and `quotes` is public
- [ ] Built all 5 layers, app runs, quotes persist offline AND survive rotation

All 4 checked → **Day 16 done. Real MVVM, real REST API, real database — one working project, built from an empty folder.**

---

## 📋 Summary Table

| Layer | Piece | Talks to |
|---|---|---|
| Model | `Quote`, `QuoteDao`, `QuoteApi`, `AppDatabase` | Each other only |
| Model | `QuoteRepository` | Dao + Api — the ONLY class allowed to touch both |
| ViewModel | `QuoteViewModel` | Repository only |
| View | `QuoteScreen` | ViewModel only |

---

## ⏭️ Day 17 Preview

1. Dependency Injection basics — why `QuoteViewModelFactory` by hand doesn't scale
2. `Hilt` — Google's official DI recommendation
3. Build: rewire Quote Keeper with Hilt, delete the manual factory

Move **"Day 17"** when ready.
 
---

**No rush. No pressure. You just built a real, layered, offline-capable app from an empty project — the actual shape of a production Android app.** 🎉