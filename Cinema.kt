package cinema

class Cinema(rows: Int, seats: Int) {
    private val grid: MutableList<MutableList<Char>> = ArrayList()
    private val totalIncome = if (rows * seats <= 60) {
        rows * seats * 10
    } else (rows / 2 * seats * 10) + ((rows / 2 + 1) * seats * 8)
    private val symbol = 'S'
    private var symbolStr = ""
    private var soldTickets = 0
    private var currentIncome = 0
    private var exit = false

    init {
        for (ch in 1..seats) symbolStr += symbol
        for (i in 1..rows) grid.add(symbolStr.toMutableList())

        while (!exit) {
            println("1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit")
            when(readln()) {
                "1" -> print(rows, seats)
                "2" -> while (!buy(rows, seats)) continue
                "3" -> statistics(rows, seats)
                "0" -> exit = true
            }
        }
    }

    private fun print(rows: Int, seats: Int) {
        println("Cinema:")
        print(" ")
        for (i in 1..seats) {
            if (i == seats) {
                print(" $i\n")
            } else print(" $i")
        }
        for (i in 1..rows) {
            println("$i " + grid[i - 1].joinToString(" "))
        }
    }

    private fun buy(rows: Int, seats: Int) : Boolean {
        var row: Int
        var seat: Int
        while (true) {
            row = integerInput("Enter a row number:")
            seat = integerInput("Enter a seat number in that row:")
            if (row in 1..rows && seat in 1..seats) break
            else {
                println("Wrong input!")
                continue
            }
        }

        if (grid[row - 1][seat - 1] == 'B') {
            println("That ticket has already been purchased!")
            return false
        }

        currentIncome += if (rows * seats <= 60) {
            println("Ticket price: $10")
            10
        } else {
            if (row > rows / 2) {
                println("Ticket price: $8")
                8
            } else {
                println("Ticket price: $10")
                10
            }
        }

        grid[row - 1][seat - 1] = 'B'
        soldTickets++
        return true
    }

    private fun statistics(rows: Int, seats: Int) {
        println("Number of purchased tickets: $soldTickets\n" +
                "Percentage: ${"%.2f".format(soldTickets * 100.0 / (rows * seats))}%\n" +
                "Current income: \$$currentIncome\n" +
                "Total income: \$$totalIncome")
    }
}

fun main() {
    val rows = integerInput("Enter the number of rows:")
    val seats = integerInput("Enter the number of seats in each row:")

    Cinema(rows, seats)
}

fun integerInput(str: String) : Int {
    while (true ) {
        try {
            println(str)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            println("Only numbers are allowed.")
        }
    }
}