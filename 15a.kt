val dx = intArrayOf(1, 0, -1, 0)
val dy = intArrayOf(0, 1, 0, -1)
val readingOrder = intArrayOf(2, 3, 1, 0)
val inf = Int.MAX_VALUE / 2

class Creature(val type: Int, var health: Int) {
    val isElf: Boolean get() = type == 1

    override fun toString(): String =
        (if (isElf) "E" else "G") + "($health)"
}

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    fun go(d: Int): Point = Point(x + dx[d], y + dy[d])

    fun distance(other: Point): Int = Math.abs(x - other.x) + Math.abs(y - other.y)

    override fun compareTo(other: Point): Int =
        (x - other.x).takeIf { it != 0 } ?:
        (y - other.y).takeIf { it != 0 } ?: 0

    override fun toString(): String =
        "($x, $y)"
}

operator fun Array<IntArray>.get(p: Point): Int? =
    this.getOrNull(p.x)?.getOrNull(p.y)

operator fun Array<IntArray>.set(p: Point, value: Int) {
    this[p.x][p.y] = value
}

fun main() {
    val lines = generateSequence { readLine()?.substringBefore(" ") }.toList()
    val n = lines.size
    val m = lines[0].length
    val a = Array(n) { IntArray(m) }
    val c = mutableListOf(Creature(-1, -1))
    for (i in 0 until n) {
        for (j in 0 until m) {
            a[i][j] = when (val char = lines[i][j]) {
                '#' -> -1
                '.' -> 0
                'E', 'G' -> {
                    c.add(Creature(if (char == 'E') 1 else 0, 200))
                    c.lastIndex
                }
                else -> throw AssertionError(lines[i][j].toString())
            }
        }
    }

    val moved = hashSetOf<Int>()
    val targets = mutableListOf<Point>()
    val adjacent = sortedSetOf<Point>()
    val queue = mutableListOf<Point>()
    val dist = Array(n) { IntArray(m) }
    for (iter in 0 until Int.MAX_VALUE) {
        moved.clear()

        println("After $iter round(s):")
        for (i in 0 until n) {
            val d = mutableListOf<Creature>()
            for (j in 0 until m) {
                print(when (a[i][j]) {
                    -1 -> '#'
                    0 -> '.'
                    else -> {
                        d.add(c[a[i][j]])
                        if (c[a[i][j]].type == 1) 'E' else 'G'
                    }
                })
            }
            println("  ${d.joinToString()}")
        }
        println()

        for (i in 0 until n) {
            for (j in 0 until m) {
                val id = a[i][j]
                if (id < 1 || !moved.add(id)) continue

                targets.clear()
                for (x in 0 until n) {
                    for (y in 0 until m) {
                        if (a[x][y] >= 1 && c[a[x][y]].isElf != c[id].isElf) {
                            targets.add(Point(x, y))
                        }
                    }
                }
                if (targets.isEmpty()) {
                    val sum = a.sumBy { row ->
                        row.sumBy { w ->
                            if (w >= 1) c[w].health else 0
                        }
                    }
                    println(iter.toLong() * sum)
                    return
                }

                adjacent.clear()
                for (target in targets) {
                    for (d in 0 until 4) {
                        val p = target.go(d)
                        if (a[p] == 0) {
                            adjacent.add(p)
                        }
                    }
                }

                var pos = Point(i, j)
                if (targets.none { it.distance(pos) == 1 }) {
                    queue.clear()
                    var qb = 0
                    queue.add(pos)
                    dist.forEach { it.fill(inf) }
                    dist[pos] = 0
                    var minDist = inf
                    while (qb < queue.size) {
                        val z = queue[qb++]
                        if (z in adjacent) {
                            minDist = minOf(minDist, dist[z]!!)
                        }
                        for (d in 0 until 4) {
                            val p = z.go(d)
                            if (a[p] == 0 && dist[p] == inf) {
                                dist[p] = dist[z]!! + 1
                                queue.add(p)
                            }
                        }
                    }

                    if (minDist == inf) continue
                    val chosen = adjacent.firstOrNull { p -> dist[p] == minDist } ?: continue
                    queue.clear()
                    qb = 0
                    queue.add(chosen)
                    dist.forEach { it.fill(inf) }
                    dist[chosen] = 0
                    while (qb < queue.size) {
                        val z = queue[qb++]
                        for (d in 0 until 4) {
                            val p = z.go(d)
                            if (a[p] == 0 && dist[p] == inf) {
                                dist[p] = dist[z]!! + 1
                                queue.add(p)
                            }
                        }
                    }

                    val step = pos.go(readingOrder.firstOrNull { d ->
                        dist[pos.go(d)] == minDist - 1
                    } ?: error("No best d for $pos -> $chosen, id=$id"))
                    a[step] = id
                    a[pos] = 0
                    pos = step
                }

                val minHealth = (0 until 4).mapNotNull { d ->
                    a[pos.go(d)]?.takeIf { it >= 1 && c[it].isElf != c[id].isElf }?.let { c[it].health }
                }.min() ?: continue

                val attack = readingOrder.first { d ->
                    a[pos.go(d)]?.takeIf { it >= 1 && c[it].isElf != c[id].isElf }?.let { c[it].health } == minHealth
                }.let { pos.go(it) }
                c[a[attack]!!].health -= 3
                if (c[a[attack]!!].health <= 0) {
                    a[attack] = 0
                }
            }
        }
    }
}
