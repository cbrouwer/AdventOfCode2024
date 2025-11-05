import java.io.File

fun main() {
    //part1()
    part2()

}

fun part1() {
    val input = getInput()
    val digits = input.map { line -> 
        line.filter { it.isDigit() }
    }.map { 
        "${it.first()}${it.last()}" 
    }.sumOf { 
        it.toInt() 
    }
    
    println("Part 1: $digits")
    
}

fun part2() {
   val input = getInput()
    val digits = input.sumOf { line -> 
        val nrs = getDigits(line)
        val nr =  "${nrs.first()}${nrs.last()}".toInt()
        nr
       }
    println("Part 2: $digits")
}

val numberStrings: Map<String, Int> = mapOf(
    "one" to 1, 
    "two" to 2 , 
    "three" to 3, 
    "four" to 4,
    "five" to 5, 
    "six" to 6,
    "seven" to 7,
    "eight" to 8 , 
    "nine" to 9,
)


fun getDigits(line: String): List<Int> {
    return line.mapIndexed { idx, char -> 
        when {
            char.isDigit() -> char.digitToInt()
            else -> { 
                // check if any numberStrings match
                val matchingNr = numberStrings.keys.firstOrNull { key -> 
                   line.indexOf(key, idx) == idx
                }
                matchingNr?.let { numberStrings[it] }
            }
        }
    }.filterNotNull()
}



fun getInput(): List<String> = File("day_1_input.txt").readLines()
