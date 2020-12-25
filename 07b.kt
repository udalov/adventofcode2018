fun main() {
    val lines = generateSequence { readLine() }.toList()
    val g = mutableMapOf<Char, MutableList<Char>>()
    for (c in 'A'..'Z') g[c] = arrayListOf()
    val was = sortedSetOf<Char>()
    for (line in lines) {
        val ss = line.split(" ")
        val from = ss[1][0]
        val to = ss[7][0]
        was.add(from)
        was.add(to)
        g[from]!!.add(to)
    }

    val n = 8
    val a = arrayListOf<Int>()
    val b = arrayListOf<Char>()
    repeat(n) { a.add(-1); b.add('#') }
    for (t in 0..Int.MAX_VALUE) {
        for (index in a.indices) {
            if (a[index] == t) {
                a[index] = -1
                was.remove(b[index])
                g[b[index]] = mutableListOf()
            }
        }
        if (a.all { it == -1 } && g.values.all { it.isEmpty() } && was.isEmpty()) {
            println(t)
            break
        }
        if (a.none { it == -1 }) continue
        for (index in a.indices) {
            if (a[index] != -1) continue
            val x = ('A'..'Z').firstOrNull { i ->
                (g[i]!!.isNotEmpty() || i in was) && ('A'..'Z').none { j -> i in g[j]!! } && b.none { it == i }
            } ?: break
            a[index] = t + (x - 'A') + 61
            b[index] = x
        }
    }
}
