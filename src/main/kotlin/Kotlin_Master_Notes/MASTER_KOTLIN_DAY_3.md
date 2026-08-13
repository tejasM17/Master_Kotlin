# 🚀 MASTER KOTLIN — Day 3: Classes & Null Safety (ACCELERATED)

**Session Edited:** August 13, 2026  
**Mode:** 🏃 FAST-TRACK (Less story, more code)  
**Difficulty:** ⭐⭐⭐ (Intermediate)  
**Time to Complete:** 3-4 hours  
**Status:** Ready for Real Projects! 💼

---

## 📚 Quick Navigation

- [Level 6: Classes & Objects](#level-6-classes--objects)
- [Level 7: Null Safety](#level-7-null-safety)
- [Quick Syntax Reference](#quick-syntax-reference)
- [Real-World Patterns](#real-world-patterns)
- [Next: Intermediate Concepts](#next-intermediate-concepts)

---

## Level 6: Classes & Objects

### What You're Building

**Before (Functions only):**
```kotlin
var heroName = "Aragorn"
var heroLevel = 12
var heroHealth = 100

fun displayHero() {
    println("$heroName - Level $heroLevel - $heroHealth HP")
}
```

**After (Classes):**
```kotlin
class Hero(val name: String, var level: Int, var health: Int) {
    fun display() {
        println("$name - Level $level - $health HP")
    }
}

val hero = Hero("Aragorn", 12, 100)
hero.display()
```

**Why?** Bundles related data + behavior together. Less error-prone, more organized. **Essential for Android apps, REST APIs, game engines.**

---

### Basic Class Structure

```kotlin
class Hero(val name: String, var level: Int) {
    // Constructor parameters above
    // Properties & methods below
    
    fun levelUp() {
        level++
        println("$name is now level $level!")
    }
}

fun main() {
    val hero = Hero("Alice", 5)
    hero.levelUp()  // Alice is now level 6!
}
```

**Breakdown:**
- `class Hero` — Defines a blueprint
- `(val name: String, var level: Int)` — Constructor (runs when object is created)
- `val` in constructor = immutable property
- `var` in constructor = mutable property
- Methods defined inside `{ }`

---

### Properties & Methods

```kotlin
class Player(
    val username: String,      // immutable
    var health: Int,            // mutable
    var mana: Int
) {
    // Properties (additional)
    var isAlive = true
    
    // Method 1: Take damage
    fun takeDamage(amount: Int) {
        health -= amount
        if (health <= 0) {
            isAlive = false
            println("$username died!")
        }
    }
    
    // Method 2: Restore mana
    fun restoreMana(amount: Int) {
        mana += amount
    }
    
    // Method 3: Status
    fun status(): String {
        return "$username: HP=$health, Mana=$mana"
    }
}

fun main() {
    val player = Player("Alice", 100, 50)
    
    player.takeDamage(20)
    println(player.status())     // Alice: HP=80, Mana=50
    
    player.restoreMana(30)
    println(player.status())     // Alice: HP=80, Mana=80
}
```

---

### Init Block (Setup Code)

Run code when object is created:

```kotlin
class Hero(val name: String, var level: Int) {
    init {
        println("Hero $name created at level $level")
        if (level > 20) println("Wow, high level!")
    }
}

val h1 = Hero("Alice", 5)   // Hero Alice created at level 5
val h2 = Hero("Bob", 25)    // Hero Bob created at level 25
                             // Wow, high level!
```

---

### Secondary Constructors

```kotlin
class Weapon(val name: String, val damage: Int) {
    // Primary constructor above
    
    // Secondary constructor (alternative way to create)
    constructor(name: String) : this(name, 10) {
        println("Created basic $name")
    }
}

val sword1 = Weapon("Sword", 50)       // Primary
val sword2 = Weapon("Dagger")          // Secondary (10 damage)
```

---

### Class Inheritance (is-a relationship)

**Hero is a Character. Enemy is a Character.**

```kotlin
open class Character(val name: String, open var health: Int) {
    open fun takeDamage(amount: Int) {
        health -= amount
        println("$name took $amount damage. Health: $health")
    }
}

class Hero(name: String, health: Int) : Character(name, health) {
    fun levelUp() {
        println("$name leveled up!")
    }
}

class Enemy(name: String, health: Int) : Character(name, health) {
    override fun takeDamage(amount: Int) {
        val reducedDamage = amount / 2  // Enemies resist 50%
        super.takeDamage(reducedDamage)  // Call parent's takeDamage
    }
}

fun main() {
    val hero = Hero("Alice", 100)
    val enemy = Enemy("Goblin", 50)
    
    hero.takeDamage(20)      // Alice took 20 damage. Health: 80
    enemy.takeDamage(20)     // Goblin took 10 damage. Health: 40
}
```

**Key points:**
- Parent class needs `open` keyword
- Child class `: Parent`
- Override methods with `override`
- Call parent with `super.method()`

---

### Data Classes (for storing data)

**Perfect for:** API responses, database models, data transfer

```kotlin
data class User(val id: Int, val name: String, val email: String)

fun main() {
    val user1 = User(1, "Alice", "alice@example.com")
    val user2 = User(1, "Alice", "alice@example.com")
    
    println(user1 == user2)  // true! (auto-compared by values)
    println(user1)           // User(id=1, name=Alice, email=alice@example.com)
    
    // Auto-generated copy() method
    val user3 = user1.copy(id = 2, name = "Alice2")
    println(user3)           // User(id=2, name=Alice2, email=alice@example.com)
}
```

**What you get for free:**
- `equals()` — Compare by values
- `hashCode()` — For collections
- `toString()` — Nice printing
- `copy()` — Clone with changes
- `componentN()` — Destructuring

---

### Companion Objects (Static-like behavior)

```kotlin
class Hero(val name: String) {
    companion object {
        const val MAX_LEVEL = 100
        var heroCount = 0
        
        fun createDefaultHero(): Hero {
            heroCount++
            return Hero("Hero#$heroCount")
        }
    }
}

fun main() {
    println(Hero.MAX_LEVEL)          // 100 (access without instance)
    
    val h1 = Hero.createDefaultHero() // Hero#1
    val h2 = Hero.createDefaultHero() // Hero#2
    
    println(Hero.heroCount)          // 2
}
```

---

### Visibility Modifiers

```kotlin
class BankAccount(private val balance: Int) {
    // private = only this class can access
    
    fun getBalance(): Int {
        return balance  // ✅ OK (inside class)
    }
}

fun main() {
    val account = BankAccount(1000)
    println(account.getBalance())     // ✅ 1000
    // println(account.balance)       // ❌ Error (private)
}
```

| Modifier | Visible in | Use Case |
|----------|-----------|----------|
| `public` (default) | Everywhere | Normal properties |
| `private` | Only in this class | Internal state |
| `protected` | This class + subclasses | Inheritance |
| `internal` | Same module | Library internals |

---

### Practice: RPG Battle System with Classes

```kotlin
data class Item(val name: String, val damage: Int = 0, val healing: Int = 0)

open class Character(val name: String, var health: Int, var maxHealth: Int) {
    open fun takeDamage(amount: Int) {
        health = maxOf(0, health - amount)
        println("$name took $amount damage! Health: $health/$maxHealth")
    }
    
    open fun heal(amount: Int) {
        health = minOf(maxHealth, health + amount)
        println("$name healed $amount! Health: $health/$maxHealth")
    }
}

class Hero(name: String, health: Int) : Character(name, health, health) {
    private val inventory = mutableListOf<Item>()
    
    fun addItem(item: Item) {
        inventory.add(item)
        println("$name picked up ${item.name}")
    }
    
    fun attack(target: Character, weapon: Item) {
        val damage = weapon.damage
        println("$name attacks with ${weapon.name}!")
        target.takeDamage(damage)
    }
}

class Enemy(name: String, health: Int) : Character(name, health, health) {
    override fun takeDamage(amount: Int) {
        val reduced = (amount * 0.8).toInt()  // 20% damage reduction
        super.takeDamage(reduced)
    }
}

fun main() {
    val hero = Hero("Alice", 100)
    val enemy = Enemy("Orc", 60)
    
    val sword = Item("Sword", damage = 25)
    hero.addItem(sword)
    
    hero.attack(enemy, sword)  // Alice attacks with Sword!
                               // Orc took 20 damage! Health: 40/60
    
    enemy.takeDamage(15)       // Orc took 12 damage! Health: 28/60
}
```

---

### Challenge: E-commerce System

Build an e-commerce system:

1. `Product` data class: name, price, stock
2. `Cart` class: add items, remove items, calculate total
3. `Customer` class: name, email, cart
4. `Order` data class: id, customer, items, total

```kotlin
// Your code here
```

<details>
<summary>Solution</summary>

```kotlin
data class Product(val id: Int, val name: String, val price: Double, var stock: Int)

data class Order(val id: Int, val customerName: String, val items: List<Product>, val total: Double)

class Cart {
    private val items = mutableListOf<Product>()
    
    fun addItem(product: Product) {
        if (product.stock > 0) {
            items.add(product)
            product.stock--
            println("Added ${product.name} to cart")
        } else {
            println("${product.name} out of stock")
        }
    }
    
    fun removeItem(product: Product) {
        if (items.remove(product)) {
            product.stock++
            println("Removed ${product.name} from cart")
        }
    }
    
    fun getTotal(): Double = items.sumOf { it.price }
    
    fun checkout(customerId: Int, customerName: String): Order {
        val order = Order(customerId, customerName, items.toList(), getTotal())
        println("Order created: $customerId - $customerName - Total: ${getTotal()}")
        items.clear()
        return order
    }
}

class Customer(val id: Int, val name: String, val email: String) {
    val cart = Cart()
}

fun main() {
    val p1 = Product(1, "Sword", 50.0, 5)
    val p2 = Product(2, "Shield", 30.0, 3)
    
    val customer = Customer(1, "Alice", "alice@example.com")
    
    customer.cart.addItem(p1)
    customer.cart.addItem(p2)
    
    val order = customer.cart.checkout(customer.id, customer.name)
    println("Order total: ${order.total}")
}
```

</details>

---

## Level 7: Null Safety

### The Problem Kotlin Solves

```java
// Java - CRASHES if user is null
String name = user.getName();
int length = name.length();  // ❌ NullPointerException if name is null!
```

```kotlin
// Kotlin - Forces you to think about null
val name: String = user.name          // ❌ Can't be null
val name: String? = user.name         // ✅ Can be null
```

**Kotlin doesn't let you forget about nulls. That's the whole point.**

---

### Non-Nullable vs Nullable

```kotlin
// Non-nullable (must have a value)
val name: String = "Alice"
// name = null  // ❌ Error!

// Nullable (can be null)
val name: String? = "Alice"
val name2: String? = null    // ✅ Allowed
```

**Type difference:**
- `String` = never null
- `String?` = might be null

---

### Safe Call Operator ?.

Use when property might be null:

```kotlin
val user: User? = getUser()  // might be null

// Without safe call
if (user != null) {
    println(user.name.uppercase())
}

// With safe call (shorter)
println(user?.name?.uppercase())  // null if user is null, name is null, or both

// Safe call + elvis operator
val name = user?.name ?: "Unknown"  // Use "Unknown" if null
```

---

### Elvis Operator ?:

"Use this if null, otherwise use that"

```kotlin
val name: String? = null

val result = name ?: "Guest"
println(result)  // Guest

val age: Int? = 25
val ageDisplay = (age?.toString()) ?: "Unknown"
println(ageDisplay)  // 25
```

---

### Not-Null Assertion !!

**⚠️ Use sparingly.** Says "I'm 100% sure this isn't null"

```kotlin
val name: String? = "Alice"
val length = name!!.length  // ✅ I know it's not null

val nullable: String? = null
val crash = nullable!!  // ❌ Crashes!
```

**Rule:** Only use `!!` when you're absolutely certain. Better to use `?.` or `?:`

---

### Nullable Collections

```kotlin
val items: List<String>? = null          // List itself can be null
val items: List<String?>? = null         // Items OR list can be null

val list: List<String?> = listOf("a", null, "b")
for (item in list) {
    println(item?.uppercase() ?: "NULL")
}
```

---

### let Function (most common)

Execute code **only if not null**:

```kotlin
val user: User? = getUser()

// Traditional way
if (user != null) {
    println(user.name)
    sendEmail(user.email)
}

// Using let
user?.let {
    println(it.name)
    sendEmail(it.email)
}
```

**In context:**
```kotlin
data class User(val id: Int, val name: String, val email: String)

val user: User? = User(1, "Alice", "alice@example.com")

user?.let {
    println("User name: ${it.name}")
    println("User email: ${it.email}")
}
```

---

### also Function (for side effects)

Do something, then return the same object:

```kotlin
val user = User(1, "Alice", "alice@example.com")
    .also { println("Created user: ${it.name}") }
    .also { saveToDatabase(it) }
```

**Similar to:**
```kotlin
val user = User(1, "Alice", "alice@example.com")
println("Created user: ${user.name}")
saveToDatabase(user)
```

---

### apply Function (for initialization)

Set up object properties, return the object:

```kotlin
// Traditional
val hero = Hero("Alice", 10)
hero.health = 100
hero.mana = 50

// Using apply
val hero = Hero("Alice", 10).apply {
    health = 100
    mana = 50
}
```

---

### Nullable Function Parameters

```kotlin
fun greet(name: String?) {
    println("Hello, ${name ?: "stranger"}")
}

greet("Alice")   // Hello, Alice
greet(null)      // Hello, stranger
```

---

### Nullable Return Values

```kotlin
fun getUserById(id: Int): User? {
    return if (id > 0) {
        User(id, "User$id", "user$id@example.com")
    } else {
        null  // Not found
    }
}

fun main() {
    val user = getUserById(1)
    val name = user?.name ?: "Not found"
    println(name)
}
```

---

### Smart Casts

After null check, Kotlin knows it's not null:

```kotlin
val obj: Any = "Hello"

if (obj is String) {
    // After this check, obj is automatically String type
    println(obj.length)  // ✅ No need for cast
}

val value: String? = "Test"
if (value != null) {
    // Kotlin knows value is not null now
    println(value.length)  // ✅ Works!
}
```

---

### Practice: User Management System

```kotlin
data class User(val id: Int, val name: String, val email: String?)

class UserRepository {
    private val users = mutableListOf(
        User(1, "Alice", "alice@example.com"),
        User(2, "Bob", null),  // No email
        User(3, "Charlie", "charlie@example.com")
    )
    
    fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }
    
    fun sendNotification(userId: Int) {
        getUserById(userId)?.let {
            it.email?.let { email ->
                println("Sending email to $email for user ${it.name}")
            } ?: run {
                println("${it.name} has no email address")
            }
        } ?: run {
            println("User $userId not found")
        }
    }
}

fun main() {
    val repo = UserRepository()
    
    repo.sendNotification(1)  // Sends email to alice@example.com
    repo.sendNotification(2)  // Bob has no email address
    repo.sendNotification(99) // User 99 not found
}
```

---

## Quick Syntax Reference

### Classes

```kotlin
// Basic class
class Hero(val name: String, var health: Int)

// With init block
class Hero(val name: String) {
    init { println("Hero created!") }
}

// With methods
class Hero(val name: String) {
    fun attack() { println("$name attacks!") }
}

// Data class (auto equals, toString, copy)
data class User(val id: Int, val name: String)

// Inheritance
open class Character(open val name: String)
class Hero(name: String) : Character(name)

// Visibility
private val secret = "shh"
public val visible = "hello"
```

### Null Safety

```kotlin
// Type hints
val name: String = "safe"       // never null
val name: String? = null        // can be null

// Safe operations
user?.name                       // null if user is null
user?.name ?: "Unknown"          // elvis operator
user?.name!!                     // not-null assertion

// Control flow
if (user != null) { }            // smart cast
user?.let { }                    // scope function
user?.also { }                   // do & return
user?.apply { }                  // setup & return
```

---

## Real-World Patterns

### Pattern 1: API Response Handling

```kotlin
data class ApiResponse<T>(val data: T?, val error: String?)

fun fetchUser(id: Int): ApiResponse<User> {
    return try {
        val user = User(id, "Alice", "alice@example.com")
        ApiResponse(data = user, error = null)
    } catch (e: Exception) {
        ApiResponse(data = null, error = e.message)
    }
}

fun main() {
    val response = fetchUser(1)
    
    response.data?.let {
        println("User: ${it.name}")
    } ?: run {
        println("Error: ${response.error}")
    }
}
```

### Pattern 2: Database Query

```kotlin
data class Product(val id: Int, val name: String, val price: Double)

class ProductDAO {
    fun findById(id: Int): Product? {
        // Simulating database query
        return if (id > 0) Product(id, "Item $id", 99.99) else null
    }
}

fun displayProduct(id: Int, dao: ProductDAO) {
    dao.findById(id)?.apply {
        println("Product: $name")
        println("Price: $$price")
    } ?: println("Product not found")
}
```

### Pattern 3: Form Validation

```kotlin
data class User(val email: String?, val age: Int?)

fun validateUser(user: User?): Boolean {
    return user?.let {
        val validEmail = it.email?.contains("@") ?: false
        val validAge = it.age?.let { age -> age >= 18 } ?: false
        validEmail && validAge
    } ?: false
}
```
---

## ✅ Level 6-7 Checklist

- [ ] Understanding classes and object creation
- [ ] Properties (val, var) in classes
- [ ] Methods and behavior
- [ ] init block for initialization
- [ ] Inheritance with `open` and `override`
- [ ] Data classes for data transfer
- [ ] Companion objects
- [ ] Nullable types (`String?`)
- [ ] Safe call operator (`?.`)
- [ ] Elvis operator (`?:`)
- [ ] Not-null assertion (`!!`)
- [ ] `let`, `also`, `apply` functions
- [ ] Smart casts
- [ ] Built 2+ projects with classes

---

## 📊 Progress Summary

```
BEGINNER KOTLIN ✅ (Levels 1-7)
├─ Level 1: Hello World ✅
├─ Level 2: Basic Types ✅
├─ Level 3: Collections ✅
├─ Level 4: Control Flow ✅
├─ Level 5: Functions ✅
├─ Level 6: Classes ✅
└─ Level 7: Null Safety ✅

INTERMEDIATE KOTLIN 🚀 (Levels 8-12)
├─ Level 8: Extension Functions
├─ Level 9: Scope Functions Deep Dive
├─ Level 10: Collections Advanced
├─ Level 11: Lambdas & Higher-Order Functions
└─ Level 12: Sequences

ADVANCED KOTLIN 🔥 (Levels 13-16)
├─ Level 13: Coroutines (CRITICAL)
├─ Level 14: Generics
├─ Level 15: DSLs
└─ Level 16: Advanced Patterns

SPECIALIZATION 🎯 (Pick One)
├─ Android (Jetpack Compose, MVVM, Room, Retrofit)
├─ Backend (Ktor/Spring Boot)
└─ Multiplatform (KMP)
```

**You're 44% done with beginner, ready for intermediate!** 📈

---

## Official Resources

- **Kotlin Docs:** https://kotlinlang.org/docs/
- **Tour:** https://kotlinlang.org/docs/kotlin-tour.html
- **Null Safety:** https://kotlinlang.org/docs/kotlin-tour-intermediate.html#null-safety
- **Classes:** https://kotlinlang.org/docs/classes.html
- **Playground:** https://play.kotlinlang.org/

---

🚀 **You're moving fast. Keep this momentum!**
