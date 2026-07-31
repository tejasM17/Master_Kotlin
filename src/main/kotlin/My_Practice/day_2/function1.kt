package My_Practice.day_2

fun calculateDamage(weapon: String): Int {
    return when (weapon) {
        "Sword" -> 50
        "Knife" -> 40
        "Arrow" -> 30
        else -> 10
    }
}

fun noArgnoRet() {
    println("\nTHis is no arg and no ret fun.. broii@@")
}

fun displayTitle() {
    println("╔════════════════════════════╗")
    println("║   MASTER KOTLIN QUEST      ║")
    println("╚════════════════════════════╝")
}

fun greetHero(heroName: String) {
    println("🍺 Innkeeper: Welcome, $heroName!")
}

fun main() {
    println("Sword damage : ${calculateDamage("Sword")}")
    println("Sword damage : ${calculateDamage("")}")
    println("Sword damage : ${calculateDamage("Arrow")}")

    noArgnoRet()
    noArgnoRet()

    displayTitle()

    greetHero("Alice")
    greetHero("Bob")
    greetHero("Charlie")

}