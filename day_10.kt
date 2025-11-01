import java.io.File

fun main() {
    part1()
    part2()

}

fun part1() {
    val input = getInput()
    
    val startingPoints = input.filter { it.score == 0 }

    val sumOfAllRoutes = startingPoints.sumOf { 
        val routes = walk(it, input, setOf(it))
        println("Found $routes for starting point $it")
        routes.count()
    }
    println("Part 1: $sumOfAllRoutes")
    
}

fun walk(from: MapPoint, map: List<MapPoint>, pointsSeen: Set<MapPoint>): Set<MapPoint> {
    val possibleMoves = getPossibleMoves(from, map, pointsSeen)
    return when {
        from.score == 9 -> setOf(from)
        possibleMoves.isEmpty() -> emptySet()
        else -> {
            possibleMoves.flatMap { 
                walk(it, map, pointsSeen+it)
            }.toSet()
        }
    }
}

fun getPossibleMoves(from: MapPoint, map: List<MapPoint>, pointsSeen: Set<MapPoint>): List<MapPoint> =
     map.filter{ from != it}.filter { p -> 
       from.isWalkable(p) && !pointsSeen.contains(p)
    }


fun part2() {
    val input = getInput()

    
    
}

fun getInput(): List<MapPoint> = File("day_10_input.txt").readLines()
    .flatMapIndexed { yidx, line -> 
        line.mapIndexed { xidx, elem -> 
            MapPoint(xidx, yidx, elem.digitToInt())
        }
    }

data class MapPoint(val x: Int, val y: Int, val score: Int) {
    fun isWalkable(other: MapPoint): Boolean = 
        (
            Math.abs(x - other.x) < 2 && y == other.y  ||
            x == other.x && Math.abs(y - other.y) < 2
        ) && other.score - score == 1
}