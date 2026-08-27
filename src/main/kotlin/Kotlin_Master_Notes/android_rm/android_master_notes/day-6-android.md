# 📱 Day 6 — Android: LazyColumn (Scrollable Lists)

**Date:** August 27, 2026

---

## 🗺️ THE BIG PICTURE (Where You Are)

```
DONE ✅                          TODAY 🎯              NEXT ⏭️
Day 1: Text                      Day 6: LazyColumn      Day 7: Add/remove items
Day 2: Column/Row/Box                                   Day 8+: Navigation
Day 3: Button + onClick                                 Week 3: Activities/Lifecycle
Day 4: remember + mutableStateOf                         Week 4-5: Room + Retrofit
Day 5: TextField + hoisting                              Week 6: MVVM
                                                          Week 7: Capstone app
```

**Research done for today:** Verified `developer.android.com/develop/ui/compose/lists` — "Lazy lists and lazy grids" doc. ✅ CONFIRMED

**What changes today:** every screen so far showed a *few* fixed items. Real apps show lists — contacts, messages, products. Today you learn the tool for that.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `LazyColumn` | A scrollable list that only draws what's visible on screen |
| 2 | `items()` | The function that turns a `List<T>` into rows inside a `LazyColumn` |
| 3 | Build | A scrollable todo list, read-only |

---

## 1️⃣ LazyColumn

**Definition:** A vertical list. Only renders items currently visible — not the whole list at once.

**Why not just `Column`?** `Column` draws every item immediately, even off-screen ones. Bad for 1,000 items. `LazyColumn` skips off-screen ones. Confirmed official quote: *"only compose and lay out items which are visible in the component's viewport."*

**Example:**

```kotlin
LazyColumn {
    item { Text("Header") }
    item { Text("Row 1") }
    item { Text("Row 2") }
}
```

**Rule:** `Column { Text("A"); Text("B") }` → `LazyColumn { item { Text("A") }; item { Text("B") } }`. Same idea, wrapped in `item { }`.

✅ **Concept 1 done when:** you can say why `LazyColumn` beats `Column` for long lists, in one sentence.

---

## 2️⃣ items() — Turning Data Into Rows

**Definition:** A function inside `LazyColumn` that loops over a `List<T>` and draws one row per item.

**Example (confirmed official pattern):**

```kotlin
val fruits = listOf("Apple", "Banana", "Mango")

LazyColumn {
    items(fruits) { fruit ->
        Text(text = fruit)
    }
}
```

**Read it like this:** *"For each `fruit` in `fruits`, draw a `Text`."*

**Compare to a normal loop:**
```kotlin
// Normal Kotlin loop — NOT lazy, would draw everything at once
for (fruit in fruits) { Text(text = fruit) }

// LazyColumn version — lazy, only draws visible ones
LazyColumn { items(fruits) { fruit -> Text(text = fruit) } }
```

✅ **Concept 2 done when:** you can write `items(list) { }` from memory.

---

## 3️⃣ Build: Scrollable Todo List

**Goal:** Show a list of tasks. Scroll works automatically — you write zero scroll code.

```kotlin
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Task(val id: Int, val title: String)

@Composable
fun TaskRow(task: Task) {
    Card(modifier = Modifier.padding(8.dp)) {
        Text(
            text = task.title,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun TodoListScreen(tasks: List<Task>) {
    LazyColumn {
        items(tasks) { task ->
            TaskRow(task = task)
        }
    }
}
```

**Test data + call it:**

```kotlin
val sampleTasks = listOf(
    Task(1, "Learn LazyColumn"),
    Task(2, "Build todo app"),
    Task(3, "Push to GitHub"),
    Task(4, "Sleep well"),
    Task(5, "Repeat tomorrow")
)

// In setContent { }
TodoListScreen(tasks = sampleTasks)
```

**Run it. Expected result:** 5 cards, one per task, scrollable if the list is long enough.

---

## ⚡ One Official Best Practice (short version)

Adding a `key` to `items()` prevents bugs when the list reorders:

```kotlin
items(tasks, key = { task -> task.id }) { task ->
    TaskRow(task = task)
}
```

**Why:** Confirmed official quote — *"providing a stable key enables item state to be consistent across dataset changes."* You'll actually need this in Day 7 when tasks get added/removed. Noting it now, using it then.

✅ **Concept 3 done when:** your 5 tasks render and scroll.

---

## 🎯 Checkpoint

- [ ] Can say why `LazyColumn` ≠ `Column` for long lists
- [ ] Wrote `items(list) { }` yourself
- [ ] `TodoListScreen` runs, shows 5 cards, scrolls

All 3 checked → **Day 6 done.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `LazyColumn` | Scrollable, only draws visible rows |
| `items(list) { }` | Loop that draws one row per data item |
| `key = { }` | Keeps item state correct when list changes (needed Day 7) |

---

## ⏭️ Day 7 Preview

1. Adding new tasks (button + `TextField` + `mutableStateListOf`)
2. Removing tasks (swipe or button)
3. Build: fully working todo app (add + remove + scroll)

Move **"Day 7"** when ready.