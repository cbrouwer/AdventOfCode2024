import java.io.File

fun main() {
    part1()
    part2()

}

fun part1() {
    val input = getInput()

    val prep : List<Long?> = input.windowed(2, 2, true).withIndex().flatMap { (idx, value) -> 
        List(value[0]) { _ -> idx.toLong() } + 
        (value.getOrNull(1)?.let { List(it) { _ -> null }} ?: emptyList() )
    }

    val emptySpots = prep.indices.filter { prep[it] == null }.toMutableList()


    val sum = prep.withIndex().reversed().sumOf { (idx, elem) ->
        if (elem != null) {
            elem * (emptySpots.removeFirstOrNull() ?: idx)
        } else {
            emptySpots.removeLastOrNull()
            0
        }
    }
    println("Part 1: $sum")

   
}


fun part2() {
    val input = getInput()

    val disk : List<Long?> = input.windowed(2, 2, true).withIndex().flatMap { (idx, value) -> 
        List(value[0]) { _ -> idx.toLong() } + 
        (value.getOrNull(1)?.let { List(it) { _ -> null }} ?: emptyList() )
    }
   
    var currentNumber: Long? = null
    var currentSize: Int? = null

    val optimizedDisk = disk.toMutableList()

    disk.withIndex().reversed().forEach { (idx, elem) ->
         if (elem != null && currentNumber == null) {
            currentNumber = elem
            currentSize = 1
         } else if (elem != null && currentNumber != null && elem == currentNumber) {
            currentSize = currentSize!! +  1
         } else if (elem != currentNumber) {
            val openSpotIdx = findEmptySpotOfSize(currentSize!!, optimizedDisk, idx)
            if (openSpotIdx != null) {
                for (updateIdx in 0..currentSize-1) { 
                    optimizedDisk[openSpotIdx + updateIdx] = currentNumber
                     
                    // Offset by one since idx is already pointing at the next number
                    optimizedDisk[idx+updateIdx+1] = null
                }
            } 
            // Finished moving, or unable to
            currentNumber = elem
            currentSize = 1
        }
    }
     val newSum = optimizedDisk.withIndex().sumOf{ (idx, value) -> 
        value?.times(idx) ?: 0
    }
   
    println("Part 2: $newSum")

}

fun findEmptySpotOfSize(size: Int, disk: List<Long?>, maxIdx: Int): Int?  {
    return disk.indices.firstOrNull { 
        if (it > maxIdx) {
            false
        } else {
            val allNull = (0..size-1).all{  i ->
                if (it+i > disk.size-1) {
                    false 
                }else {
                    disk[it+i] == null
                }
            
            }
            if (allNull) {
                true
            } else {
                false
            }
        }
    }
    
}


fun getInput(): List<Int> = File("day_9_input.txt").readLines().first().map{ it.digitToInt() }
