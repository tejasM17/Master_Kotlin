package My_Practice.day_2

fun main() {
    println("========== NPC ENCOUNTER SYSTEM ==========\n")

    val heroLevel = 8
    val heroHealth = 75
    val heroGold = 250

    println("Hero Stats: Level $heroLevel | Health: $heroHealth HP | Gold: $heroGold\n")

    println("You encounter the blacksmith..")
    if (heroLevel >= 5) {
        println("Blacksmith: Greetings, warrior!")

        if (heroGold >= 100) {
            println("Blacksmith: You have enough gold for Sword!!")
        }else {
            println("Blacksmith: You need ${100 - heroGold} more Gold for Sword!!")
        }
    } else {
        println("You are too week, beginner!")
    }

    println("\n" + "=".repeat(40) + "\n")

    println("📍 You encounter the Dungeon Guard...")

    when {
        heroLevel < 5 -> println("👮 Guard: Turn back, weakling!")
        heroLevel < 15 && heroHealth < 50 -> println("👮 Guard: You're wounded! Rest first.")
        heroLevel >= 15 || heroHealth >= 80 -> println("👮 Guard: You may pass, mighty one!")
        else -> println("👮 Guard: You look ready. Proceed with caution.")
    }

    println("\n" + "=".repeat(40) + "\n")

    val heroClass = "Warrior"
    println("📍 You encounter the Merchant...")
    when (heroClass) {
        "Warrior" -> println("🛍️  Merchant: I have mighty swords for you!")
        "Mage" -> println("🛍️  Merchant: Spell scrolls! Get your scrolls here!")
        "Archer" -> println("🛍️  Merchant: Arrows and bows of the finest quality!")
        else -> println("🛍️  Merchant: Welcome, traveler!")
    }

    println("\n" + "=".repeat(40) + "\n")

    println("📊 Your Rank:")
    when (heroLevel) {
        in 1..4 -> println("🌱 Novice (Level $heroLevel)")
        in 5..10 -> println("💪 Warrior (Level $heroLevel)")
        in 11..20 -> println("⚔️  Champion (Level $heroLevel)")
        else -> println("🌟 Legend (Level $heroLevel)")
    }

    println("\n" + "=".repeat(40) + "\n")

    // ===== BATTLE READINESS =====
    val isReady = heroLevel >= 10 && heroHealth >= 50
    val hasResources = heroGold >= 100

    println("⚡ Battle Readiness:")
    if (isReady && hasResources) {
        println("✅ You're FULLY prepared! Ready for epic battle!")
    } else if (isReady || hasResources) {
        println("⚠️  You're PARTIALLY prepared. Could be better.")
    } else {
        println("❌ You're NOT ready. Train more!")
    }
}
