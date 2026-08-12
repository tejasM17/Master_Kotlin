package org.tejas.kotlin.My_Practice.day_3

class Hero(val name: String, var level: Int, var health: Int) {
    fun display() {
        println("$name - Level $level - $health HP")
    }

    // methods
    fun levelUp() {
        level++
        println("$name is now Level: $level ")
    }

    init {
        println("Hero $name created at level $level")
        if (level > 20) println("Wow, high level!")
    }
}

fun main() {
    val hero = Hero("Tej", 22, 100) //constructor
    val noob = Hero("noob", 2, 100) //constructor

    hero.display()
    hero.levelUp()
    noob.display()
    noob.levelUp()
}
