import java.io.File

fun main() {
    part1()
    part2()

}

fun part1() {
    val input = getInput()
    val antennas: List<Antenna> = getAntennas(input)

    val maxX = input[0].length
    val maxY = input.size

    val res = antennas.flatMap { antenna1 -> 
        // for each antenna, check with each other antenna 
        antennas
            .filter{it.frequency == antenna1.frequency && it != antenna1}
            .map { antenna2 -> 
                val deltaX = antenna2.x - antenna1.x
                val deltaY = antenna2.y - antenna1.y
                val antinode = Antinode(antenna2.x + deltaX, antenna2.y + deltaY)
                if (antinode.isValid(maxX, maxY)) {
                   antinode
                } else { 
                    null
                }
            }.filterNotNull()
    }
    println("Part1: ${res.toSet().size}")
}

fun part2() {
    val input = getInput()
    val antennas: List<Antenna> = getAntennas(input)

    val maxX = input[0].length
    val maxY = input.size

    val res = antennas.flatMap { antenna1 -> 
        // for each antenna, check with each other antenna 
        antennas
            .filter{it.frequency == antenna1.frequency && it != antenna1}
            .flatMap { antenna2 -> 
                val deltaX = antenna2.x - antenna1.x
                val deltaY = antenna2.y - antenna1.y

                var point = Antinode(antenna1.x + deltaX, antenna1.y + deltaY)
                val points = emptySet<Antinode>().toMutableSet()

                while (point.isValid(maxX, maxY)) {
                    points.add(point) 
                    point = Antinode(point.x + deltaX, point.y + deltaY)
                }
                points
            }
    }
    println("Part 2: ${res.toSet().size}")

}

fun getInput(): List<String> = File("day_8_input.txt")
    .readLines()

fun getAntennas(input: List<String>): List<Antenna> = input
    .flatMapIndexed { yidx, line -> 
        line.mapIndexed { xidx, char -> 
            if (char != '.') {
                Antenna(xidx, yidx, char)
            } else {
                null
            }
        }.filterNotNull()
    }

data class Antinode(val x: Int, val y: Int) {
    fun isValid(maxX: Int, maxY: Int): Boolean = x >= 0 && x < maxX && y >= 0 && y < maxY
}
data class Antenna(val x: Int, val y: Int, val frequency: Char)

