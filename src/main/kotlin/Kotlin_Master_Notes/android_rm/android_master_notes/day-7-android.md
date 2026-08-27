# 📱 Day 7 — Android: Add & Remove Items (Working Todo App)

**Date:** August 27, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅                              TODAY 🎯                    NEXT ⏭️
Day 1-5: Text→Button→State→Input     Day 7: Add/remove list      Week 2: Navigation
Day 6: LazyColumn (read-only list)   items (working todo app)    Week 3: Activities
```

**Research done for today:** Verified `developer.android.com/codelabs/jetpack-compose-state` (official State codelab, covers `mutableStateListOf` directly). ✅ CONFIRMED

**What changes today:** Day 6's todo list couldn't add or remove anything — it was frozen data. Today it becomes a real, working app.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `mutableStateListOf` | A list Compose can actually watch for add/remove |
| 2 | Add item | TextField + Button + `.add()` |
| 3 | Remove item | Button per row + `.remove()` → full app |

---

## 1️⃣ mutableStateListOf — The List Compose Can Watch

**The bug first (confirmed real, common mistake):**

```kotlin
// ❌ BROKEN — looks reasonable, doesn't work
var tasks = remember { mutableListOf("Task 1", "Task 2") }

Button(onClick = { tasks.add("New Task") }) { Text("Add") }
```

**Why it's broken:** `mutableListOf()` is a plain Kotlin list. Compose has no way to "see" `.add()` happening. Confirmed directly — a plain list *"is not a state object"*. No recomposition, nothing appears.

**The fix:**

```kotlin
// ✅ WORKS — Compose watches this list directly
val tasks = remember { mutableStateListOf("Task 1", "Task 2") }

Button(onClick = { tasks.add("New Task") }) { Text("Add") }
```

**Only difference:** `mutableListOf` → `mutableStateListOf`. That single word swap is the entire fix.

**One official warning worth knowing (confirmed from the docs):** always create the list *with* its starting items in one line, like above. Don't create an empty list then `.add()` items separately outside a click — that causes duplicate items on every recomposition.

✅ **Concept 1 done when:** you can say why `mutableListOf` fails but `mutableStateListOf` works.

---

## 2️⃣ Add Item — TextField + Button + .add()

```kotlin
@Composable
fun AddTaskRow(onAdd: (String) -> Unit) {
    var newTask by remember { mutableStateOf("") }

    Row(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = newTask,
            onValueChange = { newTask = it },
            label = { Text("New task") },
            modifier = Modifier.weight(1f)
        )
        Button(onClick = {
            if (newTask.isNotBlank()) {
                onAdd(newTask)
                newTask = ""   // clear the field after adding
            }
        }) {
            Text("Add")
        }
    }
}
```

**Read it like this:**
1. Type into `TextField` → updates `newTask` (Day 5's pattern)
2. Tap `Button` → calls `onAdd(newTask)` (hoisted — this row doesn't own the actual list, Day 4-5's pattern)
3. Clears the field so it's ready for the next task

✅ **Concept 2 done when:** you understand why `onAdd` is a parameter, not the list itself (hoisting again).

---

## 3️⃣ Remove Item + Full Working App

```kotlin
@Composable
fun TaskRow(task: String, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = task, modifier = Modifier.weight(1f))
        Button(onClick = onRemove) {
            Text("✕")
        }
    }
}

@Composable
fun TodoApp() {
    val tasks = remember { mutableStateListOf("Learn LazyColumn", "Build todo app") }

    Column {
        AddTaskRow(onAdd = { newTask -> tasks.add(newTask) })

        LazyColumn {
            items(tasks, key = { it }) { task ->
                TaskRow(
                    task = task,
                    onRemove = { tasks.remove(task) }
                )
            }
        }
    }
}
```

**Call it:**
```kotlin
// In setContent { }
TodoApp()
```

### Trace the flow (this is the whole app in 4 steps)

| Step | What happens |
|---|---|
| 1 | Type "Buy milk" → `AddTaskRow`'s local `newTask` updates |
| 2 | Tap "Add" → `onAdd("Buy milk")` runs → `tasks.add("Buy milk")` |
| 3 | `tasks` is a `mutableStateListOf` → Compose notices → `LazyColumn` recomposes |
| 4 | New row appears instantly. Tap "✕" on any row → `tasks.remove(task)` → row disappears |

**Why `key = { it }` matters here:** confirmed from Day 6 — without a stable key, removing a row in the middle of the list can confuse which row's state belongs to which item. Using the task text itself as the key keeps everything correct.

---

## 🎯 Checkpoint

- [ ] Can explain why `mutableListOf` silently fails but `mutableStateListOf` works
- [ ] Built `AddTaskRow` — typing + tapping Add adds a real row
- [ ] Built `TaskRow` with a remove button — tapping ✕ deletes that row
- [ ] Full `TodoApp` runs: add tasks, remove tasks, list scrolls if long

All 4 checked → **Day 7 done. You built your first fully functional CRUD-style app (Create + Read + Delete).**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `mutableStateListOf` | The list type Compose can actually watch |
| Plain `mutableListOf` + `remember` | Looks similar, silently broken for UI updates |
| `AddTaskRow` | Hoisted input pattern, applied to adding data |
| `TaskRow` + `onRemove` | Hoisted removal pattern — row doesn't own the list, just reports "remove me" |
| Full loop | Type → Add → Recompose → Remove → Recompose |

---

## ⏭️ Day 8 Preview

Week 1 is done after today. Day 8 starts **Week 2: Navigation Compose**:
1. `NavHost` + `NavController` — moving between screens
2. Passing data (like a selected task) to another screen
3. Build: tap a task → opens a detail screen

Move **"Day 8"** when ready.

---

**No rush. No pressure. This is the first app in this whole journey that actually behaves like a real app — not a single-screen demo.** 🎉