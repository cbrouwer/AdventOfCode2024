import java.io.File

fun main() {
    part1()
    part2()

}


fun countMatches(s: String): Int {
    val regex = """mul\((\d+),(\d+)\)""".toRegex()
    val sum = regex.findAll(s).sumOf { 
        it.groupValues
            .drop(1)
            .map { it.toInt()}
            .reduce(Int::times)
    }
    return sum
}


fun part1() {
    val input: String = getInput().joinToString()

   
    println(countMatches(input))
}

fun part2() {
   val input = getInput().joinToString()
   val regex = """(do\(\)|^).*?(don't\(\)|$)""".toRegex()

   val sum = regex.findAll(input).sumOf {
    countMatches(it.value)
   }
   println(sum)

//    var count = true
//    val sum = regex.findAll(input).sumOf { 
//         if (it.groupValues[2] == "mul" && count) {
//              it.groupValues[3].toInt() * it.groupValues[4].toInt()
//         } else if (it.value == "do()" && !count) {
//             count = true
//             0
//         } else if (it.value == "don't()" && count) {
//             count = false
//             0
//         } else {
//             //just keep spinning
//             0
//         }

//     }
}


fun getInput(): List<String> = File("day3input.txt").readLines()
