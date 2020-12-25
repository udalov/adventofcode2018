import kotlin.math.*

fun main() {
    val lines = generateSequence { readLine() }.toList()
    val p = mutableListOf<Pair<Int, Int>>()
    for (line in lines) {
        val (x, y) = line.split(", ").map(String::toInt)
        p.add(Pair(x + 250, y + 250))
    }

    val a = Array(1000) { IntArray(1000) }
    for (x in 0 until 1000) {
        for (y in 0 until 1000) {
            var ans = Int.MAX_VALUE
            var best = -1
            for ((index, z) in p.withIndex()) {
                val (xx, yy) = z
                val cur = abs(x - xx) + abs(y - yy)
                if (cur < ans) {
                    ans = cur
                    best = index + 1
                } else if (cur == ans) {
                    best = -1
                }
            }
            a[x][y] = best
        }
    }

    val bad = hashSetOf(-1)
    for (i in 0 until 1000) {
        bad.add(a[i].first())
        bad.add(a[i].last())
        bad.add(a.first()[i])
        bad.add(a.last()[i])
    }

    val m = hashMapOf<Int, Int>()
    for (x in 0 until 1000) {
        for (y in 0 until 1000) {
            if (a[x][y] !in bad) {
                m[a[x][y]] = (m[a[x][y]] ?: 0) + 1
            }
        }
    }

    println(m.values.max())
}
