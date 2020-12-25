const val mod = 20183

data class Point(val x: Int, val y: Int)

fun main() {
    val depth = 6969
    val target = Point(796, 9)

    /*
    val depth = 510
    val target = Point(10, 10)
    */

    val n = 1000
    val a = Array(n) { IntArray(n) }
    for (sum in 0..2*(n-1)) {
        for (i in 0..sum) {
            val j = sum - i
            if (i !in a.indices || j !in a[i].indices) continue

            when {
                i + j == 0 -> a[i][j] = 0
                i == target.x && j == target.y -> a[i][j] = 0
                i == 0 -> a[i][j] = (j * 16807) % mod
                j == 0 -> a[i][j] = (i * 48271) % mod
                else -> a[i][j] = (a[i - 1][j] * a[i][j - 1]) % mod
            }
            a[i][j] = (a[i][j] + depth) % mod
        }
    }

    val b = Array(n) { IntArray(n) }
    for (i in 0 until n) {
        for (j in 0 until n) {
            b[i][j] = a[i][j] % 3
        }
    }

    var ans = 0
    for (i in 0..target.x) {
        for (j in 0..target.y) {
            ans += b[i][j].also { if (it < 0) error(it) }
            // print(".=|"[b[i][j]])
        }
        // println()
    }

    println(ans)
}
