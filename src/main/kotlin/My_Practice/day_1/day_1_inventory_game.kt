package My_Practice.day_1

fun main() {
    println("============ Day 1_inventory_game ===========")

    // LIST GAME
    val backpack = mutableListOf("Sword", "Shield", "Potion", "Gun")
    println("📦 Backpack (${backpack.size} items) : ")
    backpack.forEachIndexed { index, item ->

        println("[$index] $item")
    }

    backpack.add("Bow")
    println("\n✅ Added Bow")
    backpack.add("Knife")
    println("\n✅ Knife")
    backpack.add("Magic Stone")
    println("\n✅ Magic Stone")


    println("\uD83D\uDCE6 Now Backpack has ${backpack.size} items")

    backpack.forEachIndexed { index, item ->

        println("[$index] $item")
    }

    // SET GAME
    println("\n========= SET GAME ========\n")

    val achievements = mutableSetOf("1St Quest", "Defeat Boss", "Collect Mana crystal")
    println("\n \uD83C\uDFC6 Achivements (${achievements.size}) :")
    achievements.forEach { ach ->
        println(" - $ach")
    }
    achievements.add("Defeat Boss")  // Duplicate, ignored
    println("\n📝 Tried to add duplicate 'Defeat Boss'")
    println("🏆 Still ${achievements.size} achievements (duplicate ignored)\n")


    println("========== MAP GAME ========\n")

    val itemStats = mutableMapOf(
        "Sword" to 50,
        "Shield" to 30,
        "Potion" to 20,
        "Bow" to 40
    )

    println("\n⚔️  Item Stats (Damage):")
    itemStats.forEach { (item, damage) ->
        println("  $item → $damage damage")
    }
    itemStats["Magic Stone"] = 90
    itemStats["Knife"] = 80
    itemStats["Sword"] = 60

    println("\n Added 2 more items :")
    itemStats.forEach { (item, damage) ->
        println("  $item → $damage damage")
    }

    println("\n✨ Upgraded Sword!")
    println("⚔️  Sword now does ${itemStats["Sword"]} damage")

    val totalDamage = itemStats.values.sum()
    println("Total damage Accros all weapons : $totalDamage")
}