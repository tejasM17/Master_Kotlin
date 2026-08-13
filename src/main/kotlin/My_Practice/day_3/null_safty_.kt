package My_Practice.day_3

class null_safty {

}

fun greet(name: String?) {
    println("Hello, ${name ?: "stranger"}")
}

fun main() {
    greet("Alice")   // Hello, Alice
    greet(null)
    val name: String? = "Alice"
    val length = name!!.length

    println(length)


//    val items: List<String>? = null          // List itself can be null
//    val items: List<String?>? = null         // Items OR list can be null

    val list: List<String?> = listOf("a", null, "b")
    for (item in list) {
        println(item?.uppercase() ?: "NULL")
    }
}