# 📱 Day 4 — Android: Fixing the Counter (remember + mutableStateOf)

**Date:** August 24, 2026

**Official sources for today (verified):**
- developer.android.com/develop/ui/compose/state — official "State and Jetpack Compose" doc ✅ VERIFIED
- developer.android.com/develop/ui/compose/state-hoisting — official "Where to hoist state" doc ✅ VERIFIED
- developer.android.com/codelabs/jetpack-compose-state — "State in Jetpack Compose" codelab ✅ VERIFIED
- developer.android.com/codelabs/basic-android-kotlin-compose-using-state — Tip Time codelab (hoisting example) ✅ VERIFIED

**Time estimate:** 60-90 minutes. You've got more time today — good, this one has a satisfying payoff.

---

## 🧠 Quick Recap (30 seconds)

Yesterday you built this and watched it **not work**:

```kotlin
@Composable
fun BrokenCounter() {
    var count = 0   // a plain Kotlin variable — invisible to Compose

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text("Tap me")
        }
    }
}
```

The number in `count` really was incrementing (you confirmed it via Logcat) — but the screen never redrew. Today you fix that, and understand exactly *why* the fix works.

---

## 💾 Concept 1: `remember` — Surviving Recomposition

### The Problem `remember` Solves

Every time a composable function re-runs (recomposes), by default, **any local variable inside it gets reset** — because the function is just running again from the top, like calling a normal function twice.

```kotlin
@Composable
fun Broken() {
    var count = 0   // reset to 0 EVERY single recomposition, even if it wasn't 0 a moment ago
    // ...
}
```

`remember` tells Compose: **"Hold onto this value across recompositions — don't let it reset."**

### The Official Definition

Confirmed directly from developer.android.com/develop/ui/compose/state:
> *"A value computed by `remember` is stored in the Composition during initial composition, and the stored value is returned during recomposition."*

### The Mental Model

> **`remember` is a sticky note Compose attaches to this specific spot in the UI tree. Even when the function re-runs, Compose checks the sticky note first instead of starting fresh.**

### Important Limit (know this now, not later)

`remember` only survives **recomposition** (normal UI updates). It does **NOT** survive:
- Screen rotation (a "configuration change")
- The app process being killed

For that, there's a cousin called `rememberSaveable` — you'll meet it properly in a later day. Today, plain `remember` is exactly right for what we're building.

---

## 🔭 Concept 2: `mutableStateOf` — The Observable Container

### `remember` Alone Isn't Enough

`remember` solves "don't reset the value." But Compose still needs to know **when that value changes**, so it knows to redraw. That's a *different* job — done by `mutableStateOf`.

### The Official Pattern

```kotlin
var count by remember { mutableStateOf(0) }
```

Two ideas stacked together:

| Piece | Job |
|---|---|
| `mutableStateOf(0)` | Creates a special observable box holding `0`. Compose can "subscribe" to this box and get notified on changes. |
| `remember { ... }` | Makes sure that box itself isn't recreated from scratch on every recomposition |

### Why They're Always Used Together

If you used `mutableStateOf` *without* `remember`:
```kotlin
// ❌ Creates a BRAND NEW observable box every recomposition — defeats the purpose
var count by mutableStateOf(0)
```
The box would reset to a fresh `0` every time the function re-ran — same problem as before, just with extra steps.

If you used `remember` *without* `mutableStateOf`:
```kotlin
// ❌ Value survives, but Compose has no way to detect when it changes
var count = remember { 0 }
```
This compiles, but changing `count` won't trigger a redraw — Compose isn't watching a plain `Int`, only a `MutableState<Int>`.

**You need both, together, every time.** This exact combined pattern is confirmed as the standard approach across every official doc verified above.

### What `by` Is Actually Doing

```kotlin
var count by remember { mutableStateOf(0) }
```

Without `by`, you'd have to write the clunkier:
```kotlin
var count = remember { mutableStateOf(0) }
count.value++              // have to say .value every time
Text("Count: ${count.value}")
```

`by` is a Kotlin feature (a **property delegate**) that lets you treat `count` like a normal `Int` — write `count++`, read `count` directly — while Compose still tracks every change underneath. Both styles work; `by` is simply what you'll see in almost all real Compose code, including every official example verified today.

### 🎮 Quick Check

