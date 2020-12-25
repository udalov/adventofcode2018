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
            var sum = 0
            for ((xx, yy) in p) {
                sum += abs(x - xx) + abs(y - yy)
            }
            a[x][y] = sum
        }
    }

    val bad = hashSetOf(-1)
    for (i in 0 until 1000) {
        bad.add(a[i].first())
        bad.add(a[i].last())
        bad.add(a.first()[i])
        bad.add(a.last()[i])
    }

    var ans = 0
    for (x in 0 until 1000) {
        for (y in 0 until 1000) {
            if (a[x][y] !in bad && a[x][y] < 10000) {
                ans++
            }
        }
    }

    println(ans)
}
