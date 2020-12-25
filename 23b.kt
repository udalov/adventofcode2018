val dt = intArrayOf(-1, 0, 1)

data class Point(var x: Int, var y: Int, var z: Int) {
    fun dist(o: Point): Long = Math.abs(x - o.x).toLong() + Math.abs(y - o.y).toLong() + Math.abs(z - o.z).toLong()
}

fun main() {
    val lines = generateSequence { readLine() }.toList()
    val regex = "pos=<([-\\d]+),([-\\d]+),([-\\d]+)>, r=([-\\d]+)".toRegex()
    val a = arrayListOf<Pair<Point, Int>>()
    val xs = sortedSetOf<Int>()
    val ys = sortedSetOf<Int>()
    val zs = sortedSetOf<Int>()
    for (line in lines) {
        val (xx, yy, zz, rr) = regex.matchEntire(line)!!.destructured
        val p = Point(xx.toInt(), yy.toInt(), zz.toInt())
        val r = rr.toInt()
        a.add(p to r)
        for (d in dt) {
            xs.add(p.x + d * r)
            ys.add(p.y + d * r)
            zs.add(p.z + d * r)
        }
    }

    val mx = hashMapOf<Int, Int>()
    val my = hashMapOf<Int, Int>()
    val mz = hashMapOf<Int, Int>()
    val nx = hashMapOf<Int, Int>()
    val ny = hashMapOf<Int, Int>()
    val nz = hashMapOf<Int, Int>()
    var t = 0
    for (x in xs) {
        mx[x] = t
        nx[t] = x
        t++
    }
    t = 0
    for (y in ys) {
        my[y] = t
        ny[t] = y
        t++
    }
    t = 0
    for (z in zs) {
        mz[z] = t
        nz[t] = z
        t++
    }

    val O = Point(0, 0, 0)
    val D = 100

    var ans = 0L
    var best = 0
    var bestW: Point? = null
    var w = O.copy()
    ooo@for ((p, r) in a) {
        val (x, y, z) = p
        for (dx in dt) {
            for (dy in dt) {
                for (dz in dt) {
                    w.x = x + dx * r
                    w.y = y + dy * r
                    w.z = z + dz * r
                    val c = a.count { it.first.dist(w) <= it.second.toLong() }
                    if (c < 700) continue

                    val lx = mx[w.x]!!
                    val ly = my[w.y]!!
                    val lz = mz[w.z]!!
                    println(w)
                    for (dx in Math.max(-D, -lx)..Math.min(D, nx.size - lx - 1)) {
                        for (dy in Math.max(-D, -ly)..Math.min(D, ny.size - ly - 1)) {
                            for (dz in Math.max(-D, -lz)..Math.min(D, nz.size - lz - 1)) {
                                w.x = nx[lx + dx]!!
                                w.y = ny[ly + dy]!!
                                w.z = nz[lz + dz]!!
                                val cur = a.count { it.first.dist(w) <= it.second.toLong() }
                                if (cur > best || (cur == best && w.dist(O) < ans)) {
                                    best = cur
                                    ans = w.dist(O)
                                    bestW = w.copy()
                                    println("    $w: $cur")
                                }
                            }
                        }
                    }
                    break@ooo
                }
            }
        }
    }
    /*
    for ((p, r) in a) {
        val (x, y, z) = p
        for (dx in dt) {
            for (dy in dt) {
                for (dz in dt) {
                    w.x = x + dx * r
                    w.y = y + dy * r
                    w.z = z + dz * r
                    val cur = a.count { it.first.dist(w) <= it.second.toLong() }
                    // println("$w: $cur")
                    if (cur > best || (cur == best && w.dist(O) < ans)) {
                        best = cur
                        ans = w.dist(O)
                        bestW = w.copy()
                    }
                }
            }
        }
    }
    */

    println(ans)
    println(best)
    println(bestW)

    // 124623009 (884) Point(x=27138903, y=40826542, z=56657564)
    // 124623001 (906) Point(x=26562949, y=41396204, z=56663848)
}
