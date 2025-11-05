import java.io.File

fun main() {
    part1()
   // part2()

}

val wordToFind = "XMAS"
val directions = (-1..1).flatMap { x ->
    (-1..1).map { y ->
        x to y
    }
}.filterNot { it.first == 0 && it.second == 0}

fun part1() {
    val input = getInput()
    

    val matches: Int = input.flatMapIndexed { yidx, line -> 
        line.mapIndexed { xidx, char -> 
            directions.count { vector -> 
                dive(wordToFind, input, yidx, xidx, vector)
            }
        }
    }.sum()
    println(matches)
}

tailrec fun dive(leftToFind: String, input: List<String>, y:Int, x: Int, vector: Pair<Int, Int>): Boolean =
    when { 
        leftToFind.isEmpty() -> true
        leftToFind.first() != input.getOrNull(y)?.getOrNull(x) -> false
        else -> dive(leftToFind.substring(1), input, y+vector.first, x + vector.second, vector)
    }



// tailrec fun dive(input: List<String>, y:Int, x: Int, dy: Int, dx: Int, wordSoFar: String): Boolean {
//     if (wordSoFar == wordToFind) return true
//     val newLetter = input.getOrNull(y+dy)?.getOrNull(x+dx)
//     return if (newLetter != null && wordToFind.startsWith(wordSoFar + newLetter)) {
//         dive(input, y+dy, x+dx, dy, dx, wordSoFar + newLetter)
//     } else {
//         false
//     }
// }

fun part2() {
    val input = getInput()

    val possibleCoordinates: List<Pair<Int, Int>> = input.flatMapIndexed { yidx, line -> 
        line.mapIndexed { xidx, char -> 
            if (char == 'A') {
               yidx to xidx
            } else { 
                null
            }   
        }.filterNotNull()
    }
    val matches = possibleCoordinates.filter{ 
        checkXmas(input, it)
    }
    println(matches.count())

    
}

fun checkXmas(input: List<String>, coordinate: Pair<Int, Int>): Boolean {
    val lineAbove = input.getOrNull(coordinate.first -1)
    val lineBelow = input.getOrNull(coordinate.first + 1)
    if (lineAbove == null || lineBelow == null) return false

    val charAboveLeft = lineAbove.getOrNull(coordinate.second-1)
    val charAboveRight = lineAbove.getOrNull(coordinate.second+1)
    val charBelowLeft = lineBelow.getOrNull(coordinate.second-1)
    val charBelowRight = lineBelow.getOrNull(coordinate.second+1)

    return isMas(charAboveLeft, charBelowRight) && isMas(charAboveRight, charBelowLeft)
}

fun isMas(char1: Char?, char2: Char?): Boolean = 
    char1 == 'M' && char2 == 'S' ||
        char1 == 'S' && char2 == 'M'

fun getInput(): List<String> = File("day_4_input.txt").readLines()
