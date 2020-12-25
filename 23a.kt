data class Point(val x: Int, val y: Int, val z: Int) {
    fun dist(o: Point): Long = Math.abs(x - o.x).toLong() + Math.abs(y - o.y).toLong() + Math.abs(z - o.z).toLong()
}

fun main() {
    val lines = generateSequence { readLine() }.toList()
    val regex = "pos=<([-\\d]+),([-\\d]+),([-\\d]+)>, r=([-\\d]+)".toRegex()
    val a = arrayListOf<Pair<Point, Int>>()
    for (line in lines) {
        val (x, y, z, r) = regex.matchEntire(line)!!.destructured
        a.add(Point(x.toInt(), y.toInt(), z.toInt()) to r.toInt())
    }

    val m = a.maxBy { it.second }!!
    println(a.count { it.first.dist(m.first) <= m.second.toLong() })
}
