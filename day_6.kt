import java.io.File


const val GUARD_CHAR: Char = '^'
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
            if (xidx > 0) Coordinate(xidx, yidx, Direction.UP) else null
        }
        .filterNotNull()
        .first()
    val guard = Guard(coordinate, setOf(coordinate))

    val walkedGuard = walk(input, guard)
    println(walkedGuard.positionsSeen.size)

}

tailrec fun walk(input: List<List<Char>>, guard: Guard): Guard {
    
    val newCoordinate = when (guard.coordinate.direction) {
        Direction.UP -> Coordinate(guard.coordinate.x, guard.coordinate.y-1, Direction.UP)
        Direction.DOWN -> Coordinate(guard.coordinate.x, guard.coordinate.y+1, Direction.DOWN)
        Direction.LEFT -> Coordinate(guard.coordinate.x-1, guard.coordinate.y, Direction.LEFT)
        Direction.RIGHT -> Coordinate(guard.coordinate.x+1, guard.coordinate.y, Direction.RIGHT)
    }

    val nextChar = input.getOrNull(newCoordinate.y)?.getOrNull(newCoordinate.x)
    
    if (nextChar == null) {
        return guard
    }

    val newGuard = when {
        (nextChar == WALL_CHAR) -> guard.copy(coordinate = guard.coordinate.copy(direction = turn(guard.coordinate.direction)))
        else -> guard.copy(coordinate = newCoordinate, positionsSeen = guard.positionsSeen + newCoordinate)
    }
    return walk(input, newGuard)
}

fun turn(direction: Direction) : Direction = when (direction) {
    Direction.UP -> Direction.RIGHT
    Direction.RIGHT -> Direction.DOWN
    Direction.DOWN -> Direction.LEFT
    Direction.LEFT -> Direction.UP
}

fun part2() {
    val input:List<MutableList<Char>> = getInput()
    
    // Find guard position
    val coordinate = input
        .mapIndexed { yidx, line -> 
            val xidx = line.indexOf(GUARD_CHAR)
            if (xidx > 0) Coordinate(xidx, yidx, Direction.UP) else null
        }
        .filterNotNull()
        .first()
    val guard = Guard(coordinate, setOf(coordinate))

    // Find all possible coordinates this guard walks by. These are the only positions we need to place obsticles at
    val walkedGuard = walk(input, guard)

    // Ceate set of coordinates, ignoring direction (resetting all) 
    val uniquePositions = walkedGuard.positionsSeen.map { it.copy(direction = Direction.UP)}.toSet()

    // For each position we walked by, add an obstable and check if we get stuck
    val countStuck = uniquePositions.map{ (xidx, yidx, _) -> 
        val rightInFrontOfGuard = guard.coordinate.copy(y = guard.coordinate.y-1)

        if (xidx == rightInFrontOfGuard.x && yidx == rightInFrontOfGuard.y) {
            // We're right in front of the guard; can't place it here   
            println("Cannot put it at x $xidx, y $yidx with guard at $guard")
            false
        } else {
            input.get(yidx).set(xidx, WALL_CHAR)
            val guardGetsStuck = getsStuck(input, guard)
            // restore char
            input.get(yidx).set(xidx, '.')
            if (guardGetsStuck) {
                println("Guard gets stuck at x $xidx, y $yidx")
            }
            guardGetsStuck
        } 
    }.filter{ it}.count()
    

    println(countStuck)

}


tailrec fun getsStuck(input: List<List<Char>>, guard: Guard): Boolean {
    val newCoordinate = when (guard.coordinate.direction) {
        Direction.UP -> Coordinate(guard.coordinate.x, guard.coordinate.y-1, Direction.UP)
        Direction.DOWN -> Coordinate(guard.coordinate.x, guard.coordinate.y+1, Direction.DOWN)
        Direction.LEFT -> Coordinate(guard.coordinate.x-1, guard.coordinate.y, Direction.LEFT)
        Direction.RIGHT -> Coordinate(guard.coordinate.x+1, guard.coordinate.y, Direction.RIGHT)
    }

    val nextChar = input.getOrNull(newCoordinate.y)?.getOrNull(newCoordinate.x)
    
    if (nextChar == null) {
        // we got out, so not stuck!
        return false
    }
    if (guard.positionsSeen.contains(newCoordinate)) {
        // No wall in front of us, but we've seen this position before!
        return true
    }
    
    // We still in the area, and we haven't seen the new position before. Let's keep walking...
    val newGuard = when {
        (nextChar == WALL_CHAR) -> guard.copy(coordinate = guard.coordinate.copy(direction = turn(guard.coordinate.direction)))
        else -> guard.copy(coordinate = newCoordinate, positionsSeen = guard.positionsSeen + newCoordinate)
    }
    return getsStuck(input, newGuard)
}


fun getInput(): List<MutableList<Char>> = File("day_6_input.txt").readLines().map { it.toMutableList()}

data class Coordinate(val x: Int, val y: Int, val direction: Direction)
enum class Direction {
    UP, DOWN, LEFT, RIGHT
}
data class Guard (
    val coordinate: Coordinate, 
    val positionsSeen: Set<Coordinate>,
)
