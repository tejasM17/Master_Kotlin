# 📱 Day 5 — Android: TextField, Real State Hoisting, Live Greeting

**Date:** August 26, 2026
**Today's concepts (exactly 3, no more):**
1. `TextField` — letting the user type
2. Hoisting that text state properly — the preview from Day 4, applied for real
3. A tiny live-greeting project — text updates the screen as you type, no button needed

**Official sources for today (verified live, August 2026):**
- developer.android.com/develop/ui/compose/text/user-input — official text input doc ✅ VERIFIED
- developer.android.com/develop/ui/compose/state-hoisting ✅ VERIFIED (same doc from Day 4, applied now)
- developer.android.com/codelabs/basic-android-kotlin-compose-using-state — Tip Time codelab, `EditNumberField` hoisting example ✅ VERIFIED

**Time estimate:** 60-90 minutes.

---

## ⚠️ An Honest Heads-Up Before We Start

While verifying today's sources, I found something worth flagging rather than hiding: **Google announced a newer TextField API** at Google I/O 2025 (`TextFieldState` + `rememberTextFieldState()`), which is now the *officially recommended* approach going forward, confirmed on developer.android.com/develop/ui/compose/text/user-input and a dedicated official migration guide.

**Why we're not using it today anyway:** the classic pattern (`value` + `onValueChange`) is still what nearly all existing codelabs, tutorials, and real production code use as of today — including the exact Tip Time codelab I verified above. It also directly reuses everything you learned Day 4 (`remember` + `mutableStateOf`), so the concepts compound instead of introducing a parallel system.

**The honest plan:** learn the classic pattern properly today (it's not deprecated, just "not the newest"), and I'll flag the newer `TextFieldState` API again as an optional upgrade once you're comfortable — likely around the time you build forms with validation.

---

## ⌨️ Concept 1: TextField — Letting the User Type

### The Basic Shape

Confirmed from official sources and consistent across every verified example:

```kotlin
@Composable
fun SimpleInput() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { newText -> text = newText }
    )
}
```

Look familiar? **This is exactly Day 4's pattern**, just applied to `String` instead of `Int`.

| Piece | Job |
|---|---|
| `var text by remember { mutableStateOf("") }` | Same state mechanism as your counter — just starts as an empty string instead of `0` |
| `value = text` | Tells the TextField what to currently display |
| `onValueChange = { newText -> text = newText }` | Runs **every single keystroke** — updates `text`, which triggers recomposition, which redraws the TextField with the new value |

### The Detail That Trips People Up

A `TextField` does **not** manage its own typing automatically. If you forget `onValueChange`, or forget to actually update `text` inside it, **the TextField will visually refuse to accept input** — it'll just sit there ignoring keystrokes. This looks like a bug but is actually Compose working exactly as designed: the TextField only shows what `value` tells it to show, and nothing updates `value` unless your code does it.

```kotlin
// ❌ BROKEN — looks like a real TextField, but typing does nothing visible
TextField(
    value = text,
    onValueChange = { }   // received the new text, then threw it away!
)
```

### `TextField` vs `OutlinedTextField`

Both work identically in terms of state — purely visual difference:

| Composable | Looks like |
|---|---|
| `TextField` | Filled background, underline |
| `OutlinedTextField` | Bordered outline, transparent background |

You'll use `OutlinedTextField` today since it's what the official Tip Time codelab (verified above) uses, and it's the more common choice in modern Material 3 apps.

### Adding a Label

```kotlin
OutlinedTextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Your name") }
)
```

`{ newText -> text = newText }` and `{ text = it }` are the exact same thing — `it` is Kotlin's automatic name for a lambda's single parameter when you don't name one yourself. Both styles are common; you'll see `it` used constantly from here on.

---

## 🔼 Concept 2: Real State Hoisting — Applying Day 4's Preview

### Recall Day 4's Preview

You saw this shape:
```kotlin
// Stateless — caller controls everything
@Composable
fun CounterDisplay(count: Int, onIncrement: () -> Unit)

// Stateful — owns and passes down its own state
@Composable
fun CounterScreen()
```

Today we apply the **exact same shape** to a real, official example — confirmed directly from the Tip Time codelab (developer.android.com/codelabs/basic-android-kotlin-compose-using-state):

```kotlin
// STATELESS — doesn't own the state, just displays + reports changes
@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
    )
}

// STATEFUL — the actual owner of the state
@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }

    EditNumberField(
        value = amountInput,
        onValueChange = { amountInput = it }
    )
}
```

### Why Bother? (The Real Payoff)

This isn't busywork — it's confirmed directly by the official docs (developer.android.com/develop/ui/compose/state-hoisting) as the reason professional Compose code is written this way:

> *"State hoisting is a programming pattern where you move the state to the caller of a composable... to make the composable more reusable and testable."*

**Concretely:** `EditNumberField` above has **zero idea** it's being used for a tip calculator. It could be reused for a name field, a search box, a phone number — because it doesn't own or care about *what* the text means, only that it displays a `String` and reports changes. **That reusability is the entire point.**

### 🎮 Quick Check

Which version below is easier to reuse in a completely different screen?

```kotlin
// A
@Composable
fun NameField() {
    var name by remember { mutableStateOf("") }
    TextField(value = name, onValueChange = { name = it })
}

// B
@Composable
fun NameField(name: String, onNameChange: (String) -> Unit) {
    TextField(value = name, onNameChange)
}
```

<details>
<summary>Answer</summary>

**B.** Version A can *only* ever manage its own private `name` — nothing outside can read it, validate it, save it, or share it with another composable. Version B lets any parent screen own and control that state however it needs to — which is exactly what "hoisting" buys you.
</details>

---

## 🎯 Concept 3: Build It — Live Greeting Screen

Now combine everything from Day 1 through today into one small, real app.

```kotlin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// STATELESS — reusable input field, knows nothing about "greeting"
@Composable
fun NameInputField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Enter your name") },
        modifier = modifier
    )
}

// STATEFUL — owns the state, wires everything together
@Composable
fun LiveGreetingScreen() {
    var name by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(24.dp)) {
        NameInputField(
            name = name,
            onNameChange = { name = it },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (name.isNotEmpty()) {
            Text(text = "Hello, $name!")
        } else {
            Text(text = "Type your name above 👆")
        }
    }
}
```
 

---

## 📋 Day 5 Summary

| What you learned | Why it matters |
|---|---|
| `TextField` / `OutlinedTextField` | Your first real user-input composable |
| `value` + `onValueChange` | Same `remember`+`mutableStateOf` pattern from Day 4, now driven by typing instead of tapping |
| Real state hoisting | Turned Day 4's preview into an actual reusable, testable component — confirmed as official best practice, not a stylistic choice |
| Live recomposition on keystroke | Same "surgical redraw" mechanism as the counter — now triggered by text input |
| (Flagged, not taught) `TextFieldState` / `rememberTextFieldState()` | Newer official API — noted honestly for later, not needed yet |

---

**No rush. No pressure. You built a live, typing-reactive screen today, and you understand exactly why the reusable version is written the way it is — not just copied from a tutorial.** 🎉