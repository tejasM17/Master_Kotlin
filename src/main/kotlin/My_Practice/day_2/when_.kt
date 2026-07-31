package My_Practice.day_2

fun main() {

    val ncpNamee = "Healer"

    when (ncpNamee) {
        "Blacksmith" -> println("Blacksmith: I forgot weapons")
        "Healer" -> println("Healer: I mend your wounds")
        "Guard" -> println("Guard: State your business")
        else -> println("Unknown NCP ?")
    }

    val heroLevel = 8

    when (heroLevel) {
        in 1..4 -> println("🌱 Novice")
        in 5..10 -> println("💪 Warrior")
        in 11..20 -> println("⚔️  Champion")
        else -> println("🌟 Legend")
    }

    val day = "Saturday"

    when (day) {
        "Saturday", "Sunday" -> println("🎉 Weekend! Party time!")
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> println("😴 Work day...")
        else -> println("❓ Invalid day")
    }

    println("\n======= Use Loical Operations =======\n")

    val heroLvl = 12
    val heroHealth = 100

    if (heroLvl > 10 && heroHealth > 50) {
        println("✅ You're strong AND healthy. Ready for battle!")
    } else {
        println("❌ You need to be both strong AND healthy.")
    }

    val hasKey = false
    val hasPicklock = true

    if (hasKey || hasPicklock) {
        println("✅ You can open the door! (with key OR picklock)")
    } else {
        println("❌ You can't open the door.")
    }

    val isAlive = true

    if (!isAlive) {
        println("💀 You're dead.")
    } else {
        println("✅ You're alive!")
    }

    val level = 12
    val health = 100
    val hasMagic = false

    if ((level > 10 && health > 50) || hasMagic) {
        println("⚔️  You're ready for the boss fight!")
    } else {
        println("🛡️  You need to prepare more.")
    }
}