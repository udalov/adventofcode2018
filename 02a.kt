fun main() {
    var a2 = 0
    var a3 = 0
    while (true) {
        val line = readLine() ?: break
        val m = hashMapOf<Char, Int>()
        for (c in line) m[c] = (m[c] ?: 0) + 1
        var b2 = false
        var b3 = false
        for ((k, v) in m) {
            if (v == 2) b2 = true
            if (v == 3) b3 = true
        }
        if (b2) a2++
        if (b3) a3++
    }
    println(a2 * a3)
}
