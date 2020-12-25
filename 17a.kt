val n = 1796
val m = 2000
val a = Array(n) { IntArray(m) }

fun go(x: Int, y: Int) {
    a[x][y] = 2
    if (x == a.lastIndex) {
        return
    }
    if (a[x + 1][y] == 2) {
        return
    }
    if (a[x + 1][y] == 0) {
        go(x + 1, y)
    }
    if (a[x + 1][y] == 1) {
        var y1 = y
        while (true) {
            if (a[x][y1 - 1] == 1) break
            if (a[x + 1][y1] == 0) {
                go(x + 1, y1)
            }
            if (a[x + 1][y1] != 1) break
            y1--
        }
        var y2 = y
        while (true) {
            if (a[x][y2 + 1] == 1) break
            if (a[x + 1][y2] == 0) {
                go(x + 1, y2)
            }
            if (a[x + 1][y2] != 1) break
            y2++
        }
        if (a[x + 1][y1] == 1 && a[x + 1][y2] == 1) {
            for (yy in y1..y2) {
                a[x][yy] = 1
            }
        } else {
            for (yy in y1..y2) {
                a[x][yy] = 2
            }
            if (a[x + 1][y1] != 1) go(x + 1, y1)
            if (a[x + 1][y2] != 1) go(x + 1, y2)
        }
    }
}

fun main() {
    val lines = generateSequence { readLine() }.toList()
    val regex = "([xy])=(\\d+), [xy]=(\\d+)\\.\\.(\\d+)".toRegex()
    var min = 1000000
    var max = 0
    for (line in lines) {
        val (xy, s1, s2, s3) = regex.matchEntire(line)!!.destructured
        val a1 = s1.toInt()
        val a2 = s2.toInt()
        val a3 = s3.toInt()
        if (xy == "x") {
            for (i in a2..a3) a[i][a1] = 1
            min = minOf(min, a2)
            max = maxOf(max, a3)
        } else {
            for (j in a2..a3) a[a1][j] = 1
            min = minOf(min, a1)
            max = maxOf(max, a1)
        }
    }
    val b = Array(n) { IntArray(m) }
    for (i in 0 until n) for (j in 0 until m) b[i][j] = a[i][j]
    val was = a.indices.sumBy { i -> if (i in min..max) a[i].indices.sumBy { j -> if (a[i][j] != 0) 1 else 0 } else 0 }

    go(0, 500)

    for (y in 0 until minOf(400, n)) {
        for (x in 400..600) {
            print(when (a[y][x]) {
                0 -> '.'
                1 -> if (a[y][x] == b[y][x]) '#' else '~'
                2 -> '|'
                else -> error(a[y][x])
            })
        }
        println()
    }

    val now = a.indices.sumBy { i -> if (i in min..max) a[i].indices.sumBy { j -> if (a[i][j] != 0) 1 else 0 } else 0 }
    println(now - was)
}
