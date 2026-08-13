package My_Practice.day_3

data class Usr(val id: Int, val name: String, val email: String?)

class UserRepository{
    private val users = mutableListOf(
        Usr(11, "Raju", "raj@example.com"),
        Usr(22, "Raamm", null),
        Usr(22, "Tej", "tej@example.com")
    )

    fun getUserById(id: Int): Usr? {
        return users.find { it.id == id }
    }

    fun getUserInfo(user: Usr): Usr{
        return Usr(user.id, user.name, user.email.toString())
    }

    fun sendNotification(userId: Int) {
        getUserById(userId)?.let {
            it.email?.let { email ->
                println("Sending email to $email for user ${it.name}")
            } ?: run {
                println("${it.name} has no email address")
            }
        }?: run {
            println("User $userId Not found..!")
        }
    }
}


fun main() {
    val repo = UserRepository()

    println(repo.getUserById(22))
    println(repo.getUserById(33))
    repo.sendNotification(22)
    repo.sendNotification(11)
}