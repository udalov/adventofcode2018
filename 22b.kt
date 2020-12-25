val dx = intArrayOf(1, 0, -1, 0)
val dy = intArrayOf(0, 1, 0, -1)

const val mod = 20183

data class Point(val x: Int, val y: Int)

// 0 -- torch
// 1 -- climbing gear
// 2 -- neither
fun enc(x: Int, y: Int, z: Int) =
    (x shl 12) + (y shl 2) + z

fun dec(w: Int) =
    arrayOf(w shr 12, (w shr 2) and 1023, w and 3)

fun ok(terrain: Int, tool: Int) = when (terrain) {
    0 -> tool != 2
    1 -> tool != 0
    2 -> tool != 1
    else -> error("$terrain $tool")
}

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

    val q = sortedSetOf<Long>()
    val d = mutableMapOf<Int, Int>()
    val start = enc(0, 0, 0)
    d[start] = 0
    q.add(start + (0L shl 22))
    while (q.isNotEmpty()) {
        val wt = q.first()
        q.remove(wt)
        val w = (wt and ((1 shl 22) - 1)).toInt()
        val (x, y, z) = dec(w)
        if (x == target.x && y == target.y && z == 0) {
            println(d[w])
            break
        }

        for (dd in 0 until 4) {
            val xx = x + dx[dd]
            val yy = y + dy[dd]
            if (xx in 0 until n && yy in 0 until n && ok(b[xx][yy], z)) {
                val ww = enc(xx, yy, z)
                val nd = d[w]!! + 1
                if (ww in d && d[ww]!! > nd) {
                    q.remove(ww + (d[ww]!!.toLong() shl 22))
                    d.remove(ww)
                }
                if (ww !in d) {
                    d[ww] = nd
                    q.add(ww + (nd.toLong() shl 22))
                }
            }
        }

        for (t in 0 until 3) if (t != z && ok(b[x][y], t)) {
            val ww = enc(x, y, t)
            val nd = d[w]!! + 7
            if (ww in d && d[ww]!! > nd) {
                q.remove(ww + (d[ww]!!.toLong() shl 22))
                d.remove(ww)
            }
            if (ww !in d) {
                d[ww] = nd
                q.add(ww + (nd.toLong() shl 22))
            }
        }
    }
}
