# 📱 Day 1 — Android: Your First Composable App

**Prerequisite check:** ✅ Android Studio already installed (confirmed)
**Today's concepts (exactly 3, no more):**
1. What a Composable function actually is
2. Creating your first real Android Studio project
3. The `Text` composable — building and running something on screen

**Official source for today:** developer.android.com/codelabs/basic-android-kotlin-compose-first-app (✅ verified live)

**Time estimate:** 60-90 minutes. No pressure

---

## 🧠 Concept 1: What Is a Composable Function?

### The Old Way vs Compose Way (context, 2 min read)

Before Compose, Android UIs were built with XML files (a separate "layout language") plus Kotlin/Java code that manipulated them. You'd write `findViewById()` everywhere. It was clunky and error-prone.

**Jetpack Compose throws that out.** Your UI is now just... Kotlin functions.

### The Core Idea

```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
```

That's it. That's a complete, valid piece of UI. Let's break down exactly what's happening:

| Piece | What it means |
|---|---|
| `@Composable` | An annotation — a tag that tells the Compose compiler "this function describes part of a UI, treat it specially" |
| `fun Greeting(name: String)` | A totally normal Kotlin function — takes input, does something |
| `Text(text = "Hello $name!")` | Calls another composable (`Text`, built into Compose) to actually draw text on screen |

### The Mental Model (memorize this)

> **A composable function describes WHAT the UI should look like, given some data. It does NOT return anything, and it does NOT manually update the screen. Compose handles the "how to actually draw it" part for you.**

This is called **declarative UI**. You declare *what* you want ("a greeting with this name"), not *how* to build it step-by-step ("create a TextView, set its text, add it to a layout, measure it...").

### 🎮 Quick Check (answer before continuing)

Which of these is a valid composable function? (Think, then check below)

```kotlin
// A
fun ShowMessage(msg: String) {
    Text(text = msg)
}

// B
@Composable
fun ShowMessage(msg: String) {
    Text(text = msg)
}
```

<details>
<summary>Answer</summary>

**B** is correct. Without `@Composable`, the Kotlin compiler has no idea this function is meant to describe UI — it would actually fail to compile once you try to call `Text()` inside it, because `Text()` itself requires being called from within a composable context.
</details>

---

## 🛠️ Concept 2: Creating Your First Project

Since Android Studio is already installed, let's go straight to project creation.

### Step-by-Step (matches the official codelab exactly)

1. Open Android Studio
2. If you see the "Welcome" screen → click **New Project**. If a project is already open → **File → New → New Project**
3. In the template list, select **Empty Activity** (this is the Compose-based empty template — make sure it does NOT say "Empty Views Activity", that's the old XML system)
4. Configure your project:
    - **Name:** `MyFirstApp` (or anything you like)
    - **Package name:** leave the auto-generated one (e.g. `com.example.myfirstapp`)
    - **Minimum SDK:** leave the default Android Studio suggests
    - **Language:** Kotlin (should be default)
5. Click **Finish** and wait for Gradle to sync (this can take a minute or two the first time — this is normal, Android Studio is downloading dependencies)

### Understanding What Just Got Created

Open the file explorer panel on the left, navigate to:
```
app → java → com.example.myfirstapp → MainActivity.kt
```

You'll see something like:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstAppTheme {
                // Compose UI goes here
            }
        }
    }
}
```

**Don't worry about memorizing this yet.** Just recognize the shape:
- `MainActivity` — the entry point of your app (we'll cover Activities properly in Week 3)
- `setContent { }` — this is the bridge between old-style Android and Compose. Everything inside these curly braces is where your composables live.

### 🎯 Checkpoint 2

- [ ] Project created successfully
- [ ] Gradle sync finished without errors (green bar at bottom, no red error banners)
- [ ] You can locate `MainActivity.kt` in the file tree

If Gradle sync failed: this is almost always an internet connection issue (it's downloading libraries). Just click **"Try Again"** / **Sync Now** if you see that option.

---

## ✍️ Concept 3: The Text Composable — Build & Run

Now let's actually put something on screen that's *yours*, not the template default.

### Step 1: Find the Default Composable

Inside `MainActivity.kt`, scroll down. Android Studio's template already generated something like this:

```kotlin
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
```

This IS a composable function, exactly like Concept 1 explained — Android Studio just wrote it for you.

### Step 2: Make It Yours

Change the text so it's clearly your own work. Find this line:

```kotlin
Greeting("Android")
```

Change `"Android"` to your own name:

```kotlin
Greeting("YourName")
```

### Step 3: Run It

1. Look at the top toolbar for a device dropdown (might say something like "Pixel 8" or similar)
2. If no emulator exists yet, click the dropdown → **Device Manager** → create one (Android Studio will guide you — pick any modern phone, like Pixel 7)
3. Click the green ▶️ **Run** button
4. Wait for the emulator to boot (first time can take 2-3 minutes — be patient, no rush)

### Expected Result

You should see a phone screen appear with text on it:

```
Hello YourName!
```

**That text came from a composable function you understand the mechanics of.** That's the whole loop: write a composable → Compose renders it → you see it on a real (virtual) device.

### 🎯 Checkpoint 3 (Today's Final Check)

- [ ] App builds without red errors
- [ ] Emulator (or physical device) launches
- [ ] Your custom text appears on screen
- [ ] You can explain out loud, in your own words, what `@Composable` does

If you can check all 4 boxes — **Day 1 is genuinely complete. Stop here for today if you want.** No need to rush into Day 2.

---

## 🧩 Optional Stretch (only if you have energy left — skip freely)

Try changing the `Text()` call to add a second line, by adding another `Text()` composable right below the first one inside `Greeting`:

```kotlin
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
    Text(text = "Welcome to Day 1!", modifier = modifier)
}
```

Run it again. Notice anything odd about how the two texts are positioned? (Hint: you'll likely see them overlapping — that's expected! We haven't learned layouts yet — that's Day 2's job, specifically `Column`.)

---

## 📋 Day 1 Summary

| What you learned | Why it matters |
|---|---|
| `@Composable` annotation | Every single Compose UI element starts here — this is the foundation of everything ahead |
| Declarative UI mental model | Changes how you think about UI forever — you describe outcomes, not steps |
| Project structure (`MainActivity.kt`, `setContent{}`) | You now know where your code lives |
| `Text()` composable | Your first real, working UI element |
| Build → Run → See on device loop | The core development loop you'll repeat thousands of times |

---

## 🚀 When You're Ready for Day 2

Day 2 will cover (also just 1-3 concepts, same pacing):
1. **Modifiers** — how to control size, padding, spacing of composables
2. **Column, Row, Box** — the three basic layout building blocks that fix the overlapping text issue above
 
---

**No rush. No pressure. You completed real, working Android code today.** 🎉