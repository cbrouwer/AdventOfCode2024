import java.io.File

fun main() {
   // part1()
    part2()

}


fun isValid(numbers: List<Int>): Boolean {
    val allDecreasing = numbers.zipWithNext().all { pair ->
        pair.first - pair.second in 1..3
    }
    val allIncreasing = numbers.zipWithNext().all { pair -> 
        pair.second - pair.first in 1..3
    }
    
    return allIncreasing || allDecreasing
}

fun part1() {
    val input = getInput()
    val splitted: List<List<Int>> = input
      .map { it.split(" ").map{ it.toInt()} }

      
    val valid = splitted.count { lineList -> 
        val allDecreasing = lineList
            .zipWithNext()
            .all { pair ->
                 pair.first - pair.second in 1..3
            }
       val allIncreasing = lineList
            .zipWithNext()
            .all { pair -> 
                pair.second - pair.first in 1..3
            }

        allDecreasing || allIncreasing
    }
    println(valid)
    
}

fun part2() {
   val input = getInput()
    val splitted: List<List<Int>> = input.map { it.split(" ").map{ it.toInt()} }

      
    val valid = splitted.count { lineList -> 

        if (isValid(lineList)) {
            true
        } else {
            // try to remove 1 item 
            lineList.indices.any { idx -> 
                val newList = lineList.filterIndexed { listIdx, _ -> idx != listIdx}
                isValid(newList)
            }
        }
    }
    println(valid)
}


fun getInput(): List<String> = File("day2input.txt").readLines()
