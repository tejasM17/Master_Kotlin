package My_Practice.day_3

data class User(val id: Int, val name: String, val email: String)


fun main() {
    val user1 = User(1, "tej", "tej@example.com")
    val user2 = User(1, "tej", "tej@example.com")

    println(user1 == user2)
    println(user1)

    val user3 = user1.copy(id = 2, name = "tej2")
    println(user3)
}
