package My_Practice.day_2

// damage calculator
fun calculateDamage(weapon: String, heroLevel: Int, isCritical: Boolean = false): Int {
    val baseDamage = when (weapon) {
        "Sword" -> 50
        "Bow" -> 30
        "Fist" -> 70
        else -> 10
    }
    val levelBonus  = heroLevel * 2
    var totalDamage = baseDamage + levelBonus

    if (isCritical) {
        totalDamage *= 2
        println("\uD83D\uDCA5 CRITICAL HIT!")
    }
    return totalDamage
}

//display hearo status
fun displayHeroStatus(name: String, level: Int, health: Int, gold: Int){
    println("\n╔════════════════════════════╗")
    println("║  HERO STATUS              ║")
    println("╠════════════════════════════╣")
    println("║ Name: $name")
    println("║ Level: $level")
    println("║ Health: $health HP")
    println("║ Gold: $gold")
    println("╚════════════════════════════╝\n")
}

fun calEnterDungeon(heroLevel: Int, heroHealth: Int, hasKye: Boolean = false): Boolean {
    return heroLevel >= 10 && heroHealth > 30 || hasKye
}

fun purchaseItem(itemName: String, price: Int, gold: Int): Boolean {
    if (gold >= price) {
        println("Purchased $itemName for $price gold!!")
        return true
    } else {
        println("Not have enough gold!. Need ${price - gold} Golds more..")
        return false
    }
}

fun getQuestReward(difficulty: Int): Int {
    return when (difficulty) {
        1,2 -> 50
        3 -> 150
        4 -> 300
        5 -> 500
        else -> 0
    }
}

fun calculateExpToLevel(currentLevel: Int): Int {
    return currentLevel * 100
}

fun main() {
    println("=================== MASTER KOTLIN RPG ==================")

    val heroName = "Tej"
    val heroLevel = 15
    val heroHealth = 95
    var heroGold = 4500

    displayHeroStatus(heroName, heroLevel, heroHealth, heroGold)

    println("SCENARIO 1: You encountered a Goblin!!")

    val damage1 = calculateDamage("Sword", heroLevel)
    println("Regular attack : $damage1 damage\n")

    val damage2 = calculateDamage("Sword", heroLevel, isCritical = true)
    println("Total damage: $damage2 damage\n")

    println("🚪 SCENARIO 2: Dungeon Guard")

    if (calEnterDungeon(heroLevel, heroHealth)) {
        println("Guard: You may enter!")
    } else {
        println("Guard: You'r not ready yet!!")
    }

    println("\n🛍️  SCENARIO 3: Visit Merchant")

    val canBuySword = purchaseItem("Legendary sword", 2000, heroGold)
    if (canBuySword) {
        heroGold -= 2000
    }
    println()

    val canBuyPotion = purchaseItem("Health Potion", 1500, heroGold)
    if (canBuyPotion) {
        heroGold -= 1500
    }
    println("\n Update Hero Status\n")

    displayHeroStatus(heroName, heroLevel, heroHealth, heroGold)

    println("\n🎯 SCENARIO 4: Complete Quest")
    val questDifficulty = 4
    val questReward = getQuestReward(questDifficulty)
    println("Quest difficulty: $questDifficulty")
    println("Reward: $questReward")
    heroGold += questReward
    println()


    println("\n⬆️  SCENARIO 5: Experience Gain")
    val expNeeded = calculateExpToLevel(heroLevel)
    println("Experience needed to level up: $expNeeded XP")
    println()

    displayHeroStatus(heroName, heroLevel, heroHealth, heroGold)
}


