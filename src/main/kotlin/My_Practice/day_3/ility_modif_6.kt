package My_Practice.day_3

class BankAccount(private val balance: Int) {
    fun getBalance() : Int {
        return balance
    }
}

fun main() {
    val account = BankAccount(5000)
    println("Balance : ${account.getBalance()}")
}