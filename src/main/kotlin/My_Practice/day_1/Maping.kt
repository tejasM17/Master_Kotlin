package My_Practice.day_1

fun main() {
    val maps = mapOf(
        "key" to "Value",
        "Tiptur" to 40,
        "Hassan" to 20,
        "Banglore" to 10
    )

    println("Maps size = ${maps.size}")
    println(maps)
    println("Map key : ${maps.keys}")
    println("Map valu : ${maps.values}\n\n ======= Mutable Maping =======")

    val inventory = mutableMapOf(
        "sword" to 40,
        "Shield" to 30,
        "Poison" to 20,
    )

    println(inventory["sword"])
    println(inventory["Shield"])

    inventory["sword"] = 3
    inventory["Shiels"] = 2
    println(inventory["sword"])
    println(inventory["Shield"])

    println(inventory.containsKey("Shield"))
    println(inventory.containsValue(3))

    println(inventory.keys)
    println(inventory.values)
    println("\n\n======== Loop throught Map =========\n\n")

    val ncpDialoug = mapOf(
        "Blackmagic" to "let's Content creation",
        "Game" to "Time pass resources",
        "Gym" to "Mind management session"
    )
    
    ncpDialoug.forEach { ncp, dialouge ->
        println("$ncp say: $dialouge")
    }
}