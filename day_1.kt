import java.io.File

fun main() {
    part1()
    part2()

}

fun part1() {
    val input = getInput()
    val splitted: List<Pair<Int, Int>> = input
      .map { it.split("   ") }
      .map { it[0].toInt() to it[1].toInt()}

    val leftSide = splitted.map{ it.first }.sorted()
    val rightSide = splitted.map { it.second }.sorted()

    val sumDiff = leftSide
        .zip(rightSide)
        .sumOf { pair -> 
            Math.abs(pair.first - pair.second)
        }

    println(sumDiff)
}

fun part2() {
    val input = getInput()
    val splitted: List<Pair<Int, Int>> = input
      .map { it.split("   ") }
      .map { it[0].toInt() to it[1].toInt()}

    val leftSide = splitted.map{ it.first }
    val rightSide = splitted.map { it.second }

    val rightSideFrequence = rightSide.groupingBy { it }.eachCount()

    val sumScore = leftSide.sumOf { leftNumber -> 
        leftNumber * (rightSide.count{ it == leftNumber})
    }
    println(sumScore)
}

fun getInput(): List<String> = File("day1input.txt").readLines()
