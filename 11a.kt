fun main() {
    val serial = 1718
    val a = Array(300) { IntArray(300) }
    for (i in 0 until 300) {
        for (j in 0 until 300) {
            val x = i + 1
            val y = j + 1
            val id = x + 10
            a[i][j] = (((id * y + serial) * id) / 100) % 10 - 5
        }
    }

    var best = -1000000
    var ans = Pair(-1, -1)
    for (i in 1 until 299) {
        for (j in 1 until 299) {
            var cur = 0
            for (dx in -1..1) for (dy in -1..1) cur += a[i + dx][j + dy]
            if (cur > best) {
                best = cur
                ans = i to j
            }
        }
    }
    
    println(best)
    println("${ans.first},${ans.second}")
}
