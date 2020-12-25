data class Claim(
    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int
)

fun main() {
    val regex = ".*@ (\\d+),(\\d+): (\\d+)x(\\d+)".toRegex()
    val claims = generateSequence { readLine() }.map { line ->
        val (xs, ys, ws, hs) = regex.matchEntire(line)!!.destructured
        val x = xs.toInt()
        val y = ys.toInt()
        val w = ws.toInt()
        val h = hs.toInt()
        Claim(x, y, x + w, y + h)
    }.toList()

    val a = Array(1000) { IntArray(1000) }

    for ((x1, y1, x2, y2) in claims) {
        for (x in x1 until x2) {
            for (y in y1 until y2) {
                a[x][y]++
            }
        }
    }

    val ans = a.sumBy { row ->
        row.sumBy { if (it > 1) 1 else 0 }
    }
    println(ans)
}
