# 📱 Day 8 — Android: Navigation (Multi-Screen Apps)

**Date:** August 28, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 1)                          TODAY 🎯 (Week 2 starts)     NEXT ⏭️
Day 1-7: Single-screen todo app,          Day 8: Navigate between      Week 3: Activities/
add + remove, all in one screen           screens + pass data          Lifecycle
```

**Research done for today:** Verified `developer.android.com/develop/ui/compose/navigation` (official Navigation Compose doc) + `developer.android.com/guide/navigation/navcontroller`. ✅ CONFIRMED

**What changes today:** every app so far lived on ONE screen. Real apps have many screens (list → detail → settings...). Today you connect two screens together, properly.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `NavController` + `NavHost` | The engine that swaps which screen is showing |
| 2 | `composable(route)` | Registers one screen under a named path |
| 3 | Build | Tap a task → opens a detail screen showing it |

---

## 1️⃣ NavController + NavHost

**Definition (confirmed official, 3 parts):**

| Part | Job |
|---|---|
| `NavController` | Tells the app "go to this screen" |
| `NavGraph` | The map of all possible screens |
| `NavHost` | The container that actually displays whichever screen is current |

**Setup (exact official pattern):**

```kotlin
val navController = rememberNavController()

NavHost(
    navController = navController,
    startDestination = "list"
) {
    // screens go here — Concept 2
}
```

**One line, one meaning:** `rememberNavController()` creates the controller. `startDestination = "list"` says which screen shows first.

✅ **Concept 1 done when:** you can say what each of the 3 parts does, in a few words each.

---

## 2️⃣ composable(route) — Registering a Screen

**Definition:** each screen gets a unique text name ("route"). `composable("name") { }` says *"when the route is 'name', show this UI."*

```kotlin
NavHost(navController = navController, startDestination = "list") {
    composable("list") {
        Text("This is the list screen")
    }
    composable("detail") {
        Text("This is the detail screen")
    }
}
```

**Moving between them:**
```kotlin
navController.navigate("detail")   // switches NavHost to show the detail screen
```

✅ **Concept 2 done when:** you can register 2 routes and switch between them with a button.

---

## 3️⃣ Build: Tap a Task → Detail Screen (with data)

**Goal:** reuse Day 7's todo list. Tapping a task opens a second screen showing that task's name.

### Step 1: Add the navigation dependency

In `app/build.gradle.kts`, inside `dependencies { }`:
```kotlin
implementation("androidx.navigation:navigation-compose:2.9.8")
```
(Confirmed current version from official docs. Sync Gradle after adding.)

### Step 2: The two screens

```kotlin
@Composable
fun ListScreen(tasks: List<String>, onTaskClick: (String) -> Unit) {
    LazyColumn {
        items(tasks, key = { it }) { task ->
            Text(
                text = task,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onTaskClick(task) }
            )
        }
    }
}

@Composable
fun DetailScreen(taskName: String) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "Task Detail")
        Text(text = taskName)
    }
}
```

**Notice:** neither screen mentions `navController` anywhere. `ListScreen` just calls `onTaskClick(task)` — a plain lambda. This matches the **official recommendation**, confirmed directly: *"you shouldn't pass the navController directly into any composable and instead pass navigation callbacks as parameters"* — keeps screens testable and reusable, same hoisting principle from Day 4-5, now applied to navigation.

### Step 3: Wire it with NavHost + passing data

```kotlin
@Composable
fun TodoNavApp() {
    val navController = rememberNavController()
    val tasks = remember { mutableStateListOf("Learn Navigation", "Build detail screen") }

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(
                tasks = tasks,
                onTaskClick = { task ->
                    navController.navigate("detail/$task")
                }
            )
        }
        composable(
            route = "detail/{taskName}",
            arguments = listOf(navArgument("taskName") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskName = backStackEntry.arguments?.getString("taskName") ?: ""
            DetailScreen(taskName = taskName)
        }
    }
}
```

**Read the data-passing part like this:**

| Piece | Job |
|---|---|
| `route = "detail/{taskName}"` | `{taskName}` is a placeholder slot in the route |
| `navController.navigate("detail/$task")` | Fills that slot with the real value when navigating |
| `navArgument("taskName") { type = NavType.StringType }` | Tells Navigation "expect a String in that slot" |
| `backStackEntry.arguments?.getString("taskName")` | Reads the actual value back out on the detail screen |

**Call it:**
```kotlin
// In setContent { }
TodoNavApp()
```

### Expected Result

List screen shows your tasks. Tap one → screen changes to show "Task Detail" + the exact task you tapped. The system back button returns you to the list (this is automatic — `NavHost` manages a back stack for you, confirmed official behavior).

---

## 🎯 Checkpoint

- [ ] Can name the 3 navigation parts and their jobs
- [ ] Built 2 routes and switched between them
- [ ] Full `TodoNavApp` runs: list → tap → detail screen shows correct task
- [ ] System back button returns to the list

All 4 checked → **Day 8 done.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `NavController` | Issues "go to X" commands |
| `NavHost` | Displays whichever screen is current |
| `composable(route) { }` | Registers one screen |
| `"detail/{taskName}"` + `navArgument` | Passing data between screens through the route |
| Lambdas, not `navController`, passed into screens | Keeps screens reusable/testable (official recommendation) |

---

## ⏭️ Day 9 Preview — Week 3 Begins

1. What an `Activity` actually is (the container Compose lives inside)
2. Activity lifecycle — `onCreate`, `onResume`, etc.
3. Why this matters even though you mostly won't touch it directly in Compose apps

Move **"Day 9"** when ready.

---

**No rush. No pressure. You just connected two real screens with real data passing between them — the exact pattern every multi-screen app in the Play Store uses.** 🎉