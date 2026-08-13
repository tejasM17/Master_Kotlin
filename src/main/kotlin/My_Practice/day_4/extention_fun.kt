package My_Practice.day_4

data class Order(val id: Int, val customer: String, val items: List<String>, val total: Double)

val orders = listOf(
    Order(1, "Alice", listOf("Sword", "Shield"), 80.0),
    Order(2, "Bob", listOf("Potion", "Potion"), 20.0),
    Order(3, "Alice", listOf("Bow"), 40.0)
)

fun main() {
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
}