# 📱 Day 13 — Android: Room DAO — Making Day 7's App Persist

**Date:** September 12, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅                                TODAY 🎯                        NEXT ⏭️
Day 12: @Entity (Task table defined)   Day 13: @Dao + Flow —          Week 5:
                                        tasks survive app close        Retrofit (network)
```

**Research done for today:** Verified `developer.android.com/training/data-storage/room/accessing-data` (official, confirmed **45 days old — very current**) 
+ the official `@Dao` reference. ✅ CONFIRMED. Notably confirmed directly: 

+ *"@Query DAO methods can now be... Flow. The returned Flow will re-emit a new set of values if the observing tables in the query are invalidated."*

**What changes today:** Day 12 defined a table shape. Today you actually read/write it — and reconnect Day 7's `TodoApp` so tasks **survive closing the app**, which was the whole point of starting Room.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `@Dao` | An interface listing the exact operations allowed on a table |
| 2 | `Flow<List<Task>>` return type | Room can hand your UI a live, auto-updating stream of the table |
| 3 | Build | Reconnect Day 7's todo app to Room — for real persistence |

---

## 1️⃣ @Dao — Data Access Object

**Definition (confirmed official):** *"Data Access Objects are the main classes where you define your database interactions."* Just an interface — Room writes the actual implementation for you at compile time.

```kotlin
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>
}
```

**Read it like this:**

| Piece | Job |
|---|---|
| `@Dao interface TaskDao` | "These are the only allowed operations on the tasks table" |
| `@Insert suspend fun insert(task: Task)` | No SQL needed — Room writes the `INSERT` for you |
| `@Delete suspend fun delete(task: Task)` | Same — Room matches the row by primary key automatically |
| `@Query("SELECT * FROM tasks")` | For anything beyond basic insert/delete, you write real SQL |

**Direct callback to Day 5:** `suspend fun insert(...)` — this is a real suspend function, same rules as Day 5's coroutines apply. You cannot call it from a normal function, only from a coroutine.

✅ **Concept 1 done when:** you can say why `insert`/`delete` need no SQL but `getAllTasks` does.

---

## 2️⃣ Flow — A Live, Auto-Updating Table

**The one line that matters most today:**
```kotlin
fun getAllTasks(): Flow<List<Task>>
```

**Definition (confirmed official):** *"The returned Flow will re-emit a new set of values if the observing tables in the query are invalidated."*

**Plain meaning:** you don't need to manually re-query after every insert/delete. **Room automatically pushes a fresh list through the Flow the instant the table changes.**

**Direct callback to Day 5's Province 6 (Flow):** this is the exact same `Flow<T>` you learned there — cold, collected with `.collect()` or, in Compose, with `collectAsState()`. Room isn't a new concept here — it's Day 5's Flow, applied to a database table instead of a manual `flow { }` builder.

✅ **Concept 2 done when:** you can explain why `getAllTasks()` doesn't need to be called again after adding a task.

---

## 3️⃣ Build: Reconnect Day 7's Todo App

### Step 1: Update the DAO and Database

Add `TaskDao` (above) to your project. Then update `AppDatabase` from Day 12:

```kotlin
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
```

**One new piece:** `abstract fun taskDao(): TaskDao` — this is how the rest of your app gets access to the DAO. Confirmed official pattern.

### Step 2: Create the actual database instance

```kotlin
import androidx.room.Room

val db = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "app-database"
).build()
```

`"app-database"` is just the file name Room uses on disk — that's the real, persistent file.

### Step 3: Rewire the screen (reusing Day 7's `AddTaskRow` and `TaskRow` unchanged)

```kotlin
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun RoomTodoApp() {
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()
    }
    val taskDao = db.taskDao()
    val scope = rememberCoroutineScope()

    val tasks by taskDao.getAllTasks().collectAsState(initial = emptyList())

    Column {
        AddTaskRow(onAdd = { newTitle ->
            scope.launch { taskDao.insert(Task(title = newTitle)) }
        })

        LazyColumn {
            items(tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task.title,
                    onRemove = { scope.launch { taskDao.delete(task) } }
                )
            }
        }
    }
}
```

**The one genuinely new piece:** `collectAsState(initial = emptyList())` — converts a `Flow` into Compose `State` (Day 4's concept), so the `LazyColumn` recomposes automatically whenever Room pushes a new list.

**Why `scope.launch { }` everywhere:** `insert()` and `delete()` are `suspend` functions (Concept 1). Composables aren't coroutines themselves, so you need a scope to launch them from — `rememberCoroutineScope()` gives you exactly that, tied to this composable's lifetime.

**Call it:**
```kotlin
// In setContent { }
RoomTodoApp()
```

### 🧪 The Real Test (this is the whole point of Day 12-13)

1. Run the app, add 2-3 tasks
2. **Fully close the app** (swipe away from Recents, not just Home)
3. Reopen it

**Expected result:** your tasks are still there. Compare this directly to Day 7 — same UI, same `AddTaskRow`/`TaskRow` components, but now backed by a real file on disk instead of memory that vanishes.

---

## 🎯 Checkpoint

- [ ] Can explain why `@Insert`/`@Delete` need no SQL, but `@Query` does
- [ ] Can explain why `getAllTasks()` auto-updates without re-calling it
- [ ] Built `TaskDao`, updated `AppDatabase`
- [ ] `RoomTodoApp` runs — tasks survive a full app close and reopen

All 4 checked → **Day 13 done. Week 4 complete.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `@Dao` | Interface of allowed table operations |
| `@Insert` / `@Delete` | No SQL needed, Room generates it |
| `@Query("...")` | Write real SQL for anything else |
| `Flow<List<Task>>` | Auto-updating stream — Room re-emits on table change |
| `collectAsState()` | Bridges Flow (Day 5) into Compose State (Day 4) |
| `rememberCoroutineScope()` | Lets a composable launch suspend DAO calls |

---

## ⏭️ Day 14 Preview — Week 5 Begins

1. `Retrofit` — calling a real REST API
2. Defining an API interface + a data model for the response
3. Build: fetch a real list from a public API and display it

Move **"Day 14"** when ready.

---

**No rush. No pressure. This is the first app in the entire journey that keeps its data after you close it — a genuinely production-shaped milestone.** 🎉