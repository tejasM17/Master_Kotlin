package My_Practice.day_2

fun main() {
    val heroLevel = 8

    if (heroLevel < 5) {
        println("Blacksmith: You're too weak. Come back later.")
    } else if (heroLevel <= 10) {
        println("Blacksmith: I can forge you a basic sword!")
    } else {
        println("Blacksmith: Ah, a true warrior! Here's a legendary blade!")
    }

    val heroLvl = 4
    val heroHealth = 30

    if (heroHealth <= 0) {
        println("💀 You are dead. Game over!")
    } else if (heroLvl < 10) {
        println("⚠️  You're a weak adventurer.")
    } else if (heroLvl < 15) {
        println("💪 You're a strong warrior!")
    } else {
        println("🌟 You're a legendary hero!")
    }
}
