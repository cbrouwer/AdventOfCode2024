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
    println(sum)

   
}

// fun part1_old() {
//     val input = getInput()

//     val actualInput = if (input.size %2 == 0) input else input + 0

    
//     val (evenWithOrigIndex, unevenWithOrigIndex) = actualInput.withIndex().partition { it.index % 2 == 0}
    
//     val result: List<DiskFile> = evenWithOrigIndex.zip(unevenWithOrigIndex).flatMapIndexed { fileIdx, (eventValueWithIdx, unevenValueWithIdx) -> 
//         val list1: List<DiskFile> = List(eventValueWithIdx.value) { _ -> DiskFile(fileIdx.toLong()) }
//         val list2: List<DiskFile> = List(unevenValueWithIdx.value) { _ -> DiskFile(-1L) }
//         list1 + list2
//     }

    
//     //(result.toDiskString())

//     val updatedResult = result.toMutableList()
//     while (!hasGaps(updatedResult) ) {
//         switchAround(updatedResult)
//     }
//   //  println(updatedResult.toDiskString())
//     println(checksum(updatedResult))
   
// }

// fun hasGaps(list: List<DiskFile>): Boolean {
//     val idx1 = list.indexOfFirst { it.isEmpty() }
//     val idx2 = list.indexOfLast {  !it.isEmpty()}
//     return idx1 == idx2 +1
// }

// fun switchAround(list: MutableList<DiskFile>) {

//     val lastCharIdx = list.indexOfLast { !it.isEmpty() }
//     val firstEmptyIdx = list.indexOfFirst {it.isEmpty()}
    
//     val emptyFile = list.get(firstEmptyIdx)
//     val fileToSwitch = list.get(lastCharIdx)

//     list.set(firstEmptyIdx, DiskFile(fileToSwitch.fileId))
//     list.set(lastCharIdx, DiskFile(-1))
// }

fun part2() {
    val input = getInput()
}

fun getInput(): List<Int> = File("day_9_input.txt").readLines().first().map{ it.digitToInt() }

// data class DiskFile(val fileId: Long) {
//     fun isEmpty(): Boolean = this.fileId == -1L
// }
// fun List<DiskFile>.toDiskString(): String {
//     val sb = StringBuilder()
//     this.forEach{ file ->
//         if (file.fileId < 0) sb.append(".") else sb.append("${file.fileId}")
//     }
//     return sb.toString()
// }

// fun checksum(input: List<DiskFile>): Long {
//     return input.withIndex().filterNot{it.value.isEmpty()}.sumOf { (idx, file) ->
//         idx * file.fileId
//     }
// }