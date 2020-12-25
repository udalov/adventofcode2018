val dx = intArrayOf(1, 0, -1, 0)
val dy = intArrayOf(0, 1, 0, -1)

class Cart(var d: Int, var m: Int)

fun main() {
    val a = generateSequence { readLine()?.toCharArray() }.toList()
    val n = a.size
    val m = a[0].size
    var c = Array(n) { IntArray(m) { -1 } }
    val carts = mutableListOf<Cart>()
    for (i in 0 until n) {
        for (j in 0 until m) {
            val d = when (a[i][j]) {
                'v' -> {
                    a[i][j] = '|'
                    0
                }
                '>' -> {
                    a[i][j] = '-'
                    1
                }
                '^' -> {
                    a[i][j] = '|'
                    2
                }
                '<' -> {
                    a[i][j] = '-'
                    3
                }
                else -> -1
            }
            if (d != -1) {
                c[i][j] = carts.size
                carts.add(Cart(d, 1))
            }
        }
    }

    val moved = hashSetOf<Int>()
    for (tick in 1..Int.MAX_VALUE) {
        moved.clear()
        for (i in 0 until n) {
            for (j in 0 until m) {
                val id = c[i][j]
                if (id == -1 || id in moved) continue
                moved.add(id)
                val cart = carts[id]

                if (a[i][j] == '+') {
                    cart.d = (cart.d + cart.m) % 4
                    cart.m = when (val w = cart.m) {
                        3 -> 1
                        0 -> 3
                        1 -> 0
                        else -> throw AssertionError("$i $j $w")
                    }
                } else if (a[i][j] == '/') {
                    when (val w = cart.d) {
                        0 -> cart.d = 3
                        1 -> cart.d = 2
                        2 -> cart.d = 1
                        3 -> cart.d = 0
                        else -> throw AssertionError("$i $j $w")
                    }
                } else if (a[i][j] == '\\') {
                    when (val w = cart.d) {
                        0 -> cart.d = 1
                        1 -> cart.d = 0
                        2 -> cart.d = 3
                        3 -> cart.d = 2
                        else -> throw AssertionError("$i $j $w")
                    }
                }
                
                c[i][j] = -1

                val xx = i + dx[cart.d]
                val yy = j + dy[cart.d]
                if (c[xx][yy] != -1) {
                    println(tick)
                    println("$yy,$xx")
                    return
                }

                c[xx][yy] = id
            }
        }

        /*
        println("tick $tick")
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (c[i][j] != -1) {
                    println("cart ${c[i][j]} at $i $j (d=${carts[c[i][j]].d})")
                }
            }
        }
        println()
        */
    }
}
