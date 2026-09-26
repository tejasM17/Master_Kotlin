# 📱 Day 18 — Android: Capstone Planning (Week 7 Begins)

**Date:** September 22, 2026

> 💬 *"By failing to prepare, you are preparing to fail."* — Benjamin Franklin

---

## 🗺️ THE BIG PICTURE

```
DONE ✅ (Week 1-6)                         TODAY 🎯 (Week 7 starts)        NEXT ⏭️
17 days: Compose, state, nav, lifecycle,   Day 18: PLAN the capstone —     Day 19: Build
permissions, WorkManager, Room, Retrofit,  no new code yet, just scope     the detail screen
error handling, MVVM, Hilt                 + structure                    + favorites
```

**Research done for today:** Verified against multiple current industry sources on Android package structure. ⚠️ **Honest flag, same as Day 15:** there is no single official Google page that mandates one exact folder layout — "package by feature" is the confirmed dominant convention (and matches how Google's own **Now in Android** reference app, flagged back on Day 1, is actually organized), but for an app this size, a simpler layer-based structure is the calibrated, honest recommendation. I'll tell you exactly when to switch.

**Why planning gets its own day:** every day 1-17 was "learn a thing, use it immediately." A capstone is different — the risk isn't *not knowing enough*, it's building something unfocused. Today prevents that.

---

## 🎯 Today = 3 Concepts Only

| # | Concept | One-line definition |
|---|---|---|
| 1 | Capstone scope | Deciding exactly what's in and what's out |
| 2 | Package structure | Organizing files so the project reads like a story, not a junk drawer |
| 3 | Build | Reorganize Quote Keeper's existing files, write this week's concrete checklist |

---

## 1️⃣ Capstone Scope — "Quote Keeper Pro"

**The project:** take the existing Quote Keeper (Days 16-17) and add exactly the pieces that prove skills it hasn't touched yet — no more, no less.

**Direct mapping — every feature ties back to a specific day, on purpose:**

| New Feature | Proves | From Day |
|---|---|---|
| Tap a quote → detail screen | Navigation Compose | Day 8 |
| ❤️ "Favorite" toggle, saved to Room | Room writes beyond insert-only | Day 12-13 |
| Loading / Success / Error states, done properly | Sealed class UI state | Day 15 |
| Background auto-refresh every few hours | WorkManager | Day 11 |
| Runtime permission for notifications on new quote | Permissions | Day 10 |

**What's explicitly OUT of scope (and why that's correct, not lazy):** user accounts, multiple users, cloud sync across devices, testing (that's a separate, later topic). A capstone proves breadth of what you've learned — it is not "build the next Instagram." Confirmed good practice: scope creep is the #1 reason portfolio projects never finish.

✅ **Concept 1 done when:** you can recite the 5 features and which day each one proves.

---

## 2️⃣ Package Structure — Right-Sized, Not Over-Engineered

**For THIS app's size (5 features, 1 domain — quotes), layer-based is the honest right call:**

```
com.example.quotekeeper
├── data
│   ├── Quote.kt              (Entity)
│   ├── QuoteDao.kt
│   ├── QuoteApi.kt
│   ├── QuoteResponse.kt
│   └── QuoteRepository.kt
├── di
│   └── AppModule.kt
├── ui
│   ├── list
│   │   ├── QuoteListScreen.kt
│   │   └── QuoteListViewModel.kt
│   ├── detail
│   │   ├── QuoteDetailScreen.kt
│   │   └── QuoteDetailViewModel.kt
│   └── theme
├── worker
│   └── QuoteSyncWorker.kt
├── QuoteKeeperApp.kt
└── MainActivity.kt
```

**The one rule that matters today (confirmed industry-standard, cross-referenced across current sources):** *group files by what they DO (data/di/ui/worker), and inside `ui`, group by SCREEN (list/detail) — not by file type alone.* This is the same "package by feature" idea, scaled down honestly for a 2-screen app.

**When to switch to full package-by-feature:** the moment you add a 3rd unrelated domain (not just another quote screen — something like "user profile" or "settings"). Not today. Noting it so you recognize the signal later, not because you need it now.

✅ **Concept 2 done when:** you can say why `ui` splits into `list`/`detail`, but `data` doesn't.

---

## 3️⃣ Build: Reorganize + Write the Week's Checklist

### Step 1 — Physically reorganize Quote Keeper

In Android Studio's Project panel, create the folders above and **drag your existing Day 16-17 files into them**:
- `Quote.kt`, `QuoteDao.kt`, `QuoteApi.kt`, `QuoteResponse.kt`, `AppDatabase.kt`, `QuoteRepository.kt` → `data`
- `AppModule.kt` → `di`
- `QuoteScreen.kt` → rename to `QuoteListScreen.kt`, move to `ui/list`
- `QuoteViewModel.kt` → rename to `QuoteListViewModel.kt`, same folder

Rebuild the project (**Build → Rebuild Project**) after moving files — Android Studio usually fixes imports automatically, but always verify it compiles clean before moving on.

### Step 2 — This Week's Concrete Build Order

```
Day 18 (today): Plan + reorganize                    ✅
Day 19: Detail screen + Navigation (Day 8 skill)
Day 20: Favorite toggle — Room update query (Day 13 skill, extended)
Day 21: Proper sealed-class UiState (Day 15 skill, applied for real this time)
Day 22: WorkManager background sync (Day 11 skill, reconnected)
Day 23: Notification permission for new quotes (Day 10 skill, reconnected)
Day 24: Polish pass + README + push to GitHub
```

**Why this order:** each day depends only on skills already proven, never on something from a later day — same incremental rule that's held since Day 1.

---

## 🎯 Checkpoint

- [ ] Can recite all 5 capstone features and which day each proves
- [ ] Can explain why layer-based (not full feature-based) is the right call at this size
- [ ] Reorganized Quote Keeper's actual files into the new folder structure
- [ ] Project still builds with zero errors after reorganizing
- [ ] Wrote/copied the Day 19-24 checklist somewhere you'll actually see it again

All 5 checked → **Day 18 done. The capstone has a real shape now, not just a vibe.**

---

## 📋 Summary Table

| Learned | Meaning |
|---|---|
| Scoped feature list | 5 features, each tied to a specific already-learned skill — no scope creep |
| Layer-based structure (for now) | `data` / `di` / `ui` (split by screen) / `worker` |
| Package-by-feature | The convention to switch to later, once a 2nd unrelated domain appears |
| Week 7 build order | Each day only depends on earlier, already-proven skills |

---

## ⏭️ Day 19 Preview

1. Reconnecting Day 8's navigation pattern to Quote Keeper
2. A `QuoteDetailScreen` showing one quote, full-size
3. Build: tap a quote in the list → real detail screen, real data passed through

Move **"Day 19"** when ready.
 
---

**No rush. No pressure. A clear plan is still real progress — most unfinished portfolio projects failed here, not at the coding.** 🎉