package My_Practice.day_3

class Her(val name: String) {
    companion object {
        const val MAX_LEVEL = 100
        var heroCount = 0


        fun createDefaultHero() : Her {
            heroCount++
            return Her("Hero #$heroCount")
        }
    }
}

fun main() {
    println(Her.MAX_LEVEL)

    val h1 = Her.createDefaultHero()
    val h2 = Her.createDefaultHero()
    val h3 = Her.createDefaultHero()
    val h4 = Her.createDefaultHero()
    val h5 = Her.createDefaultHero()

    println(Her.heroCount)
}