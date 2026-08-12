package My_Practice.day_3

class Player(
    val userName: String,
    var health: Int,
    var mana: Int
) {
    var isAlive = true // properties (additional)

    fun takeDamage(amount: Int) {
        health -= amount
        if (health <= 0) {
            isAlive = false
            println("$userName eliminated!")
        }
    }

    fun restoreMana(amount: Int): Unit {
        mana += amount
    }

    fun status(): String {
        return "$userName: HP - $health, Mana: $mana"
    }
}

fun main() {
    val p1 = Player("Teja",100, 60)

    println(p1.status())
    p1.takeDamage(99)

    println(p1.status())
    p1.restoreMana(30)

    println(p1.status())

}