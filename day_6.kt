import java.io.File


const val GUARD_CHAR: Char = '^'
//const val GUARD_CHARS: Set<Char> = setOf('^', '>', '<', 'v')
const val WALL_CHAR: Char = '#'

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = getInput()
    // Find guard position
    val coordinate = input
        .mapIndexed { yidx, line -> 
            val xidx = line.indexOf(GUARD_CHAR)
            if (xidx > 0) Coordinate(xidx, yidx) else null
        }
        .filterNotNull()
        .first()
    val guard = Guard(coordinate, Direction.UP, setOf(coordinate))

    val walkedGuard = walk(input, guard)
    println(walkedGuard.positionsSeen.size)

}

tailrec fun walk(input: List<List<Char>>, guard: Guard): Guard {
    
    val newCoordinate = when (guard.direction) {
        Direction.UP -> Coordinate(guard.coordinate.x, guard.coordinate.y-1)
        Direction.DOWN -> Coordinate(guard.coordinate.x, guard.coordinate.y+1) 
        Direction.LEFT -> Coordinate(guard.coordinate.x-1, guard.coordinate.y)
        Direction.RIGHT -> Coordinate(guard.coordinate.x+1, guard.coordinate.y)
    }

    val nextChar = input.getOrNull(newCoordinate.y)?.getOrNull(newCoordinate.x)
    
    if (nextChar == null) {
        return guard
    }

    val newGuard = when {
        (nextChar == WALL_CHAR) -> guard.copy(direction = turn(guard.direction))
        else -> guard.copy(coordinate = newCoordinate, positionsSeen = guard.positionsSeen + newCoordinate)
    }
    println("Walking to ${guard.coordinate} facing ${guard.direction}")
    return walk(input, newGuard)
}

fun turn(direction: Direction) : Direction = when (direction) {
    Direction.UP -> Direction.RIGHT
    Direction.RIGHT -> Direction.DOWN
    Direction.DOWN -> Direction.LEFT
    Direction.LEFT -> Direction.UP
}

fun part2() {
    val input = getInput()
    
}

fun getInput(): List<List<Char>> = File("day_6_input.txt").readLines().map { it.toList()}



data class Coordinate(val x: Int, val y: Int)
enum class Direction {
    UP, DOWN, LEFT, RIGHT
}
data class Guard (
    val coordinate: Coordinate, 
    val direction: Direction,
    val positionsSeen: Set<Coordinate>,
)
