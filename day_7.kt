import java.io.File

fun main() {
    part1()
    part2()

}

fun part1() {
    val input = getInput()
    
    val sum = input.filter{ calculate(it.numbers).contains(it.goal) }.sumOf { it.goal }
    println("Part1: $sum")
}



fun calculate(numbers: List<Int> ): List<Long> =
    when {
        numbers.size < 2 -> numbers.map { it.toLong() }
        else -> 
            calculate(numbers.dropLast(1)).flatMap { 
                listOf(
                    it + numbers.last(), 
                    it * numbers.last()
                )
            }
    }

fun calculatePart2(numbers: List<Int> ): List<Long> {
    return when {
        numbers.size < 2 -> numbers.map { it.toLong() }
        else -> 
            calculatePart2(numbers.dropLast(1)).flatMap { 
               listOf(
                    it + numbers.last(), 
                    it * numbers.last(),
                    "$it${numbers.last()}".toLong(),
                )
            }
    }
}


fun part2() {
    val input = getInput()

    val sum = input.filter{ calculatePart2(it.numbers).contains(it.goal) }
    .also{  println("Found match in $it")}
    .sumOf { 
        it.goal
     }
     println("Part2: $sum")
}

fun getInput(): List<Equation> = File("day_7_input.txt").readLines().map { 
    val splitted = it.split(": ")
    Equation(goal = splitted[0].toLong(), numbers = splitted[1].split(' ').map { it.trim().toInt() })
}

data class Equation(val goal: Long, val numbers: List<Int>)
