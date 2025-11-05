import java.io.File

fun main() {
    part1()
    part2()

}

val possibleOperators: List<((Long, Long) -> Long)> = listOf(
    { a, b -> a + b },
    { a, b -> a * b }
)

fun part1() {
    val input = getInput()
    
    val sum = input
        .filter{
            hasSolution(
                possibleOperators, 
                it.goal,
                it.numbers.first(),
                it.numbers.subList(1, it.numbers.size)
            ) 
        }
        .also { println("CHECK ME! $it") }
        .sumOf { it.goal }
    println("Part1: $sum")
}


fun hasSolution(
    operators: List<(Long, Long) -> Long>,
    goal: Long,
    sum: Long,
    numbers: List<Long>
): Boolean {
    return when {
        numbers.isEmpty() ->  goal == sum
        sum > goal -> false
        else -> {
            
            operators.any { op -> 
                hasSolution(
                    operators,
                    goal,
                    op(sum, numbers.first()),
                    numbers.subList(1, numbers.size)
                )
            }
        }
    }
}
   
fun part2() {
    val input = getInput()

     val sum = input
        .filter{
            hasSolution(
                possibleOperators + {a, b -> "$a$b".toLong()}, 
                it.goal,
                it.numbers.first(),
                it.numbers.subList(1, it.numbers.size)
            ) 
        }
        .also { println("CHECK ME! $it") }
        .sumOf { it.goal }
    println("Part1: $sum")
}

fun getInput(): List<Equation> = File("day_7_input.txt").readLines().map { 
    val splitted = it.split(": ")
    Equation(goal = splitted[0].toLong(), numbers = splitted[1].split(' ').map { it.trim().toLong() })
}

data class Equation(val goal: Long, val numbers: List<Long>)
