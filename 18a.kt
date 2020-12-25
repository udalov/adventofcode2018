val dx = intArrayOf(1, 1, 0, -1, -1, -1, 0, 1)
val dy = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)

fun main() {
    var a = generateSequence { readLine()?.toCharArray() }.toList().toTypedArray()
    val n = a.size
    val m = a[0].size
    var b = Array(n) { CharArray(m) }
    for (iter in 1..10) {
        println(iter)
        for (i in 0 until n) println(a[i].joinToString(""))
        println()

        for (i in 0 until n) {
            for (j in 0 until m) {
                when (a[i][j]) {
                    '.' -> {
                        var t = 0
                        for (d in 0 until 8) {
                            val x = i + dx[d]
                            val y = j + dy[d]
                            if (x in a.indices && y in a[x].indices && a[x][y] == '|') t++
                        }
                        if (t >= 3) b[i][j] = '|' else b[i][j] = a[i][j]
                    }
                    '|' -> {
                        var t = 0
                        for (d in 0 until 8) {
                            val x = i + dx[d]
                            val y = j + dy[d]
                            if (x in a.indices && y in a[x].indices && a[x][y] == '#') t++
                        }
                        if (t >= 3) b[i][j] = '#' else b[i][j] = a[i][j]
                    }
                    '#' -> {
                        var l = false
                        var t = false
                        for (d in 0 until 8) {
                            val x = i + dx[d]
                            val y = j + dy[d]
                            if (x in a.indices && y in a[x].indices) {
                                if (a[x][y] == '#') l = true
                                if (a[x][y] == '|') t = true
                            }
                        }
                        if (l && t) b[i][j] = '#' else b[i][j] = '.'
                    }
                }
            }
        }

        var t = b
        b = a
        a = t
    }

    var t = 0
    var l = 0
    for (i in 0 until n) {
        for (j in 0 until m) {
            when (a[i][j]) {
                '|' -> t++
                '#' -> l++
            }
        }
    }

    println(t * l)
}
