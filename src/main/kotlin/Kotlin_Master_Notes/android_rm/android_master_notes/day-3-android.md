# 📱 Day 3 — Android: Button, onClick & Your First "Bug" That Isn't Really a Bug

**Official sources for today (verified live, 24 August 2026):**
- developer.android.com/develop/ui/compose/components/button — official Button reference ✅ VERIFIED
- developer.android.com/codelabs/jetpack-compose-basics — Button + onClick + recomposition section ✅ VERIFIED

**Time estimate:** 45-60 minutes — shorter today, the concepts are simpler, but the ending is important. Read it fully.

---

## 🔘 Concept 1: The Button Composable

### Straight From the Official Docs

```kotlin
@Composable
fun FilledButtonExample(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Filled")
    }
}
```

This exact snippet is from developer.android.com/develop/ui/compose/components/button. Notice the shape:

```kotlin
Button(onClick = { /* what happens on tap */ }) {
    // whatever goes INSIDE the button (usually a Text)
}
```

**Two separate slots.** The first (`onClick`) is *behavior*. The `{ }` block after it is *content* — what's visually drawn inside the button. This is the same "trailing lambda" pattern you already saw with `Column { }` and `Row { }` in Day 2 — Compose uses this pattern constantly once you notice it.

### The Official Button Family

Confirmed from the docs — there are 5 Material button types, but you'll use these 3 constantly:

| Composable | Looks like | Use for |
|---|---|---|
| `Button(...)` | Solid filled color | Primary action ("Submit", "Save") |
| `OutlinedButton(...)` | Outline only | Secondary action ("Cancel") |
| `TextButton(...)` | Just text, no background | Low-emphasis action ("Skip", "Learn more") |

All three share the exact same `onClick` + content-lambda shape.

### 🎮 Quick Check

```kotlin
OutlinedButton(onClick = { }) {
    Text("I'm an Outlined Button")
}
```

What will render? An outlined-style button with no fill, containing the text "I'm an Outlined Button" — and this line comes **word-for-word** from the official docs' example. You're now reading real production-pattern code, not simplified toy syntax.

---

## ⚡ Concept 2: onClick — Functions as Values

### The Part That Feels Weird At First

```kotlin
Button(onClick = { println("Tapped!") }) {
    Text("Tap me")
}
```

`onClick` isn't a String or a Boolean — it's a **function**. You're not saying "here's a value," you're saying "here's a block of code to run *later*, whenever the user taps."

The official Compose Basics codelab confirms this directly and calls out why it feels unusual:

> *"If you're not familiar with functions being used this way, it is a very powerful Kotlin feature that is used extensively in Compose... Functions are first class citizens in Kotlin, so they can be assigned to a variable, passed into other functions and even be returned from them."*

### The Mental Model

> **`onClick = { ... }` is a sealed envelope. Compose doesn't open it (run the code) until the exact moment the user's finger lifts off the button. Everything else on screen keeps running normally while that envelope just... waits.**

This connects directly to Day 5's coroutine ideas — Compose is built around events happening *when they happen*, not in a rigid top-to-bottom sequence. You already have the mental muscle for this from coroutines.

### Passing a Real Function (Not Just Inline Code)

```kotlin
fun logTap() {
    println("Button was tapped!")
}

@Composable
fun MyScreen() {
    Button(onClick = { logTap() }) {
        Text("Log a tap")
    }
}
```

You can put *any* Kotlin code inside that `{ }` — call a function, do a calculation, whatever. Same rule as any lambda you've written since Day 4 of Kotlin fundamentals (`when`, `filter { }`, etc.) — this isn't new syntax, it's the same lambda concept applied to UI.

---

## 🧪 Concept 3: Let's Try to Build a Counter (Watch Closely)

### The Setup

Let's try the "obvious" way to build a tap counter — using a totally normal Kotlin variable, exactly like you'd do in any regular Kotlin program from your earlier days.

```kotlin
@Composable
fun BrokenCounter() {
    var count = 0   // <-- a completely normal Kotlin variable

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text("Tap me")
        }
    }
}
```

Call `BrokenCounter()` from your `setContent { }` block (replace whatever you had there from Day 2), and run it.

### 🔍 Now Actually Tap the Button Several Times

Watch the "Count: 0" text closely as you tap.

**Notice anything? The number never changes on screen — even though you're tapping it repeatedly.**

### Why This "Bug" Isn't a Mistake — It's the Whole Point

This is not broken code. It's demonstrating a real, fundamental rule of Compose, and I'm showing it to you deliberately (the same way Day 1's overlapping text and Day 2's modifier-order confusion were deliberate).

Here's the honest technical reason, confirmed by the official docs' own explanation of state and recomposition:

> A composable function only redraws itself ("recomposes") when Compose is told a **piece of tracked state** has changed. A plain `var count = 0` is invisible to Compose — it's just a regular number sitting in memory. Compose has no way of knowing it changed, so it never re-runs `BrokenCounter()` to show the new value.

**Your `count++` IS happening.** If you added `println("count is now $count")` inside the `onClick`, you'd see it correctly climbing in Logcat — 1, 2, 3, 4. **The data is right. The screen just doesn't know to look at it again.**

### 🎯 Checkpoint: Prove It to Yourself

Add a print statement to confirm the value really is changing behind the scenes:

```kotlin
Button(onClick = {
    count++
    println("count is now $count")
}) {
    Text("Tap me")
}
```

Run it, tap a few times, check the **Logcat** panel at the bottom of Android Studio. You'll see the numbers climbing correctly — while the on-screen "Count: 0" stays frozen. **That gap between "the data changed" and "the screen updated" is the exact problem Day 4 solves.**

---

## 🎯 Final Checkpoint (Today's Check)

- [ ] Built and ran a working `Button` with `onClick`
- [ ] Understand `onClick = { }` holds code that runs *later*, on tap
- [ ] Built `BrokenCounter` and confirmed the on-screen number does NOT update
- [ ] Confirmed via Logcat that the underlying `count` variable DOES actually increment correctly
- [ ] Can explain out loud, in your own words: *"Why doesn't the counter update on screen?"*

If all 5 are checked — **Day 3 done.**

---

## 📋 Day 3 Summary

| What you learned | Why it matters |
|---|---|
| `Button` / `OutlinedButton` / `TextButton` | The three buttons you'll reach for in nearly every screen you ever build |
| `onClick` as a function value | Core to how ALL of Compose's interactivity works — not just buttons |
| Plain variables don't trigger recomposition | One of the single most important rules in all of Compose — you now understand it from the inside, not just as a memorized fact |

---

**No rush. No pressure. You just personally discovered — not just read about — the single most important rule in Jetpack Compose.** 🎉