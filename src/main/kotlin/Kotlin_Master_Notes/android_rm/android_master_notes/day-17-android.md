# 📱 Day 17 — Android: Dependency Injection with Hilt

**Date:** September 22, 2026
*(Corrected — previous days had drifted to a stale date; this is the actual current date going forward.)*

> 💬 *"Simplicity is the ultimate sophistication."* — Leonardo da Vinci

---

## 🗺️ THE BIG PICTURE

```
DONE ✅                                  TODAY 🎯                      NEXT ⏭️
Day 16: Quote Keeper — full MVVM,        Day 17: Hilt — delete the     Week 7:
REST + Room, manual factory wiring       manual factory, let Hilt      Capstone project
                                          build everything
```

**Research done for today:** Verified `developer.android.com/training/dependency-injection/hilt-android` (official, confirmed current — 96 days old at check time, current Hilt version **2.57.1**). Direct confirmed quote: *"In Compose, you don't need to annotate individual composables. Instead, annotate your root ComponentActivity with `@AndroidEntryPoint`."* ✅ CONFIRMED

**What changes today:** Day 16 ended with `QuoteViewModelFactory` — hand-written, and I flagged it as boilerplate on purpose. Today you delete it.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | The DI problem | Why manual wiring breaks down as an app grows |
| 2 | Hilt's 4 core annotations | The minimum set that makes DI automatic |
| 3 | Build | Quote Keeper, rewired with Hilt, factory deleted |

---

## 1️⃣ The Problem, Concretely

**Yesterday, to get one `QuoteViewModel`, you needed:**
```kotlin
val db = Room.databaseBuilder(...)
val retrofit = Retrofit.Builder()...
val repository = QuoteRepository(db.quoteDao(), retrofit.create(QuoteApi::class.java))
val viewModel = viewModel(factory = QuoteViewModelFactory(repository))
```

**4 lines, in every screen that needs it.** Add a second screen needing the same `ViewModel` → copy all 4 lines again, or thread the repository through constructor parameters everywhere by hand.

**Definition (confirmed official):** *"Doing manual dependency injection requires you to construct every class and its dependencies by hand... Hilt provides a standard way to use DI... providing containers for every Android class in your project and managing their lifecycles automatically."*

✅ **Concept 1 done when:** you can point at yesterday's 4 manual-wiring lines and say what Hilt removes.

---

## 2️⃣ Hilt's 4 Core Annotations

**Confirmed official — these 4 cover almost everything you need:**

| Annotation | Goes on | Job |
|---|---|---|
| `@HiltAndroidApp` | Your `Application` class | Starts Hilt's dependency graph for the whole app |
| `@AndroidEntryPoint` | Your root Activity | Gives that Activity (and everything inside it — all Compose screens) access to Hilt |
| `@Inject constructor` | Regular classes (like `Repository`) | "Here's how to build me, and what I need" |
| `@HiltViewModel` | Your `ViewModel` | "Hilt, you own building this" |

**Confirmed official, exact quote for Compose specifically:** *"you don't need to annotate individual composables. Instead, annotate your root ComponentActivity"* — one annotation covers your entire UI tree.

✅ **Concept 2 done when:** you can say which of the 4 goes on `MainActivity`, and which goes on `QuoteViewModel`.

---

## 3️⃣ Build: Rewire Quote Keeper

### Step 1 — Dependencies

```kotlin
// Project-level build.gradle.kts
plugins {
    id("com.google.dagger.hilt.android") version "2.57.1" apply false
}

// app/build.gradle.kts
plugins {
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}
```

### Step 2 — Application class

```kotlin
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuoteKeeperApp : Application()
```

Register it in `AndroidManifest.xml`:
```xml
<application android:name=".QuoteKeeperApp" ... >
```

### Step 3 — MainActivity

```kotlin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteScreen()   // note: no viewModel passed in anymore!
        }
    }
}
```

### Step 4 — Repository, made injectable

```kotlin
class QuoteRepository @Inject constructor(
    private val dao: QuoteDao,
    private val api: QuoteApi
) {
    val quotes: Flow<List<Quote>> = dao.getAllQuotes()

    suspend fun refreshQuotes() {
        val response = api.getQuotes()
        dao.insertAll(response.quotes)
    }
}
```

**Only change from Day 16:** added `@Inject` before `constructor`. Hilt now knows how to build a `QuoteRepository` — *if* it also knows how to build a `QuoteDao` and `QuoteApi`.

### Step 5 — Telling Hilt how to build Room and Retrofit

You can't put `@Inject constructor` on `Room` or `Retrofit` directly — they're built via builder functions, not plain constructors. For these, Hilt uses a **Module**:

```kotlin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "quote-db").build()

    @Provides
    fun provideQuoteDao(db: AppDatabase): QuoteDao = db.quoteDao()

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    fun provideQuoteApi(retrofit: Retrofit): QuoteApi =
        retrofit.create(QuoteApi::class.java)
}
```

**Read it like this:** each `@Provides` function is a recipe. Hilt reads the **return type** to know what it builds, and the **parameters** to know what it needs first. `@InstallIn(SingletonComponent::class)` means "build this once, share it everywhere" — confirmed official scoping pattern.

### Step 6 — The ViewModel

```kotlin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    val quotes: StateFlow<List<Quote>> = repository.quotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            try { repository.refreshQuotes() } catch (e: Exception) { /* offline OK */ }
        }
    }
}
```

**Notice:** everything below `@HiltViewModel` is unchanged from Day 16. Only the annotations changed — the actual logic never had to know DI existed.

### Step 7 — The View, now trivial

```kotlin
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QuoteScreen(viewModel: QuoteViewModel = hiltViewModel()) {
    val quotes by viewModel.quotes.collectAsState()

    Column {
        Button(onClick = { viewModel.refresh() }) { Text("Refresh Quotes") }
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

**`viewModel: QuoteViewModel = hiltViewModel()`** — one line replaces all of Day 16's Step 5 manual factory. `QuoteViewModelFactory` can now be **deleted entirely.**

---

## 🎯 Checkpoint

- [ ] Can explain the 4 core Hilt annotations and where each goes
- [ ] Can explain why Room/Retrofit need a `@Module` but `Repository` just needs `@Inject constructor`
- [ ] Rewired Quote Keeper — `QuoteViewModelFactory.kt` deleted
- [ ] App still runs identically (same 5-step test from Day 16 still passes)

All 4 checked → **Day 17 done. Week 6 complete.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `@HiltAndroidApp` | Starts the whole app's dependency graph |
| `@AndroidEntryPoint` | Gives one Activity (and its whole Compose tree) access to Hilt |
| `@Inject constructor` | "Build me this way" — for plain classes |
| `@Module` + `@Provides` | "Build me this way" — for classes you don't control (Room, Retrofit) |
| `@HiltViewModel` | Marks a ViewModel as Hilt-managed |
| `hiltViewModel()` | Compose function that fetches the right instance automatically |

---

## ⏭️ Day 18 Preview — Week 7: Capstone

Week 7 combines **everything** from Days 1-17 into one larger, polished app — your actual portfolio piece. Day 18 starts by planning its structure before writing code.

Move **"Day 18"** when ready.
 
---

**No rush. No pressure. You just removed an entire category of repetitive, error-prone code — and every line that's left is logic that actually matters.** 🎉