What's wrong here?

```kotlin
@Composable
fun Broken() {
    val count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) { Text("Count: $count") }
}
```

<details>
<summary>Answer</summary>

`val` instead of `var`. `count++` requires reassigning the value, which needs `var`. This is a genuine compile error in real Android Studio — good one to recognize on sight, since it's an easy typo to make once `by` makes `count` look like a normal variable.
</details>

---

## 🛠️ Concept 3: Fixing `BrokenCounter` For Real

### The Complete Fix

```kotlin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun WorkingCounter() {
    var count by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text("Tap me")
        }
    }
}
```

**The only change from yesterday's broken version:** one line.
```kotlin
var count = 0                                  // yesterday (broken)
var count by remember { mutableStateOf(0) }    // today (fixed)
```

### 🎯 Build It

1. Open `MainActivity.kt`
2. Add `WorkingCounter()` above or below your `BrokenCounter()` (keep both — comparing them side by side is the point)
3. In `setContent { }`, call `WorkingCounter()` instead of `BrokenCounter()`
4. Run it. Tap the button several times.

### Expected Result

The number now updates **live, on every tap** — exactly what you expected yesterday, and it wasn't magic: it's `remember` (keep the box) + `mutableStateOf` (make the box observable) working together, triggering recomposition of just the `Text` that reads `count`.

### Why Only the `Text` Redraws (Not the Whole Screen)

This is worth sitting with for a second, confirmed by how recomposition actually works: Compose tracks *which specific composables read a given piece of state*. When `count` changes, **only** the `Text(text = "Count: $count")` line gets re-run — the `Column`, the `Button` itself, everything else is untouched. This is why Compose apps stay fast even with complex screens: recomposition is surgical, not a full-screen refresh.

---

## 🧩 Optional Stretch: A First Taste of State Hoisting (skip freely if you're tired)

You don't need this to complete Day 4 — but if you have energy, this previews something important for Day 5+.

Right now, `WorkingCounter` **owns** its own state — nobody outside the function can see or control `count`. The official docs call this a **stateful composable**.

```kotlin
// Stateful — count lives and dies inside this one function
@Composable
fun WorkingCounter() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) { Text("Count: $count") }
}
```

The official pattern for making it reusable is **state hoisting** — moving the state *up* to whoever calls this composable, and passing it back down as parameters:

```kotlin
// Stateless — the caller now owns and controls count
@Composable
fun CounterDisplay(count: Int, onIncrement: () -> Unit) {
    Button(onClick = onIncrement) { Text("Count: $count") }
}

// The caller (parent) now owns the state
@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    CounterDisplay(count = count, onIncrement = { count++ })
}
```

This exact before/after pattern is confirmed directly from official sources (the Bugfender guide cross-referencing developer.android.com's own examples, and the official `HelloScreen`/`HelloContent` example in the State doc). Don't worry about mastering this today — just notice the shape. **State goes down (as a parameter), events go up (as a function call).** This one sentence is literally how every non-trivial Compose screen is built. You'll use it for real starting Day 5.

---

## 🎯 Final Checkpoint (Today's Check)

- [ ] Understand what `remember` alone does (survives recomposition)
- [ ] Understand what `mutableStateOf` alone does (makes a value observable)
- [ ] Understand why you always need **both together**
- [ ] Built `WorkingCounter` and watched the number update live on tap
- [ ] Can explain out loud: "why does only the Text redraw, not the whole Column?"

If all 5 are checked — **Day 4 done, and yesterday's cliffhanger is officially resolved.**

---

## 📋 Day 4 Summary

| What you learned | Why it matters |
|---|---|
| `remember` | Keeps a value alive across recompositions instead of resetting it |
| `mutableStateOf` | Wraps a value so Compose can detect changes to it |
| `remember { mutableStateOf(x) }` | The single most common line of code in beginner-to-intermediate Compose apps |
| `by` delegate | Lets you use state like a normal variable (`count++`) instead of `.value` everywhere |
| Surgical recomposition | Only composables that actually read changed state get redrawn — this is *why* Compose is fast |
| (Preview) State hoisting | State down, events up — the pattern nearly all real Compose screens follow |
 

---

**No rush. No pressure. You didn't just fix a bug today — you understood exactly why it was broken and exactly why the fix works, from first principles.** 🎉