package My_Practice.day_2

fun main() {
    val heroLvl = 7

    if (heroLvl >= 5){
        println("Welcome. Worrier! You can enter the castle")
    } else {
        println("You'r too week. Come back when you are LVL. 5")
    }

    val gold = 200
    val house = 300

    if (gold >= house) {
        println("You can afford the house")
    } else {
        println("search for another house")
    }
}