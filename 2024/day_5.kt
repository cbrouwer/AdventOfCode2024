import java.io.File

fun main() {
  //  part1()
    part2()

}

fun part1() {
    val input = getInput()
    val index = input.indexOf("")
    val rules: Map<Int, Set<Int>> = input
        .take(index)
        .map { 
            val splitted =  it.split("|")
            splitted[0].toInt() to splitted[1].toInt()
        }
        .groupBy ({it.first}, {it.second})
        .mapValues { it.value.toSet() }

    val pages: List<List<Int>> = input.takeLast(input.size - index-1).map{ 
        it.split(",").map{it.toInt()}
    }

    val validPages = pages.filter { pageList -> 
        isValidList(pageList, rules)
    }
    
    val sum = validPages.sumOf { it.elementAt((it.size -1 ) / 2)}
    println(sum)
    
}

fun isValidList(list: List<Int>, rules: Map<Int, Set<Int>>): Boolean =
    list.filterIndexed { idx, elem -> 
        val elemRules = rules[elem] ?: emptyList()
        val elemsBefore = list.take(idx)
        (elemsBefore intersect elemRules).isEmpty()
    }.size == list.size


fun part2() {
   val input = getInput()
    val index = input.indexOf("")
    val rules: Map<Int, Set<Int>> = input
        .take(index)
        .map { 
            val splitted =  it.split("|")
            splitted[0].toInt() to splitted[1].toInt()
        }
        .groupBy ({it.first}, {it.second})
        .mapValues { it.value.toSet() }

    val pages = input.takeLast(input.size - index-1).map{ 
        it.split(",").map{it.toInt()}
    }

    val invalidPages = pages.filterNot { pageList -> 
       isValidList(pageList, rules)
    }

    val validPages = invalidPages.map {  invalidPage ->
       reorderUntilValid(invalidPage, rules)
    }
     val sum = validPages.sumOf { it.elementAt((it.size -1 ) / 2)}
    println(sum)
    
}

tailrec fun reorderUntilValid(page: List<Int>, rules: Map<Int, Set<Int>>): List<Int> {
    val newList = shuffle(page, rules)
    return when {
        isValidList(newList, rules) -> return newList
        newList == page -> return page
        else -> reorderUntilValid(newList, rules)
    }
}

fun shuffle(page: List<Int>, rules: Map<Int, Set<Int>> ): List<Int> { 
    page.forEachIndexed { idx, elem -> 
        val elemRules = rules[elem] ?: emptySet()
        val elemsBefore = page.take(idx)
        val forbiddenBefore = (elemsBefore intersect elemRules).toList()

        if (forbiddenBefore.isNotEmpty()) {
            val putFirst: List<Int> = page.take(idx) - forbiddenBefore
            return putFirst + listOf(elem) + forbiddenBefore + page.takeLast(page.size - (idx + 1) )
        }
    }
    // Apparently it was valid all along?
    return page
}

fun getInput(): List<String> = File("day_5_input.txt").readLines()
