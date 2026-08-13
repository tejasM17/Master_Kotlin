# 🚀 MASTER KOTLIN — Day 4: Intermediate Concepts (ACCELERATED)

**Session Edited:** August 13, 2026  
**Mode:** 🏃🏃 TURBO FAST-TRACK (Levels 8-11)  
**Difficulty:** ⭐⭐⭐⭐ (Intermediate)  
**Time to Complete:** 4-5 hours  
**Status:** Gateway to Advanced Kotlin! 🔥

---

## 📚 Quick Navigation

- [Level 8: Extension Functions](#level-8-extension-functions)
- [Level 9: Advanced Collections](#level-9-advanced-collections)
- [Level 10: Lambdas & Higher-Order Functions](#level-10-lambdas--higher-order-functions)
- [Level 11: Interfaces & Contracts](#level-11-interfaces--contracts)
- [Real-World Patterns](#real-world-patterns)
- [Next: Advanced Concepts](#next-advanced-concepts)

---

## Level 8: Extension Functions

### What You're Learning

Add methods to **existing classes without inheritance**. Game-changer for Android dev.

```kotlin
// Add a method to String without changing String class
fun String.shout() = this.uppercase() + "!!!"

val greeting = "hello"
println(greeting.shout())  // HELLO!!!
```

### Basic Extension

```kotlin
// Add method to List
fun <T> List<T>.secondElement(): T? = if (size > 1) this[1] else null

val items = listOf("a", "b", "c")
println(items.secondElement())  // b

// Add method to Int
fun Int.isEven() = this % 2 == 0

println(5.isEven())  // false
println(6.isEven())  // true
```

### Extension with Parameters

```kotlin
fun String.repeat(times: Int): String {
    return this.repeat(times)  // Built-in repeat exists, just demo
}

fun Int.multiplyBy(factor: Int) = this * factor

println("x".repeat(3))      // xxx
println(5.multiplyBy(2))    // 10
```

### Real-World Examples

```kotlin
// For Android: View extensions
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

// Usage
myButton.show()
myButton.hide()

// For API: String extensions
fun String.isEmailValid(): Boolean {
    return this.contains("@") && this.contains(".")
}

println("alice@example.com".isEmailValid())  // true
println("alice".isEmailValid())              // false

// For Data: List extensions
fun <T> List<T>.random(): T {
    return this[(0 until size).random()]
}

val items = listOf(1, 2, 3, 4, 5)
println(items.random())  // Random element
```

### Extension Properties

```kotlin
val String.hasNumbers: Boolean
    get() = this.any { it.isDigit() }

val String.wordCount: Int
    get() = this.split(" ").size

println("hello123".hasNumbers)      // true
println("hello world".wordCount)    // 2
```

### Receiver & this

```kotlin
// In extensions, 'this' = the object being extended
fun String.wrapInBrackets(): String {
    return "[$this]"  // this = the String itself
}

fun StringBuilder.appendLine(text: String) {
    this.append(text).append("\n")  // this = StringBuilder
}

val sb = StringBuilder()
sb.appendLine("Line 1")
sb.appendLine("Line 2")
println(sb.toString())
```

### Challenge: Utility Extensions

Create these extensions:

```kotlin
// 1. String: remove vowels
fun String.removeVowels(): String { }

// 2. List<Int>: average
fun List<Int>.average(): Double { }

// 3. Int: factorial
fun Int.factorial(): Int { }
```

<details>
<summary>Solutions</summary>

```kotlin
fun String.removeVowels(): String {
    return this.filter { !listOf('a','e','i','o','u').contains(it.lowercaseChar()) }
}

fun List<Int>.average(): Double {
    return if (isEmpty()) 0.0 else sum().toDouble() / size
}

fun Int.factorial(): Int {
    return if (this <= 1) 1 else this * (this - 1).factorial()
}

// Test
println("hello world".removeVowels())  // hll wrld
println(listOf(1, 2, 3, 4, 5).average())  // 3.0
println(5.factorial())  // 120
```

</details>

---

## Level 9: Advanced Collections

### Map, Filter, Reduce (The Big Three)

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

// MAP: transform each element
val doubled = numbers.map { it * 2 }
println(doubled)  // [2, 4, 6, 8, 10]

// FILTER: keep elements that pass test
val evens = numbers.filter { it % 2 == 0 }
println(evens)  // [2, 4]

// REDUCE: combine all into single value
val sum = numbers.reduce { acc, value -> acc + value }
println(sum)  // 15
```

### Chain Operations

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

val result = numbers
    .filter { it > 2 }      // [3, 4, 5]
    .map { it * it }        // [9, 16, 25]
    .reduce { acc, v -> acc + v }  // 50

println(result)  // 50
```

### Fold (Reduce with Initial Value)

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

// Reduce (no initial)
val sum1 = numbers.reduce { acc, v -> acc + v }  // 15

// Fold (with initial)
val sum2 = numbers.fold(100) { acc, v -> acc + v }  // 115
val sum3 = numbers.fold(0) { acc, v -> acc + v }    // 15

// Fold with String
val sentence = listOf("Hello", "from", "Kotlin")
val joined = sentence.fold("") { acc, word -> 
    if (acc.isEmpty()) word else "$acc $word" 
}
println(joined)  // Hello from Kotlin
```

### GroupBy (Organize by Key)

```kotlin
data class Person(val name: String, val age: Int)

val people = listOf(
    Person("Alice", 25),
    Person("Bob", 25),
    Person("Charlie", 30)
)

val byAge = people.groupBy { it.age }
println(byAge)
// {25=[Alice, Bob], 30=[Charlie]}

byAge.forEach { (age, persons) ->
    println("Age $age: ${persons.map { it.name }}")
}
```

### Partition (Split into Two)

```kotlin
val numbers = listOf(1, 2, 3, 4, 5, 6)

val (evens, odds) = numbers.partition { it % 2 == 0 }
println(evens)  // [2, 4, 6]
println(odds)   // [1, 3, 5]
```

### Associate (Key-Value Pairs)

```kotlin
data class Product(val id: Int, val name: String, val price: Double)

val products = listOf(
    Product(1, "Sword", 50.0),
    Product(2, "Shield", 30.0),
    Product(3, "Potion", 10.0)
)

// Create map from list
val productMap = products.associateBy { it.id }
println(productMap)  // {1=Product(...), 2=Product(...), ...}

// Create map with transformation
val priceMap = products.associateBy({ it.name }, { it.price })
println(priceMap)  // {Sword=50.0, Shield=30.0, Potion=10.0}
```

### Flatten & FlatMap

```kotlin
// Flatten: merge nested lists
val nested = listOf(listOf(1, 2), listOf(3, 4), listOf(5))
val flat = nested.flatten()
println(flat)  // [1, 2, 3, 4, 5]

// FlatMap: map + flatten
val text = listOf("hello", "world")
val chars = text.flatMap { it.toList() }
println(chars)  // [h, e, l, l, o, w, o, r, l, d]
```

### Real-World Example: E-Commerce

```kotlin
data class Order(val id: Int, val customer: String, val items: List<String>, val total: Double)

val orders = listOf(
    Order(1, "Alice", listOf("Sword", "Shield"), 80.0),
    Order(2, "Bob", listOf("Potion", "Potion"), 20.0),
    Order(3, "Alice", listOf("Bow"), 40.0)
)

// Total revenue
val totalRevenue = orders.sumOf { it.total }
println("Revenue: $$totalRevenue")

// Orders by customer
val byCustomer = orders.groupBy { it.customer }
println(byCustomer)

// All items ordered
val allItems = orders.flatMap { it.items }
println("Items: $allItems")

// Items frequency
val itemFreq = allItems.groupingBy { it }.eachCount()
println("Frequency: $itemFreq")
```

---

## Level 10: Lambdas & Higher-Order Functions

### Lambda Basics

```kotlin
// Lambda: anonymous function
val add = { a: Int, b: Int -> a + b }
println(add(5, 3))  // 8

// Lambda in list operations
val numbers = listOf(1, 2, 3, 4, 5)
val squared = numbers.map { x -> x * x }
println(squared)  // [1, 4, 9, 16, 25]

// Multiple parameters
val greet = { name: String, age: Int -> 
    "Hello $name, you are $age years old" 
}
println(greet("Alice", 25))
```

### Higher-Order Functions (Functions as Parameters)

```kotlin
// Function that takes another function
fun applyOperation(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

val add = { x: Int, y: Int -> x + y }
val multiply = { x: Int, y: Int -> x * y }

println(applyOperation(5, 3, add))       // 8
println(applyOperation(5, 3, multiply))  // 15

// Pass lambda directly
println(applyOperation(5, 3) { a, b -> a - b })  // 2
```

### Function Types

```kotlin
// () -> Int : takes nothing, returns Int
val getValue: () -> Int = { 42 }

// (String) -> String : takes String, returns String
val uppercase: (String) -> String = { it.uppercase() }

// (Int, Int) -> Boolean : takes 2 Ints, returns Boolean
val isGreater: (Int, Int) -> Boolean = { a, b -> a > b }

println(getValue())                      // 42
println(uppercase("hello"))              // HELLO
println(isGreater(10, 5))               // true
```

### Return from Lambda

```kotlin
val validateEmail: (String) -> Boolean = { email ->
    email.contains("@") && email.contains(".")
}

val processPayment: (Double) -> String = { amount ->
    if (amount > 0) "Processing $${String.format("%.2f", amount)}"
    else "Invalid amount"
}

println(validateEmail("alice@example.com"))  // true
println(processPayment(99.99))               // Processing $99.99
```

### Last Parameter is Lambda

Kotlin allows lambda as last parameter outside parentheses:

```kotlin
// Normal way
numbers.map({ x -> x * 2 })

// Better way (lambda outside)
numbers.map { x -> x * 2 }

// Single parameter lambda (implicit 'it')
numbers.map { it * 2 }
```

### Practice: Functional Programming

```kotlin
data class Product(val name: String, val price: Double, val quantity: Int)

val inventory = listOf(
    Product("Sword", 50.0, 5),
    Product("Shield", 30.0, 0),  // out of stock
    Product("Potion", 10.0, 20)
)

// 1. Filter available items
val available = inventory.filter { it.quantity > 0 }
println("Available: ${available.map { it.name }}")

// 2. Calculate total value
val totalValue = inventory.fold(0.0) { acc, product ->
    acc + (product.price * product.quantity)
}
println("Total inventory value: $$totalValue")

// 3. Find expensive items
val expensive = inventory.filter { it.price > 25 }
println("Expensive: ${expensive.map { "${it.name} - $${it.price}" }}")

// 4. Group by price range
val byPrice = inventory.groupBy { product ->
    when {
        product.price < 20 -> "Cheap"
        product.price < 40 -> "Mid"
        else -> "Expensive"
    }
}
println("By price: $byPrice")
```

---

## Level 11: Interfaces & Contracts

### Basic Interface

```kotlin
interface Animal {
    fun makeSound()
    fun move()
}

class Dog : Animal {
    override fun makeSound() {
        println("Woof!")
    }
    override fun move() {
        println("Running on 4 legs")
    }
}

fun main() {
    val dog: Animal = Dog()
    dog.makeSound()  // Woof!
}
```

### Interface with Properties

```kotlin
interface Vehicle {
    val brand: String
    val year: Int
    
    fun start()
    fun stop()
}

class Car(override val brand: String, override val year: Int) : Vehicle {
    override fun start() {
        println("$brand car starting...")
    }
    override fun stop() {
        println("$brand car stopping...")
    }
}

fun main() {
    val car = Car("Toyota", 2023)
    println("${car.brand} - ${car.year}")
    car.start()
}
```

### Default Implementations

```kotlin
interface Logger {
    fun log(message: String) {
        println("[LOG] $message")
    }
    
    fun error(message: String) {
        println("[ERROR] $message")
    }
}

class ConsoleLogger : Logger {
    // Uses default implementations
}

val logger: Logger = ConsoleLogger()
logger.log("Starting app")
logger.error("Something went wrong")
```

### Multiple Interfaces

```kotlin
interface Drawable {
    fun draw()
}

interface Clickable {
    fun onClick()
}

class Button : Drawable, Clickable {
    override fun draw() {
        println("Drawing button")
    }
    override fun onClick() {
        println("Button clicked!")
    }
}

fun main() {
    val button = Button()
    button.draw()
    button.onClick()
}
```

### Interface Segregation (Best Practice)

```kotlin
// ✅ GOOD: Specific interfaces
interface Renderer {
    fun render(data: String)
}

interface Validator {
    fun validate(data: String): Boolean
}

class FormProcessor : Renderer, Validator {
    override fun render(data: String) = println("Rendering: $data")
    override fun validate(data: String) = data.isNotEmpty()
}

// ❌ BAD: One big interface
interface MegaProcessor {
    fun render(data: String)
    fun validate(data: String): Boolean
    fun save(data: String)
    fun delete(data: String)
    // ... 10 more methods
}
```

### Real-World: Repository Pattern

```kotlin
data class User(val id: Int, val name: String, val email: String)

interface UserRepository {
    fun findById(id: Int): User?
    fun findAll(): List<User>
    fun save(user: User): Boolean
    fun delete(id: Int): Boolean
}

class InMemoryUserRepository : UserRepository {
    private val users = mutableListOf<User>()
    
    override fun findById(id: Int) = users.find { it.id == id }
    override fun findAll() = users.toList()
    override fun save(user: User): Boolean {
        users.removeIf { it.id == user.id }
        users.add(user)
        return true
    }
    override fun delete(id: Int) = users.removeIf { it.id == id }
}

// Easy to swap implementations
class DatabaseUserRepository : UserRepository {
    // Database implementation
    override fun findById(id: Int): User? { /* DB query */ return null }
    override fun findAll(): List<User> { /* DB query */ return emptyList() }
    override fun save(user: User) = true
    override fun delete(id: Int) = true
}

fun processUser(repo: UserRepository, userId: Int) {
    repo.findById(userId)?.let {
        println("User: ${it.name} (${it.email})")
    }
}
```

---

## Real-World Patterns

### Pattern 1: Builder Pattern

```kotlin
data class HttpRequest(
    val url: String,
    val method: String = "GET",
    val headers: Map<String, String> = emptyMap(),
    val body: String? = null,
    val timeout: Int = 5000
)

class HttpRequestBuilder {
    var url = ""
    var method = "GET"
    var headers = mutableMapOf<String, String>()
    var body: String? = null
    var timeout = 5000
    
    fun header(key: String, value: String) = apply {
        headers[key] = value
    }
    
    fun method(m: String) = apply { method = m }
    fun body(b: String) = apply { body = b }
    fun timeout(t: Int) = apply { timeout = t }
    
    fun build() = HttpRequest(url, method, headers, body, timeout)
}

fun main() {
    val request = HttpRequest(url = "https://api.example.com/users").let {
        HttpRequestBuilder().apply {
            url = "https://api.example.com/users"
            method("POST")
            header("Content-Type", "application/json")
            body("""{"name":"Alice"}""")
        }.build()
    }
}
```

### Pattern 2: Strategy Pattern

```kotlin
interface PaymentStrategy {
    fun pay(amount: Double): Boolean
}

class CreditCardPayment : PaymentStrategy {
    override fun pay(amount: Double): Boolean {
        println("Processing credit card payment: $$amount")
        return true
    }
}

class PayPalPayment : PaymentStrategy {
    override fun pay(amount: Double): Boolean {
        println("Processing PayPal payment: $$amount")
        return true
    }
}

class ShoppingCart(private val paymentStrategy: PaymentStrategy) {
    fun checkout(total: Double) {
        if (paymentStrategy.pay(total)) {
            println("Order placed!")
        }
    }
}

fun main() {
    val cart1 = ShoppingCart(CreditCardPayment())
    cart1.checkout(99.99)
    
    val cart2 = ShoppingCart(PayPalPayment())
    cart2.checkout(49.99)
}
```

### Pattern 3: Functional Pipeline

```kotlin
typealias Transform<T> = (T) -> T

fun <T> pipeline(vararg transforms: Transform<T>): Transform<T> {
    return { input ->
        transforms.fold(input) { acc, transform -> transform(acc) }
    }
}

fun main() {
    val text = "  hello world  "
    
    val process = pipeline<String>(
        { it.trim() },
        { it.uppercase() },
        { it.replace(" ", "-") },
        { "[$it]" }
    )
    
    println(process(text))  // [HELLO-WORLD]
}
```

---

## ✅ Levels 8-11 Checklist

- [ ] Extension functions on built-in types
- [ ] Extension properties
- [ ] Map, filter, reduce operations
- [ ] Fold, groupBy, partition
- [ ] FlatMap and chaining
- [ ] Lambda expressions
- [ ] Higher-order functions
- [ ] Function types
- [ ] Passing lambdas as parameters
- [ ] Interfaces with multiple implementations
- [ ] Default interface implementations
- [ ] Repository pattern
- [ ] Strategy pattern
- [ ] Functional pipelines

---

## Progress Update

```
BEGINNER ✅ (Levels 1-7)
├─ Hello World ✅
├─ Basic Types ✅
├─ Collections ✅
├─ Control Flow ✅
├─ Functions ✅
├─ Classes ✅
└─ Null Safety ✅

INTERMEDIATE 🚀 (Levels 8-11) ← YOU ARE HERE
├─ Extension Functions ✅
├─ Advanced Collections ✅
├─ Lambdas & Higher-Order Functions ✅
└─ Interfaces & Contracts ✅

ADVANCED 🔥 (Next)
├─ Coroutines (Critical!)
├─ Generics
├─ Delegation
└─ DSLs

READY FOR: Android Basics, Ktor Backend, KMP
```

**You're 55% done with fundamentals!** 📈

---

## Official Resources

- **Extension Functions:** https://kotlinlang.org/docs/extensions.html
- **Collections:** https://kotlinlang.org/docs/collections-overview.html
- **Lambdas:** https://kotlinlang.org/docs/lambdas.html
- **Interfaces:** https://kotlinlang.org/docs/interfaces.html
- **Higher-Order Functions:** https://kotlinlang.org/docs/lambdas.html#higher-order-functions

---

🚀 **You're crushing it. Intermediate Kotlin is yours!**
