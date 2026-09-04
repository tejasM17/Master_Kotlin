# 📱 Day 10 — Android: Runtime Permissions

**Date:** September 4, 2026

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 3, Day 9)              TODAY 🎯                     NEXT ⏭️
Activity + lifecycle                 Day 10: Permissions          WorkManager
                                                                   (background work)
```

**Research done for today:** Verified `developer.android.com/develop/ui/compose/notifications/notification-permission` (official, direct Compose example) + `developer.android.com/reference/kotlin/androidx/activity/compose/rememberLauncherForActivityResult.composable` (official API reference). ✅ CONFIRMED

**What changes today:** so far your apps only touched their own screen and data. The moment an app wants the camera, location, or to send notifications — Android forces you to **ask the user, at runtime**, not just declare it and hope.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | Runtime permission | A sensitive capability the user must approve while the app is running |
| 2 | `rememberLauncherForActivityResult` | The Compose tool that opens the permission popup and gets the answer |
| 3 | Build | A button that requests a permission and shows granted/denied |

---

## 1️⃣ What Is a Runtime Permission?

**Definition:** for sensitive stuff (camera, location, notifications, contacts), Android doesn't just trust your app's manifest — it **pops up a dialog asking the actual user**, the first time you need it.

**Two-step requirement (both needed, confirmed standard):**

**Step 1 — Declare it in `AndroidManifest.xml`** (this alone does NOT grant it, just states intent):
```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

**Step 2 — Actually request it at runtime**, in your code (Concept 2 below). Skipping Step 2 means the feature silently fails, even with the manifest line present.

✅ **Concept 1 done when:** you can explain why declaring a permission in the manifest alone isn't enough.

---

## 2️⃣ rememberLauncherForActivityResult

**Definition (confirmed official):** *"the API that allows you to receive results from an Activity"* — used here to launch the permission dialog and receive the user's yes/no.

**The exact official pattern (verified from developer.android.com's own Compose notification-permission guide):**

```kotlin
val context = LocalContext.current

val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
) { isGranted ->
    if (isGranted) {
        // Permission granted, proceed
    } else {
        // Permission denied, handle accordingly
    }
}
```

**Read it like this:**

| Piece | Job |
|---|---|
| `rememberLauncherForActivityResult(...)` | Sets up (but doesn't yet trigger) the permission flow |
| `ActivityResultContracts.RequestPermission()` | "I'm asking for ONE permission" (there's also `RequestMultiplePermissions()` for asking several at once) |
| `{ isGranted -> ... }` | The callback — runs once the user taps Allow or Deny |
| `permissionLauncher.launch(Manifest.permission.X)` | **Actually opens the dialog** — nothing happens until you call this, usually from a button's `onClick` |

**Same shape as everything since Day 3:** set something up, give it a callback lambda, trigger it later on user action. No new mental model — just a new API surface.

✅ **Concept 2 done when:** you can say what `launch()` does vs what `rememberLauncherForActivityResult` alone does.

---

## 3️⃣ Build: Permission-Gated Button

**Goal:** a button that requests notification permission and shows the result on screen.

### Step 1: Manifest

In `AndroidManifest.xml`, inside `<manifest>`:
```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

### Step 2: The composable

```kotlin
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDemoScreen() {
    var statusText by remember { mutableStateOf("Not requested yet") }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        statusText = if (isGranted) "✅ Granted" else "❌ Denied"
    }

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "Status: $statusText")
        Button(onClick = {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }) {
            Text("Request Notification Permission")
        }
    }
}
```

**Call it:**
```kotlin
// In setContent { }
PermissionDemoScreen()
```

### Trace the flow

| Step | What happens |
|---|---|
| 1 | Screen shows "Status: Not requested yet" |
| 2 | Tap button → `permissionLauncher.launch(...)` → system dialog appears |
| 3 | User taps Allow or Deny → the `{ isGranted -> ... }` callback fires |
| 4 | `statusText` updates → Compose recomposes → screen shows ✅ or ❌ |

**Expected result:** first run shows the real Android permission popup. Your choice instantly reflects in the `Text` above the button — same recompose-on-state-change mechanism from Day 4, just triggered by a system dialog instead of your own button.

### One Real-World Note (worth knowing, not building today)

If the user denies once, tapping the button again may show the dialog again — but if they deny **twice**, Android may stop showing the dialog at all and just auto-deny. Real apps check `shouldShowRequestPermissionRationale()` to detect this and show an explanation instead. Flagging this now; not required for today's checkpoint.

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| Runtime permission | User must approve sensitive features live, not just at install |
| Manifest declaration | Required, but not sufficient alone |
| `rememberLauncherForActivityResult` | Sets up the permission flow + callback |
| `ActivityResultContracts.RequestPermission()` | "Ask for exactly one permission" |
| `.launch(permission)` | Actually opens the dialog |

---

## ⏭️ Day 11 Preview

1. `WorkManager` — running work reliably even if the app closes
2. Scheduling a simple deferred task
3. Build: a small background task demo

Move **"Day 11"** when ready.

---

**No rush. No pressure. You just handled a real system-level user interaction, not just your app's own UI.** 🎉