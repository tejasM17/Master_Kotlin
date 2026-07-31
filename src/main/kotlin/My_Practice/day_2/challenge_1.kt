package My_Practice.day_2

fun main() {
    val heroHealth = 65
    val mana = 45
    val isPoisoned = false

    println("=== HERO STATUS ===\n")

    // Health Check (if/else if/else)
    print("Health: ")
    if (heroHealth > 75) {
        println("Excellent ($heroHealth HP)")
    } else if (heroHealth >= 50) {
        println("Good ($heroHealth HP)")
    } else if (heroHealth >= 25) {
        println("Wounded ($heroHealth HP)")
    } else {
        println("Critical ($heroHealth HP)")
    }

    // Mana Check (when)
    print("Mana: ")
    when (mana) {
        0 -> println("Out of mana")
        in 1..50 -> println("Low mana ($mana)")
        in 51..100 -> println("Decent mana ($mana)")
        else -> println("Full mana ($mana)")
    }

    // Poison Check
    println("Poisoned: ${if (isPoisoned) "Yes" else "No"}")

    println()

    // Combat Readiness (complex condition)
    if ((heroHealth > 50 && mana > 30) || !isPoisoned) {
        println("✅ Ready for combat!")
    } else {
        println("⚠️  Rest before fighting!")
    }
}