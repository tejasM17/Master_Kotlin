package My_Practice.day_3

import kotlin.math.max


data class Item(val name: String, val damage: Int = 0, val healing: Int = 0)

open class Charectoor(val name: String, var health: Int, var maxHealth: Int) {
    open fun takeDamage(amount: Int){
        health = maxOf(0, health - amount)

        println("$name takes $amount damage!!. Health: $health/$maxHealth")
    }

    open fun heal(amount: Int) {
        health = minOf(maxHealth, health + amount)
        println("$name healed $amount Health: $health/$maxHealth")
    }
}

class Heroo(name: String, health: Int) : Charectoor(name, health, health) {
    private val inventory = mutableListOf<Item>()

    fun addItem(item: Item){
        inventory.add(item)
        println("$name picked up ${item.name}")
    }
    fun attack(target: Charectoor, weapon: Item){
        val damage = weapon.damage
        println("$name attacks with ${weapon.name}!")
        target.takeDamage(damage)
    }
}

class Enemyy(name: String, health: Int) : Charectoor(name, health, health) {
    override fun takeDamage(amount: Int){
        val reduced = (amount * 0.8).toInt()  // 20% damage reduction
        super.takeDamage(reduced)
    }
}


fun main() {
    val hero = Heroo("Tej", 100)
    val enemy = Enemyy("Dragon", 60)

    val dagger = Item("Dagger", damage = 25)
    hero.addItem(dagger)
    hero.attack(enemy, dagger)
    enemy.takeDamage(12)
}