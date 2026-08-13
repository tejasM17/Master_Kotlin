package My_Practice.day_3

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
    val p1 = Product(10, "Sword", 50.0, 5)
    val p2 = Product(20, "Shield", 30.0, 3)
    val p3 = Product(30, "Dagger", 70.0, 4)

    val customer1 = Customer(111, "Tej", "tej@example.com")

    customer1.cart.addItem(p3)
    customer1.cart.addItem(p1)

    val order = customer1.cart.checkout(customer1.id, customer1.name)
    println("Order total: ${order.total}")
}