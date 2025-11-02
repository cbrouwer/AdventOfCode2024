import java.io.File

fun main() {
    part1()
    part2()

}

fun part1() {
    val input = getInput()

    var stones = input
    var blinksLeft = 25
    while (blinksLeft > 0) {
        stones = blink(stones)
        blinksLeft -= 1
    }
    println("Part 1: ${stones.size}")
    
}

fun blink(stones: List<Stone>): List<Stone> {
    return stones.flatMap{ stone -> 
       blink(stone)
    }
}

fun blink(stone: Stone): List<Stone> =
    when {
        stone.number == 0L -> listOf(Stone(1))
        stone.shouldSplit() -> stone.split()
        else -> listOf(Stone(stone.number * 2024L))
    } 


fun part2() {
    val input = getInput()
    val blinks = 50

    val totalSum = input.sumOf { stone -> 
        var blinksLeft = blinks
        var result = listOf(stone)
        while (blinksLeft > 0) {
            result = blink(result)
            blinksLeft -= 1
        }
        println("Finishd stone $stone; size ${result.size}")
        result.size
    }
    
    println("Part 2: $totalSum")
}

fun getInput(): List<Stone> = File("day_11_input.txt").readLines().first().split(" ").map { it.toStone() } 

fun String.toStone(): Stone = Stone(this.toLong())

@JvmInline
value class Stone(val number: Long) {
    fun shouldSplit(): Boolean = number.toString().length % 2 == 0
    fun split(): List<Stone> {
        val numberS = number.toString()
        val indexToSplit = numberS.length / 2
        val result = listOf(
            Stone(numberS.take(indexToSplit).toLong()),
            Stone(numberS.takeLast(indexToSplit).toLong()),
        )
        return result
    } 
}
