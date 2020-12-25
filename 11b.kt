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

    var s = Array(301) { IntArray(301) }
    for (i in 1..300) {
        for (j in 1..300) {
            s[i][j] = s[i - 1][j] + s[i][j - 1] - s[i - 1][j - 1] + a[i - 1][j - 1]
        }
    }

    var best = -1000000
    var ans = Triple(-1, -1, -1)
    for (i in 1..300) {
        for (j in 1..300) {
            for (k in 1 until Math.min(i, j)) {
                val cur = s[i][j] - s[i - k][j] - s[i][j - k] + s[i - k][j - k]
                if (cur > best) {
                    best = cur
                    ans = Triple(i - k + 1, j - k + 1, k)
                }
            }
        }
    }
    
    println(best)
    println("${ans.first},${ans.second},${ans.third}")
}
