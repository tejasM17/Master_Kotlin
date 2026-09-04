# 📱 Day 9 — Android: Activities & Lifecycle

**Date:** September 3, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 1-2)                    TODAY 🎯 (Week 3 starts)      NEXT ⏭️
Compose UI, state, lists,             Day 9: What's actually        Permissions,
navigation between screens            running your Compose code     WorkManager
```

**Research done for today:** Verified `developer.android.com/guide/components/activities/activity-lifecycle` (official, last updated 2026-05-13) + `developer.android.com/topic/libraries/architecture/views/activity-lifecycle-views`. ✅ CONFIRMED

**What changes today:** every day so far you called `setContent { }` inside something called `MainActivity` without asking what it actually is. Today you find out — and why it matters even in a 100% Compose app.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `Activity` | The container that hosts one screen of your app |
| 2 | The 6 lifecycle callbacks | The stages every Activity passes through |
| 3 | Why it matters in Compose | See it happen live with Logcat |

---

## 1️⃣ What Is an Activity?

**Definition (confirmed official):** an `Activity` represents **one screen** with a user interface. Your whole app since Day 1 has technically been living inside ONE Activity — `MainActivity`.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoNavApp()   // ← everything you've built lives in here
        }
    }
}
```

**Key fact:** Day 8's multi-screen navigation (list → detail) did **not** create new Activities. Both screens live inside the same single `MainActivity`. `NavHost` just swapped which composable was showing. One Activity, many composable "screens" — this is the standard modern approach, confirmed as the recommended Compose architecture.

✅ **Concept 1 done when:** you can say why Day 8's navigation didn't need a second Activity.

---

## 2️⃣ The 6 Lifecycle Callbacks

**Definition:** as the Activity is created, shown, hidden, or destroyed, Android calls specific functions on it, in a fixed, predictable order (confirmed official — "the system invokes each of these callbacks as the activity enters a new state").

| Callback | Fires when | Plain meaning |
|---|---|---|
| `onCreate()` | Activity is first created | "Set everything up" (this is where `setContent{}` lives) |
| `onStart()` | About to become visible | "About to be seen" |
| `onResume()` | User can now interact | "Fully active, on screen, tappable" |
| `onPause()` | Losing focus (e.g. a dialog appears) | "Something's covering me" |
| `onStop()` | No longer visible | "Fully hidden (user left the app)" |
| `onDestroy()` | Being removed from memory | "Cleanup, about to disappear entirely" |

**Normal app-open order:** `onCreate → onStart → onResume`
**Normal app-close order:** `onPause → onStop → onDestroy`

**Real scenario (confirmed official):** you open Activity A, it navigates to Activity B:
```
A.onPause() → B.onCreate() → B.onStart() → B.onResume() → (then, if A not visible) A.onStop()
```
Notice: **A pauses before B even starts.** This ordering guarantee is exactly why you can safely save state in `onPause` without racing the next screen.

✅ **Concept 2 done when:** you can recite the 6 callbacks in order from memory.

---

## 3️⃣ See It Happen — Logcat Demo

Add this to your existing `MainActivity`:

```kotlin
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "onCreate")
        setContent {
            TodoNavApp()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy")
    }
}
```

### Try This (3 experiments)

1. **Run the app.** Open the **Logcat** panel (bottom of Android Studio), filter by "Lifecycle". You'll see: `onCreate → onStart → onResume`
2. **Press the device Home button** (don't close the app). Watch Logcat: `onPause → onStop`
3. **Reopen the app from Recents.** Watch Logcat: `onStart → onResume` — **notice `onCreate` does NOT run again.** The Activity was reused, not recreated.

### Why This Matters Even in Compose

You almost never override these 6 methods directly in a Compose app — Compose has its own tools (`LaunchedEffect`, `DisposableEffect`) for lifecycle-aware work. But knowing this happens **underneath** explains real behavior you'll hit later:
- Why a video should pause in `onPause` (about to lose the screen)
- Why network calls shouldn't start in `onStop` (nothing to show results to)
- Why your data survives switching apps but not always a full close

✅ **Concept 3 done when:** you've watched all 3 experiments produce the log output you predicted.

---

## 🎯 Checkpoint

- [ ] Can explain why Day 8's 2 screens didn't need 2 Activities
- [ ] Can list all 6 callbacks in the correct order
- [ ] Ran the Logcat demo and saw `onPause`/`onStop` on Home press
- [ ] Confirmed `onCreate` does NOT re-run when reopening from Recents

All 4 checked → **Day 9 done.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `Activity` | One screen container — your app has had just one (`MainActivity`) this whole time |
| `onCreate → onStart → onResume` | Opening sequence |
| `onPause → onStop → onDestroy` | Closing sequence |
| `onCreate` doesn't rerun on resume | The Activity is reused, not rebuilt, when returning from background |

---

## ⏭️ Day 10 Preview

1. Runtime permissions — what they are, why Android asks
2. Requesting a permission properly (the official Compose-friendly pattern)
3. Build: a permission-gated feature (small demo)

Move **"Day 10"** when ready.

---

**No rush. No pressure. You now know what's actually running underneath every screen you've built since Day 1.** 🎉