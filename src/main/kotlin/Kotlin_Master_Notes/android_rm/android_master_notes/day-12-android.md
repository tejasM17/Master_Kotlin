# 📱 Day 12 — Android: Room Database, Part 1 (Entity)

**Date:** September 12, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 1-3)                    TODAY 🎯 (Week 4 starts)      NEXT ⏭️
Compose UI, state, navigation,        Day 12: Room — define a       Day 13: DAO +
lifecycle, permissions, WorkManager   real database table           actual queries
```

**Research done for today:** 
+ Verified `developer.android.com/training/data-storage/room/defining-data` (official Room entity guide) 
+ `developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room` (official, **current**, non-deprecated codelab) 
+ `developer.android.com/reference/kotlin/androidx/room/package-summary` (official reference). ✅ CONFIRMED

**⚠️ Correction from Day 1's roadmap:** back on Day 1 I flagged `android-room-with-a-view-kotlin` as unverified and said I'd check it today. I did — it's now officially labeled **"(Deprecated)"** on developer.android.com. Using the current codelab instead. Good that we waited.

**What changes today:** Day 7's todo list used `mutableStateListOf` — real, but it vanishes the moment the app closes. Today starts the fix: **data that survives.**

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `Room` | Google's library for a real local database, without writing raw SQL |
| 2 | `@Entity` | Turns a data class into a real database table |
| 3 | Build | Define your first real table (no queries yet — Day 13) |

---

## 1️⃣ What Is Room?

**Definition (confirmed official):** Room sits on top of SQLite (Android's built-in database engine) and lets you *"define your database schema without writing any SQL code"* for the basic parts.

**3 core pieces (confirmed official, from the Room package summary):**

| Piece | Job |
|---|---|
| `Entity` | One table |
| `DAO` (Data Access Object) | The methods that read/write that table |
| `Database` | Ties Entities + DAOs together, the actual access point |

**Today = just the first piece.** One thing at a time, same rule as every day so far.

✅ **Concept 1 done when:** you can name Room's 3 core pieces.

---

## 2️⃣ @Entity — A Table, Defined as a Data Class

**The exact official pattern (confirmed, from the current Persisting Data codelab):**

```kotlin
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false
)
```

**Read it like this:**

| Piece | Job |
|---|---|
| `@Entity(tableName = "tasks")` | "This class = a table named tasks" |
| `data class Task(...)` | Same `data class` you learned in Kotlin Level 6 — nothing new about the class itself |
| `@PrimaryKey(autoGenerate = true)` | Every table needs one unique-per-row column. `autoGenerate = true` means Room assigns IDs for you (1, 2, 3...) |
| `val title: String` | A normal property → becomes a normal column |

**Nice fact worth noticing:** the `Task` shape here is almost identical to Day 7's `data class Task(val id: Int, val title: String)`. **Room doesn't ask you to learn a new way to model data — it just adds annotations to the Kotlin you already know.**

✅ **Concept 2 done when:** you can point at each line of an `@Entity` and say what it does.

---

## 3️⃣ Build: Your First Real Table

### Step 1: Add Room dependencies

In `app/build.gradle.kts`:
```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.3.12" apply false // needed for Room's code generation
}

dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
}
```

### Step 2: Define the Entity

Create a new file `Task.kt`:
```kotlin
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false
)
```

### Step 3: The minimal Database class (just enough to compile today)

```kotlin
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase()
```

**Don't worry about this file's details yet** — `@Database` just registers which Entities exist. You'll come back and add a real DAO reference here in Day 13.

### 🎯 Checkpoint Build

1. Add both files to your project
2. Build the project (Build → Make Project)
3. **It should compile with no errors.** That's it for today — you're not running a screen yet, just confirming the table definition itself is valid.

If you get a KSP-related error, double-check the `plugins { }` block in `build.gradle.kts` — this is the single most common setup mistake with Room, confirmed by cross-referencing multiple current guides.

---

## 🎯 Checkpoint

- [ ] Can name Room's 3 core pieces (Entity, DAO, Database)
- [ ] Can explain what `@PrimaryKey(autoGenerate = true)` does
- [ ] `Task.kt` entity compiles
- [ ] `AppDatabase.kt` compiles, references `Task::class`

All 4 checked → **Day 12 done.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| Room | Local database on top of SQLite, minimal SQL needed |
| `@Entity` | Data class → real table |
| `@PrimaryKey(autoGenerate = true)` | Unique row ID, auto-assigned |
| `@Database(entities = [...])` | Registers which tables exist (DAO comes next) |

---

## ⏭️ Day 13 Preview

1. `@Dao` — the interface with your actual queries
2. `suspend fun insert()`, `@Query("SELECT * FROM tasks")` — real read/write
3. Build: reconnect Day 7's todo app to Room instead of `mutableStateListOf` — **tasks survive closing the app**

Move **"Day 13"** when ready.

---

**No rush. No pressure. You just defined your first real, persistent database table — the exact thing that makes Day 7's app actually production-shaped.** 🎉