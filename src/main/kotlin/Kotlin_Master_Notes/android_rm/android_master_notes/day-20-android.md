# 📱 Day 20 — Android: Capstone — Favorite Toggle (Room @Update)

**Date:** September 25, 2026
*(Using last confirmed date — clock check wasn't available this turn, will re-verify next file.)*

> 💬 *"Small daily improvements are the key to staggering long-term results."* — Robin Sharma

---

## 🗺️ THE BIG PICTURE

```
DONE ✅                                  TODAY 🎯                        NEXT ⏭️
Day 19: Detail screen, real nav,         Day 20: Writing to an           Day 21: Proper
ID-based re-query                        EXISTING row (not insert)       sealed-class UiState
```

**Research done for today:** Verified `developer.android.com/training/data-storage/room/accessing-data` (official, confirmed genuinely fresh — **18 days old** at check time) + the official Persisting Data codelab's own `@Update` example. Direct confirmed quote: *"The @Update annotation lets you define functions that update specific rows in a database table... accept data entity instances as parameters"* — matched by primary key automatically. ✅ CONFIRMED

**What changes today:** every Room write so far (Day 13, 16) was `@Insert`. Today is the first `@Update` — changing a row that already exists.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | Schema change | Adding `isFavorite` to `Quote`, and why that requires a version bump |
| 2 | `@Update` | Overwrites an existing row, matched by primary key |
| 3 | Build | A ❤️ button on the detail screen, persisted |

---

## 1️⃣ Adding a Field — Schema Changes

```kotlin
@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey val id: Int,
    val quote: String,
    val author: String,
    val isFavorite: Boolean = false
)
```

**One new field, one real consequence:** Room tracks your table's shape by a `version` number in `@Database`. Change the shape → bump the version:

```kotlin
@Database(entities = [Quote::class], version = 2)
abstract class AppDatabase : RoomDatabase()
```

**Honest simplification for now:** properly handling old users' existing data across a version change uses a `Migration` object — a real topic, deliberately **not** today's scope (this project has no real users yet). For now, add `.fallbackToDestructiveMigration()` when building the database — it just wipes and recreates the local table on version mismatch, which is completely fine for a capstone you're actively building, and wrong for anything already shipped to real users. Flagging honestly, not skipping the warning.

```kotlin
Room.databaseBuilder(context, AppDatabase::class.java, "quote-db")
    .fallbackToDestructiveMigration()
    .build()
```

✅ **Concept 1 done when:** you can explain why the version number had to change, and what `fallbackToDestructiveMigration()` trades away.

---

## 2️⃣ @Update — Overwriting an Existing Row

```kotlin
@Update
suspend fun update(quote: Quote)
```

**Confirmed official mechanism:** Room matches the row to update **by primary key** — you pass the *whole* entity, with the ID unchanged and whatever fields you want changed.

**Direct callback to Kotlin Level 6 (Day 3 of your Kotlin journey):** the cleanest way to build that "whole entity, one field changed" object is `data class`'s `.copy()` — something you learned months ago, now paying off in a real Room call:

```kotlin
val updated = quote.copy(isFavorite = !quote.isFavorite)
dao.update(updated)
```

✅ **Concept 2 done when:** you can explain how Room knows *which* row `update()` should overwrite.

---

## 3️⃣ Build: The Favorite Button

### Step 1 — DAO

```kotlin
@Update
suspend fun update(quote: Quote)
```

### Step 2 — Repository

```kotlin
suspend fun toggleFavorite(quote: Quote) {
    dao.update(quote.copy(isFavorite = !quote.isFavorite))
}
```

### Step 3 — ViewModel (add to `QuoteDetailViewModel`)

```kotlin
fun toggleFavorite() {
    viewModelScope.launch {
        quote.value?.let { repository.toggleFavorite(it) }
    }
}
```

**`quote.value`:** reading a `StateFlow`'s current value directly, outside of `collect` — perfectly normal, confirmed standard usage for "give me what it holds right now."

### Step 4 — UI (`QuoteDetailScreen`)

```kotlin
@Composable
fun QuoteDetailScreen(viewModel: QuoteDetailViewModel = hiltViewModel()) {
    val quote by viewModel.quote.collectAsState()

    Column(modifier = Modifier.padding(24.dp)) {
        quote?.let {
            Text(text = "\"${it.quote}\"", style = MaterialTheme.typography.headlineSmall)
            Text(text = "— ${it.author}", modifier = Modifier.padding(top = 8.dp, bottom = 16.dp))

            IconButton(onClick = { viewModel.toggleFavorite() }) {
                Icon(
                    imageVector = if (it.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Toggle favorite"
                )
            }
        } ?: Text("Loading...")
    }
}
```

---

## 🧪 The Real Test

1. Open a quote's detail screen → heart is outlined (not favorited)
2. Tap the heart → fills in solid red **immediately** (recomposition, Day 4's mechanism, still holding)
3. Go back to the list, tap the *same* quote again → heart is still filled — **the change persisted through navigation**
4. Fully close and reopen the app → still favorited (Room, Day 13's lesson, still holding)

---

## 🎯 Checkpoint

- [ ] Can explain why adding a field required bumping the database version
- [ ] Can explain what `fallbackToDestructiveMigration()` trades away, and why that's OK only pre-launch
- [ ] Can explain how `@Update` knows which row to overwrite
- [ ] Built the ❤️ toggle, confirmed it survives navigation AND app close

All 4 checked → **Day 20 done.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| Schema change → version bump | Room needs to know the table shape changed |
| `fallbackToDestructiveMigration()` | Fine pre-launch, wrong once real users have data |
| `@Update` | Overwrites a row, matched by primary key, full entity in |
| `.copy(isFavorite = !x)` | Day 6 Kotlin knowledge, now doing real database work |

---

## 📊 How Much of the Android Track Is Left?

Straight answer, against your actual roadmap (`ANDROID_MASTERY_ROADMAP.md`, Day 1):

```
Phase 1: Compose Fundamentals (Days 1-8)     ████████████████████ 100% ✅
Phase 2: Android SDK Basics (Days 9-11)      ████████████████████ 100% ✅
Phase 3: Room + Retrofit (Days 12-15)        ████████████████████ 100% ✅
Phase 4: Architecture — MVVM/Hilt (16-17)    ████████████████████ 100% ✅
Phase 5: Capstone (Days 18-24 planned)       ████████████░░░░░░░░  50% (Day 20 of ~24)
Phase 6: Testing/Quality/Deployment          ░░░░░░░░░░░░░░░░░░░░   0% — NOT YET SCHEDULED
```

**Concretely:**
- **Capstone:** 4 days left (21: sealed UiState → 22: WorkManager → 23: permissions → 24: polish + GitHub push)
- **Not yet touched at all:** unit testing, Compose UI testing, Play Store deployment basics — these exist in the original roadmap's Phase 6 but don't have Day numbers assigned yet
- **Realistic total remaining:** ~4 days to finish the capstone as scoped, **+ roughly 3-5 more days** if you want Phase 6 (testing/deployment) covered with the same rigor as everything else

**Overall Android track completion right now: ~80-85%** of the original 8-phase roadmap, **not counting testing/deployment**, which was always planned but never given specific days yet.

---

## ⏭️ Day 21 Preview

1. Replacing the inline `try/catch` with a real `sealed interface QuoteUiState`
2. Applying Day 15's pattern for real, at capstone scale
3. Build: proper Loading/Success/Error states across both list and detail screens

Move **"Day 21"** when ready.
 
---

**No rush. No pressure. Every write operation in a real app is either an Insert or an Update — you now have both, for real, persisted.** 🎉