package My_Practice.day_1

fun main() {
    val trophi = setOf("Kotlin", "Android", "Java", "Kotlin", "😮‍💨")

    println(trophi.size)
    println(trophi)

    val visited = mutableSetOf("Tiptur", "Hassan", "Manglore", "Banglore")

    visited.add("Dubai")
    visited.add("London")
    visited.add("Paris")
    visited.remove("Hassan")

    println("Visted size = ${visited.size}")
    println(visited)
    println(visited.contains("Tiptur"))
}