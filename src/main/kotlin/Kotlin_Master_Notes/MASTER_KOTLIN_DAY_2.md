# 🎮 MASTER KOTLIN — Day 2: Control Flow & Functions

**Session Created:** July 31, 2026  
**Difficulty:** ⭐⭐⭐ (Beginner to Intermediate)  
**Time to Complete:** ~1-3 hours hands-on   

---

## 📚 Table of Contents

1. [Level 4: Control Flow](#level-4-control-flow--making-decisions)
2. [Level 5: Functions](#level-5-functions--reusable-spells--recipes)
3. [Quick Reference](#quick-reference)
4. [Common Patterns](#common-patterns)
5. [Next Steps](#next-steps)

---

# Level 4: Control Flow — Making Decisions

## 🧠 The Story

Your hero is now **Day 4**, and they've leveled up significantly. But here's the problem: the program always does the **exact same thing** every time it runs. Boring!

Today, the **NPC Blacksmith** says: *"Welcome, hero! Your level determines what I can forge for you."*

- **Level < 5?** → "You're too weak. Come back later."
- **Level 5-10?** → "I can forge you a basic sword!"
- **Level > 10?** → "Ah, a true warrior! Here's a legendary blade!"

Your program now needs **intelligence**. It needs to make **decisions based on data**. This is where **Control Flow** comes in—the ability to say *"IF this is true, DO that; OTHERWISE, do something else."*

---

## The Decision Makers 🎯

| Tool | Use Case | Best For | Personality |
|------|----------|----------|-------------|
| **if / else** | Simple binary choice (yes/no) | Two paths only | The basics |
| **if / else if / else** | Multiple conditions (chain) | Ordered checking, multiple gates | Step-by-step |
| **when** | Many specific values or ranges | Exact matches, elegance | The power move |

---

## #1 IF/ELSE — The Binary Gate 🚪

### Story

You're standing at a gate with one question: *"Are you level 5 or higher?"*
- **Yes?** → Door opens, you pass through.
- **No?** → Door stays closed, you're turned away.

### The Syntax

```kotlin
if (condition) {
    // Do this if condition is TRUE
} else {
    // Do this if condition is FALSE
}
```

### Real Example

```kotlin
fun main() {
    val heroLevel = 7
    
    if (heroLevel >= 5) {
        println("Welcome, warrior! You may enter the castle.")
    } else {
        println("You're too weak. Come back when you're level 5.")
    }
}
```

**Output:**
```
Welcome, warrior! You may enter the castle.
```

### Comparison Operators (The Questions)

These are how you **ask questions**:

| Operator | Means | Question | Example |
|----------|-------|----------|---------|
| `>` | Greater than | "Is A bigger than B?" | `level > 5` |
| `<` | Less than | "Is A smaller than B?" | `health < 20` |
| `>=` | Greater than or equal | "Is A at least B?" | `age >= 18` |
| `<=` | Less than or equal | "Is A at most B?" | `mana <= 0` |
| `==` | Equal to | "Are they the same?" | `name == "Hero"` |
| `!=` | Not equal to | "Are they different?" | `status != "dead"` |

### Simple If (No Else)

Sometimes you only care if something is TRUE:

```kotlin
val hasKey = true

if (hasKey) {
    println("✅ Door unlocked!")
}
// If false, nothing happens (no else block)
```

### If with Variables

```kotlin
fun main() {
    val playerGold = 150
    val swordPrice = 100
    
    if (playerGold >= swordPrice) {
        println("💰 You can afford the sword!")
    } else {
        println("💰 You need ${swordPrice - playerGold} more gold.")
    }
}
```

**Output:**
```
💰 You can afford the sword!
```

### Key Patterns

**Pattern 1: Check Eligibility**
```kotlin
if (age >= 18) {
    println("Adult")
} else {
    println("Minor")
}
```

**Pattern 2: Validate Input**
```kotlin
if (userInput != "") {
    println("Valid input: $userInput")
} else {
    println("Empty input!")
}
```

**Pattern 3: Resource Check**
```kotlin
if (mana >= 30) {
    println("Cast spell!")
} else {
    println("Not enough mana")
}
```

---

## #2 IF / ELSE IF / ELSE — The Chain ⛓️

### Story

Now there are **three gates**, each asking a different question:

1. *"Are you level 1-4?"* → Gate A
2. *"Are you level 5-10?"* → Gate B
3. *"Are you level 11+"?* → Gate C

Your hero goes through them **in order**. Once they pass a gate, they **stop checking**.

### The Syntax

```kotlin
if (condition1) {
    // Checked first
} else if (condition2) {
    // Checked second (only if condition1 is false)
} else if (condition3) {
    // Checked third (only if condition1 & condition2 are false)
} else {
    // Fallback (if ALL above are false)
}
```

### Real Example: NPC Blacksmith

```kotlin
fun main() {
    val heroLevel = 8
    
    if (heroLevel < 5) {
        println("Blacksmith: You're too weak. Come back later.")
    } else if (heroLevel <= 10) {
        println("Blacksmith: I can forge you a basic sword!")
    } else {
        println("Blacksmith: Ah, a true warrior! Here's a legendary blade!")
    }
}
```

**Output:**
```
Blacksmith: I can forge you a basic sword!
```

### Step-by-Step Execution

When you run this with `heroLevel = 8`:

```
1. Is heroLevel < 5?          → 8 < 5?  → NO ❌
2. Is heroLevel <= 10?        → 8 <= 10? → YES ✅
   Execute this block and STOP
3. Never reaches the else block
```

### Important: Order Matters! 🔴

```kotlin
// ❌ WRONG ORDER
if (heroLevel < 10) {
    println("Weak")
} else if (heroLevel < 5) {     // This can NEVER be true!
    println("Very weak")        // Unreachable code
}

// ✅ CORRECT ORDER
if (heroLevel < 5) {
    println("Very weak")
} else if (heroLevel < 10) {
    println("Weak")
}
```

### Multiple Conditions Example

```kotlin
fun main() {
    val heroLevel = 15
    val heroHealth = 30
    
    if (heroHealth <= 0) {
        println("💀 You are dead. Game over!")
    } else if (heroLevel < 10) {
        println("⚠️  You're a weak adventurer.")
    } else if (heroLevel < 15) {
        println("💪 You're a strong warrior!")
    } else {
        println("🌟 You're a legendary hero!")
    }
}
```

**Output:**
```
🌟 You're a legendary hero!
```

### Real-World Pattern: Status Checker

```kotlin
fun checkStatus(health: Int) {
    if (health > 75) {
        println("Excellent")
    } else if (health > 50) {
        println("Good")
    } else if (health > 25) {
        println("Wounded")
    } else {
        println("Critical")
    }
}

fun main() {
    checkStatus(100)   // Excellent
    checkStatus(60)    // Good
    checkStatus(30)    // Wounded
    checkStatus(10)    // Critical
}
```

---

## #3 WHEN — The Power Move 💥

### Story

The **when** expression is like a **switch board**. Instead of asking ordered questions, you're saying: *"Give me the exact value, and I'll tell you what to do."*

```
Value is "A" → Do this
Value is "B" → Do that
Value is "C" → Do something else
Default → If none match
```

### The Syntax

```kotlin
when (value) {
    matchValue1 -> action1
    matchValue2 -> action2
    matchValue3 -> action3
    else -> defaultAction
}
```

### Real Example: NPC Dialogue

```kotlin
fun main() {
    val npcName = "Healer"
    
    when (npcName) {
        "Blacksmith" -> println("🔨 Blacksmith: I forge weapons!")
        "Healer" -> println("🏥 Healer: I mend your wounds!")
        "Guard" -> println("👮 Guard: State your business!")
        "Merchant" -> println("🛍️  Merchant: Best deals in town!")
        else -> println("❓ Unknown NPC")
    }
}
```

**Output:**
```
🏥 Healer: I mend your wounds!
```

### When with Ranges

A **range** is a sequence of numbers: `1..10` means 1, 2, 3... all the way to 10.

```kotlin
fun main() {
    val heroLevel = 8
    
    when (heroLevel) {
        in 1..4 -> println("🌱 Novice (Level $heroLevel)")
        in 5..10 -> println("💪 Warrior (Level $heroLevel)")
        in 11..20 -> println("⚔️  Champion (Level $heroLevel)")
        else -> println("🌟 Legend (Level $heroLevel)")
    }
}
```

**Output:**
```
💪 Warrior (Level 8)
```

### When with Multiple Matches

You can match multiple values in one branch:

```kotlin
fun main() {
    val day = "Saturday"
    
    when (day) {
        "Saturday", "Sunday" -> println("🎉 Weekend! Party time!")
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> println("😴 Work day...")
        else -> println("❓ Invalid day")
    }
}
```

**Output:**
```
🎉 Weekend! Party time!
```

### When Without a Value (Advanced)

```kotlin
fun main() {
    val level = 8
    val health = 30
    
    when {
        health <= 0 -> println("💀 Dead")
        level < 5 && health < 50 -> println("Weak and hurt")
        level > 10 || health > 75 -> println("Strong!")
        else -> println("Average")
    }
}
```

This version evaluates **expressions** instead of matching values.

### When vs If/Else

| Situation | Use `when` | Use `if/else` |
|-----------|-----------|---------------|
| Match exact values | ✅ Better | ❌ Verbose |
| Check ranges | ✅ Cleaner with `in` | ✅ Works but longer |
| Multiple conditions with `&&` / `\|\|` | ❌ Awkward | ✅ Better |
| Two branches only | ❌ Overkill | ✅ Better |

---

## Logical Operators — Combining Conditions 🧩

Sometimes one condition isn't enough. You need **multiple conditions at once**.

| Operator | Means | Example | Logic |
|----------|-------|---------|-------|
| `&&` | **AND** (both must be true) | `level > 10 && health > 50` | All conditions true |
| `\|\|` | **OR** (at least one must be true) | `hasPotion \|\| canSpell` | At least one true |
| `!` | **NOT** (flips true/false) | `!isDead` | Reverses the result |

### AND (&&) — Both Must Be True 🔗

"I need BOTH conditions to be true"

```kotlin
fun main() {
    val heroLevel = 12
    val heroHealth = 100
    
    if (heroLevel > 10 && heroHealth > 50) {
        println("✅ You're strong AND healthy. Ready for battle!")
    } else {
        println("❌ You need to be both strong AND healthy.")
    }
}
```

**Output:**
```
✅ You're strong AND healthy. Ready for battle!
```

**Truth Table:**
```
true  && true  = true   ✅
true  && false = false  ❌
false && true  = false  ❌
false && false = false  ❌
```

**Memory:** `&&` is like two doors. You can only pass if **both** doors are open.

### OR (||) — At Least One Must Be True ✨

"I need AT LEAST ONE condition to be true"

```kotlin
fun main() {
    val hasKey = false
    val hasPicklock = true
    
    if (hasKey || hasPicklock) {
        println("✅ You can open the door! (with key OR picklock)")
    } else {
        println("❌ You can't open the door.")
    }
}
```

**Output:**
```
✅ You can open the door! (with key OR picklock)
```

**Truth Table:**
```
true  || true  = true   ✅
true  || false = true   ✅
false || true  = true   ✅
false || false = false  ❌
```

**Memory:** `||` is like two paths. You can proceed if **at least one** path is open.

### NOT (!) — Flip the Result 🔄

"The opposite of this condition"

```kotlin
fun main() {
    val isAlive = true
    
    if (!isAlive) {
        println("💀 You're dead.")
    } else {
        println("✅ You're alive!")
    }
}
```

**Output:**
```
✅ You're alive!
```

**Truth Table:**
```
!true  = false  ❌
!false = true   ✅
```

**Memory:** `!` is like flipping a coin. If it was heads, it's now tails.

### Complex Conditions 🧮

```kotlin
fun main() {
    val level = 12
    val health = 100
    val hasMagic = true
    
    if ((level > 10 && health > 50) || hasMagic) {
        println("⚔️  You're ready for the boss fight!")
    } else {
        println("🛡️  You need to prepare more.")
    }
}
```

**Breakdown:**
```
level > 10 && health > 50  →  true && true  →  true
hasMagic                   →  true
true || true               →  true  ✅

OUTPUT: ⚔️  You're ready for the boss fight!
```

### De Morgan's Laws (Advanced)

```kotlin
// These are equivalent:
if (!(isAlive && isHealthy)) {
    println("Not good")
}

if (!isAlive || !isHealthy) {
    println("Not good")
}

// Rule: !(A && B) = !A || !B
//       !(A || B) = !A && !B
```

---

## 🎯 Practice: Interactive NPC System

Replace your `main.kt` with this:

```kotlin
fun main() {
    println("========== NPC ENCOUNTER SYSTEM ==========\n")
    
    val heroLevel = 8
    val heroHealth = 75
    val heroGold = 250
    
    println("Hero Stats: Level $heroLevel | Health: $heroHealth HP | Gold: $heroGold\n")
    
    // ===== NPC 1: BLACKSMITH =====
    println("📍 You encounter the Blacksmith...")
    if (heroLevel >= 5) {
        println("🔨 Blacksmith: Greetings, warrior!")
        
        if (heroGold >= 100) {
            println("🔨 Blacksmith: You have enough gold for a sword!")
        } else {
            println("🔨 Blacksmith: You need ${100 - heroGold} more gold for a sword.")
        }
    } else {
        println("🔨 Blacksmith: You're too weak, beginner!")
    }
    
    println("\n" + "=".repeat(40) + "\n")
    
    // ===== NPC 2: DUNGEON GUARD =====
    println("📍 You encounter the Dungeon Guard...")
    when {
        heroLevel < 5 -> println("👮 Guard: Turn back, weakling!")
        heroLevel < 15 && heroHealth < 50 -> println("👮 Guard: You're wounded! Rest first.")
        heroLevel >= 15 || heroHealth >= 80 -> println("👮 Guard: You may pass, mighty one!")
        else -> println("👮 Guard: You look ready. Proceed with caution.")
    }
    
    println("\n" + "=".repeat(40) + "\n")
    
    // ===== NPC 3: MERCHANT (with when on exact value) =====
    val heroClass = "Warrior"
    println("📍 You encounter the Merchant...")
    when (heroClass) {
        "Warrior" -> println("🛍️  Merchant: I have mighty swords for you!")
        "Mage" -> println("🛍️  Merchant: Spell scrolls! Get your scrolls here!")
        "Archer" -> println("🛍️  Merchant: Arrows and bows of the finest quality!")
        else -> println("🛍️  Merchant: Welcome, traveler!")
    }
    
    println("\n" + "=".repeat(40) + "\n")
    
    // ===== LEVEL PROGRESSION =====
    println("📊 Your Rank:")
    when (heroLevel) {
        in 1..4 -> println("🌱 Novice (Level $heroLevel)")
        in 5..10 -> println("💪 Warrior (Level $heroLevel)")
        in 11..20 -> println("⚔️  Champion (Level $heroLevel)")
        else -> println("🌟 Legend (Level $heroLevel)")
    }
    
    println("\n" + "=".repeat(40) + "\n")
    
    // ===== BATTLE READINESS =====
    val isReady = heroLevel >= 10 && heroHealth >= 50
    val hasResources = heroGold >= 100
    
    println("⚡ Battle Readiness:")
    if (isReady && hasResources) {
        println("✅ You're FULLY prepared! Ready for epic battle!")
    } else if (isReady || hasResources) {
        println("⚠️  You're PARTIALLY prepared. Could be better.")
    } else {
        println("❌ You're NOT ready. Train more!")
    }
}
```

**Expected Output:**
```
========== NPC ENCOUNTER SYSTEM ==========

Hero Stats: Level 8 | Health: 75 HP | Gold: 250

📍 You encounter the Blacksmith...
🔨 Blacksmith: Greetings, warrior!
🔨 Blacksmith: You have enough gold for a sword!

========================================

📍 You encounter the Dungeon Guard...
👮 Guard: You look ready. Proceed with caution.

========================================

📍 You encounter the Merchant...
🛍️  Merchant: I have mighty swords for you!

========================================

📊 Your Rank:
💪 Warrior (Level 8)

========================================

⚡ Battle Readiness:
✅ You're FULLY prepared! Ready for epic battle!
```

---

## 🏆 Challenge #1 — Level-Based Quest Rewards

**Difficulty:** ⭐⭐ Medium

Modify the program to add a **quest system**:

1. Create a variable `questDifficulty` with a value from `1` to `5`
2. Use `when` to match the difficulty and print the reward
3. Use `if/else if/else` to check if hero level is high enough
4. Combine with `&&` to ensure hero is both strong AND the difficulty is manageable

**Example logic:**
- Difficulty 1-2 + Any level → "Easy quest, 50 gold"
- Difficulty 3 + Level >= 5 → "Medium quest, 100 gold"
- Difficulty 4-5 + Level >= 10 → "Hard quest, 300 gold"
- Difficulty 4-5 + Level < 10 → "Quest is too hard!"

<details>
<summary>Click for solution</summary>

```kotlin
fun main() {
    val heroLevel = 8
    val questDifficulty = 4
    
    println("Quest Difficulty: $questDifficulty | Your Level: $heroLevel\n")
    
    when (questDifficulty) {
        1, 2 -> {
            println("Quest Type: EASY")
            println("Reward: 50 gold")
        }
        3 -> {
            println("Quest Type: MEDIUM")
            if (heroLevel >= 5) {
                println("✅ You can do this quest!")
                println("Reward: 100 gold")
            } else {
                println("❌ You're too weak for this quest.")
            }
        }
        4, 5 -> {
            println("Quest Type: HARD")
            if (heroLevel >= 10 && questDifficulty <= 4) {
                println("✅ You can attempt this quest!")
                println("Reward: 300 gold")
            } else if (heroLevel >= 12) {
                println("✅ You're a legend! Reward: 500 gold")
            } else {
                println("❌ This quest is too dangerous for you.")
            }
        }
        else -> println("Invalid difficulty level")
    }
}
```

</details>

---

## 🏆 Challenge #2 — Comprehensive Status Check

**Difficulty:** ⭐⭐ Medium

Create a program that:

1. Declares variables: `heroLevel`, `heroHealth`, `mana`, `isPoisoned`
2. Uses **if/else if/else** to check health status:
   - Health > 75 → "Excellent"
   - Health 50-75 → "Good"
   - Health 25-49 → "Wounded"
   - Health < 25 → "Critical"
3. Uses **when** to check mana levels:
   - 0 → "Out of mana"
   - 1-50 → "Low mana"
   - 51-100 → "Decent mana"
   - 101+ → "Full mana"
4. Use **&&** and **||** to create a condition:
   - If (health > 50 AND mana > 30) OR NOT isPoisoned → "Ready for combat"
   - Otherwise → "Rest before fighting"

<details>
<summary>Click for solution</summary>

```kotlin
fun main() {
    val heroHealth = 65
    val mana = 45
    val isPoisoned = false
    
    println("=== HERO STATUS ===\n")
    
    // Health Check (if/else if/else)
    print("Health: ")
    if (heroHealth > 75) {
        println("Excellent ($heroHealth HP)")
    } else if (heroHealth >= 50) {
        println("Good ($heroHealth HP)")
    } else if (heroHealth >= 25) {
        println("Wounded ($heroHealth HP)")
    } else {
        println("Critical ($heroHealth HP)")
    }
    
    // Mana Check (when)
    print("Mana: ")
    when (mana) {
        0 -> println("Out of mana")
        in 1..50 -> println("Low mana ($mana)")
        in 51..100 -> println("Decent mana ($mana)")
        else -> println("Full mana ($mana)")
    }
    
    // Poison Check
    println("Poisoned: ${if (isPoisoned) "Yes" else "No"}")
    
    println()
    
    // Combat Readiness (complex condition)
    if ((heroHealth > 50 && mana > 30) || !isPoisoned) {
        println("✅ Ready for combat!")
    } else {
        println("⚠️  Rest before fighting!")
    }
}
```

</details>

---

## 🧠 Memory Anchors — Level 4

| Concept | Analogy | Example |
|---------|---------|---------|
| **if/else** | One gate, two paths | `if (level >= 5) { enter } else { exit }` |
| **if/else if/else** | Three ordered gates | Gate A → Gate B → Gate C |
| **when** | Switchboard, exact matches | `when (day) { "Monday" -> ... }` |
| **>**, **<**, **==** | Comparison questions | `level > 10`, `health == 100` |
| **&&** | Both must be true | `level > 10 && health > 50` |
| **\|\|** | At least one true | `hasKey \|\| hasPicklock` |
| **!** | Flip the answer | `!isDead` → "Is NOT dead?" |
| **in** range | Check if in range | `when (x) { in 1..10 -> ... }` |

---

## ✅ Level 4 Checklist

- [ ] I understand `if/else` for binary decisions
- [ ] I understand `if/else if/else` for chained decisions
- [ ] I understand `when` for matching exact values
- [ ] I can use `when` with ranges (`in 1..10`)
- [ ] I understand comparison operators (`>`, `<`, `==`, `!=`, `>=`, `<=`)
- [ ] I can use `&&` (AND) to combine conditions
- [ ] I can use `||` (OR) to combine conditions
- [ ] I can use `!` (NOT) to flip a boolean
- [ ] I ran the practice code and it worked
- [ ] I attempted at least one challenge

---

---

# Level 5: Functions — Reusable Spells & Recipes

## 🧠 The Story

Your hero is now **Day 5**, and they've encountered a **problem**.

Every time they need to calculate damage, they write the same 5 lines of code. When they need to check if they can enter a dungeon, they copy-paste the same `if` statements. **The code is bloated, repetitive, and messy.**

Then a wise **Sage** appears and says:

*"Young adventurer, you don't need to write a new recipe every time you want to brew a potion. Write the recipe ONCE, then call it whenever you need a potion. That's what functions are—reusable spells."*

**Today, you learn to write recipes that you (and others) can call infinitely.**

---

## What Is a Function? 🔮

A **function** is a **named block of reusable code**. Think of it as:

| Analogy | Explanation |
|---------|-------------|
| 📖 **Recipe** | Instructions written once, used many times |
| 🔮 **Spell** | Cast once, defined forever |
| ⚙️ **Machine** | Input something → Machine processes → Output something |
| 📞 **Phone Call** | You call a number → Someone picks up → They do something → They hang up |
| 🏭 **Factory** | Raw materials in → Processing → Finished product out |

### The Problem It Solves

**Without functions (bad):**
```kotlin
fun main() {
    // Calculate damage for sword attack
    val damage1 = 50
    println("Sword damage: $damage1")
    
    // Calculate damage for arrow attack
    val damage2 = 30
    println("Arrow damage: $damage2")
    
    // Calculate damage for magic attack
    val damage3 = 70
    println("Magic damage: $damage3")
}
```

**With functions (good):**
```kotlin
fun calculateDamage(weapon: String): Int {
    return when (weapon) {
        "Sword" -> 50
        "Arrow" -> 30
        "Magic" -> 70
        else -> 10
    }
}

fun main() {
    println("Sword damage: ${calculateDamage("Sword")}")
    println("Arrow damage: ${calculateDamage("Arrow")}")
    println("Magic damage: ${calculateDamage("Magic")}")
}
```

**Benefits:**
- ✅ Less code
- ✅ No copy-paste mistakes
- ✅ Changes in one place update everywhere
- ✅ Easier to read and understand

---

## Function Anatomy 🦴

Let's break down a function piece by piece:

```kotlin
fun greetHero(name: String): String {
    return "Welcome, $name!"
}
```

| Part | Name | Meaning |
|------|------|---------|
| `fun` | Keyword | "This is a function declaration" |
| `greetHero` | Function name | What you call it |
| `(name: String)` | Parameters | What it accepts as input |
| `: String` | Return type | What type it gives back |
| `{ ... }` | Body | The code that runs |
| `return` | Keyword | Send back a result |

### Visual Breakdown

```
    ┌─ Declares this is a function
    │
    ↓
  fun   greetHero      (name: String)  :  String  {
  │     │               │ │            │  │        │
  │     │               │ │            │  │        └─ Body starts
  │     │               │ │            │  └─ Return type
  │     │               │ └─ Parameter type
  │     │               └─ Parameter name
  │     └─ Function name
  └─ Keyword
  
      return "Welcome, $name!"
      │      └─ The value being returned
      └─ Keyword: send this value back
  }
```

---

## Simple Functions (No Parameters, No Return)

### Story

Your hero wants to **greet the innkeeper** every time they enter the tavern. It's the same greeting every time—why repeat it?

### Syntax

```kotlin
fun greetInnkeeper() {
    println("🍺 Innkeeper: Welcome, traveler!")
}

fun main() {
    greetInnkeeper()  // Call it once
    greetInnkeeper()  // Call it again
}
```

**Output:**
```
🍺 Innkeeper: Welcome, traveler!
🍺 Innkeeper: Welcome, traveler!
```

### Key Points

- **No parameters** = `()` (empty parentheses)
- **No return type** = Just omit it (it returns `Unit`, which means "nothing")
- **Call it** = `functionName()`

### Common Simple Functions

```kotlin
fun displayTitle() {
    println("╔════════════════════════════╗")
    println("║   MASTER KOTLIN QUEST      ║")
    println("╚════════════════════════════╝")
}

fun playSound() {
    println("🔊 *ding!*")
}

fun resetGame() {
    println("Game reset!")
}

fun main() {
    displayTitle()
    playSound()
    resetGame()
}
```

---

## Functions with Parameters (Input) 📥

### Story

Instead of a single greeting, the innkeeper should **greet the hero by name**.

### Syntax

```kotlin
fun greetHero(heroName: String) {
    println("🍺 Innkeeper: Welcome, $heroName!")
}

fun main() {
    greetHero("Alice")
    greetHero("Bob")
    greetHero("Charlie")
}
```

**Output:**
```
🍺 Innkeeper: Welcome, Alice!
🍺 Innkeeper: Welcome, Bob!
🍺 Innkeeper: Welcome, Charlie!
```

### How It Works

```kotlin
fun greetHero(heroName: String)
              └─ heroName is a variable that holds whatever we pass in
              
greetHero("Alice")
          └─ "Alice" is the argument (the actual value)
```

### Multiple Parameters

```kotlin
fun purchaseItem(itemName: String, price: Int, quantity: Int) {
    val total = price * quantity
    println("🛍️  You bought $quantity x $itemName for $total gold")
}

fun main() {
    purchaseItem("Sword", 100, 1)
    purchaseItem("Potion", 20, 5)
}
```

**Output:**
```
🛍️  You bought 1 x Sword for 100 gold
🛍️  You bought 5 x Potion for 100 gold
```

### Parameter Types

| Type | Purpose | Example |
|------|---------|---------|
| `String` | Text | `fun say(message: String)` |
| `Int` | Whole numbers | `fun addPoints(points: Int)` |
| `Double` | Decimals | `fun setPrice(price: Double)` |
| `Boolean` | True/False | `fun setAlive(alive: Boolean)` |

---

## Functions with Return Values 📤

### Story

Now the hero needs to **calculate damage** and get a **result back** to use later.

### Syntax

```kotlin
fun calculateDamage(weapon: String): Int {
    return when (weapon) {
        "Sword" -> 50
        "Bow" -> 30
        "Staff" -> 70
        else -> 10
    }
}

fun main() {
    val swordDamage = calculateDamage("Sword")
    println("Sword damage: $swordDamage")
    
    val totalDamage = calculateDamage("Sword") + calculateDamage("Bow")
    println("Combined damage: $totalDamage")
}
```

**Output:**
```
Sword damage: 50
Combined damage: 80
```

### How It Works

```
┌─ Function is defined
│
fun calculateDamage(weapon: String): Int {
    return 50
    └─ This value goes back to whoever called the function
}

│
└─ Function is called
   val damage = calculateDamage("Sword")
                └─ damage receives 50
```

### Return Types

| Type | Meaning | Example |
|------|---------|---------|
| `: Int` | Returns a whole number | `return 50` |
| `: String` | Returns text | `return "Hello"` |
| `: Boolean` | Returns true/false | `return true` |
| `: Double` | Returns decimal | `return 3.14` |
| (no type) | Returns nothing (`Unit`) | Just println, no return |

### Examples by Return Type

**Returns Int:**
```kotlin
fun rollDice(): Int {
    return (1..6).random()
}
```

**Returns String:**
```kotlin
fun getWelcomeMessage(): String {
    return "Hello, adventurer!"
}
```

**Returns Boolean:**
```kotlin
fun isEven(number: Int): Boolean {
    return number % 2 == 0
}
```

---

## Functions with Both Parameters AND Returns 🔄

### Story

The **damage calculator** now depends on both the **weapon** and the **hero's level** (higher level = more damage).

### Example 1: Damage Calculation

```kotlin
fun calculateDamage(weapon: String, heroLevel: Int): Int {
    val baseDamage = when (weapon) {
        "Sword" -> 50
        "Bow" -> 30
        "Staff" -> 70
        else -> 10
    }
    
    val bonusDamage = heroLevel * 2  // Level gives bonus
    return baseDamage + bonusDamage
}

fun main() {
    val damage1 = calculateDamage("Sword", 5)
    println("Level 5, Sword: $damage1 damage")  // 50 + 10 = 60
    
    val damage2 = calculateDamage("Staff", 15)
    println("Level 15, Staff: $damage2 damage")  // 70 + 30 = 100
}
```

**Output:**
```
Level 5, Sword: 60 damage
Level 15, Staff: 100 damage
```

### Example 2: Eligibility Check

```kotlin
fun canEnterDungeon(heroLevel: Int, heroHealth: Int): Boolean {
    return heroLevel >= 10 && heroHealth > 50
}

fun main() {
    if (canEnterDungeon(12, 100)) {
        println("✅ Welcome to the dungeon!")
    } else {
        println("❌ You're not ready.")
    }
    
    if (canEnterDungeon(8, 100)) {
        println("✅ Welcome to the dungeon!")
    } else {
        println("❌ You're not ready.")
    }
}
```

**Output:**
```
✅ Welcome to the dungeon!
❌ You're not ready.
```

### Example 3: Text Generation

```kotlin
fun generateGreeting(name: String, title: String): String {
    return "$title $name, welcome to our tavern!"
}

fun main() {
    val msg1 = generateGreeting("Alice", "Adventurer")
    println(msg1)  // Adventurer Alice, welcome to our tavern!
    
    val msg2 = generateGreeting("Bob", "Champion")
    println(msg2)  // Champion Bob, welcome to our tavern!
}
```

---

## Default Parameters 🎁

### Story

Most of the time, heroes buy items with **quantity = 1**. But sometimes they want more. Instead of requiring quantity every time, we give it a **default value**.

### Syntax

```kotlin
fun purchaseItem(itemName: String, price: Int, quantity: Int = 1) {
    val total = price * quantity
    println("🛍️  Bought $quantity x $itemName for $total gold")
}

fun main() {
    purchaseItem("Sword", 100)           // quantity defaults to 1
    purchaseItem("Potion", 20, 5)        // quantity is 5
    purchaseItem("Book", 50, 3)          // quantity is 3
}
```

**Output:**
```
🛍️  Bought 1 x Sword for 100 gold
🛍️  Bought 5 x Potion for 100 gold
🛍️  Bought 3 x Book for 150 gold
```

### Key Points

- **Default value:** `paramName: Type = defaultValue`
- **Optional to override:** If you don't pass it, it uses the default
- **Can override:** Pass your own value to override the default

### Example: Greetings with Default

```kotlin
fun greet(name: String, greeting: String = "Hello") {
    println("$greeting, $name!")
}

fun main() {
    greet("Alice")                    // Uses default "Hello"
    greet("Bob", "Hiya")              // Overrides with "Hiya"
    greet("Charlie", "Welcome")       // Overrides with "Welcome"
}
```

**Output:**
```
Hello, Alice!
Hiya, Bob!
Welcome, Charlie!
```

### Multiple Defaults

```kotlin
fun attackEnemy(weapon: String = "Sword", damage: Int = 50, critical: Boolean = false): Int {
    var total = damage
    if (critical) total *= 2
    println("Attack with $weapon for $total damage${if (critical) " (CRITICAL)" else ""}")
    return total
}

fun main() {
    attackEnemy()                        // Sword, 50 damage
    attackEnemy("Magic Staff")           // Magic Staff, 50 damage
    attackEnemy("Sword", 100)            // Sword, 100 damage
    attackEnemy("Bow", 30, true)         // Bow, 60 damage (critical)
}
```

---

## Function Best Practices 🏆

### 1. Clear, Descriptive Names

```kotlin
// ❌ Bad
fun calc(x: Int): Int {
    return x * 2
}

// ✅ Good
fun doubleAttackDamage(baseDamage: Int): Int {
    return baseDamage * 2
}
```

### 2. Single Responsibility (One Job)

```kotlin
// ❌ Bad (does too much)
fun handleBattle(hero: String, enemy: String) {
    val heroDamage = 50
    val enemyDamage = 30
    // ... massive calculation
}

// ✅ Good (one job each)
fun calculateDamage(attacker: String): Int { }
fun isVictory(heroHealth: Int, enemyHealth: Int): Boolean { }
fun displayBattleResult(winner: String) { }
```

### 3. Avoid Unnecessary Parameters

```kotlin
// ❌ Bad (passes too many things)
fun greet(heroName: String, heroLevel: Int, heroClass: String, heroGold: Int) {
    println("Hello, $heroName!")
}

// ✅ Good (only what's needed)
fun greet(heroName: String) {
    println("Hello, $heroName!")
}
```

### 4. Function Naming Convention

```kotlin
// ✅ Good: camelCase, descriptive verb+noun
fun calculateDamage() { }
fun canEnterDungeon() { }
fun purchaseItem() { }
fun getExpToLevel() { }
fun displayHeroStatus() { }
fun isAlive() { }

// ❌ Bad: unclear or wrong casing
fun calc() { }
fun check() { }
fun buy() { }
fun CalcDamage() { }  // Should be camelCase
```

---

## 🎯 Practice: Complete RPG System

Replace your `main.kt` with this comprehensive example:

```kotlin
// ====== DAMAGE CALCULATOR ======
fun calculateDamage(weapon: String, heroLevel: Int, isCritical: Boolean = false): Int {
    val baseDamage = when (weapon) {
        "Sword" -> 50
        "Bow" -> 30
        "Staff" -> 70
        else -> 10
    }
    
    val levelBonus = heroLevel * 2
    var totalDamage = baseDamage + levelBonus
    
    if (isCritical) {
        totalDamage *= 2
        println("💥 CRITICAL HIT!")
    }
    
    return totalDamage
}

// ====== ELIGIBILITY CHECK ======
fun canEnterDungeon(heroLevel: Int, heroHealth: Int, hasKey: Boolean = false): Boolean {
    return heroLevel >= 10 && heroHealth > 30 || hasKey
}

// ====== ITEM PURCHASE ======
fun purchaseItem(itemName: String, price: Int, gold: Int): Boolean {
    if (gold >= price) {
        println("✅ Purchased $itemName for $price gold")
        return true
    } else {
        println("❌ Not enough gold! Need ${price - gold} more.")
        return false
    }
}

// ====== LEVEL UP PROGRESS ======
fun calculateExpToLevel(currentLevel: Int): Int {
    return currentLevel * 100
}

// ====== QUEST REWARD ======
fun getQuestReward(difficulty: Int): Int {
    return when (difficulty) {
        1, 2 -> 50
        3 -> 150
        4 -> 300
        5 -> 500
        else -> 0
    }
}

// ====== DISPLAY HERO STATUS ======
fun displayHeroStatus(name: String, level: Int, health: Int, gold: Int) {
    println("\n╔════════════════════════════╗")
    println("║  HERO STATUS              ║")
    println("╠════════════════════════════╣")
    println("║ Name: $name")
    println("║ Level: $level")
    println("║ Health: $health HP")
    println("║ Gold: $gold")
    println("╚════════════════════════════╝\n")
}

// ====== MAIN GAME ======
fun main() {
    println("========== MASTER KOTLIN RPG ==========\n")
    
    // Hero stats
    val heroName = "Aragorn"
    var heroLevel = 12
    var heroHealth = 85
    var heroGold = 350
    
    displayHeroStatus(heroName, heroLevel, heroHealth, heroGold)
    
    // ===== SCENARIO 1: Attack =====
    println("⚔️  SCENARIO 1: You encounter a goblin!")
    val damage1 = calculateDamage("Sword", heroLevel)
    println("Regular attack: $damage1 damage\n")
    
    val damage2 = calculateDamage("Sword", heroLevel, isCritical = true)
    println("Total damage: $damage2 damage\n")
    
    // ===== SCENARIO 2: Dungeon Entry =====
    println("🚪 SCENARIO 2: Dungeon Guard")
    if (canEnterDungeon(heroLevel, heroHealth)) {
        println("✅ Guard: You may enter!")
    } else {
        println("❌ Guard: You're not ready.")
    }
    println()
    
    // ===== SCENARIO 3: Shopping =====
    println("🛍️  SCENARIO 3: Visit Merchant")
    val canBuySword = purchaseItem("Legendary Sword", 200, heroGold)
    if (canBuySword) {
        heroGold -= 200
    }
    println()
    
    val canBuyPotion = purchaseItem("Health Potion", 50, heroGold)
    if (canBuyPotion) {
        heroGold -= 50
    }
    println()
    
    displayHeroStatus(heroName, heroLevel, heroHealth, heroGold)
    
    // ===== SCENARIO 4: Quest Completion =====
    println("🎯 SCENARIO 4: Complete Quest")
    val questDifficulty = 4
    val questReward = getQuestReward(questDifficulty)
    println("Quest Difficulty: $questDifficulty")
    println("Reward: $questReward gold")
    heroGold += questReward
    println()
    
    // ===== SCENARIO 5: Level Up =====
    println("⬆️  SCENARIO 5: Experience Gain")
    val expNeeded = calculateExpToLevel(heroLevel)
    println("Experience needed to level up: $expNeeded XP")
    println()
    
    displayHeroStatus(heroName, heroLevel, heroHealth, heroGold)
}
```

**Expected Output:**
```
========== MASTER KOTLIN RPG ==========

╔════════════════════════════╗
║  HERO STATUS              ║
╠════════════════════════════╣
║ Name: Aragorn
║ Level: 12
║ Health: 85 HP
║ Gold: 350
╚════════════════════════════╝

⚔️  SCENARIO 1: You encounter a goblin!
Regular attack: 74 damage

💥 CRITICAL HIT!
Total damage: 148 damage

🚪 SCENARIO 2: Dungeon Guard
✅ Guard: You may enter!

🛍️  SCENARIO 3: Visit Merchant
✅ Purchased Legendary Sword for 200 gold
✅ Purchased Health Potion for 50 gold

... [more output]
```

---

## 🏆 Challenge #1 — Bank Account System

**Difficulty:** ⭐⭐ Medium

Create functions for a simple **bank system**:

1. `deposit(currentBalance: Int, amount: Int): Int` — Add money, return new balance
2. `withdraw(currentBalance: Int, amount: Int): Boolean` — Remove money if available, return success
3. `getInterest(balance: Int, rate: Double = 0.05): Double` — Calculate interest (default 5%)
4. `displayBalance(heroName: String, balance: Int)` — Pretty print the balance

**Test it:**
```kotlin
fun main() {
    var myGold = 1000
    
    myGold = deposit(myGold, 200)
    println("After deposit: $myGold")
    
    val withdrawSuccess = withdraw(myGold, 500)
    if (withdrawSuccess) {
        myGold -= 500
    }
    
    val interest = getInterest(myGold)
    println("Interest earned: ${interest.toInt()} gold")
    
    displayBalance("Hero", myGold)
}
```

<details>
<summary>Click for solution</summary>

```kotlin
fun deposit(currentBalance: Int, amount: Int): Int {
    println("💰 Deposited $amount gold")
    return currentBalance + amount
}

fun withdraw(currentBalance: Int, amount: Int): Boolean {
    return if (currentBalance >= amount) {
        println("💰 Withdrew $amount gold")
        true
    } else {
        println("❌ Not enough gold!")
        false
    }
}

fun getInterest(balance: Int, rate: Double = 0.05): Double {
    return balance * rate
}

fun displayBalance(heroName: String, balance: Int) {
    println("\n=== $heroName's Account ===")
    println("Current Balance: $balance gold")
}

fun main() {
    var myGold = 1000
    
    myGold = deposit(myGold, 200)
    println("After deposit: $myGold")
    
    val withdrawSuccess = withdraw(myGold, 500)
    if (withdrawSuccess) {
        myGold -= 500
    }
    
    val interest = getInterest(myGold)
    println("Interest earned: ${interest.toInt()} gold")
    
    displayBalance("Aragorn", myGold)
}
```

</details>

---

## 🏆 Challenge #2 — Spell Casting System

**Difficulty:** ⭐⭐ Medium

Create a **magic system** with functions:

1. `castSpell(spellName: String, manaRequired: Int, currentMana: Int): Boolean`
   - Check if hero has enough mana
   - If yes: cast it and return true
   - If no: return false

2. `getSpellDamage(spellName: String, spellLevel: Int = 1): Int`
   - Return damage based on spell and level
   - Use default level = 1

3. `meditate(currentMana: Int, duration: Int = 5): Int`
   - Restore mana based on meditation time
   - Default 5 seconds = restore 20 mana

**Test it:**
```kotlin
fun main() {
    var heroMana = 50
    
    val canCast = castSpell("Fireball", 30, heroMana)
    if (canCast) {
        heroMana -= 30
        val damage = getSpellDamage("Fireball", 3)
        println("Fireball deals $damage damage!")
    }
    
    heroMana = meditate(heroMana, 10)
    println("Mana after meditation: $heroMana")
}
```

<details>
<summary>Click for solution</summary>

```kotlin
fun castSpell(spellName: String, manaRequired: Int, currentMana: Int): Boolean {
    return if (currentMana >= manaRequired) {
        println("🔮 $spellName cast!")
        true
    } else {
        println("❌ Not enough mana for $spellName")
        false
    }
}

fun getSpellDamage(spellName: String, spellLevel: Int = 1): Int {
    val baseDamage = when (spellName) {
        "Fireball" -> 50
        "Icebolt" -> 40
        "Lightning" -> 60
        else -> 20
    }
    return baseDamage + (spellLevel * 10)
}

fun meditate(currentMana: Int, duration: Int = 5): Int {
    val restored = duration * 4
    println("🧘 Meditating for $duration seconds... Restored $restored mana")
    return currentMana + restored
}

fun main() {
    var heroMana = 50
    
    val canCast = castSpell("Fireball", 30, heroMana)
    if (canCast) {
        heroMana -= 30
        val damage = getSpellDamage("Fireball", 3)
        println("Fireball deals $damage damage!")
    }
    
    heroMana = meditate(heroMana, 10)
    println("Mana after meditation: $heroMana")
}
```

</details>

---

## 🏆 Challenge #3 — Complete Combat System

**Difficulty:** ⭐⭐⭐ Hard (Team Challenge!)

Work with your friends to build a complete combat system:

1. `Hero` data: name, level, health, mana, weapon
2. `calculateAttackDamage(weapon: String, level: Int): Int` — Weapon + level bonus
3. `castSpell(spellName: String, mana: Int): Int` — Returns damage
4. `takeDamage(currentHealth: Int, damage: Int): Int` — Returns new health
5. `isAlive(health: Int): Boolean` — Check if hero is alive
6. `battleRound(heroHealth: Int, heroWeapon: String, enemyHealth: Int): Pair<Int, Int>` 
   - Returns (newHeroHealth, newEnemyHealth) after one round

This is a **team project**. Divide the functions among friends and test together!

---

## 🧠 Memory Anchors — Level 5

| Concept | Analogy | Example |
|---------|---------|---------|
| **Function** | Recipe / Spell | Write once, use many times |
| **Parameters** | Recipe ingredients | `fun bake(flour: Int, sugar: Int)` |
| **Arguments** | The actual ingredients | `bake(2, 1)` |
| **Return type** | What the recipe produces | `: Int`, `: String`, `: Boolean` |
| **Return value** | The actual result | `return 42` |
| **Default parameter** | Pre-measured ingredient | `flour: Int = 2` |
| **No return** | Just make noise | `fun shout()` (returns Unit) |
| **Reusability** | One recipe, many cakes | Call a function 100 times |

---

## Function Structure Quick Reference

```kotlin
// No params, no return
fun myFun() {
    println("Hello")
}

// With params, no return
fun greet(name: String) {
    println("Hello, $name")
}

// With params and return
fun add(a: Int, b: Int): Int {
    return a + b
}

// With default params
fun purchase(item: String, qty: Int = 1) {
    println("Bought $qty x $item")
}

// Calling
myFun()
greet("Alice")
val result = add(5, 3)
purchase("Sword")
purchase("Potion", 5)
```

---

## ✅ Level 5 Checklist

- [ ] I understand what functions are (reusable code blocks)
- [ ] I can write functions with NO parameters or returns
- [ ] I can write functions WITH parameters
- [ ] I can write functions that RETURN values
- [ ] I can write functions with both parameters and returns
- [ ] I understand default parameters
- [ ] I can call functions multiple times
- [ ] I ran the practice code (RPG System) and it worked
- [ ] I attempted Challenge #1 (Bank System)
- [ ] I attempted Challenge #2 (Spell Casting)
- [ ] I understand function naming conventions

---

---

# Quick Reference

## Control Flow Syntax Cheat Sheet

### If/Else
```kotlin
if (condition) {
    // do something
} else {
    // do something else
}
```

### If/Else If/Else
```kotlin
if (condition1) {
    // first choice
} else if (condition2) {
    // second choice
} else {
    // default
}
```

### When
```kotlin
when (value) {
    "A" -> println("It's A")
    "B" -> println("It's B")
    else -> println("Something else")
}
```

### When with Range
```kotlin
when (level) {
    in 1..5 -> println("Beginner")
    in 6..10 -> println("Intermediate")
    else -> println("Advanced")
}
```

### Logical Operators
```kotlin
if (a > 5 && b < 10) { }      // Both true
if (a > 5 || b < 10) { }      // At least one true
if (!isAlive) { }              // NOT alive
```

---

## Function Syntax Cheat Sheet

### Simple Function
```kotlin
fun sayHello() {
    println("Hello!")
}
```

### With Parameters
```kotlin
fun greet(name: String) {
    println("Hello, $name!")
}
```

### With Return
```kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}
```

### With Defaults
```kotlin
fun buy(item: String, qty: Int = 1) {
    println("Bought $qty x $item")
}
```

### Calling Functions
```kotlin
sayHello()
greet("Alice")
val result = add(5, 3)
buy("Sword")
buy("Potion", 5)
```

---

# Common Patterns

## Pattern 1: Validation Pattern (if/else)

```kotlin
fun validateAge(age: Int): Boolean {
    if (age < 0) {
        println("Age can't be negative")
        return false
    } else if (age > 120) {
        println("Age seems too high")
        return false
    } else {
        println("Valid age")
        return true
    }
}
```

## Pattern 2: Category Pattern (when)

```kotlin
fun categorizeLevel(level: Int) {
    when (level) {
        in 1..5 -> println("Beginner")
        in 6..15 -> println("Intermediate")
        in 16..30 -> println("Advanced")
        else -> println("Master")
    }
}
```

## Pattern 3: Decision with Multiple Conditions (&&, ||)

```kotlin
fun canAttack(hasWeapon: Boolean, hasEnergy: Boolean, isAlive: Boolean): Boolean {
    return hasWeapon && hasEnergy && isAlive
}
```

## Pattern 4: Reusable Calculator (Functions)

```kotlin
fun calculateTotal(itemPrice: Int, quantity: Int, discountPercent: Int = 0): Int {
    val subtotal = itemPrice * quantity
    val discount = (subtotal * discountPercent) / 100
    return subtotal - discount
}

fun main() {
    println(calculateTotal(100, 3))        // 300
    println(calculateTotal(100, 3, 10))    // 270
}
```

---

# Next Steps

## Level 6: Classes & Objects (Coming Next! 🎯)

---

### For Revision:
- Use **Memory Anchors** to quickly recall concepts
- Use **Quick Reference** for syntax lookups
- Do **challenges again** without looking at solutions
- Teach someone else — best way to learn

---

# Summary: Day 2 Wins! 🏆

✅ **Control Flow Mastered**
- Used `if/else` for binary decisions
- Chained `if/else if/else` for multiple checks
- Wielded `when` for elegant matching
- Combined conditions with `&&`, `||`, `!`

✅ **Functions Mastered**
- Created reusable code blocks
- Used parameters to pass data in
- Used returns to get data out
- Leveraged default parameters
- Practiced with RPG system

**Total Progress: ~40% of beginner Kotlin!** 📈

---

## For Your Study

**Copy this checklist and track your progress:**

```
Day 2 Progress Checklist (Team Edition)
========================================

Level 4: Control Flow
├─ [ ] if/else fundamentals
├─ [ ] if/else if/else chains  
├─ [ ] when expressions
├─ [ ] Comparison operators
├─ [ ] && (AND) operator
├─ [ ] || (OR) operator
├─ [ ] ! (NOT) operator
├─ [ ] Practice exercise: NPC Encounter
├─ [ ] Challenge #1: Quest Rewards
└─ [ ] Challenge #2: Status Check

Level 5: Functions
├─ [ ] Function basics
├─ [ ] Parameters (input)
├─ [ ] Return values (output)
├─ [ ] Default parameters
├─ [ ] Practice exercise: RPG System
├─ [ ] Challenge #1: Bank System
├─ [ ] Challenge #2: Spell Casting
└─ [ ] Challenge #3: Combat System (Team)

Code Quality
├─ [ ] All code runs without errors
├─ [ ] Functions have clear names
├─ [ ] Functions do one job each
└─ [ ] Code is readable & commented
```

---

## Resources

- **Official Kotlin Docs:** https://kotlinlang.org/docs/home.html
- **Kotlin Tour:** https://kotlinlang.org/docs/kotlin-tour-welcome.html
- **Kotlin Playground:** https://play.kotlinlang.org/

---

**Happy Learning! 🎮**

May your code compile, your functions be pure, and your conditions always be true! 🚀
