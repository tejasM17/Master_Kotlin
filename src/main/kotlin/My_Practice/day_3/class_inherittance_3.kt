package My_Practice.day_3

open class Character(val name: String, open var health: Int) {
    open fun takeDamage(amount : Int) {
        health -= amount
        println("$name take $amount Damage. Health - $health")
    }
}

class Hero( name: String, health: Int) : Character(name, health) {
    fun levelUp() {
        println("$name leveled Up..!")
    }
}

class Enemy(name: String, health: Int) : Character(name, health) {
    override fun takeDamage(amount: Int) {
        val reduceDamage = amount / 2
        super.takeDamage(reduceDamage)
    }
}

fun main() {
    val hero = Hero("Tejj", 99)
    val enemy = Enemy("Goblin", 80)

    hero.takeDamage(20)
    enemy.takeDamage(30)
    hero.levelUp()
}
