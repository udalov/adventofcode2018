val dir = "SENW"
val dx = intArrayOf(1, 0, -1, 0)
val dy = intArrayOf(0, 1, 0, -1)

data class Point(val x: Int, val y: Int)

val a = hashMapOf<Point, Int>()

fun go(s: String, xx: Int, yy: Int, ii: Int, n: Int) {
    var i = ii
    var balance = 0
    var x = xx
    var y = yy
    while (i < n) {
        val d = dir.indexOf(s[i])
        if (d != -1) {
            val z = Point(x, y)
            a[z] = (a[z] ?: 0) or (1 shl d)
            x += dx[d]
            y += dy[d]
            val w = Point(x, y)
            a[w] = (a[w] ?: 0) or (1 shl ((d + 2) % 4))
            i++
            continue
        }

        if (s[i] != '(') throw AssertionError(s[i])
        var p = ++i
        var j = i
        while (true) {
            if (balance == 0) {
                if (s[j] == '|') {
                    go(s, x, y, p, j)
                    p = ++j
                    continue
                } else if (s[j] == ')') {
                    go(s, x, y, p, j)
                    j++
                    break
                }
            }
            when (s[j]) {
                '(' -> balance++
                ')' -> balance--
            }
            j++
        }
        i = j
    }
}

fun main() {
    val s = readLine()!!
    go(s, 0, 0, 0, s.length)

    /*
    for ((k, v) in a) {
        for (d in 0 until 4) if ((v and (1 shl d)) != 0) println("$k ${dir[d]}")
    }
    */

    val q = arrayListOf<Point>()
    val d = hashMapOf<Point, Int>()
    val start = Point(0, 0)
    d[start] = 0
    q.add(start)
    var qb = 0
    while (qb < q.size) {
        val z = q[qb++]
        val (x, y) = z
        for (dd in 0 until 4) {
            if ((a[z]!! and (1 shl dd)) == 0) continue

            val xx = x + dx[dd]
            val yy = y + dy[dd]
            val w = Point(xx, yy)
            if (w in d) continue

            d[w] = d[z]!! + 1
            q.add(w)
        }
    }

    println(d.values.max())
}
