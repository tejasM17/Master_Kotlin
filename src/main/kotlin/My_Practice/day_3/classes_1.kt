package org.tejas.kotlin.My_Practice.day_3

class Hero(val name: String) {
    fun greet() {
        println("Hello $name")
    }
}

fun main() {
    val hero = Hero("Tejas")
    hero.greet()
}