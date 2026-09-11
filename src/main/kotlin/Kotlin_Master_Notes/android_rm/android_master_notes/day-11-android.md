# 📱 Day 11 — Android: WorkManager (Background Work)

**Date:** August 31, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 3)                      TODAY 🎯                      NEXT ⏭️
Activity/Lifecycle, Permissions       Day 11: Guaranteed            Week 4: Room
                                       background work               (local database)
```

- **Research done for today:** Verified `developer.android.com/reference/kotlin/androidx/work/WorkManager` (official reference) 
- `developer.android.com/reference/androidx/work/CoroutineWorker` (official reference) 
- `developer.android.com/codelabs/basic-android-kotlin-compose-workmanager` (official codelab). ✅ CONFIRMED

- **What changes today:** your Day 5 coroutines (Weather App, News Feed Loader) all ran **while the app was open**. Close the app → they're gone. Today's tool survives that — the work still happens even if the user leaves.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | `WorkManager` | Android's tool for guaranteed background work, even after the app closes |
| 2 | `CoroutineWorker` | Where you write the actual suspend work to run |
| 3 | Build | Enqueue a real background task and watch it run |

---

## 1️⃣ What Is WorkManager?

**Definition (confirmed official):** *"the recommended library for persistent work. Scheduled work is guaranteed to execute sometime after its Constraints are met."*

**Why not just a coroutine?**

| Approach | Survives app close? | Use for |
|---|---|---|
| Plain coroutine (`launch`/`async`) | ❌ No — dies with the app | Live screen work (Day 5-10) |
| `WorkManager` | ✅ Yes — Android reschedules it | Uploading a file, syncing data, sending logs |

**Under the hood (confirmed official):** WorkManager doesn't invent new mechanisms — it uses `JobScheduler` on modern Android, or a fallback on old versions. **You write one API; Android picks the right engine.**

**One real limit to know:** confirmed official — *"All background work is given a maximum of ten minutes to finish its execution."* Long tasks need to be split or use different tools (foreground services) — not today's concern, just good to know it exists.

✅ **Concept 1 done when:** you can say the one key difference between a plain coroutine and WorkManager.

---

## 2️⃣ CoroutineWorker — Where the Work Lives

**Definition (confirmed official):** *"A ListenableWorker implementation that provides interop with Kotlin Coroutines. Override the doWork function to do your suspending work."*

```kotlin
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.content.Context
import kotlinx.coroutines.delay
import android.util.Log

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "Background work started")
        delay(3000)   // pretend this is a real upload/sync
        Log.d("SyncWorker", "Background work finished")
        return Result.success()
    }
}
```

**Read it like this:**

| Piece | Job |
|---|---|
| `: CoroutineWorker(context, params)` | Required constructor shape — Android creates this for you |
| `override suspend fun doWork()` | **This is the only method you actually write.** Runs your real logic |
| `return Result.success()` | Tells WorkManager "done, it worked" (also `Result.failure()`, `Result.retry()`) |

**Confirmed official default:** if you don't configure anything else, `CoroutineWorker` runs on `Dispatchers.Default` — connects directly back to Day 5's Province 4 (Dispatchers) knowledge. Nothing new to learn there, just applied in a new place.

✅ **Concept 2 done when:** you can say what the ONE method you must override is called.

---

## 3️⃣ Build: Enqueue and Run It

### Step 1: Add the dependency

In `app/build.gradle.kts`:
```kotlin
implementation("androidx.work:work-runtime-ktx:2.10.0")
```

### Step 2: Create and enqueue the request (confirmed official pattern)

```kotlin
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun WorkManagerDemoScreen() {
    val context = LocalContext.current

    Button(onClick = {
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>().build()
        WorkManager.getInstance(context).enqueue(syncRequest)
    }) {
        Text("Start Background Sync")
    }
}
```

**Call it:**
```kotlin
// In setContent { }
WorkManagerDemoScreen()
```

### Trace the flow

| Step | What happens |
|---|---|
| 1 | Tap button → `OneTimeWorkRequestBuilder<SyncWorker>().build()` creates one work request |
| 2 | `WorkManager.getInstance(context).enqueue(syncRequest)` hands it to Android |
| 3 | Android runs `SyncWorker.doWork()` — check **Logcat**, filter "SyncWorker" |
| 4 | 3 seconds later: "Background work finished" appears |

### 🧪 The Real Test (do this)

1. Run the app, tap the button
2. **Immediately press Home** (leave the app, don't force-close it)
3. Watch Logcat anyway — "Background work finished" still appears after 3 seconds

**That's the entire point of today.** The work kept running after you left. A plain `launch { delay(3000) }` inside the composable would have been cancelled the moment you left the screen (structured concurrency from Day 5 — no scope, no survival). `WorkManager` is a deliberate escape hatch from that rule, for exactly the cases that need it.

---

## 🎯 Checkpoint

- [ ] Can state the one key difference between a coroutine and WorkManager
- [ ] Can name the one method every `CoroutineWorker` must override
- [ ] Built `SyncWorker` + enqueued it, saw both Logcat lines
- [ ] Confirmed the work finished even after leaving the app

All 4 checked → **Day 11 done. Week 3 complete.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| `WorkManager` | Guaranteed background work, survives app close |
| `CoroutineWorker` | Suspend-function-friendly worker base class |
| `doWork()` | The one method you override — your actual task |
| `OneTimeWorkRequestBuilder<T>().build()` | Packages your worker into a request |
| `WorkManager.getInstance(context).enqueue(...)` | Hands it off to Android |
| `Result.success()/.failure()/.retry()` | How you report the outcome |

---

## ⏭️ Day 12 Preview — Week 4 Begins

1. `Room` — what it is, why apps need local databases
2. `@Entity` — defining a table as a data class
3. Build: the first Room table (no queries yet — that's Day 13)

Say **"Day 12"** when ready.

---

**No rush. No pressure. You just made something survive without you — that's a genuinely different category of skill than anything through Day 10.** 🎉