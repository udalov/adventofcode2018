data class Point(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun dist(o: Point) = Math.abs(x - o.x) + Math.abs(y - o.y) + Math.abs(z - o.z) + Math.abs(w - o.w)
}

fun main() {
    val lines = generateSequence { readLine() }.toList()
    val a = mutableListOf<Point>()
    for (line in lines) {
        val (x, y, z, w) = line.split(",").map(String::toInt)
        val p = Point(x + 10, y + 10, z + 10, w + 10)
        a.add(p)
    }

    /*
    val m = 20
    val b = Array(m) { Array(m) { Array(m) { IntArray(m) } } }
    for ((x, y, z, w) in a) {
        b[x][y][z][w]++
    }
    */

    val n = a.size
    val t = IntArray(n) { it }
    for (i in 0 until n) {
        for (j in 0 until i) {
            if (a[i].dist(a[j]) <= 3) {
                val tj = t[j]
                for (k in 0 until n) if (t[k] == tj) {
                    t[k] = t[i]
                }
            }
        }
    }

    println(t.toSet().size)
}
