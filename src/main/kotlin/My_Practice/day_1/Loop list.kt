package My_Practice.day_1

fun main() {
    val quests = listOf("Find Dragon", "Save Princess", "Collect Gold")

    // Way 1: forEach (cleaner)
    quests.forEach { quest ->
        println("Quest: $quest")
    }

    // Way 2: for loop
    for (quest in quests) {
        println("Quest: $quest")
    }

    // Way 3: access by index
    for (i in quests.indices) {
        println("Quest $i: ${quests[i]}")
    }
}