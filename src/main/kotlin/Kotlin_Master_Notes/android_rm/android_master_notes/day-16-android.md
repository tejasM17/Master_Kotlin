# 📱 Day 16 — Android: MVVM, From a Brand New Project

**Date:** September 20, 2026

> 💬 *"The secret of getting ahead is getting started."* — Mark Twain

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 1-5)                     TODAY 🎯 (Week 6 starts)      NEXT ⏭️
Compose, state, Room, Retrofit,        Day 16: MVVM — a fresh,       Day 17: Reconnect
error handling (all in composables)    tiny project, built right     the real apps to it
```

**Research done for today:** Verified `developer.android.com/topic/libraries/architecture/viewmodel` (official ViewModel overview) 
+ `developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state` (official ViewModel codelab) 
+ `developer.android.com/topic/architecture/views/ui-layer` (official UI layer architecture guide — confirms the exact state-holder pattern below). ✅ CONFIRMED

**Why a brand new project today:** every day so far mixed a new concept into an existing, growing app. MVVM is a *shape*, not a feature — learning it inside your Room/Retrofit app would tangle two hard things together. Today: **one tiny, disposable project**, built only to see MVVM cleanly.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | MVVM (Model–View–ViewModel) | 3 layers, each with exactly one job |
| 2 | `StateFlow` + backing property | The official pattern for a ViewModel to safely expose state |
| 3 | Build | A brand new counter app, done the MVVM way — and proven to survive rotation |

---

## 🆕 Step 0: Create the New Project

Same steps as Day 1, different name:

1. Android Studio → **New Project → Empty Activity**
2. Name: `MvvmCounter`
3. Language: Kotlin, defaults otherwise
4. Finish, wait for Gradle sync

This project will only ever contain today's counter. That's intentional.

---

## 1️⃣ MVVM — Three Layers, One Job Each

**Definition (confirmed official, ViewModel overview):** *"The ViewModel class is a business logic or screen level state holder. It exposes state to the UI and encapsulates related business logic."*

| Layer | Job | Today's example |
|---|---|---|
| **Model** | The plain data shape | `CounterUiState(val count: Int)` |
| **ViewModel** | Owns the state, contains the logic, survives rotation | `CounterViewModel` |
| **View** | Just displays state, just sends events up | `CounterScreen` composable |

**The one-sentence rule:** **View shows and asks. ViewModel decides and remembers. Model just carries data.**

**Why bother — the actual official reason (not a style preference):** confirmed directly — *"Its principal advantage is that it caches state and persists it through configuration changes... your UI doesn't have to fetch data again when navigating between activities, or following configuration changes, such as when rotating the screen."*

**Direct callback to Day 4:** your `WorkingCounter` used `remember { mutableStateOf(0) }` — that survives *recomposition*, but confirmed official limit from Day 4: it does **not** survive rotation. Today's version will.

✅ **Concept 1 done when:** you can say which of the 3 layers "decides," which "shows," and which "carries."

---

## 2️⃣ StateFlow + Backing Property — The Official Pattern

**The exact pattern, confirmed from 3 independent official Google pages today:**

```kotlin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CounterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()
}
```

**Read it like this:**

| Piece | Job |
|---|---|
| `private val _uiState` | The **real, mutable** box — only this class can change it (underscore = "the private version," a naming convention you'll see everywhere) |
| `val uiState` (no underscore) | The **public, read-only** view of the same data — outside code can look, not touch |
| `.asStateFlow()` | Converts the mutable version into a read-only one for that public property |

**Why two properties for one value?** Confirmed official reasoning: this "backing property... protects the app data inside the ViewModel from unwanted and unsafe changes by external classes." The `CounterScreen` should never be able to just set `count = 999` directly — it can only *ask* the ViewModel to change it.

**Updating state — the official update pattern:**
```kotlin
fun increment() {
    _uiState.update { currentState ->
        currentState.copy(count = currentState.count + 1)
    }
}
```
`.update { }` + `.copy()` — confirmed official across multiple current pages. `copy()` is Day 6 Kotlin knowledge (data classes) — nothing new there, just used inside a ViewModel now.

✅ **Concept 2 done when:** you can explain why `_uiState` is private but `uiState` is public.

---

## 3️⃣ Build: The MVVM Counter

### Model
```kotlin
data class CounterUiState(val count: Int = 0)
```

### ViewModel
```kotlin
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CounterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    fun increment() {
        _uiState.update { it.copy(count = it.count + 1) }
    }
}
```

### View
```kotlin
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "Count: ${uiState.count}")
        Button(onClick = { viewModel.increment() }) {
            Text("Tap me")
        }
    }
}
```

**Call it:**
```kotlin
// In setContent { }
CounterScreen()
```

**Notice what `CounterScreen` does NOT contain:** no `remember`, no `mutableStateOf`, no logic. It reads `uiState` and calls `viewModel.increment()`. That's the entire View layer's job — same hoisting principle from Day 4-5, now scaled up to a real architectural pattern.

**`viewModel = viewModel()`:** confirmed standard Compose pattern — this default parameter automatically creates (or reuses) the correct `CounterViewModel` instance tied to this screen's lifecycle. You don't construct it with `CounterViewModel()` yourself.

### 🧪 The Real Test — Rotation Survival

1. Run the app, tap the button a few times (count goes up)
2. **Rotate the emulator** (Ctrl+F11 on most setups, or the rotate button in the emulator toolbar)
3. **Expected result:** the count is **still there** after rotation — unlike Day 4's `WorkingCounter`, which would have silently reset to 0

That single test is the entire payoff of MVVM today — confirmed directly from the official docs' own stated advantage.

---

## 🎯 Checkpoint

- [ ] New `MvvmCounter` project created and running
- [ ] Can say what View/ViewModel/Model each do, in a few words
- [ ] Can explain why `_uiState` is private and `uiState` is public
- [ ] Built the full 3-file counter and confirmed count survives rotation

All 4 checked → **Day 16 done.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| MVVM | Model (data) → ViewModel (logic + state) → View (display + events) |
| `ViewModel` class | Survives configuration changes like rotation — plain `remember` doesn't |
| `_uiState` / `uiState` | Private mutable, public read-only — the official backing property pattern |
| `.update { it.copy(...) }` | The standard way to change state inside a ViewModel |
| `viewModel()` | Gets the correctly-scoped ViewModel instance in a composable |

---

## ⏭️ Day 17 Preview

1. Reconnecting this exact pattern to a **real** feature — Day 13's Room-backed todo list, now behind a `TaskViewModel`
2. Where the Room `Flow` from Day 13 plugs into the ViewModel's `StateFlow`
3. Build: the todo app, now properly MVVM-shaped, survives both app close (Room) **and** rotation (ViewModel)

Move **"Day 17"** when ready.

---

**No rush. No pressure. You just built the exact architectural shape that every real Android app in production uses — from a project that took five minutes to create.** 🎉